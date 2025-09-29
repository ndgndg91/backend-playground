# Example
- ZADD {key} [NX|XX] [GT|LT] [CH] [INCR] score member [score member ...]
- ZCARD {key}
- ZINCRBY {key} increment member
- ZRANGE {key} start stop
- ZREVRANGE {key} start stop
<pre>
127.0.0.1:6379> ZADD products 0 books
(integer) 1
127.0.0.1:6379> ZADD products 0 iphone 0 tv
(integer) 2
127.0.0.1:6379> keys *
1) "products"
127.0.0.1:6379> type products
zset
127.0.0.1:6379> ZCARD products
(integer) 3
127.0.0.1:6379> ZINCRBY products 1 books
"1"
127.0.0.1:6379> ZINCRBY products 1 iphone
"1"
127.0.0.1:6379> ZINCRBY products 1 tv
"1"
127.0.0.1:6379> ZINCRBY products 1 iphone
"2"
127.0.0.1:6379> ZRANGE products 0 -1
1) "books"
2) "tv"
3) "iphone"
127.0.0.1:6379> ZINCRBY products 1 iphone
"3"
127.0.0.1:6379> ZINCRBY products 1 books
"2"
127.0.0.1:6379> ZRANGE products 0 -1
1) "tv"
2) "books"
3) "iphone"
127.0.0.1:6379> ZRANGE products 0 -1 withscores
1) "tv"
2) "1"
3) "books"
4) "2"
5) "iphone"
6) "3"
127.0.0.1:6379> ZRANGE products -1 -1
1) "iphone"
127.0.0.1:6379> ZRANGE products 0 0
1) "tv"
127.0.0.1:6379> ZRANGE products 0 0 rev withscores
1) "iphone"
2) "3"
127.0.0.1:6379> ZRANGE products 0 1 rev withscores
1) "iphone"
2) "3"
3) "books"
4) "2"
</pre>

- show rank
<pre>
127.0.0.1:6379> ZRANK products books
(integer) 1
127.0.0.1:6379> ZRANK products iphone
(integer) 2
127.0.0.1:6379> ZRANK products tv
(integer) 0
127.0.0.1:6379> ZREVRANK products iphone
(integer) 0
</pre>

- show score
<pre>
127.0.0.1:6379> ZSCORE products iphone
"3"
127.0.0.1:6379> ZSCORE products tv
"1"
</pre>

- priority queue
<pre>
127.0.0.1:6379> ZPOPMAX products
1) "iphone"
2) "3"
127.0.0.1:6379> ZPOPMAX products
1) "books"
2) "2"
127.0.0.1:6379> ZCARD products
(integer) 1
</pre>