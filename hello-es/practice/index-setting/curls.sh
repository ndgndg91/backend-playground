curl -i -X GET "https://localhost:9200/my_index/_settings?pretty" \
-u elastic:ndgndg91 -k


curl -i -X PUT "https://localhost:9200/my_index/_settings?pretty" \
-u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "index.number_of_replicas": 3
}
'

curl -i -X PUT "https://localhost:9200/my_index/_settings?pretty" \
-u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "index.refresh_interval": "1s"
}
'

curl -i -X GET "https://localhost:9200/_cat/indices?v" -u elastic:ndgndg91 -k
curl -i -X GET "https://localhost:9200/my_index?pretty" -u elastic:ndgndg91 -k
curl -i -X GET "https://localhost:9200/my_index/_mapping?pretty" -u elastic:ndgndg91 -k


curl -i -X DELETE "https://localhost:9200/my_index" -u elastic:ndgndg91 -k

curl -i -X PUT "https://localhost:9200/my_index?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "settings": {
    "number_of_replicas": 3,
    "number_of_shards": 2
  }
}
'

curl -i -X PUT "https://localhost:9200/my_index/_doc/1?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "title": "hello world",
  "views": 1234,
  "public": true,
  "point": 4.5,
  "created": "2025-10-04T20:00:00.234Z"
}
'