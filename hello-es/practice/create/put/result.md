```shell
HTTP/1.1 201 Created
Location: /my_index/_doc/1
X-elastic-product: Elasticsearch
content-type: application/json
content-length: 202

{
  "_index" : "my_index",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}

```