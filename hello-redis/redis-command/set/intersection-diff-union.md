# Example
<pre>
127.0.0.1:6379> SADD skill:java 1 2 3 4
(integer) 4
127.0.0.1:6379> SADD skill:js 2 3 4
(integer) 3
127.0.0.1:6379> SADD skill:aws 4 5 6
(integer) 3
127.0.0.1:6379> SADD candidate:criminal 4 5 6
(integer) 3
</pre>

# Intersection
- 교집합
- SINTER {key} [key...]
<pre>
127.0.0.1:6379> SINTER skill:java skill:js skill:aws
1) "4"
</pre>

# Diff
- 합집합 - 교집합
- SDIFF {key} [key...]
<pre>
127.0.0.1:6379> SDIFF skill:java skill:js skill:aws
1) "1"
127.0.0.1:6379> SDIFF skill:java candidate:criminal
1) "1"
2) "2"
3) "3"
</pre>

# Union
- 합집합
- SUNION {key} [key...]
<pre>
127.0.0.1:6379> SUNION skill:java skill:js skill:aws
1) "1"
2) "2"
3) "3"
4) "4"
5) "5"
6) "6"
</pre>

# Intersection store
- 교집합으로 새로운 set 생성
- SINTERSTORE {destination} key [key...] 
<pre>
127.0.0.1:6379> SINTERSTORE java-js skill:java skill:js
(integer) 3
127.0.0.1:6379> SMEMBERS java-js
1) "2"
2) "3"
3) "4"
127.0.0.1:6379> keys *
1) "skill:js"
2) "skill:aws"
3) "skill:java"
4) "candidate:criminal"
5) "java-js"
</pre>

# Diff store
- 합집합 - 교집합으로 새로운 set 생성
- SDIFFSTORE {destination} key [key...]
<pre>
127.0.0.1:6379> SDIFFSTORE java-js-diff skill:java skill:js
(integer) 1
127.0.0.1:6379> keys *
1) "skill:js"
2) "skill:aws"
3) "skill:java"
4) "candidate:criminal"
5) "java-js"
6) "java-js-diff"
127.0.0.1:6379> SMEMBERS java-js-diff
1) "1"
</pre>

# Union store
- 합집합으로 새로운 set 생성
- SUNIONSTORE {destination} key [key...]
<pre>
127.0.0.1:6379> SUNIONSTORE java-js-union skill:java skill:js
(integer) 4
127.0.0.1:6379> SMEMBERS java-js-union
1) "1"
2) "2"
3) "3"
4) "4"

</pre>