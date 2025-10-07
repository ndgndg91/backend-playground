curl -i -X PUT "https://localhost:9200/mapping_test/_doc/1" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "createdDate": "2025-10-03T20:00:00.234Z"
}
'

curl -i -X PUT "https://localhost:9200/mapping_test/_doc/1?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "createdDate": "2025-10-03T20:00:00.234Z"
}
'

curl -i -X PUT "https://localhost:9200/mapping_test/_doc/2?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "createdDate": "1759845033000"
}
'

curl -i -X POST "https://localhost:9200/mapping_test/_search?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "query": {
    "range": {
      "createdDate": {
        "gte": "2025-10-01T17:00:00.000Z",
        "lte": "2025-10-30T17:00:00.000Z"
      }
    }
  }
}
'