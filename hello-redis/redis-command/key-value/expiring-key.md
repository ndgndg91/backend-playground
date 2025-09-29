## Expiring key
- expiring key : after 10 seconds, remove key automatically
<pre>
127.0.0.1:6379> set a b ex 10
OK
127.0.0.1:6379> keys *
1) "a"
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> set a b ex 10
OK
127.0.0.1:6379> ttl a
(integer) 9
127.0.0.1:6379> ttl a
(integer) 8
127.0.0.1:6379> ttl a
(integer) 5
127.0.0.1:6379> ttl a
(integer) 3
127.0.0.1:6379> get a
"b"
127.0.0.1:6379> ttl a
(integer) 1
127.0.0.1:6379> ttl a
(integer) -2
127.0.0.1:6379> get a
(nil)
127.0.0.1:6379> set a b ex 10
OK
127.0.0.1:6379> ttl a
(integer) 8
127.0.0.1:6379> expire a 60
(integer) 1
127.0.0.1:6379> ttl a
(integer) 59
127.0.0.1:6379> ttl a
(integer) 52
127.0.0.1:6379> expire a 600
(integer) 1
127.0.0.1:6379> ttl a
(integer) 599
</pre>

- use unix timestamp
<pre>
# 현재 22/09/25 21:55:00 근처
# 1664110800 - 22/09/05 22:00:00
127.0.0.1:6379> set a b exat 1664110800
OK
127.0.0.1:6379> ttl a
(integer) 269
</pre>

- use milliseconds
<pre>
127.0.0.1:6379> set a b px 3000
OK
127.0.0.1:6379> ttl a
(integer) 1
127.0.0.1:6379> ttl a
(integer) -2
</pre>

- same expire and change only value
<pre>
127.0.0.1:6379> set a b ex 60
OK
127.0.0.1:6379> ttl a
(integer) 58
127.0.0.1:6379> set a c keepttl
OK
127.0.0.1:6379> ttl a
(integer) 50
</pre>