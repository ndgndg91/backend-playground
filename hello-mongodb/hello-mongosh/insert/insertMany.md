#  insertMany 예시

---

- ordered true - insert 중 중간에서 실패할 경우 이후 남은 도큐먼트는 insert 되지 않고 실패처리한다.
- ordered false - insert 중 중간에서 실패할 경우, 실패한 도큐먼트를 제외하고 mongod 에 의해 재정렬 후 insert 를 계혹샇ㄴ다. 

insertMany().
```
db.movies.insertMany([
{"title": "Dark Knight"},
{"title": "인셉션"}
])
{
  acknowledged: true,
  insertedIds: {
    '0': ObjectId("653e59604771cdcb9f7028a0"),
    '1': ObjectId("653e59604771cdcb9f7028a1")
  }
}

db.movies.insertMany([
{"title": "반지의 제왕 - 반지 원정대"},
{"title": "반지의 제왕 - 두개의 탑"},
{"title": "반지의 제왕 - 왕의 귀환"}
], {"ordered": false})
{
  acknowledged: true,
  insertedIds: {
    '0': ObjectId("653e59634771cdcb9f7028a2"),
    '1': ObjectId("653e59634771cdcb9f7028a3"),
    '2': ObjectId("653e59634771cdcb9f7028a4")
  }
}

```