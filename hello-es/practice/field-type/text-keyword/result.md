```shell
curl -i -X PUT "https://localhost:9200/mapping_test/_doc/3?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "keywordString": "Hello World!",
  "textString": "Hello World!"
}
'
HTTP/1.1 201 Created
Location: /mapping_test/_doc/3
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 206

{
  "_index" : "mapping_test",
  "_id" : "3",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 4,
    "successful" : 3,
    "failed" : 0
  },
  "_seq_no" : 2,
  "_primary_term" : 1
}
```

```shell
curl -i -X GET "https://localhost:9200/mapping_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {                      
    "match": {                
      "textString": "hello"
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "took" : 11,
  "timed_out" : false,
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 0.18232156,
    "hits" : [
      {
        "_index" : "mapping_test",
        "_id" : "3",
        "_score" : 0.18232156,
        "_source" : {
          "keywordString" : "Hello World!",
          "textString" : "Hello World!"
        }
      }
    ]
  }
}
```

```shell
 curl -i -X GET "https://localhost:9200/mapping_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "match": {
      "keywordString": "hello"
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "took" : 8,
  "timed_out" : false,
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 0,
      "relation" : "eq"
    },
    "max_score" : null,
    "hits" : [ ]
  }
}
```

```shell
curl -i -X GET "https://localhost:9200/mapping_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "match": {
      "keywordString": "Hello World!"
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "took" : 4,
  "timed_out" : false,
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 0.18232156,
    "hits" : [
      {
        "_index" : "mapping_test",
        "_id" : "3",
        "_score" : 0.18232156,
        "_source" : {
          "keywordString" : "Hello World!",
          "textString" : "Hello World!"
        }
      }
    ]
  }
}
```