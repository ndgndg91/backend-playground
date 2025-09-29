# replaceOne 예시

-----------
```
db.users.insertOne({
"name": "남동길",
"friends": 32,
"enemies": 2
})
var ndg = db.users.findOne({"name": "남동길"});
ndg.relationships = {"friends": ndg.friends, "enemies": ndg.enemies};
ndg.username = ndg.name
delete ndg.friends;
delete ndg.enemies;
delete ndg.name;
db.users.replaceOne({"name": "남동길"}, ndg)
```