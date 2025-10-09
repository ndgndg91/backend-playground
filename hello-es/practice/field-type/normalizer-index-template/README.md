## 1. 노멀라이저 (Normalizer): Keyword 필드 전용 분석기
**노멀라이저(Normalizer)** 는 keyword 필드에만 적용할 수 있는 단순화된 분석기입니다. keyword 필드는 텍스트를 토큰으로 쪼개지 않고 통째로 다루는데, 이때 대소문자를 통일하거나 특정 문자를 정리하는 등의 "정규화" 과정을 거치고 싶을 때 사용합니다.

분석기(Analyzer)와의 가장 큰 차이점은 토크나이저(Tokenizer)의 유무입니다.

### 분석기(Analyzer) vs. 노멀라이저(Normalizer) 비교

| 구분           | 분석기 (Analyzer)              | 노멀라이저 (Normalizer)                         |
|:-------------|:----------------------------|:-------------------------------------------|
| **적용 대상**    | `text` 필드 (검색용)             | `keyword` 필드 (정확한 값 일치, 집계용)               |
| **토크나으저 사용** | ✅ **사용함** (텍스트를 여러 토큰으로 분리) | ❌ **사용 안 함** (텍스트를 **하나의 토큰**으로 유지)        |
| **구성 요소**    | 캐릭터 필터 + **토크나이저** + 토큰 필터  | 캐릭터 필터 + 토큰 필터                             |
| **주요 목적**    | 전문(Full-text) 검색을 위한 텍스트 분석 | **정확한 값(Exact-value)** 검색 및 집계 시 대소문자 등 통일 |

**언제 사용할까?**
- keyword 필드로 대소문자 구분 없이 필터링하거나 집계(aggregation)하고 싶을 때 사용합니다.
- `예시: 데이터가 Apple, apple, APPLE로 제각각 저장되어 있을 때, 이들을 모두 같은 apple로 취급하여 검색하고 집계하고 싶을 경우.`

**사용 방법**
- settings에 커스텀 노멀라이저 정의: lowercase 토큰 필터만 적용하는 노멀라이저를 만듭니다.
- mappings에서 keyword 필드에 적용: 생성한 노멀라이저를 필드에 지정합니다.

JSON
```shell
PUT my-normalized-index
{
  "settings": {
    "analysis": {
      "normalizer": {
        "my_lowercase_normalizer": {
          "type": "custom",
          "filter": [ "lowercase" ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "tag": {
        "type": "keyword",
        "normalizer": "my_lowercase_normalizer"
      }
    }
  }
}
```
```shell
// "Apple" 로 데이터를 저장해도
POST my-normalized-index/_doc/1
{ 
  "tag": "Apple" 
}

// "apple" 로 검색하거나 집계할 수 있음
GET my-normalized-index/_search
{
  "query": { "term": { "tag": "apple" } }
}
```

## 2. 인덱스 템플릿 (Index Template): 인덱스 자동 생성의 설계도
**인덱스 템플릿(Index Template)** 은 특정 패턴과 일치하는 새로운 인덱스가 생성될 때, 미리 정의된 설정(settings)과 매핑(mappings)을 자동으로 적용하는 기능입니다. 마치 집을 짓기 전의 설계도와 같습니다.

**왜 필요할까?**
로그나 메트릭 데이터처럼 매일 또는 매시간 새로운 인덱스(logs-2025-10-09, logs-2025-10-10 등)가 생성되는 환경에서 매우 중요합니다. 템플릿이 없다면 매번 새로운 인덱스를 만들 때마다 수동으로 같은 설정과 매핑을 반복해야 합니다.

장점
- 자동화: 새로운 인덱스에 설정을 자동으로 적용하여 실수를 방지합니다.
- 일관성: 모든 관련 인덱스가 동일한 구조와 설정을 갖도록 보장합니다.
- 관리 용이성: 템플릿 하나만 수정하면 앞으로 생성될 모든 인덱스에 변경 사항이 적용됩니다.

**사용 방법** (최신 방식: Composable Templates)
Elasticsearch 7.8 버전부터는 여러 개의 재사용 가능한 조각(Component)을 조합하여 템플릿을 만드는 Composable Template 방식이 권장됩니다.

1단계
- 재사용할 조각 만들기 (Component Template)
- 자주 쓰는 설정(샤드 개수, 복제본 개수 등)과 매핑을 각각의 컴포넌트 템플릿으로 만듭니다.

```shell
# 공통 설정을 위한 컴포넌트
PUT _component_template/common_settings
{
  "template": {
    "settings": {
      "number_of_shards": 1,
      "number_of_replicas": 1
    }
  }
}

# 로그 데이터 매핑을 위한 컴포넌트
PUT _component_template/logs_mappings
{
  "template": {
    "mappings": {
      "properties": {
        "@timestamp": { "type": "date" },
        "message": { "type": "text" },
        "level": { "type": "keyword" }
      }
    }
  }
}
```

2단계
- 조각들을 조합하여 최종 템플릿 만들기 (Index Template)
- logs-* 패턴의 인덱스에 적용될 최종 인덱스 템플릿을 만들고, 위에서 만든 컴포넌트들을 조합합니다.

```shell
PUT _index_template/logs_template
{
  "index_patterns": ["logs-*"],
  "composed_of": ["common_settings", "logs_mappings"]
}
```

3단계: 인덱스 자동 생성
이제 logs-로 시작하는 새 인덱스를 특별한 설정 없이 생성하면, logs_template이 자동으로 적용됩니다.

```shell
# 별도 설정 없이 인덱스 생성
PUT logs-2025-10-09

# 매핑 확인: 템플릿에 정의된 필드들이 자동으로 생성된 것을 볼 수 있음
GET logs-2025-10-09/_mapping
```
