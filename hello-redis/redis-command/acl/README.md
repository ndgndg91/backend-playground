# AUTH

- by default no auth
- <b>default</b> user has admin privileges. <b>no password</b> is set for this user.
- Redis Version < 6
    - No username
    - only password
    - Command: AUTH [PASSWORD]
- Redis Version >= 6
    - Username and Password
    - ACL - users with specific privileges.

| command                            | description                      |
|------------------------------------|----------------------------------|
| acl list                           | current user                     |
| acl whoami                         | show current user                | 
| acl setuser [username] >[password] | creates an user with password    |
| acl setuser [username] nopass      | creates an user with no password |
| acl setuser [username] on          | enables the user                 |
| acl setuser [username] off         | disables the user                |
| acl deluser [username]             | removes the user                 |
| auth [username] [password]         | authenticate user                | 


```
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
127.0.0.1:6379> acl deluser default
(error) ERR The 'default' user cannot be removed
127.0.0.1:6379> acl whoami
"default"
127.0.0.1:6379> acl setuser sam >secret
OK
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
2) "user sam off #2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b resetchannels -@all"
127.0.0.1:6379> acl deluser sam
(integer) 1
127.0.0.1:6379> acl setuser sam >ehdrlf on
OK
127.0.0.1:6379> ACL list
1) "user default on nopass ~* &* +@all"
2) "user sam on #b8feb749b3b7417ebcfea1a540de882a2479ff1050a703054c48cfbcf18961c5 resetchannels -@all"
127.0.0.1:6379> acl setuser sam off
OK
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
2) "user sam off #b8feb749b3b7417ebcfea1a540de882a2479ff1050a703054c48cfbcf18961c5 resetchannels -@all"
127.0.0.1:6379> acl setuser sam on
OK
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
2) "user sam on #b8feb749b3b7417ebcfea1a540de882a2479ff1050a703054c48cfbcf18961c5 resetchannels -@all"
127.0.0.1:6379> auth sam ehdrlf
OK
127.0.0.1:6379> acl whoami
(error) NOPERM this user has no permissions to run the 'acl|whoami' command
127.0.0.1:6379> auth default
(error) ERR AUTH <password> called without any password configured for the default user. Are you sure your configuration is correct?
127.0.0.1:6379> auth default nopass
OK
127.0.0.1:6379> acl whoami
"default"
127.0.0.1:6379> acl list
1) "user default on nopass ~* &* +@all"
2) "user sam on #b8feb749b3b7417ebcfea1a540de882a2479ff1050a703054c48cfbcf18961c5 resetchannels -@all"
```


# ACL
- acl setuser [username] >[password] on

| commands              | description                            |
|-----------------------|----------------------------------------|
| allcommands / +@all   | access to all commands in redis        |
| -get, -set...         | no access to get, set commands         |
| +@set, +@hash, +@list | access set and hash related commands   |
| allkeys / ~*          | access to all the keys in redis        |
| -numbers:*            | access to keys starting with numbers:  |


## Give all command to sam
```
127.0.0.1:6379> ACL SETUSER sam >ehdrlf on allcommands allkeys
OK
127.0.0.1:6379> ACL LIST
1) "user default on nopass ~* &* +@all"
2) "user sam on #b8feb749b3b7417ebcfea1a540de882a2479ff1050a703054c48cfbcf18961c5 ~* resetchannels +@all"
127.0.0.1:6379> ACL LIST
1) "user default on nopass ~* &* +@all"
2) "user sam on #b8feb749b3b7417ebcfea1a540de882a2479ff1050a703054c48cfbcf18961c5 ~* resetchannels +@all"
127.0.0.1:6379> ACL WHOAMI
"sam"
```

## Revoke get command to sma
```
127.0.0.1:6379> auth default nopass
OK
127.0.0.1:6379> ACL WHOAMI
"default"
127.0.0.1:6379> ACL SETUSER sam -get
OK
127.0.0.1:6379> AUTH sam ehdrlf
OK
127.0.0.1:6379> ACL LIST
1) "user default on nopass ~* &* +@all"
2) "user sam on #b8feb749b3b7417ebcfea1a540de882a2479ff1050a703054c48cfbcf18961c5 ~* resetchannels +@all -get"
127.0.0.1:6379> KEYS *
1) "a"
2) "users"
3) "city-map"
4) "product-local-cache-map"
5) "product:visit:20221010"
6) "product"
7) "weather"
127.0.0.1:6379> GET a
(error) NOPERM this user has no permissions to run the 'get' command
127.0.0.1:6379> AUTH default nopass
OK
127.0.0.1:6379> get a
"1"
```

## Revoke @dangerous command - flushall
```
127.0.0.1:6379> ACL WHOAMI
"default"
127.0.0.1:6379> ACL SETUSER sam -@dangerous
OK
127.0.0.1:6379> ACL LIST
1) "user default on nopass ~* &* +@all"
2) "user sam on #b8feb749b3b7417ebcfea1a540de882a2479ff1050a703054c48cfbcf18961c5 ~* resetchannels +@all -@dangerous -get"
127.0.0.1:6379> AUTH sam ehdrlf
OK
127.0.0.1:6379> ACL WHOAMI
"sam"
127.0.0.1:6379> ACL list
(error) NOPERM this user has no permissions to run the 'acl|list' command
127.0.0.1:6379> FLUSHALL
(error) NOPERM this user has no permissions to run the 'flushall' command
```

## Set password for default user
```
127.0.0.1:6379> ACL WHOAMI
"default"
127.0.0.1:6379> CONFIG SET requirepass ehdrlf
OK
127.0.0.1:6379> AUTH default nopass
(error) WRONGPASS invalid username-password pair or user is disabled.

% redis-cli 
127.0.0.1:6379> KEYS *
(error) NOAUTH Authentication required.
127.0.0.1:6379> AUTH default ehdrlf
OK
127.0.0.1:6379> KEYS *
1) "a"
2) "users"
3) "city-map"
4) "product-local-cache-map"
5) "product:visit:20221010"
6) "product"
7) "weather"
```

## Set nopass for default user
```
127.0.0.1:6379> CONFIG SET requirepass ""
OK
% redis-cli
127.0.0.1:6379> KEYS *
1) "a"
2) "users"
3) "city-map"
4) "product-local-cache-map"
5) "product:visit:20221010"
6) "product"
7) "weather"
```