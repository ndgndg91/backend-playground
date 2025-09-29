# Redisson Playground

- https://github.com/redisson/redisson/wiki/Table-of-Content
- https://redis.io/docs/manual/keyspace-notifications/

- Redis Client Lib for Java
- Much better abstraction compared to other libs


|       redis       |             redisson             |
|:-----------------:|:--------------------------------:|
|   SET key value   |       Bucket / AtomicLong        |
|       Hash        |  Map, MapCache, LocalCachedMap   |
|       List        | List, Queue, Deque, MessageQueue |
|        Set        |               Set                |
|     SortedSet     |    SortedSet, Priority Queue     |


- Redis AS
  - Message Queue
  - Priority Queue
- Batch
  - to save network round trip
  - reactive objects are proxy objects.
- Transaction
  - To make a set of commands atomic
  - prefer LuaScript (require knowledge on Lua).
- Pub / Sub
  - Message broadcasting
  - LocalCachedMap uses internally