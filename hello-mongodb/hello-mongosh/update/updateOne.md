# updateOne 예시

----
- filter 조건에 맞는 첫 번째 도큐먼트 갱신


## updateOne $incr
- 원자적 갱신 연산자
- 이미 존재하는 키의 값을 변경하거나 새 키를 생성
- int, long, double, decimal 타입 값에만 사용 가능

----------
```
db.pageviews.insertOne({
"_id": ObjectId("653de5f27ac72954efac02c9"),
"url":"www.example.com",
"pageviews":52
})
{
  acknowledged: true,
  insertedId: ObjectId("653de5f27ac72954efac02c9")
}

db.pageviews.updateOne({"url":"www.example.com"},{"$inc" : {"pageviews": 1}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}

db.pageviews.findOne({"_id": ObjectId("653de5f27ac72954efac02c9")})
{
  _id: ObjectId("653de5f27ac72954efac02c9"),
  url: 'www.example.com',
  pageviews: 53
}
db.pageviews.updateOne({"url":"www.example.com"},{"$inc" : {"pageviews": -13}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.pageviews.findOne({ "_id": ObjectId("653de5f27ac72954efac02c9") })
{
  _id: ObjectId("653de5f27ac72954efac02c9"),
  url: 'www.example.com',
  pageviews: 40
}
```

## updateOne $set
- $set 필드가 존재하지 않으면 새 필드가 생성된다.
- $set 데이터형 변경 가능.

----------
```
db.users.insertOne({
"name": "joe",
"age":31,
"sex":"male",
"location": "Wisconsin"
})
{
  acknowledged: true,
  insertedId: ObjectId("653dec397ac72954efac02ca")
}
db.users.updateOne({"_id": ObjectId("653dec397ac72954efac02ca")},
{"$set":{"favorite book": "War and Peace"}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.users.findOne({ _id: ObjectId("653dec397ac72954efac02ca")})
{
  _id: ObjectId("653dec397ac72954efac02ca"),
  name: 'joe',
  age: 31,
  sex: 'male',
  location: 'Wisconsin',
  'favorite book': 'War and Peace'
}
db.users.updateOne({"_id": ObjectId("653dec397ac72954efac02ca")},
{"$set":{"favorite book": "Green Eggs and Ham"}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.users.findOne({ _id: ObjectId("653dec397ac72954efac02ca")})
{
  _id: ObjectId("653dec397ac72954efac02ca"),
  name: 'joe',
  age: 31,
  sex: 'male',
  location: 'Wisconsin',
  'favorite book': 'Green Eggs and Ham'
}
db.users.updateOne({_id: ObjectId("653dec397ac72954efac02ca")}, 
{"$set": {"favorite book": ["Cat's Cradle", "Foundation Trilogy", "Ender's Game"]}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.users.findOne({ _id: ObjectId("653dec397ac72954efac02ca")})
{
  _id: ObjectId("653dec397ac72954efac02ca"),
  name: 'joe',
  age: 31,
  sex: 'male',
  location: 'Wisconsin',
  'favorite book': [ "Cat's Cradle", 'Foundation Trilogy', "Ender's Game" ]
}

```
## updateOne $unset
- $unset 키와 값 모두 제거

-----------
```
db.users.findOne({ _id: ObjectId("653dec397ac72954efac02ca")})
{
  _id: ObjectId("653dec397ac72954efac02ca"),
  name: 'joe',
  age: 31,
  sex: 'male',
  location: 'Wisconsin',
  'favorite book': [ "Cat's Cradle", 'Foundation Trilogy', "Ender's Game" ]
}
db.users.updateOne({ _id: ObjectId("653dec397ac72954efac02ca")},
{"$unset": {"favorite book": 1}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.users.findOne({ _id: ObjectId("653dec397ac72954efac02ca")})
{
  _id: ObjectId("653dec397ac72954efac02ca"),
  name: 'joe',
  age: 31,
  sex: 'male',
  location: 'Wisconsin'
}
```
----

## 내장 Document updateOne

----
```
db.blog.posts.insertOne({
title: "A Blog Post",
content: "abc",
author: {
    name: "joe",
    email: "joe@example.com"
}
})
{
  acknowledged: true,
  insertedId: ObjectId("653dfc427ac72954efac02cb")
}
db.blog.posts.updateOne({"author.name": "joe"}, {"$set": {"author.name": "joe schmoe"}})
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.blog.posts.findOne({_id: ObjectId("653dfc427ac72954efac02cb")})
{
  _id: ObjectId("653dfc427ac72954efac02cb"),
  title: 'A Blog Post',
  content: 'abc',
  author: { name: 'joe schmoe', email: 'joe@example.com' }
}
```