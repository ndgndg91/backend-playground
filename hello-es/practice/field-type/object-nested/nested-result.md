```shell
curl -i -X PUT "https://localhost:9200/nested_test" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "mappings": {
    "properties": {
      "spec": {
        "type": "nested",
        "properties": {
          "cores": {
            "type": "long"
          },
          "memory": {
            "type": "long"
          },
          "storage": {
            "type": "long"
          }
        }
      }
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 70

{
  "acknowledged":true,
  "shards_acknowledged":true,
  "index":"nested_test"
}
```

```shell
curl -i -X PUT 'https://localhost:9200/nested_test/_doc/1?pretty' -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
     {
      "spec": [
        {
          "cores": 12,
          "memory": 128,
          "storage": 8000
        },
        {
          "cores": 6,
          "memory":64,
          "storage": 8000
        },
        {
          "cores": 6,
          "memory": 32,
          "storage": 4000
        }
      ]
     }
     '
HTTP/1.1 201 Created
Location: /nested_test/_doc/1
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 205

{
  "_index" : "nested_test",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}
```

```shell
curl -i -X GET "https://localhost:9200/nested_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "bool": {
      "must": [
        {
          "term": {
            "spec.cores": "6"
          }
        },
        {
          "term": {
            "spec.memory": "128"
          }
        }
      ]
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "took" : 16,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
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

- path 에 spec 으로 nested 를 지정하여 쿼리한다.
- nested 타입은 내부적으로 각 객체를 별도의 문서로 분리해서 저장하기에 성능문제가 있을 수 있다.
- nested 타입의 무분별한 사용을 막기 위해서 인덱스 설정으로 제한을 걸었다.
  - `index.mapping.nested_fields.limit` 한 인덱스에 nested 타입을 몇 개까지 지정할 수 있는지 제한하는 값으로 기본값은 50
  - `index.mapping.nested_objects.limit` 한 문서가 nested 객체를 몇 개까지 가질 수 있는지 제한하는 값으로 기본값은 10000.
- 해당 제한을 무리하게 높이면 OOM 위험이 있다.
```shell
 curl -i -X GET "https://localhost:9200/nested_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "nested": {
      "path": "spec",
      "query": {
        "bool": {
          "must": [
            {
              "term": {
                "spec.cores": 6
              }
            },
            {
              "term": {
                "spec.memory": 64
              }
            }
          ]
        }
      }
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
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 2.0,
    "hits" : [
      {
        "_index" : "nested_test",
        "_id" : "1",
        "_score" : 2.0,
        "_source" : {
          "spec" : [
            {
              "cores" : 12,
              "memory" : 128,
              "storage" : 8000
            },
            {
              "cores" : 6,
              "memory" : 64,
              "storage" : 8000
            },
            {
              "cores" : 6,
              "memory" : 32,
              "storage" : 4000
            }
          ]
        }
      }
    ]
  }
}
```