# Example
- RPUSH {key} [elements...]
- LPUSH {key} [elements...]
- LLEN {key}
<pre>
127.0.0.1:6379> RPUSH users same mike jake
(integer) 3
127.0.0.1:6379> KEYS *
1) "users"
127.0.0.1:6379> GET users
(error) WRONGTYPE Operation against a key holding the wrong kind of value
127.0.0.1:6379> type users
list
127.0.0.1:6379> LLEN users
(integer) 3
127.0.0.1:6379> LRANGE users 0 -1
1) "same"
2) "mike"
3) "jake"
127.0.0.1:6379> LPOP users
"same"
127.0.0.1:6379> LRANGE users 0 -1
1) "mike"
2) "jake"
127.0.0.1:6379> RPOP users
"jake"
127.0.0.1:6379> LRANGE users 0 -1
1) "mike"
127.0.0.1:6379> LPOP users
"mike"
127.0.0.1:6379> LPOP users
(nil)
127.0.0.1:6379> LRANGE users 0 -1
(empty array)
127.0.0.1:6379> LLEN users
(integer) 0
</pre>