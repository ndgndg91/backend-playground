# updateMany 예시

- 조건에 맞는 모든 도큐먼트 수정

---
```
db.users.insertMany([
{birthday: "10/13/1978"},
{birthday: "10/13/1978"},
{birthday: "10/13/1978"}
])
{
  acknowledged: true,
  insertedIds: {
    '0': ObjectId("653e5cff4771cdcb9f7028a6"),
    '1': ObjectId("653e5cff4771cdcb9f7028a7"),
    '2': ObjectId("653e5cff4771cdcb9f7028a8")
  }
}
db.users.updateMany({birthday: "10/13/1978"}, {$set: {gift: "Happy Birtday!"}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 3,
  modifiedCount: 3,
  upsertedCount: 0
}
```
---