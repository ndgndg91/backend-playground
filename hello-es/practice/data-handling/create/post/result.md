```shell
% curl -i -X POST "https://localhost:9200/my_index/_doc?pretty" \
-u elastic:ndgndg91 -k \
-H 'Content-Type: application/json'  -d '
{
    "title": "hello world",
    "views": 1234,
    "public": true,
    "created": "2025-10-04T14:05:01.234Z"
}
'
HTTP/1.1 201 Created
Location: /my_index/_doc/Yxy2vZkBCGiCQNaLRxxX
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 221

{
    "_index" : "my_index",
    "_id" : "Yxy2vZkBCGiCQNaLRxxX",
    "_version" : 1,
    "result" : "created",
    "_shards" : {
        "total" : 2,
        "successful" : 2,
        "failed" : 0
    },
    "_seq_no" : 4,
    "_primary_term" : 1
}

```
