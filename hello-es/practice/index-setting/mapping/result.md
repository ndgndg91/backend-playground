```shell
curl -i -X PUT "https://localhost:9200/mapping_test?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "mappings": {
    "properties": {
      "createdDate": {
        "type": "date",
        "format": "strict_date_time || epoch_millis"
      },
      "keywordString": {
        "type": "keyword"
      },
      "textString": {
        "type": "text"
      }
    }
  },
  "settings": {
    "number_of_shards": 2,
    "number_of_replicas": 3
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 88

{
  "acknowledged" : true,
  "shards_acknowledged" : true,
  "index" : "mapping_test"
}
```

```shell
curl -i -X GET "https://localhost:9200/mapping_test?pretty" -u elastic:ndgndg91 -k
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "mapping_test" : {
    "aliases" : { },
    "mappings" : {
      "properties" : {
        "createdDate" : {
          "type" : "date",
          "format" : "strict_date_time || epoch_millis"
        },
        "keywordString" : {
          "type" : "keyword"
        },
        "textString" : {
          "type" : "text"
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
        "provided_name" : "mapping_test",
        "creation_date" : "1759841905803",
        "number_of_replicas" : "3",
        "uuid" : "G3KrNmP9RYWlQAaFBQAmaw",
        "version" : {
          "created" : "9033000"
        }
      }
    }
  }
}
```

```shell
 curl -i -X PUT "https://localhost:9200/mapping_test/_mapping?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "properties": {
    "longValue": {
      "type": "long"
    }
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 28

{
  "acknowledged" : true
}
```

```shell
 curl -i -X GET "https://localhost:9200/mapping_test?pretty" -u elastic:ndgndg91 -k
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
Transfer-Encoding: chunked

{
  "mapping_test" : {
    "aliases" : { },
    "mappings" : {
      "properties" : {
        "createdDate" : {
          "type" : "date",
          "format" : "strict_date_time || epoch_millis"
        },
        "keywordString" : {
          "type" : "keyword"
        },
        "longValue" : {
          "type" : "long"
        },
        "textString" : {
          "type" : "text"
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
        "provided_name" : "mapping_test",
        "creation_date" : "1759841905803",
        "number_of_replicas" : "3",
        "uuid" : "G3KrNmP9RYWlQAaFBQAmaw",
        "version" : {
          "created" : "9033000"
        }
      }
    }
  }
}
```