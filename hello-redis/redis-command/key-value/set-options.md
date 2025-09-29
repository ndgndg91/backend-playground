## Set Options
- xx - key 가 존재할 경우 set
<pre>
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> set a b xx
(nil)
127.0.0.1:6379> get a
(nil)
</pre>

- nx - key 가 존재하지 않을 경우 set
<pre>
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> set a b nx
OK
127.0.0.1:6379> get a
"b"
</pre>