# Single Thread
  <img width="1907" alt="스크린샷 2022-09-28 오후 9 55 25" src="https://user-images.githubusercontent.com/19872667/192783934-48cb1b0a-cdb7-4e83-a898-740dc83f0fa5.png">

- MULTI : start transaction
- EXEC or DISCARD
- WATCH key [key ...]

<pre>
127.0.0.1:6379> set user:1:balance 1
OK
127.0.0.1:6379> set user:2:balance 0
OK
</pre>

## Without TX
<pre>
# connection1
127.0.0.1:6379> DECR user:1:balance
(integer) 0
127.0.0.1:6379> INCR user:2:balance
(integer) 1
# connection2
127.0.0.1:6379> DECR user:1:balance
(integer) -1
127.0.0.1:6379> INCR user:2:balance
(integer) 2
</pre>

## With TX Without Watch
<pre>
# Tx1
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379(TX)> DECR user:1:balance
QUEUED
127.0.0.1:6379(TX)> INCR user:2:balance
QUEUED
# Tx2
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379(TX)> DECR user:1:balance
QUEUED
127.0.0.1:6379(TX)> INCR user:2:balance
QUEUED
# Tx1
127.0.0.1:6379(TX)> EXEC
1) (integer) 0
2) (integer) 1
# Tx2
127.0.0.1:6379(TX)> EXEC
1) (integer) -1
2) (integer) 2
</pre>


## With TX
<pre>
Tx1
127.0.0.1:6379> WATCH user:1:balance user:2:balance
OK
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379(TX)> DECR user:1:balance
QUEUED
127.0.0.1:6379(TX)> INCR user:2:balance
QUEUED
127.0.0.1:6379(TX)> EXEC
1) (integer) 0
2) (integer) 1
Tx2
127.0.0.1:6379> WATCH user:1:balance user:2:balance
OK
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379(TX)> DECR user:1:balance
QUEUED
127.0.0.1:6379(TX)> INCR user:2:balance
QUEUED
127.0.0.1:6379(TX)> EXEC
(nil)
</pre>

- DISCARD
<pre>
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379(TX)> DECR user:1:balance
QUEUED
127.0.0.1:6379(TX)> INCR user:2:balance
QUEUED
127.0.0.1:6379(TX)> DISCARD
OK
</pre>