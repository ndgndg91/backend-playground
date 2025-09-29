# 차세대 포인트 시스템 아키텍처 제안 (CQRS & Event Sourcing)

## 1. 문제 정의

현재 아키텍처는 소액 포인트(일명 '짤짤이')가 대량으로 지급될 경우, 포인트 사용(차감) 시점에 수많은 레코드를 처리해야 하므로 성능 부하가 발생하는 구조적 한계를 가지고 있습니다.

본 문서는 이 문제를 근본적으로 해결하고, **압도적인 성능**, **확장성**, 그리고 **완벽한 데이터 무결성**을 모두 만족시키는 차세대 아키텍처를 제안합니다.

## 2. 핵심 컨셉: CQRS (Command Query Responsibility Segregation)

CQRS는 **데이터를 변경하는 책임(Command)**과 **데이터를 조회하는 책임(Query)**을 명확히 분리하는 아키텍처 패턴입니다.

*   **Command (쓰기 모델)**: 데이터의 무결성과 일관성이 가장 중요합니다. 모든 변경 내역을 영구적으로 기록하는 데 최적화됩니다.
*   **Query (읽기 모델)**: 응답 속도가 가장 중요합니다. 데이터를 빠르고 효율적으로 조회하는 데 최적화됩니다.

이 두 책임을 분리함으로써 각 목적에 가장 적합한 데이터베이스와 데이터 모델을 독립적으로 사용할 수 있습니다.

## 3. 제안 아키텍처

![CQRS Flow](https://storage.googleapis.com/gemini-repo/20240523/225807_9a37c44a_cqrs_flow.png)

### 3.1. Write Model (명령 모델): 불변의 이벤트 저장소

모든 포인트 변경의 원본 데이터를 기록하는 시스템의 유일한 **진실의 원천(Source of Truth)**입니다.

*   **기술**: RDBMS (**MySQL**). 트랜잭션과 데이터 무결성 보장에 가장 신뢰도가 높습니다.
*   **패턴**: **이벤트 소싱 (Event Sourcing)**. 모든 변경을 '발생한 사건(Event)'으로 간주하여, 추가만 가능한(Append-Only) 로그 형태로 저장합니다. `UPDATE`나 `DELETE`가 절대 일어나지 않아 데이터의 이력을 완벽하게 보존합니다.
*   **테이블 예시 (`point_events`)**:
    ```sql
    CREATE TABLE `point_events` (
      `event_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
      `account_id` INT UNSIGNED NOT NULL,
      `event_type` VARCHAR(20) NOT NULL COMMENT 'GRANT, EXCHANGE, EXPIRE 등',
      `payload` JSON NOT NULL COMMENT '이벤트 상세 데이터',
      `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
      PRIMARY KEY (`event_id`),
      KEY `idx_account_id_created_at` (`account_id`, `created_at`)
    ) ENGINE=InnoDB;
    ```

### 3.2. Read Model (조회 모델): 실시간 조회를 위한 고성능 저장소

실제 사용자의 포인트 잔액을 조회하고, 차감 연산을 초고속으로 처리하는 데 특화된 모델입니다.

*   **기술**: In-Memory Database (**Redis**). 메모리 기반으로 동작하여 디스크 I/O가 없으므로 월등히 빠릅니다.
*   **데이터 구조**: 사용자별 **Sorted Set**
    *   **Key**: `point_balance:{accountId}`
    *   **Score**: `expires_at` (만료일의 Unix Timestamp). 만료 임박 순으로 자동 정렬됩니다.
    *   **Value**: `{grantId}:{poolId}:{amount}` 형태의 문자열. 지급 건의 고유 정보 저장.

### 3.3. Projector (프로젝터): 두 모델을 잇는 동기화 파이프라인

Write Model에 기록된 이벤트를 Read Model에 반영하여 데이터를 동기화합니다.

*   **기술**: 메시지 큐 (**Kafka**). 비동기 처리를 통해 시스템 간의 결합도를 낮추고 안정적인 데이터 전달을 보장합니다.
*   **동작 흐름**:
    1.  포인트 지급 API가 Write Model(MySQL)에 `PointGranted` 이벤트를 기록합니다.
    2.  동시에 해당 이벤트를 Kafka 토픽으로 발행(Publish)합니다.
    3.  별도의 Consumer(예: `point-event-consumer`)가 이벤트를 수신(Consume)합니다.
    4.  Consumer는 이벤트 데이터를 기반으로 Read Model(Redis)의 Sorted Set에 데이터를 추가(`ZADD`)합니다.

## 4. 포인트 사용(차감) 흐름 예시

1.  사용자가 API를 통해 포인트 사용을 요청합니다.
2.  API 서버는 **Read Model(Redis)**의 Sorted Set에서 만료일이 가장 빠른 순으로 포인트를 조회합니다. (`ZRANGE` - 매우 빠름)
3.  차감할 지급 내역(`grantId`)들을 결정하고, `PointExchanged` 이벤트를 생성합니다.
4.  생성된 이벤트를 **Write Model(MySQL)에 기록**하고, **Kafka로 발행**합니다.
5.  Consumer가 `PointExchanged` 이벤트를 수신하여 **Read Model(Redis)에서 사용된 지급 내역들을 제거**합니다. (`ZREM` - 매우 빠름)

## 5. 기대 효과

*   **압도적인 성능**: 포인트 사용 시 모든 연산이 Redis(In-Memory)에서 처리되어, '짤짤이' 지급 건수가 아무리 많아도 응답 속도가 거의 저하되지 않습니다.
*   **완벽한 감사 추적**: 모든 변경 내역은 MySQL에 불변의 이벤트 로그로 남기 때문에, 데이터 정합성을 100% 신뢰할 수 있으며 언제든 전체 이력을 추적할 수 있습니다.
*   **높은 탄력성과 복원력**: Read Model(Redis)의 데이터가 유실되더라도, Write Model(MySQL)의 이벤트 로그를 처음부터 다시 읽어(Replay) 언제든지 복원할 수 있습니다.
*   **독립적인 확장성**: API, 데이터베이스, Consumer 등 각 구성 요소를 독립적으로 확장할 수 있어 대규모 트래픽에 유연하게 대응할 수 있습니다.