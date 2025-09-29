# Upsert 예시

## 갱신 입력 (Upsert)
- updateOne, updateMany 의 3번째 인자에 옵션  도큐먼트를 지정

---
```
db.analytics.updateOne({url: "/blog"}, {$inc: {pageviews: 1}}, {upsert: true})
{
  acknowledged: true,
  insertedId: ObjectId("653e56469cce4786ad058c67"),
  matchedCount: 0,
  modifiedCount: 0,
  upsertedCount: 1
}
db.analytics.findOne({_id: ObjectId("653e56469cce4786ad058c67")})
{
  _id: ObjectId("653e56469cce4786ad058c67"),
  url: '/blog',
  pageviews: 1
}

```
---

- $setOnInsert 도큐먼트가 삽입 시에만 값을 설정할 수 있도록 한다.

---
```
db.users.updateOne({}, {$setOnInsert: {createdAt: new Date()}}, {upsert: true})
```
---

- save
---
```
var x = db.testcol.findOne()
x.num = 42
db.testcol.save(x)
```
---