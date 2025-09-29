# Metadata

---
## database 목록 조회 collection 정보 조회
```

db.adminCommand( { listDatabases: 1 } )
{
  databases: [
    { name: 'admin', sizeOnDisk: Long("40960"), empty: false },
    { name: 'config', sizeOnDisk: Long("98304"), empty: false },
    { name: 'local', sizeOnDisk: Long("40960"), empty: false },
    { name: 'ndg', sizeOnDisk: Long("237568"), empty: false }
  ],
  totalSize: Long("417792"),
  totalSizeMb: Long("0"),
  ok: 1
}
```

## collection 이름 목록 조회

---
```
db.getCollectionNames()
[ 'blog.posts', 'pageviews', 'users', 'movies' ]
```

## collection 정보 목록 조회

---
```
db.getCollectionInfos()
[
  {
    name: 'blog.posts',
    type: 'collection',
    options: {},
    info: {
      readOnly: false,
      uuid: new UUID("0f201426-52cf-4b1b-8d0c-8aadfcbc5e96")
    },
    idIndex: { v: 2, key: { _id: 1 }, name: '_id_' }
  },
  {
    name: 'pageviews',
    type: 'collection',
    options: {},
    info: {
      readOnly: false,
      uuid: new UUID("1de9b552-828a-4f70-9a0e-52137b6e393d")
    },
    idIndex: { v: 2, key: { _id: 1 }, name: '_id_' }
  },
  {
    name: 'users',
    type: 'collection',
    options: {},
    info: {
      readOnly: false,
      uuid: new UUID("25db1354-56bf-4956-a430-8aeee48a5863")
    },
    idIndex: { v: 2, key: { _id: 1 }, name: '_id_' }
  },
  {
    name: 'movies',
    type: 'collection',
    options: {},
    info: {
      readOnly: false,
      uuid: new UUID("4f0ee6a4-a658-4967-a3e1-c05a1ff26f7f")
    },
    idIndex: { v: 2, key: { _id: 1 }, name: '_id_' }
  }
]
```

## 현재 사용 중인 데이터베이스 삭제

---
```
db.dropDatabase()
```