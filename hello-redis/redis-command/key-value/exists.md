## Exists Command
key 가 존재할 경우 1 없을 경우 0 을 반환
<pre>
127.0.0.1:6379> KEYS *
(empty array)
127.0.0.1:6379> SET a b
OK
127.0.0.1:6379> EXISTS a
(integer) 1
127.0.0.1:6379> EXISTS b
(integer) 0

</pre>