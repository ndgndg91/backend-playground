## Increase Decrease Command

- value is integer
<pre>
127.0.0.1:6379> set a 1
OK
127.0.0.1:6379> INCR a
(integer) 2
127.0.0.1:6379> get a
"2"
127.0.0.1:6379> INCR a
(integer) 3
127.0.0.1:6379> get a
"3"

127.0.0.1:6379> set a 100
OK
127.0.0.1:6379> INCR a
(integer) 101
127.0.0.1:6379> get a
"101"
</pre>

- value is not a integer, use INCRBYFLOAT. but there is no DECRBYFLOAT.
<pre>
127.0.0.1:6379> set a 1.01
OK
127.0.0.1:6379> INCR a
(error) ERR value is not an integer or out of range
127.0.0.1:6379> INCRBYFLOAT a 0.1
"1.11"
127.0.0.1:6379> INCRBYFLOAT a -0.2
"0.91"
</pre>

- INCRBY command
<pre>
127.0.0.1:6379> set a 100
OK
127.0.0.1:6379> INCRBY a 20
(integer) 120
127.0.0.1:6379> DECRBY a 10
(integer) 110

</pre>

- value is not a number
<pre>
127.0.0.1:6379> set a b
OK
127.0.0.1:6379> INCR a
(error) ERR value is not an integer or out of range
</pre>

- when key not exists, create new key and set value 0 and increase 1
<pre>
127.0.0.1:6379> INCR not-exists-key
(integer) 1
127.0.0.1:6379> get not-exists-key
"1"
127.0.0.1:6379> INCR not-exists-key
(integer) 2
127.0.0.1:6379> INCR not-exists-key
(integer) 3
127.0.0.1:6379> INCR not-exists-key
(integer) 4
127.0.0.1:6379> INCR not-exists-key
(integer) 5
127.0.0.1:6379> get not-exists-key
"5"
127.0.0.1:6379> keys *
1) "not-exists-key"
</pre>

- when key not exists, create new key and set value and decrease 1
<pre>
127.0.0.1:6379> decr new
(integer) -1
127.0.0.1:6379> keys *
1) "new"
127.0.0.1:6379> decr new
(integer) -2
</pre>