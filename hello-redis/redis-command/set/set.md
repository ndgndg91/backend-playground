# Example
- SADD {key} [member ...]
- SCARD {key}
- SMEMBERS {key}
- SISMEMBER {key} {member}
- SREM {key} [member ...]
- SPOP users [count]
<pre>
127.0.0.1:6379> SADD users 1 2 3
(integer) 3
127.0.0.1:6379> SADD users 4
(integer) 1
127.0.0.1:6379> SADD users 5
(integer) 1
# set size
127.0.0.1:6379> SCARD users
(integer) 5
127.0.0.1:6379> LLEN users
(error) WRONGTYPE Operation against a key holding the wrong kind of values
# show all members
127.0.0.1:6379> SMEMBERS users
1) "1"
2) "2"
3) "3"
4) "4"
5) "5"
127.0.0.1:6379> SADD users 1
(integer) 0
127.0.0.1:6379> SADD users 2
(integer) 0
127.0.0.1:6379> SADD users 3
(integer) 0
127.0.0.1:6379> SMEMBERS users
1) "5"
2) "4"
3) "10"
4) "4.5"
5) "3"
6) "2"
7) "1"
# check member is in set
127.0.0.1:6379> SISMEMBER users 4.5
(integer) 1
127.0.0.1:6379> SISMEMBER users 8
(integer) 0
# remove member
127.0.0.1:6379> SREM users 100
(integer) 0
127.0.0.1:6379> SREM users 1
(integer) 1
127.0.0.1:6379> SMEMBERS users
1) "5"
2) "4"
3) "10"
4) "4.5"
5) "3"
6) "2"
# pop member
127.0.0.1:6379> SPOP users
"4.5"
127.0.0.1:6379> SMEMBERS users
1) "5"
2) "4"
3) "10"
4) "3"
5) "2"
# pop member 3 member
127.0.0.1:6379> SPOP users 3
1) "2"
2) "4"
3) "3"
127.0.0.1:6379> SMEMBERS users
1) "5"
2) "10"
</pre>