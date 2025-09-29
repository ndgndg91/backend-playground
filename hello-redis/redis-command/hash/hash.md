## example
its like HashMap
<pre>
127.0.0.1:6379> HSET user:1 name sam age 10 city seoul
(integer) 3
127.0.0.1:6379> keys *
1) "user:1"
127.0.0.1:6379> GET user:1
(error) WRONGTYPE Operation against a key holding the wrong kind of value
127.0.0.1:6379> TYPE user:1
hash
127.0.0.1:6379> HGET user:1
(error) ERR wrong number of arguments for 'hget' command
127.0.0.1:6379> HGET user:1 name
"sam"
127.0.0.1:6379> HGET user:1 age
"10"
127.0.0.1:6379> HGET user:1 city
"seoul"
127.0.0.1:6379> HGETALL user:1
1) "name"
2) "sam"
3) "age"
4) "10"
5) "city"
6) "seoul"
127.0.0.1:6379> HSET user:2 birthYear 1991 status active
(integer) 2
127.0.0.1:6379> HGETALL user:2
1) "birthYear"
2) "1991"
3) "status"
4) "active"
127.0.0.1:6379> EXPIRE user:2 10
(integer) 1
127.0.0.1:6379> TTL user:2
(integer) 3
127.0.0.1:6379> TTL user:2
(integer) 1
127.0.0.1:6379> TTL user:2
(integer) -2
127.0.0.1:6379> keys *
1) "user:1"
127.0.0.1:6379> HKEYS user:1
1) "name"
2) "age"
3) "city"
127.0.0.1:6379> HVALS user:1
1) "sam"
2) "10"
3) "seoul"
127.0.0.1:6379> HEXISTS user:1 status
(integer) 0
127.0.0.1:6379> HEXISTS user:1 age
(integer) 1
127.0.0.1:6379> HDEL user:1 age
(integer) 1
127.0.0.1:6379> HGETALL user:1
1) "name"
2) "sam"
3) "city"
4) "seoul"
127.0.0.1:6379> del user:1
(integer) 1
127.0.0.1:6379> keys *
(empty array)
</pre>