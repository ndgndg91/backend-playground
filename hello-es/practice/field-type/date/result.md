## format
- java.time.format.DatetimeFormatter 인식 가능한 패턴
- epoch_millis 
- epoch_second 
- date_time - `yyyyMMdd`
- strict_date_time - `yyyy-MM-dd'T'HH:mm:ss.SSSZZ`
- date_optional_time - `yyyy-MM-dd` or `yyyy-MM-dd'T'HH:mm:ss.SSSZZ`
- strict_date_optional_time

```shell
curl -i -X PUT "https://localhost:9200/mapping_test/_doc/1" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "createdDate": "2025-10-03T20:00:00.234Z"
}
'

curl -i -X PUT "https://localhost:9200/mapping_test/_doc/2" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "createdDate": "1759845033000"
}
'
HTTP/1.1 201 Created
Location: /mapping_test/_doc/1
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 145

{
  "_index":"mapping_test",
  "_id":"1",
  "_version":1,
  "result":"created",
  "_shards":{
    "total":4,
    "successful":3,
    "failed":0
  },
  "_seq_no":0,
  "_primary_term":1
}

HTTP/1.1 201 Created
Location: /mapping_test/_doc/2
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 145

{
  "_index":"mapping_test",
  "_id":"2",
  "_version":1,
  "result":"created",
  "_shards":{
    "total":4,
    "successful":3,
    "failed":0
    },
    "_seq_no":1,
    "_primary_term":1
} 
```

```shell
 curl -i -X POST "https://localhost:9200/mapping_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "range": {
      "createdDate": {
        "gte": "2025-10-01T17:00:00.000Z",
        "lte": "2025-10-30T17:00:00.000Z"
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
  "took" : 29,
  "timed_out" : false,
  "_shards" : {
    "total" : 2,
    "successful" : 2,
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
        "_index" : "mapping_test",
        "_id" : "1",
        "_score" : 1.0,
        "_source" : {
          "createdDate" : "2025-10-03T20:00:00.234Z"
        }
      },
      {
        "_index" : "mapping_test",
        "_id" : "2",
        "_score" : 1.0,
        "_source" : {
          "createdDate" : "1759845033000"
        }
      }
    ]
  }
}
```