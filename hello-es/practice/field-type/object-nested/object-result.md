```shell
curl -i -X PUT "https://localhost:9200/object_test/_doc/1?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "price": 2770.75,
  "spec": {
    "cores": 12,
    "memory": 128,
    "storage": 8000
  }
}
'
HTTP/1.1 201 Created
Location: /object_test/_doc/1
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 205

{
  "_index" : "object_test",
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

- 아래와 같이 색인된다.
```json
{
  "price": 2770.75,
  "spec.cores": 12,
  "spec.memory": 128,
  "storage": 8000
}
```

```shell
curl -i -X GET "https://localhost:9200/object_test/_mapping?pretty" -u elastic:ndgndg91 -k
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "object_test" : {
    "mappings" : {
      "properties" : {
        "price" : {
          "type" : "float"
        },
        "spec" : {
          "properties" : {
            "cores" : {
              "type" : "long"
            },
            "memory" : {
              "type" : "long"
            },
            "storage" : {
              "type" : "long"
            }
          }
        }
      }
    }
  }
}
```

```shell
 curl -i -X PUT "https://localhost:9200/object_test/_doc/1?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "price": 2770.75,
  "spec": [
    {
      "cores": 12,
      "memory": 128,
      "storage": 8000
    },
    {
      "cores": 6,
      "memory": 64,
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
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 205

{
  "_index" : "object_test",
  "_id" : "1",
  "_version" : 2,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```

- 아래와 같이 색인된다.
```json
{
  "spec.cores": [12, 6, 6],
  "spec.memory": [128, 64, 32],
  "spec.storage": [8000, 8000, 4000]
}
```

```shell
 curl -i -X GET "https://localhost:9200/object_test/_search?pretty" -u elastic:ndgndg91 -k \
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
  "took" : 6,
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
        "_index" : "object_test",
        "_id" : "1",
        "_score" : 2.0,
        "_source" : {
          "price" : 2770.75,
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