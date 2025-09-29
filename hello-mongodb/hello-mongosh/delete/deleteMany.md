# deleteMany 예시

---

```
db.movies.insertMany([
{"title": "deleteManay 대상1", "year": "1999"},
{"title": "deleteManay 대상2", "year": "1999"},
{"title": "deleteManay 대상3", "year": "1999"}
])
{
  acknowledged: true,
  insertedIds: {
    '0': ObjectId("653e593e4771cdcb9f70289d"),
    '1': ObjectId("653e593e4771cdcb9f70289e"),
    '2': ObjectId("653e593e4771cdcb9f70289f")
  }
}

db.movies.deleteMany({"year": "1999"})
{ acknowledged: true, deletedCount: 3 }

```
---

## 전체 컬렉션 삭제
---
```
db.movies.drop()
db.movies.deleteMany({})
```
---