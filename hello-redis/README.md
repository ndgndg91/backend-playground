# Hello Redis
## What will learn
- Scalable Performant Reactive Microservices
- Redis & its various data structure
- Redis + Spring Webflux
  - Caching
  - Pub/Sub + WebSocket
  - LeaderBoard
  - GeoSpatial
  - Message Queue
- Distributed Lock

## Course Structure
- Redis - Crash Course
  - Mostly redis-cli & redis commands
- Redisson - Crash Course
  - Message Queue
  - Priority Queue
- Redis Spring WebFlux Integration
  - Caching with Reactive MicroServices
  - Cache Synchronization
  - Performance testing with JMeter to understand the performance improvement
- Providing Realtime Updates with Redis Leaderboard
- Chat Application - WebSocket with Redis
- Geospatial - Restaurant Locator

## Redis
- In-Memory DB(No-Sql)
- No table/collection
- Simple Key and Values are simple string
- Any binary sequence can be the key.
- Max 2^32 keys ( > 4 billion keys )
  - list can hold 2^32 items
  - set can hold 2^32 items
- Multi purpose Data-structure server
- Use cases
  - caching
  - pub/sub
  - message queue
  - streaming
  - geospatial


### Key/Values
- SET / GET
- Expire the keys
  - Session store
  - Game
- Redis can notify us when keys are expired

### Hash
- A group of related key-vale pairs stored as an object
- Represents 1 record in a relational DB table / a document from Mong collection
- We can not expire individual fields in a Hash
- But we can expire the hash

### List
- An ordered collection of items
- Queue / Stack
- Can be used as Message Queue for communication among MSA

### Set
- An unordered collection of unique items
- O(1) constant time performance to check the existence of an item
- Intersection / Union / Diff

### Sorted Set
- An ordered collection of unique items
- Same as Set + with some scores for individual item
- It can be used to track top product by rating etc
  - StackOverFlow uses for top voted answer
- Can also be used a Priority Queue

### Transaction
- Use WATCH, MULTI, EXEC, DISCARD
- To run a group of commands as a transaction