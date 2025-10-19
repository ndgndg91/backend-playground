# _id 지정 없이 생성
curl -i -X POST "https://localhost:9200/my_index/_doc?pretty" \
-u elastic:ndgndg91 -k \
-H 'Content-Type: application/json'  -d '
{
  "title": "hello world",
  "views": 1234,
  "public": true,
  "created": "2025-10-04T14:05:01.234Z"
}
'