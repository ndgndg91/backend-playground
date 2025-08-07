-- 최종 포인트 시스템 테이블 스키마 (2025-08-04 기준)

-- 1. point_pool (포인트 풀 정보)
-- 포인트 지급의 근원이 되는 정보.
CREATE TABLE `point_pool`
(
    `pool_id`          INT UNSIGNED PRIMARY KEY COMMENT '풀 ID',
    `account_id`       INT UNSIGNED NOT NULL COMMENT '매핑된 내부 업무용 계정 ID',
    `total_budget`     BIGINT UNSIGNED NOT NULL COMMENT '풀에 집행금',
    `start_datetime`   DATETIME NOT NULL COMMENT '포인트 지급 가능 시작일시',
    `end_datetime`     DATETIME NOT NULL COMMENT '포인트 지급 가능 종료일시',
    `point_valid_days` INT      NOT NULL COMMENT '포인트 소멸 기한 (일 단위)',
    `status`           TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1:진행 중, 10:종료',
    `updated_at`       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '포인트 지급 시 사용되는 meta 테이블';



-- 2. point_grant_balance (지급 트랜잭션별 사용 가능 잔액)
-- FEFO (First Expired First Out) 원칙에 따라 포인트 사용/회수/소멸 대상을 빠르게 찾기 위한 보조 테이블.
-- 지급 시 INSERT
-- 소멸 시 DELETE
-- 사용 시 UPDATE or DELETE
-- 회수 시 UPDATE or DELETE
-- 일별 내 소멸 포인트 조회 가능
CREATE TABLE `point_grant_balance`
(
    `grant_transaction_id` BIGINT UNSIGNED PRIMARY KEY COMMENT '원본 지급 트랜잭션 ID (FK: point_transaction.id)',
    `account_id`           INT UNSIGNED NOT NULL COMMENT '사용자 ID',
    `remaining_amount`     BIGINT UNSIGNED NOT NULL COMMENT '현재 남은 포인트 잔액 (이 지급 건에 한정)',
    `expires_at`           DATETIME NULL COMMENT '원본 지급 트랜잭션의 소멸 예정일 (NULL은 영구 포인트)',
    `created_at`           DATETIME NOT NULL COMMENT '원본 지급 트랜잭션의 생성 시각',
    INDEX                  `idx_account_id_expires_at_created_at` (`account_id`, `expires_at`, `created_at`), -- 먼저 소멸되는 포인트를 조회 핵심 복합 인덱스
) COMMENT '지급 포인트의 잔액 관리 테이블 (FEFO 처리 최적화용)';



-- 3. point_transaction (포인트 원장)
-- 모든 포인트의 지급/사용/소멸/회수 기록을 불변(immutable)하게 저장하는 핵심 원장 테이블.
CREATE TABLE `point_transaction`
(
    `id`                    BIGINT UNSIGNED AUTO_INCREMENT COMMENT '트랜잭션 ID',
    `account_id`            INT UNSIGNED NOT NULL COMMENT '사용자 ID',
    `pool_id`               INT UNSIGNED NOT NULL COMMENT '풀 ID',
    `transaction_type`      TINYINT UNSIGNED NOT NULL COMMENT '거래 타입 (1:GRANT, 2:EXCHANGE_KRW, 20:EXPIRE, 30:RECLAIM, 40:COMPACTION_DEBIT, 41:COMPACTION_CREDIT)',
    `amount`                BIGINT      NOT NULL COMMENT '변동 금액 (지급: 양수, 차감: 음수)',
    `source_transaction_id` BIGINT UNSIGNED NULL COMMENT '차감 트랜잭션의 원본 지급 트랜잭션 ID',
    `exchange_id`           BIGINT UNSIGNED NULL COMMENT '원화 교환 요청 ID (point_exchange_history.exchange_id)',
    `request_id`            CHAR(36)    NOT NULL COMMENT '계정 간 자산 변동 추적 식별자',
    `reason`                VARCHAR(50) NOT NULL COMMENT '지급/사용/소멸/회수 등의 사유',
    `expires_at`            DATETIME NULL COMMENT '포인트 소멸 예정일 (지급일 경우만 유효)',
    `created_at`            DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '거래 발생 시각',
    PRIMARY KEY (`id`),
    INDEX                   `idx_account_id_created_at` (`account_id`, `created_at`),
    INDEX                   `idx_pool_id` (`pool_id`),
    INDEX                   `idx_request_id` (`request_id`),
    INDEX                   `idx_source_tx` (`source_transaction_id`), -- 소멸 포인트 대상 조회 쿼리에서 사용
) COMMENT '포인트 원장 테이블'



-- 4. point_exchange_history (포인트 원화 교환 상세)
-- 포인트의 원화 교환 프로세스에 대한 상세 정보 및 상태 변화를 추적.
CREATE TABLE `point_exchange_history`
(
    `exchange_id`         BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '교환 ID',
    `account_id`          INT UNSIGNED NOT NULL COMMENT '사용자 ID',
    `exchange_amount`     INT         NOT NULL COMMENT '교환 신청 포인트 금액',
    `received_krw_amount` INT         NOT NULL COMMENT '실제 지급된 원화 금액 (수수료 제외)',
    `commission_amount`   INT         NOT NULL COMMENT '수수료 금액',
    `exchange_status`     VARCHAR(50) NOT NULL COMMENT '교환 상태 (REQUESTED, COMPLETED, FAILED)',
    `request_at`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '교환 신청 시각',
    `completed_at`        DATETIME NULL COMMENT '교환 완료 시각',
    INDEX                 `idx_account_id` (`account_id`),
    INDEX                 `idx_request_at` (`request_at`)
) COMMENT '포인트 원화 교환 상세 내역';



-- 5. daily_pool_point_summary (일별/풀별 집계)
-- 각 풀의 일별 현황을 저장.
CREATE TABLE `daily_pool_point_summary`
(
    `summary_date`            DATE NOT NULL COMMENT '집계 기준 날짜',
    `pool_id`                 INT UNSIGNED NOT NULL COMMENT '풀 ID',
    `daily_granted_points`    BIGINT UNSIGNED DEFAULT 0 COMMENT '일별 지급된 포인트',
    `daily_expired_points`    BIGINT UNSIGNED DEFAULT 0 COMMENT '일별 소멸된 포인트',
    `daily_reclaimed_points`  BIGINT UNSIGNED DEFAULT 0 COMMENT '일별 회수된 포인트',
    `daily_exchanged_points`  BIGINT UNSIGNED DEFAULT 0 COMMENT '일별 원화로 교환된 포인트 (차감 기준)',
    `daily_commission_amount` BIGINT UNSIGNED DEFAULT 0 COMMENT '일별 발생한 원화 교환 수수료',
    `updated_at`              DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 업데이트 시각',
    `created_at`              DATETIME DEFAULT CURRENT_TIMESTAMP
    PRIMARY KEY (`summary_date`, `pool_id`),
    FOREIGN KEY (`pool_id`) REFERENCES `point_pool` (`pool_id`)
) COMMENT '일별/풀별 집계 현황';



-- 6. daily_pool_point_expiration_summary (일별/풀별 소멸 예정 포인트 집계)
-- 풀별로 소멸 예정 포인트를 집계하도록 구조 변경하여 활용성 증대.
CREATE TABLE `daily_pool_point_expiration_summary`
(
    `expiration_date`  DATE NOT NULL COMMENT '소멸 예정일',
    `pool_id`          INT UNSIGNED NOT NULL COMMENT '풀 ID',
    `scheduled_amount` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '해당 일에 소멸 예정인 포인트 금액',
    `updated_at`       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 업데이트 시각',
    `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP
    PRIMARY KEY (`expiration_date`, `pool_id`),
    FOREIGN KEY (`pool_id`) REFERENCES `point_pool` (`pool_id`)
) COMMENT '일별/풀별 소멸 예정 포인트 집계';