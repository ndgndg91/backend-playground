# Save data on disk

- snapshot on current data
<pre>
127.0.0.1:6379> set user:1:balance 1
OK
127.0.0.1:6379> set user:2:balance 0
OK
127.0.0.1:6379> BGSAVE
Background saving started


% cat dump.rdb 
REDIS0010�      redis-ver7.0.5�
redis-bits�@�ctime�O4cused-mem°)aof-base���user:2:balance�user:1:balance��O�Ww&h�N% 
</pre>