# Elasticsearch: Analyzer vs Normalizer (text & keyword 타입) 🔬

Elasticsearch에서 데이터를 어떻게 저장하고 검색할지 결정하는 가장 중요한 요소는 필드 타입과 데이터 분석(Analysis) 과정입니다. 특히 `text`와 `keyword` 타입은 각각 다른 분석 전략을 사용하며, 이를 이해하는 것은 정확한 검색 결과를 얻기 위해 필수적입니다.

- **`text` 타입**: 전문(Full-text) 검색을 위한 타입입니다. 문장이나 문단과 같은 긴 텍스트 데이터에 사용되며, **Analyzer**를 통해 텍스트를 작은 단위(토큰)로 분해하고 정제하여 저장합니다.
- **`keyword` 타입**: 정확한(Exact) 값 필터링, 정렬, 집계(Aggregation)를 위한 타입입니다. 이메일 주소, 태그, 상태 코드처럼 분해되면 안 되는 데이터에 사용되며, **Normalizer**를 통해 값을 정제합니다.

## 1. `text` 타입과 Analyzer

`Analyzer`는 `text` 타입의 필드에 입력된 문자열을 검색에 용이한 형태로 가공하는 파이프라인입니다. 이 과정은 크게 세 단계로 이루어집니다.

**`[ 원본 텍스트 ]`** ➔ **`Character Filter`** ➔ **`Tokenizer`** ➔ **`Token Filter`** ➔ **`[ 인덱싱될 토큰(들) ]`**

![Image of Elasticsearch analysis process pipeline](https://esbook.kimjmin.net/~gitbook/image?url=https%3A%2F%2F2678746270-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-legacy-files%2Fo%2Fassets%252F-Ln04DaYZaDjdiR_ZsKo%252F-LntYrdKmTe441TqYAJl%252F-LntZ63SAIfHu6Q_OgzJ%252F6.2-02.png%3Falt%3Dmedia%26token%3D52213afe-e6ab-4bc2-b9e0-20027542a79e&width=768&dpr=2&quality=100&sign=783fa31&sv=2)

### ⚙️ Analyzer의 3단계

1.  **Character Filters (문자 필터)**
    - **역할**: Tokenizer가 처리하기 전에 원본 텍스트를 전처리합니다.
    - **예시**: HTML 태그(`<b>`, `</p>`)를 제거하거나, `&`를 `and`로 치환하는 등의 작업을 수행합니다.
    - `원본`: `<p>Quick & brown fox!</p>`
    - `결과`: `Quick and brown fox!`

2.  **Tokenizer (토크나이저)**
    - **역할**: 문자 필터를 거친 텍스트를 개별 단어(토큰)로 분리합니다. **Analyzer의 핵심**이며, `text` 타입을 `keyword`와 구분 짓는 가장 중요한 단계입니다.
    - **예시**: 공백, 쉼표, 마침표 등을 기준으로 문자열을 자릅니다.
    - `원본`: `Quick and brown fox!`
    - `결과 (토큰 스트림)`: `[Quick, and, brown, fox]`

3.  **Token Filters (토큰 필터)**
    - **역할**: Tokenizer가 생성한 토큰들을 하나씩 가공합니다.
    - **예시**:
        - **소문자 변환**: `Quick` → `quick`
        - **불용어(Stopword) 제거**: `and`, `is`, `the` 와 같은 의미 없는 단어 제거 → `[quick, brown, fox]`
        - **동의어 처리**: `fox` → `[fox, quick_animal]`
        - **어간 추출(Stemming)**: `running` → `run`
    - `원본 (토큰 스트림)`: `[Quick, and, brown, fox]`
    - `결과 (최종 토큰)`: `[quick, brown, fox]`

이 과정을 거쳐 `"Quick and brown fox!"` 라는 원본 텍스트는 최종적으로 `quick`, `brown`, `fox` 라는 세 개의 토큰으로 역