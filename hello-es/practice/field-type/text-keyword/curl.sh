curl -i -X PUT "https://localhost:9200/mapping_test/_doc/3?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "keywordString": "Hello World!",
  "textString": "Hello World!"
}
'

curl -i -X GET "https://localhost:9200/mapping_test?pretty"  -u elastic:ndgndg91 -k
curl -i -X GET "https://localhost:9200/mapping_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "match": {
      "textString": "hello"
    }
  }
}
'

curl -i -X GET "https://localhost:9200/mapping_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "match": {
      "keywordString": "hello"
    }
  }
}
'

curl -i -X GET "https://localhost:9200/mapping_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "match": {
      "keywordString": "Hello World!"
    }
  }
}
'