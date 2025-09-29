#### redis command example
- set {Key} {Value}
- get {Key}
<pre>
127.0.0.1:6379> set a b
OK
127.0.0.1:6379> get a
"b"
127.0.0.1:6379> set a 1
OK
127.0.0.1:6379> get a
"1"
127.0.0.1:6379> set a 10.234
OK
127.0.0.1:6379> get a
"10.234"
127.0.0.1:6379> set a ......big..12312312
OK
127.0.0.1:6379> get a
"......big..12312312"
127.0.0.1:6379> set true false
OK
127.0.0.1:6379> get true
"false"
127.0.0.1:6379> get True
(nil)
127.0.0.1:6379> set . -
OK
127.0.0.1:6379> get .
"-"
127.0.0.1:6379> set user:1:name same
OK
127.0.0.1:6379> set user:2:name jake
OK
127.0.0.1:6379> get user:1:name
"same"
127.0.0.1:6379> get user:2:name
"jake"
</pre>
- keys {pattern}
<pre>
127.0.0.1:6379> keys user*
1) "user:2:name"
2) "user:1:name"
</pre>
- scan {cursor}
- scan {cursor} MATCH {pattern}
- scan {cursor} MATCH {pattern} count {number}
<pre>
127.0.0.1:6379> scan 0
1) "3"
2)  1) "."
    2) "12"
    3) "4"
    4) "user:2:name"
    5) "true"
    6) "2"
    7) "1"
    8) "3"
    9) "10"
   10) "user:1:name"
   11) "a"
127.0.0.1:6379> scan 3
1) "0"
2) 1) "5"
127.0.0.1:6379> scan 0 MATCH user*
1) "3"
2) 1) "user:2:name"
   2) "user:1:name"
127.0.0.1:6379> scan 0 MATCH [0-9]
1) "3"
2) 1) "4"
   2) "2"
   3) "1"
   4) "3"
</pre>
- removing key
<pre>
127.0.0.1:6379> keys user*
1) "user:2:name"
2) "user:5name"
3) "user:4:name"
4) "user:7:name"
5) "user:1:name"
6) "user:8:name"
7) "user:6:name"
8) "user:3:name"
127.0.0.1:6379> del user:2:name
(integer) 1
127.0.0.1:6379> keys user*
1) "user:5name"
2) "user:4:name"
3) "user:7:name"
4) "user:1:name"
5) "user:8:name"
6) "user:6:name"
7) "user:3:name"
127.0.0.1:6379> keys *
 1) "user:5name"
 2) "12"
 3) "user:4:name"
 4) "2"
 5) "."
 6) "user:7:name"
 7) "user:1:name"
 8) "10"
 9) "1"
10) "5"
11) "user:8:name"
12) "user:6:name"
13) "true"
14) "4"
15) "a"
16) "3"
17) "user:3:name"
127.0.0.1:6379> flushdb
OK
127.0.0.1:6379> keys *
(empty array)

(integer) 0
</pre>