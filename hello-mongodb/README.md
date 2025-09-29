# Hello mongodb

## start mongodb docker
```
docker run --name ndg-mongo -v ~/data:/data/db -d -p 27017:27017 mongo
```

## mongosh 
```
docker exec -it ndg-mongo mongosh
```