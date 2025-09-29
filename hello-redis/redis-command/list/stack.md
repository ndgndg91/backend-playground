# Stack Example
<pre>
127.0.0.1:6379> RPUSH users 1 2
(integer) 2
127.0.0.1:6379> RPUSH users 3 4 
(integer) 4
127.0.0.1:6379> RPUSH users 5
(integer) 5
127.0.0.1:6379> RPUSH users 6
(integer) 6
127.0.0.1:6379> LLEN users
(integer) 6
127.0.0.1:6379> LRANGE users 0 -1
1) "1"
2) "2"
3) "3"
4) "4"
5) "5"
6) "6"
127.0.0.1:6379> RPOP users
"6"
127.0.0.1:6379> RPOP users
"5"
127.0.0.1:6379> RPOP users
"4"
127.0.0.1:6379> RPOP users
"3"
127.0.0.1:6379> RPOP users
"2"
127.0.0.1:6379> RPOP users
"1"
127.0.0.1:6379> RPOP users
(nil)
127.0.0.1:6379> RPUSH users 1 2 3 4
(integer) 4
127.0.0.1:6379> LPUSH users 5
(integer) 5
127.0.0.1:6379> LRANGE users 0 -1
1) "5"
2) "1"
3) "2"
4) "3"
5) "4"
</pre>