```shell
curl -i -X PUT "https://localhost:9200/my_index?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "settings": {
    "number_of_replicas": 3,
    "number_of_shards": 2
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 84

{
  "acknowledged" : true,
  "shards_acknowledged" : true,
  "index" : "my_index"
}
```

```shell
 curl -i -X GET "https://localhost:9200/my_index?pretty" -u elastic:ndgndg91 -k
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "my_index" : {
    "aliases" : { },
    "mappings" : { },
    "settings" : {
      "index" : {
        "routing" : {
          "allocation" : {
            "include" : {
              "_tier_preference" : "data_content"
            }
          }
        },
        "number_of_shards" : "2",
        "provided_name" : "my_index",
        "creation_date" : "1759841244569",
        "number_of_replicas" : "3",
        "uuid" : "SGLEuu0US6uTSGFppdiKZg",
        "version" : {
          "created" : "9033000"
        }
      }
    }
  }
}
```

```shell
 curl -i -X PUT "https://localhost:9200/my_index/_doc/1" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "title": "hello world",
  "views": 1234,
  "public": true,
  "point": 4.5,
  "created": "2025-10-04T20:00:00.234Z"
}
'
HTTP/1.1 201 Created
Location: /my_index/_doc/1
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 141

{
  "_index":"my_index",
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
```


```shell
curl -i -X GET "https://localhost:9200/my_index?pretty" -u elastic:ndgndg91 -k
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "my_index" : {
    "aliases" : { },
    "mappings" : {
      "properties" : {
        "created" : {
          "type" : "date"
        },
        "point" : {
          "type" : "float"
        },
        "public" : {
          "type" : "boolean"
        },
        "title" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "views" : {
          "type" : "long"
        }
      }
    },
    "settings" : {
      "index" : {
        "routing" : {
          "allocation" : {
            "include" : {
              "_tier_preference" : "data_content"
            }
          }
        },
        "number_of_shards" : "2",
        "provided_name" : "my_index",
        "creation_date" : "1759841244569",
        "number_of_replicas" : "3",
        "uuid" : "SGLEuu0US6uTSGFppdiKZg",
        "version" : {
          "created" : "9033000"
        }
      }
    }
  }
}
```