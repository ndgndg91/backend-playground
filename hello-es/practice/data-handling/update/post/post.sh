curl -i -X POST "https://localhost:9200/my_index/_update/1?pretty" \
-u elastic:ndgndg91 -k \
-H 'Content-Type: application/json'  -d '
{
  "doc": {
    "title": "hello ealsticsearch!~"
  }
}
'