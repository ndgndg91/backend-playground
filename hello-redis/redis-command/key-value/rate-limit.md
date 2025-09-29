## Redis Rate Limit
30 min / lives 3 game example
<pre>
127.0.0.1:6379> set user:1:lives 3 ex 1800
OK
127.0.0.1:6379> ttl user:1:lives
(integer) 1794
127.0.0.1:6379> decr user:1:lives
(integer) 2
127.0.0.1:6379> decr user:1:lives
(integer) 1
127.0.0.1:6379> decr user:1:lives
(integer) 0
</pre>