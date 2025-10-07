curl -i -X POST "https://localhost:9200/my_index/_search?pretty" \
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