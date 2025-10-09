## 분석기(Analyzer)의 처리 과정 🏭
아래는 "<b>I LOVE Seoul</b> & I hate bugs 2!" 라는 텍스트가 분석기를 통과하는 과정을 그림으로 표현한 것입니다.

1. 원본 텍스트 (Original Text)
```text
<b>I LOVE Seoul</b> & I hate bugs 2!
```
2. 캐릭터 필터 (Character Filters) - "재료 손질" (0개 이상)
- 토큰으로 나누기 전, 텍스트 전체를 정리합니다.
- html_strip 필터: HTML 태그(<b>, </b>)를 제거합니다.
- mapping 필터: &를 and로 변경합니다.
```text
I LOVE Seoul and I hate bugs 2!
```
3. 토크나이저 (Tokenizer) - "재료 썰기" (딱 1개)
- 정리된 텍스트를 특정 규칙에 따라 단어(토큰)들로 분리합니다.
- standard 토크나이저: 공백과 특수문자를 기준으로 단어를 분리합니다.
```text
[ I, LOVE, Seoul, and, I, hate, bugs, 2 ]
```
4. 토큰 필터 (Token Filters) - "양념 및 가공" (0개 이상)

- 분리된 각 토큰들을 하나씩 가공합니다.
- lowercase 필터: 모든 토큰을 소문자로 바꿉니다. (I -> i, LOVE -> love, Seoul -> seoul)
- stop 필터: 의미 없는 불용어(i, and)를 제거합니다.
- stemmer 필터: 단어를 원형으로 바꿉니다. (이 예시에는 해당 단어가 없음)

```text
[ love, seoul, hate, bugs, 2 ]
```

5. 최종 결과: 색인된 토큰 (Indexed Tokens)
이 토큰들이 최종적으로 역 인덱스에 저장되어 검색에 사용됩니다.

## 각 구성 요소 상세 설명
### 1. 캐릭터 필터 (Character Filters)
역할: 토크나이저가 처리하기 전에, 원본 텍스트를 통째로 정리하거나 변경하는 전처리 단계입니다.
비유: 요리 재료를 썰기 전에 흙을 씻어내거나 껍질을 벗기는 "재료 손질" 과정과 같습니다.

주요 기능:
- HTML 태그 제거 html_strip 필터는 <b>, <p> 같은 HTML 태그를 제거합니다.
- 문자열 치환: mapping 필터는 특정 문자나 단어를 다른 것으로 바꿀 수 있습니다. (e.g., :) -> _happy_, & -> and)

### 2. 토크나이저 (Tokenizer)
역할: 캐릭터 필터를 거친 텍스트를 특정 규칙에 따라 실제 토큰들로 분리하는 핵심 단계입니다. 모든 분석기는 반드시 단 1개의 토크나이저를 가집니다.
비유: 손질된 재료를 칼로 "써는" 과정입니다. 어떻게 써느냐(공백 기준, 형태소 기준 등)에 따라 요리의 형태가 달라집니다.

주요 종류:

- Standard: 공백, 하이픈, 구두점 등을 기준으로 분리합니다. (가장 일반적)
- Whitespace: 오직 공백으로만 분리합니다.
- Nori (한국어): 한국어 문장을 의미 있는 최소 단위인 형태소로 분리합니다. (e.g., "아버지가방에들어가신다" -> 아버지, 가방, 에, 들어가, 시)

### 3. 토큰 필터 (Token Filters)
역할: 토크나이저가 만들어낸 각 토큰들을 순회하며 가공하는 후처리 단계입니다. 토큰을 변경, 추가, 삭제할 수 있습니다.
비유: 썰어놓은 재료에 소금을 뿌리고, 볶거나 끓여서 맛을 내는 "양념 및 조리" 과정입니다.

주요 기능:

- 소문자 변환: lowercase 필터는 LOVE를 love로 바꿉니다.
- 불용어 제거: stop 필터는 a, the, is 와 같이 검색에 불필요한 단어를 제거합니다.
- 동의어 처리: synonym 필터는 happy라는 토큰을 만나면 joy라는 토큰을 추가하거나 대체할 수 있습니다.
- 어간 추출: stemmer 필터는 running, ran을 모두 원형인 run으로 변경하여 검색 효율을 높입니다.



## Elasticsearch 내장 분석기(Analyzer) 완벽 정리
분석기(Analyzer)는 텍스트를 검색 가능한 **토큰(token)**으로 분리하는 규칙의 집합입니다. 하나의 분석기는 0~N개의 문자 필터(Character Filters), 1개의 토크나이저(Tokenizer), 0~N개의 **토큰 필터(Token Filters)**로 구성됩니다.

1. Standard Analyzer 📖 (가장 표준적인 분석기)
   구성: Standard Tokenizer + Lowercase Token Filter

동작:

- Standard Tokenizer: 대부분의 언어(유니코드 기반)에서 잘 동작하도록 설계된 토크나이저입니다. 단어, 숫자, 기호 등을 기준으로 텍스트를 분리합니다. 이메일 주소, URL 등도 하나의 토큰으로 인식합니다.
- Lowercase Token Filter: 모든 토큰을 소문자로 변환합니다.
```text
"The 2 QUICK Brown-Foxes jumps over the lazy dog's bone."
```
```text
분리 결과: [the, 2, quick, brown, foxes, jumps, over, the, lazy, dog's, bone]
```
사용 사례: 특별한 규칙이 필요 없는 일반적인 텍스트 검색에 사용되는 기본 분석기입니다.

2. Simple Analyzer 🧹 (단순 분리)
   구성: Letter Tokenizer + Lowercase Token Filter

동작: 문자가 아닌(non-letter) 모든 것을 기준으로 토큰을 분리합니다. 숫자나 특수문자는 모두 분리 기준이 되어 사라집니다.
```text
"The 2 QUICK Brown-Foxes jumps over the lazy dog's bone."
```
```text
분리 결과: [the, quick, brown, foxes, jumps, over, the, lazy, dog, s, bone] (숫자 2는 사라지고 dog's는 dog, s로 분리됨)
```
사용 사례: 숫자나 특수문자를 완전히 무시하고 오직 알파벳 텍스트로만 검색하고 싶을 때 사용합니다.

3. Whitespace Analyzer 🌬️ (공백 기준 분리)
   구성: Whitespace Tokenizer

동작: 오직 **공백(whitespace)**만을 기준으로 토큰을 분리합니다. 대소문자 변환이나 다른 처리를 전혀 하지 않습니다.
```text
예시: "The 2 QUICK Brown-Foxes jumps over the lazy dog's bone."
```
```text
분리 결과: [The, 2, QUICK, Brown-Foxes, jumps, over, the, lazy, dog's, bone.] (대소문자와 특수문자가 그대로 유지됨)
```
사용 사례: 소스 코드나 시스템 로그처럼 대소문자, 특수문자가 중요한 의미를 가지는 텍스트를 분석할 때 유용합니다.

4. Stop Analyzer 🚫 (불용어 제거)
   구성: Letter Tokenizer + Lowercase Token Filter + Stop Token Filter

동작: Simple Analyzer와 동일하게 동작한 후, 불용어(stopwords) 목록(기본값: 영어의 a, an, the 등)에 포함된 토큰을 제거합니다.

```text
예시: "The 2 QUICK Brown-Foxes jumps over the lazy dog's bone."
```
```text
분리 결과: [quick, brown, foxes, jumps, over, lazy, dog, s, bone] (the가 제거됨)
```

사용 사례: 검색에 큰 의미가 없는 관사, 전치사 등을 제거하여 인덱스 크기를 줄이고 검색 효율을 높이고 싶을 때 사용합니다. 언어별로 불용어 목록을 지정할 수 있습니다.

5. Keyword Analyzer 🏷️ (통짜 데이터)
   구성: Keyword Tokenizer

동작: 아무것도 하지 않습니다. 입력된 텍스트 전체를 단 하나의 토큰으로 취급합니다.
```text
예시: "The 2 QUICK Brown-Foxes jumps over the lazy dog's bone."
```
```text
분리 결과: [The 2 QUICK Brown-Foxes jumps over the lazy dog's bone.]
```
사용 사례: keyword 타입 필드의 기본 분석기입니다. 카테고리명, 상품 코드, 태그처럼 분석이 필요 없고 **정확히 일치(exact match)**하는 값을 찾을 때 사용됩니다.

6. Pattern Analyzer 🧩 (정규식 기반 분리)
   구성: Pattern Tokenizer

동작: 사용자가 제공한 **정규 표현식(Regular Expression)**을 기준으로 텍스트를 토큰으로 분리합니다.
```text
예시: 정규식을 쉼표(,)로 지정했을 때, "red,green, blue"
```
```text
분리 결과: [red, green, blue]
```
사용 사례: CSV 데이터나 특정 구분자로 연결된 문자열 등 일정한 패턴을 가진 텍스트를 분석할 때 매우 유용합니다.

7. Language Analyzers 🌐 (언어별 분석기)
   구성: 각 언어의 문법적 특성에 맞춰진 토크나이저와 필터들 (예: 형태소 분석, 어간 추출 등)

동작: 영어, 프랑스어, 독일어, 한국어(nori 플러그인 필요) 등 특정 언어에 최적화된 분석을 제공합니다. 예를 들어 영어 분석기는 **어간 추출(stemming)**을 통해 jumps, jumping, jumped를 모두 원형인 jump로 변환하여 저장합니다.

사용 사례: 특정 언어로 된 문서의 검색 정확도를 높이고 싶을 때 반드시 사용해야 합니다.

8. Fingerprint Analyzer 🕵️ (지문 생성)
   구성: Standard Tokenizer + Lowercase Token Filter + Stop Token Filter + Fingerprint Token Filter

동작: 텍스트를 분석하고 정렬한 후, 중복을 제거하고 하나의 토큰으로 합쳐서 **"지문(fingerprint)"**을 생성합니다.
```text
예시: "The quick brown fox jumps over the lazy dog"

```
```text
분리 결과: [brown dog fox jumps lazy quick the over] 
중복된 the는 제거되고 알파벳순으로 정렬된 후 하나의 토큰으로 합쳐짐)
```

사용 사례: 중복 문서 탐지에 주로 사용됩니다. 내용의 순서나 사소한 차이와 상관없이 핵심 단어들이 같다면 동일한 지문이 생성되므로, 이를 통해 비슷한 문서를 찾아낼 수 있습니다.