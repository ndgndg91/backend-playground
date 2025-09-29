# Delete 예시

---

## 전체 collection 비우기

------------
```
db.movies.insertOne({"_id": 1, "title": "삭제 대상"})
{ acknowledged: true, insertedId: 1 }
db.movies.deleteOne({"_id": 1})
{ acknowledged: true, deletedCount: 1 }

```
------------