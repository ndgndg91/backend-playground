# Elasticsearch: Doc Values vs. Fielddata 완벽 가이드

`Doc values`는 Elasticsearch에서 **정렬(sorting), 집계(aggregations), 스크립팅(scripting)**의 성능을 최적화하기 위해 사용되는 **디스크 기반의 컬럼형(column-oriented) 데이터 구조**입니다.

`Fielddata`는 주로 분석된 `text` 필드에서 집계를 위해 사용되는 레거시(legacy) 메모리 집약적 방식입니다. **결론적으로, `text` 필드에 대한 집계가 불가피한 특수한 경우가 아니라면 항상 `doc_values`를 사용해야 합니다.**

---

## ## Doc Values vs. Fielddata: 직접 비교 분석 ⚖️

| 구분         | Doc Values (현대 표준 방식)                                                                         | Fielddata (레거시 및 예외적 방식)                                                 |
|:-----------|:----------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------|
| **주요 목적**  | 효율적인 정렬(sorting), 집계(aggregations), 스크립팅                                                      | 분석된(analyzed) `text` 필드의 집계 및 정렬                                         |
| **데이터 구조** | **디스크 기반(On-disk)**, 컬럼형(column-oriented) 구조. 인덱싱 시점에 생성됨.                                    | **메모리 기반(In-memory)**, 쿼리 시점에 JVM 힙(Heap) 위에 동적으로 생성됨.                   |
| **메모리 사용** | **OS 파일 시스템 캐시**를 활용. JVM 힙에 거의 부담을 주지 않아 매우 안정적.                                             | **JVM 힙**을 대량으로 소모. `OutOfMemoryError`의 주된 원인이며 클러스터 불안정성을 유발.           |
| **성능**     | 빠르고 예측 가능함. 데이터가 미리 계산되어 디스크 접근에 최적화됨. 디스크 사용량은 약간 증가.                                        | 데이터가 힙에 다 올라가면 빠를 수 있으나, 초기 로딩이 매우 느리고 리소스를 많이 소모하여 쿼리 지연(latency)을 유발.  |
| **지원 필드**  | `text`, `annotated_text`를 **제외한** 모든 타입에 기본 활성화 (예: `keyword`, `integer`, `date`, `boolean`). | 기본적으로 비활성화. `text` 필드에 수동으로 활성화 가능. `text` 필드를 집계하는 유일한 방법.              |
| **안정성**    | **매우 높음**. OS에 의해 보호되므로 노드 장애를 일으키지 않음.                                                       | **매우 낮음**. 노드 장애를 막기 위해 쿼리를 중단시키는 `fielddata circuit breaker` 오류의 주된 원인. |
| **권장 사항**  | **항상 사용**. `text` 대신 `keyword` 필드를 사용하여 집계하도록 매핑을 설계하는 것이 베스트 프랙티스.                           | **가급적 사용 금지**. 분석된 문자열 필드에 대한 집계가 반드시 필요한 경우에만 최후의 수단으로 사용.              |

---

## ## Fielddata는 Deprecated(지원 중단) 되었나요?

"아니요, 공식적으로 Deprecated 상태는 아니지만 **사실상 Deprecated처럼 취급됩니다.**"

* **공식적으로 Deprecated가 아닌 이유**: '워드 클라우드' 예시처럼 **분석된 `text` 필드의 토큰을 집계**하는 기능은 `fielddata`가 유일한 방법이기 때문입니다. 이 기능을 완전히 제거하면 Elasticsearch의 기능 일부가 사라지는 셈이라, 대체 기술이 나오기 전까지는 기능을 남겨두고 있습니다.

* **사실상 Deprecated처럼 취급되는 이유**: Elasticsearch 공식 문서와 모든 전문가들이 `fielddata` 사용의 위험성(JVM 메모리 폭증, 클러스터 불안정)을 매우 강력하게 경고하고 있습니다. 그래서 "이 기능은 없앨 예정이니 쓰지 마세요"라고 말하는 대신 **"이 기능은 매우 위험하니 무슨 일이 벌어질지 정확히 알고 있는 게 아니라면 절대 쓰지 마세요"** 라고 안내하는 것에 가깝습니다.

> ** analogy 오래된 공장의 위험한 기계 🏭**
>
> `fielddata`는 공장 구석에 있는 낡고 위험하지만, **딱 한 가지 작업**만큼은 이 기계로만 할 수 있어서 버리지 못하는 기계와 같습니다. 모두가 그 기계가 위험한 걸 알기에 꼭 필요한 사람이 아니면 근처에도 가지 말라고 경고문이 붙어있는 상태죠.

---

## ## 올바른 해결책: Multi-fields 활용하기 💡

`fielddata` 사용을 피하고, 하나의 필드로 검색과 집계를 모두 처리하는 가장 좋은 방법은 **멀티 필드(Multi-fields)**를 사용하는 것입니다.

핵심 개념은 **"하나의 원본 데이터를 Elasticsearch에 보낼 때, 여러 방식으로 가공해서 저장해달라고 요청하는 것"** 입니다.

> ** analogy 하나의 이름, 두 개의 명함 📇**
>
> **`"Apple iPhone 17 Pro"`** 라는 상품명이 있다고 상상해 보세요. 이 데이터를 Elasticsearch에 저장할 때 멀티 필드를 사용하면, Elasticsearch가 내부적으로 두 가지 버전의 데이터를 생성합니다.
>
> 1.  **`text` 버전 명함 (검색용)**: 문장을 단어별로 쪼개서 저장합니다. (`apple`, `iphone`, `17`, `pro`)
      >     * **용도**: 사용자가 `"iphone pro"` 라고만 검색해도 이 상품을 찾아낼 수 있게 합니다. (전문 검색, Full-text search)
>
> 2.  **`keyword` 버전 명함 (집계/정렬용)**: 문장 전체를 하나의 통짜 데이터로 저장합니다. (`Apple iPhone 17 Pro`)
      >     * **용도**: `"Apple iPhone 17 Pro"` 라는 상품이 총 몇 개 팔렸는지 정확하게 집계하거나, 상품명 가나다순으로 정렬할 때 사용합니다.


### ### Multi-fields 실제 사용 예시

**1. 매핑 설정**

`product_name`이라는 필드를 생성할 때, 기본은 `text` 타입으로 하고 그 안에 `fields`를 추가해서 `keyword` 타입을 정의합니다.

```shell
PUT my-shop
{
  "mappings": {
    "properties": {
      "product_name": {
        "type": "text",      // 기본은 text 타입 (검색용)
        "fields": {
          "keyword": {       // 'keyword'라는 이름의 하위 필드를 추가
            "type": "keyword" // 이 하위 필드는 keyword 타입 (집계/정렬용)
          }
        }
      }
    }
  }
}
```
**2. 데이터 저장**
- 데이터를 저장할 때는 그냥 원본 데이터만 보내면 됩니다. Elasticsearch가 매핑을 보고 알아서 두 가지 버전으로 인덱싱합니다.

```shell
POST my-shop/_doc/1
{
  "product_name": "Apple iPhone 17 Pro"
}
POST my-shop/_doc/2
{
  "product_name": "Apple iPhone 17"
}
```

**3. 사용 방법**
- 검색할 때는 text 필드인 product_name을 사용합니다.

```shell
// "pro"가 들어간 상품 검색
GET my-shop/_search
{ "query": { "match": { "product_name": "Pro" } } }
집계나 정렬을 할 때는 .keyword가 붙은 하위 필드 product_name.keyword를 사용합니다.
```
```shell
// 상품명별로 정확히 그룹지어 개수 세기
GET my-shop/_search
{
  "size": 0,
  "aggs": {
    "products": {
      "terms": { "field": "product_name.keyword" }
    }
  }
}
```
이렇게 하면 하나의 필드만 관리하면서도 강력한 전문 검색과 안정적인 집계/정렬 기능을 모두 활용할 수 있습니다.