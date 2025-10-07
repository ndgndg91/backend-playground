# 명시적 맵핑 - 한 번 지정하면 사실상 변경이 불가능
curl -i -X PUT "https://localhost:9200/mapping_test?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "mappings": {
    "properties": {
      "createdDate": {
        "type": "date",
        "format": "strict_date_time || epoch_millis"
      },
      "keywordString": {
        "type": "keyword"
      },
      "textString": {
        "type": "text"
      }
    }
  },
  "settings": {
    "number_of_shards": 2,
    "number_of_replicas": 3
  }
}
'

curl -i -X GET "https://localhost:9200/mapping_test?pretty" -u elastic:ndgndg91 -k

# index 생성 후 맵핑 추가
curl -i -X PUT "https://localhost:9200/mapping_test/_mapping?pretty" -u elastic:ndgndg91 -k \
-H "Content-type: application/json" -d '
{
  "properties": {
    "longValue": {
      "type": "long"
    }
  }
}
'