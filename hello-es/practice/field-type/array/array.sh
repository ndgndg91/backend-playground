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

curl -i -X PUT "https://localhost:9200/array_test/_doc/1?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "longField": 309,
  "keywordField": ["hello", "world"]
}
'

curl -i -X PUT "https://localhost:9200/array_test/_doc/2?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "longField": [221, 309, 1599208568],
  "keywordField": "hello"
}
'

curl -i -X PUT "https://localhost:9200/array_test/_doc/3?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "longField": [221, 309, "hello"],
}
'

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