```shell
curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "analyzer": "standard",
  "text": "Hello, HELLO, World!"
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 439

{
  "tokens" : [
    {
      "token" : "hello",
      "start_offset" : 0,
      "end_offset" : 5,
      "type" : "<ALPHANUM>",
      "position" : 0
    },
    {
      "token" : "hello",
      "start_offset" : 7,
      "end_offset" : 12,
      "type" : "<ALPHANUM>",
      "position" : 1
    },
    {
      "token" : "world",
      "start_offset" : 14,
      "end_offset" : 19,
      "type" : "<ALPHANUM>",
      "position" : 2
    }
  ]
}
```


```shell
curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "char_filter": ["html_strip"],
  "text": "<p>I&apos;m so <b>happy</b>!</p>"
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 167

{
  "tokens" : [
    {
      "token" : "\nI'm so happy!\n",
      "start_offset" : 0,
      "end_offset" : 32,
      "type" : "word",
      "position" : 0
    }
  ]
}
```

```shell
curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "tokenizer": "keyword",
  "text": "Hello, HELLO, World!"
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 170

{
  "tokens" : [
    {
      "token" : "Hello, HELLO, World!",
      "start_offset" : 0,
      "end_offset" : 20,
      "type" : "word",
      "position" : 0
    }
  ]
}
```

```shell
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
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 1331

{
  "tokens" : [
    {
      "token" : "Hel",
      "start_offset" : 0,
      "end_offset" : 3,
      "type" : "word",
      "position" : 0
    },
    {
      "token" : "Hell",
      "start_offset" : 0,
      "end_offset" : 4,
      "type" : "word",
      "position" : 1
    },
    {
      "token" : "ell",
      "start_offset" : 1,
      "end_offset" : 4,
      "type" : "word",
      "position" : 2
    },
    {
      "token" : "ello",
      "start_offset" : 1,
      "end_offset" : 5,
      "type" : "word",
      "position" : 3
    },
    {
      "token" : "llo",
      "start_offset" : 2,
      "end_offset" : 5,
      "type" : "word",
      "position" : 4
    },
    {
      "token" : "Wor",
      "start_offset" : 7,
      "end_offset" : 10,
      "type" : "word",
      "position" : 5
    },
    {
      "token" : "Worl",
      "start_offset" : 7,
      "end_offset" : 11,
      "type" : "word",
      "position" : 6
    },
    {
      "token" : "orl",
      "start_offset" : 8,
      "end_offset" : 11,
      "type" : "word",
      "position" : 7
    },
    {
      "token" : "orld",
      "start_offset" : 8,
      "end_offset" : 12,
      "type" : "word",
      "position" : 8
    },
    {
      "token" : "rld",
      "start_offset" : 9,
      "end_offset" : 12,
      "type" : "word",
      "position" : 9
    }
  ]
}
```

```shell
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
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 546

{
  "tokens" : [
    {
      "token" : "Hel",
      "start_offset" : 0,
      "end_offset" : 3,
      "type" : "word",
      "position" : 0
    },
    {
      "token" : "Hell",
      "start_offset" : 0,
      "end_offset" : 4,
      "type" : "word",
      "position" : 1
    },
    {
      "token" : "Wor",
      "start_offset" : 7,
      "end_offset" : 10,
      "type" : "word",
      "position" : 2
    },
    {
      "token" : "Worl",
      "start_offset" : 7,
      "end_offset" : 11,
      "type" : "word",
      "position" : 3
    }
  ]
}
```


```shell
 curl -i -X POST "https://localhost:9200/_analyze?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "filter": ["lowercase"],
  "text": "Hello, World!"
}'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 163

{
  "tokens" : [
    {
      "token" : "hello, world!",
      "start_offset" : 0,
      "end_offset" : 13,
      "type" : "word",
      "position" : 0
    }
  ]
}
```