```shell
curl -i -X PUT "https://localhost:9200/array_test?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "mappings": {
    "properties": {
      "longField": {
        "type": "long"
      },
      "keywordField": {
        "type": "keyword"
      }
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 86

{
  "acknowledged" : true,
  "shards_acknowledged" : true,
  "index" : "array_test"
}
```

```shell
curl -i -X PUT "https://localhost:9200/array_test/_doc/1" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "longField": 309,
  "keywordField": ["hello", "world"]
}
'
HTTP/1.1 201 Created
Location: /array_test/_doc/1
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 143

{
  "_index":"array_test",
  "_id":"1",
  "_version":1,
  "result":"created",
  "_shards":{
    "total":2,
    "successful":2,
    "failed":0
  },
  "_seq_no":0,
  "_primary_term":1
}                                                                 
curl -i -X PUT "https://localhost:9200/array_test/_doc/2?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "longField": [221, 309, 1599208568],
  "keywordField": "hello"
}
'
HTTP/1.1 201 Created
Location: /array_test/_doc/2
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 204

{
  "_index" : "array_test",
  "_id" : "2",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```

```shell
 curl -i -X PUT "https://localhost:9200/array_test/_doc/3?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "longField": [221, 309, "hello"],
}
'
HTTP/1.1 400 Bad Request
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 561

{
  "error" : {
    "root_cause" : [
      {
        "type" : "document_parsing_exception",
        "reason" : "[3:27] failed to parse field [longField] of type [long] in document with id '3'. Preview of field's value: 'hello'"
      }
    ],
    "type" : "document_parsing_exception",
    "reason" : "[3:27] failed to parse field [longField] of type [long] in document with id '3'. Preview of field's value: 'hello'",
    "caused_by" : {
      "type" : "illegal_argument_exception",
      "reason" : "For input string: \"hello\""
    }
  },
  "status" : 400
}
```

```shell
curl -i -X GET "https://localhost:9200/array_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "term": {
      "longField": 309
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "took" : 2,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 2,
      "relation" : "eq"
    },
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "array_test",
        "_id" : "1",
        "_score" : 1.0,
        "_source" : {
          "longField" : 309,
          "keywordField" : [
            "hello",
            "world"
          ]
        }
      },
      {
        "_index" : "array_test",
        "_id" : "2",
        "_score" : 1.0,
        "_source" : {
          "longField" : [
            221,
            309,
            1599208568
          ],
          "keywordField" : "hello"
        }
      }
    ]
  }
}
```