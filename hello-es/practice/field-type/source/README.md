# Elasticsearch: 스토리지 및 인덱싱 최적화 가이드
Elasticsearch에서 인덱스 크기를 관리하고 스토리지 및 CPU 비용을 최적화하는 것은 매우 중요합니다. 이 가이드에서는 스토리지 용량에 가장 큰 영향을 미치는 _source 필드와, 색인(indexing) 오버헤드를 줄이는 index, enabled 파라미터의 역할, 그리고 각 최적화 방식의 장단점을 다룹니다.

## 1. _source 필드와 스토리지 최적화
_source 필드는 우리가 Elasticsearch에 인덱싱한 원본 JSON 문서 그 자체를 저장하는 특수한 메타 필드입니다. 이는 문서 조회, 업데이트, 리인덱싱 등 필수 기능에 사용되지만, 디스크 공간을 가장 많이 차지하는 원인이기도 합니다.

### 1.1. _source 필드란 무엇인가?
_source 필드는 Elasticsearch에 보낸 원본 JSON 문서를 그대로 저장합니다.

```shell
POST my-index/_doc/1
{
  "user": "kim",
  "post_date": "2025-10-08T12:00:00",
  "message": "Elasticsearch is fun!"
}
```

_source의 핵심 역할
전체 문서 조회: GET my-index/_doc/1 처럼 특정 문서를 조회할 때 _source 필드를 반환합니다.

- 업데이트 (Update): Update API는 _source를 읽어 문서를 수정한 후 다시 인덱싱하는 방식으로 동작합니다.
- 리인덱싱 (Reindex): 기존 인덱스의 설정을 바꿔 새로운 인덱스로 데이터를 옮길 때 원본 문서인 _source가 반드시 필요합니다.
- 하이라이팅 (Highlighting): 검색 결과에서 일치하는 키워드에 하이라이트 효과를 줄 때 _source의 내용을 기반으로 작동합니다.

**analogy 원본 사진과 메타데이터 📷**

_source는 '원본 사진' 과 같습니다. 반면, 검색을 위해 존재하는 역 인덱스(Inverted Index)나 집계를 위한 Doc Values는 사진에 대한 '메타데이터'(촬영 날짜, 인물 태그 등)와 같습니다. 메타데이터만으로는 원본 사진의 완전한 모습을 볼 수 없듯, _source가 없으면 원본 문서를 온전히 볼 수 없습니다.

### 1.2. _source 비활성화와 치명적인 문제점
디스크 절약을 위해 _source를 비활성화("enabled": false)할 수 있지만, 이는 심각한 운영상 제약을 초래하므로 절대로 권장되지 않습니다.

리인덱싱 절대 불가: 데이터 마이그레이션이나 버전 업그레이드가 불가능해집니다.

Update API 사용 불가.

검색 결과 확인 및 디버깅의 어려움: 원본을 확인할 수 없어 문제 추적이 매우 어렵습니다.

### 1.3. 대안 1: 인덱스 압축 코덱 변경
_source를 유지하면서 디스크 공간을 절약하는 안전한 방법입니다. 인덱스 설정에서 index.codec을 best_compression(DEFLATE 압축)으로 변경하면, CPU 사용량이 소폭 증가하는 대신 디스크 사용량을 줄일 수 있습니다.

JSON
```shell
// 인덱스 생성 시 설정
PUT my-logs-2025-10
{
  "settings": {
    "index.codec": "best_compression"
  }
}
```

### 1.4. 대안 2: Synthetic _source
Elasticsearch 7.10부터 도입된 현대적인 해결책입니다. _source 원본을 저장하는 대신, doc_values나 stored_fields에 저장된 값을 조합하여 조회 시 _source 문서를 즉석에서 재구성합니다.

JSON
```shell
PUT my-synthetic-index
{
  "mappings": {
    "_source": {
      "mode": "synthetic"
    },
    "properties": {
      "user": { "type": "keyword" },
      "message": {
      "type": "text",
      "store": true  // text 타입은 doc_values가 없으므로 store:true가 필수
      }
    }
  }
}
```

Update API를 사용할 수 없다는 단점이 있지만, 디스크 공간 절약과 리인덱싱 기능 유지를 모두 만족시키는 훌륭한 절충안입니다.

### `_source` 모드별 비교표

| 구분 (Feature)   | Default (`enabled: true`) | Disabled (`enabled: false`) | Synthetic Source      |
|:---------------|:--------------------------|:----------------------------|:----------------------|
| **디스크 공간**     | 큼                         | 매우 작음                       | 작음                    |
| **리인덱싱**       | ✅ **가능**                  | ❌ **불가능**                   | ✅ **가능**              |
| **Update API** | ✅ **가능**                  | ❌ **불가능**                   | ❌ **불가능**             |
| **조회 속도**      | 빠름                        | (제한적 조회만 가능)                | 보통 (재구성 비용 발생)        |
| **원본과 동일**     | 예                         | 아니오                         | 아니오 (필드 순서 등 다를 수 있음) |

## 2. index 와 enabled 를 활용한 색인 최적화
_source가 스토리지에 관한 것이라면, index와 enabled 파라미터는 CPU와 디스크를 사용하는 색인(indexing) 과정 자체의 오버헤드를 줄이는 데 초점을 맞춥니다.

### 2.1. index 파라미터 (필드 검색 여부 제어)
index 파라미터는 개별 필드의 값이 검색이 가능하도록 역 인덱스(inverted index)에 추가될지를 결정합니다.

- "index": true (기본값): 필드를 검색할 수 있습니다.
- "index": false: 필드를 검색할 수 없습니다. 하지만 값은 _source에 저장되므로, 문서 조회 시 내용은 확인 가능합니다.

언제 사용하나요?
특정 필드의 값을 조회는 해야 하지만, 검색 조건으로는 절대 사용하지 않을 경우 색인 오버헤드를 줄이기 위해 사용합니다. (예: 썸네일 이미지 URL, 검색 조건으로 쓰이지 않는 단순 설명 텍스트)

```shell
"session_id": {
  "type": "keyword",
  "index": false
}
```

### 2.2. enabled 파라미터 (JSON 객체 처리 여부 제어)
enabled 파라미터는 JSON 객체(Object) 타입 필드에 적용되며, Elasticsearch가 이 객체의 내용을 아예 파싱(분석)할지 말지를 결정합니다.

- "enabled": true (기본값): 객체 내부의 모든 하위 필드를 정상적으로 파싱하고 색인합니다.
- "enabled": false: 객체 내용물 전체를 무시합니다. 내부의 그 어떤 필드도 검색하거나 개별적으로 조회할 수 없습니다. 객체 전체가 _source에 하나의 통짜 데이터(blob)로 저장될 뿐입니다.

언제 사용하나요?
내부 구조를 검색할 필요가 전혀 없는 복잡한 JSON 객체를 원본 그대로 저장만 하고 싶을 경우 사용합니다. (예: 외부 API 응답 JSON 원본, 디버깅 목적의 로그 데이터 객체)

```shell
"raw_payload": {
  "type": "object",
  "enabled": false
}
```

### 2.3. 핵심 비교 요약

| 구분 (Feature)        | `index`                                     | `enabled`                                             |
|:--------------------|:--------------------------------------------|:------------------------------------------------------|
| **적용 대상**           | 개별 필드 (e.g., `text`, `keyword`...)          | JSON 객체 (Object) 또는 `_source` 자체                      |
| **주요 목적**           | 해당 필드를 **검색 가능하게 만들지** 여부 결정                | 해당 객체의 **내용을 파싱(분석)할지** 여부 결정                         |
| **`false` 설정 시 효과** | **검색은 불가능**하지만, `_source`에는 저장되어 **조회는 가능** | 객체 내부의 모든 필드가 **검색도, 조회도 불가능**. `_source`에 통째로 저장만 됨. |
| **핵심 비유**           | 📖 책의 **색인(index)**에 단어를 넣을지 말지 결정          | ✉️ **봉인된 편지**를 뜯어볼지, 뜯지 않고 보관할지 결정                    |