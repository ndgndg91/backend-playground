```shell
% curl -i -X POST "https://localhost:9200/my_index/_update/1" \
-u elastic:ndgndg91 -k \
-H 'Content-Type: application/json'  -d '
{
  "doc": {
    "title": "hello ealsticsearch!~"
  }
}
'
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 141

{
  "_index":"my_index",
  "_id":"1",
  "_version":2,
  "result":"updated",
  "_shards":{
    "total":2,
    "successful":2,
    "failed":0
  },
  "_seq_no":5,
  "_primary_term":1
}
```