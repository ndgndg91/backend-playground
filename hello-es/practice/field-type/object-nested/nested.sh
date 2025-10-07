curl -i -X PUT "https://localhost:9200/nested_test?pretty" -u elastic:ndgndg91 -k \
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