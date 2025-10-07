```shell
ndgndg91@namdong-gil-ui-MacBookPro practice % curl -i -X POST "https://localhost:9200/my_index/_search?pretty" \
-u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "match": {
      "title": "hello world"
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "took" : 7,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 5,
      "relation" : "eq"
    },
    "max_score" : 0.37469345,
    "hits" : [
      {
        "_index" : "my_index",
        "_id" : "YByvvZkBCGiCQNaLRxwo",
        "_score" : 0.37469345,
        "_source" : {
          "title" : "hello world",
          "views" : 1234,
          "public" : true,
          "created" : "2025-10-04T14:05:01.234Z"
        }
      },
      {
        "_index" : "my_index",
        "_id" : "YRyvvZkBCGiCQNaLkhyU",
        "_score" : 0.37469345,
        "_source" : {
          "title" : "hello world",
          "views" : 1234,
          "public" : true,
          "created" : "2025-10-04T14:05:01.234Z"
        }
      },
      {
        "_index" : "my_index",
        "_id" : "YhyvvZkBCGiCQNaLrxxj",
        "_score" : 0.37469345,
        "_source" : {
          "title" : "hello world",
          "views" : 1234,
          "public" : true,
          "created" : "2025-10-04T14:05:01.234Z"
        }
      },
      {
        "_index" : "my_index",
        "_id" : "Yxy2vZkBCGiCQNaLRxxX",
        "_score" : 0.37469345,
        "_source" : {
          "title" : "hello world",
          "views" : 1234,
          "public" : true,
          "created" : "2025-10-04T14:05:01.234Z"
        }
      },
      {
        "_index" : "my_index",
        "_id" : "1",
        "_score" : 0.08701137,
        "_source" : {
          "title" : "hello ealsticsearch!~",
          "views" : 1234,
          "public" : true,
          "created" : "2025-10-04T14:05:01.234Z"
        }
      }
    ]
  }
}
```