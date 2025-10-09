curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "analyzer": "standard",
  "text": "Hello, HELLO, World!"
}
'

curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "char_filter": ["html_strip"],
  "text": "<p>I&apos;m so <b>happy</b>!</p>"
}
'

# 기본 토크나이저로 Unicode Text Segmentation 알고리즘으로 단어 단위로 나눈다.
curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "tokenizer": "keyword",
  "text": "Hello, HELLO, World!"
}
'

# 3 ~ 4 길이의 토큰으로 쪼갠다. ngram 은 rdb 의 like 검색어와 유사한 검색을 구현할 때 사용된다. 또는 자동 완성 관련 서비스를 구현하고 싶을 때 사용한다.
# letter - 언어의 글자로 분류되는 문자
# digit - 숫자로 분류되는 문자
# whitespace - 띄어쓰기나 줄바꿈 문자 등 공백으로 인식되는 문자
# punctuation - ! " 등 문장 부호
# symbol - $ 와 같은 기호
# custom - custom_token_chars 설정을 통해 따로 지정한 커스텀 문자
curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "tokenizer": {
    "type": "ngram",
    "min_gram": 3,
    "max_gram": 4,
    "token_chars": ["letter"]
  },
  "text": "Hello, World!"
}
'

curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "tokenizer": {
      "type": "edge_ngram",
      "min_gram": 3,
      "max_gram": 4,
      "token_chars": ["letter"]
    },
    "text": "Hello, World!"
}'


curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "filter": ["lowercase"],
  "text": "Hello, World!"
}'