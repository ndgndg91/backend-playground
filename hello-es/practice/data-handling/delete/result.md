```shell
ndgndg91@namdong-gil-ui-MacBookPro practice % curl -i -X DELETE "https://localhost:9200/my_index/_doc/1" \
-u elastic:ndgndg91 -k
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 141

{
  "_index":"my_index",
  "_id":"1",
  "_version":3,
  "result":"deleted",
  "_shards":{
    "total":2,
    "successful":2,
    "failed":0
  },
  "_seq_no":6,
  "_primary_term":1
}
```