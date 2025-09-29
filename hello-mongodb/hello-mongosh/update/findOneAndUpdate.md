# 갱신한 도큐먼트 반환

- 큐를 조작하거나 원장성을 필요로 하는 작업에 사용
- findOneAndUpdate - returnNewDocument false - 도큐먼트를 갱신하기 전에 반환한다.
- findOneAndUpdate - returnNewDocument true - 도큐먼트를 갱신하고 반환한다.
---
```
ps = db.processes.findOneAndUpdate({status: "READY"},
{$set: {status: "RUNNING"}},
{sort: {priority: -1}, returnNewDocument: true})
do_something(ps)
db.processes.updateOne({_id: ps._id}, {$set:{status: "DONE"}})
```
---