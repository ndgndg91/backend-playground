```shell
% curl -i -X GET "https://localhost:9200/my_index/_doc/1?pretty" \
-u elastic:ndgndg91 -k
HTTP/1.1 200 OK
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 253

{
  "_index" : "my_index",
  "_id" : "1",
  "_version" : 1,
  "_seq_no" : 0,
  "_primary_term" : 1,
  "found" : true,
  "_source" : {
    "title" : "hello world",
    "views" : 1234,
    "public" : true,
    "created" : "2025-10-04T14:05:01.234Z"
  }
}     
```