# array 예시

---

## 요소 추가하기
### $push

- 배열 끝에 추가
---
```
db.blog.posts.findOne()
{
  _id: ObjectId("653dfc427ac72954efac02cb"),
  title: 'A Blog Post',
  content: 'abc',
  author: { name: 'joe schmoe', email: 'joe@example.com' }
}
db.blog.posts.updateOne({"title": "A Blog Post"}, {$push : {comments : {name: "joe", email: "joe@example.com", content: "nice post."}}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.blog.posts.findOne()
{
  _id: ObjectId("653dfc427ac72954efac02cb"),
  title: 'A Blog Post',
  content: 'abc',
  author: { name: 'joe schmoe', email: 'joe@example.com' },
  comments: [ { name: 'joe', email: 'joe@example.com', content: 'nice post.' } ]
}

```
### $each $push

- 한번에 여러건 배열로 push 
---
```
db.stock.ticker.updateOne({_id: "GOOG"}, {$push: {hourly: {$each : [562.776, 562.790, 559.123]}}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.stock.ticker.findOne()
{ _id: 'GOOG', hourly: [ 562.776, 562.79, 559.123 ] }

```

### 배열을 집합으로 사용하기 

- $ne
---
```
db.papers.updateOne({"authors cited": {$ne: "Richie"}}, {$push : {"author cited" : "Richie"}})
```
---
- $addToSet
---
```
db.users.insertOne({username: "joe", emails: ["joe@example.com", "joe@gmail.com", "joe@naver.com"]})
{
  acknowledged: true,
  insertedId: ObjectId("653e4e144771cdcb9f70289a")
}
db.users.updateOne({ _id: ObjectId("653e4e144771cdcb9f70289a")}, 
{$addToSet : {emails : "joe@gmail.com"}}) 
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 0,
  upsertedCount: 0
}
db.users.updateOne({ _id: ObjectId("653e4e144771cdcb9f70289a")}, 
{$addToSet : {emails : "joe@hotmail.com"}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.users.findOne({_id: ObjectId("653e4e144771cdcb9f70289a")})
{
  _id: ObjectId("653e4e144771cdcb9f70289a"),
  username: 'joe',
  emails: [
    'joe@example.com',
    'joe@gmail.com',
    'joe@naver.com',
    'joe@hotmail.com'
  ]
}

```
---
- $addToSet $each
---
```
db.users.updateOne({_id: ObjectId("653e4e144771cdcb9f70289a")},
{$addToSet: {emails: {$each : ["joe@php.net", "joe@python.org"]}}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.users.findOne({_id: ObjectId("653e4e144771cdcb9f70289a")})
{
  _id: ObjectId("653e4e144771cdcb9f70289a"),
  username: 'joe',
  emails: [
    'joe@example.com',
    'joe@gmail.com',
    'joe@naver.com',
    'joe@hotmail.com',
    'joe@php.net',
    'joe@python.org'
  ]
}
```
---
## 요소 제거하기
- $pop
- $pull - 조건에 일치하는 모든 요소를 제거한다.
---
```
db.lists.insertOne({todo: ["dishes", "laundry", "dry cleaning", "works", "meetings"]})
{
  acknowledged: true,
  insertedId: ObjectId("653e4f7e4771cdcb9f70289b")
}
db.lists.updateOne({_id: ObjectId("653e4f7e4771cdcb9f70289b")},{$pop: {todo: 1}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.lists.findOne({_id: ObjectId("653e4f7e4771cdcb9f70289b")})
{
  _id: ObjectId("653e4f7e4771cdcb9f70289b"),
  todo: [ 'dishes', 'laundry', 'dry cleaning', 'works' ]
}
db.lists.updateOne({_id: ObjectId("653e4f7e4771cdcb9f70289b")},{$pop: {todo: -1}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.lists.findOne({_id: ObjectId("653e4f7e4771cdcb9f70289b")})
{
  _id: ObjectId("653e4f7e4771cdcb9f70289b"),
  todo: [ 'laundry', 'dry cleaning', 'works' ]
}
db.lists.updateOne({_id: ObjectId("653e4f7e4771cdcb9f70289b")},{$pull: {todo: "dry cleaning"}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.lists.findOne({_id: ObjectId("653e4f7e4771cdcb9f70289b")})
{
  _id: ObjectId("653e4f7e4771cdcb9f70289b"),
  todo: [ 'laundry', 'works' ]
}

```
---

## 배열의 위치 기반 변경
- index 는 0 부터 시작한다.
- "$" 사용
- $set 은 첫 번째로 일치하는 요소만 갱신한다.
- arrayFilters - 조건에 맞는 배열 요소를 갱신

---
```
db.blog.posts.insertOne({
content: "...",
comments: [
{comment: "good post", author: "john", votes:0},
{comment: "i thought it was too short", author: "claire", votes:3},
{comment: "free watches", author: "alice", votes:-5},
{comment: "vacation getaways", author: "lynn", votes:-7},
]
})
{
  acknowledged: true,
  insertedId: ObjectId("653e51124771cdcb9f70289c")
}
db.blog.posts.updateOne({_id:ObjectId("653e51124771cdcb9f70289c")},{$inc: {"comments.0.votes" : 1}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.blog.posts.findOne({_id:ObjectId("653e51124771cdcb9f70289c")})
{
  _id: ObjectId("653e51124771cdcb9f70289c"),
  content: '...',
  comments: [
    { comment: 'good post', author: 'john', votes: 1 },
    {
      comment: 'i thought it was too short',
      author: 'claire',
      votes: 3
    },
    { comment: 'free watches', author: 'alice', votes: -5 },
    { comment: 'vacation getaways', author: 'lynn', votes: -7 }
  ]
}
db.blog.posts.updateOne({"comments.author": "john"},
{$set: {"comments.$.author": "Jim"}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.blog.posts.findOne({_id:ObjectId("653e51124771cdcb9f70289c")})
{
  _id: ObjectId("653e51124771cdcb9f70289c"),
  content: '...',
  comments: [
    { comment: 'good post', author: 'Jim', votes: 1 },
    {
      comment: 'i thought it was too short',
      author: 'claire',
      votes: 3
    },
    { comment: 'free watches', author: 'alice', votes: -5 },
    { comment: 'vacation getaways', author: 'lynn', votes: -7 }
  ]
}
db.blog.posts.updateOne({_id: ObjectId("653e51124771cdcb9f70289c")},
{$set: {"comments.$[elem].hidden" : true}},
{arrayFilters: [ {"elem.votes": {$lte: -5}} ]})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.blog.posts.findOne({_id:ObjectId("653e51124771cdcb9f70289c")})
{
  _id: ObjectId("653e51124771cdcb9f70289c"),
  content: '...',
  comments: [
    { comment: 'good post', author: 'Jim', votes: 1 },
    {
      comment: 'i thought it was too short',
      author: 'claire',
      votes: 3
    },
    {
      comment: 'free watches',
      author: 'alice',
      votes: -5,
      hidden: true
    },
    {
      comment: 'vacation getaways',
      author: 'lynn',
      votes: -7,
      hidden: true
    }
  ]
}
```
---