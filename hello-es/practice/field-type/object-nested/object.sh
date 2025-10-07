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

curl -i -X GET "https://localhost:9200/object_test/_mapping?pretty" -u elastic:ndgndg91 -k

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