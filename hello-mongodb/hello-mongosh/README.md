# mongodb

--------------------
## docker 로 mongodb container 실행 및 mongosh 실행
```
docker run --name ndg-mongo -v ~/data:/data/db -d -p 27017:27017 mongo
docker exec -it ndg-mongo mongosh
```
--------------------


- [meta 예시](./meta/meat.md)
- [insert 예시](./insert/insert.md)
- [update 예시](./update/update.md)
- [upsert 예시](./upsert/upsert.md)
- [delete 예시](./delete/delete.md)
- [array 예시](./array/array.md)