# spring grpc demo
- https://docs.spring.io/spring-grpc/reference/getting-started.html
```
./gradlew build
```
아래 class 사용
- /build/generated/source/proto/main/grpc
- /build/generated/source/proto/main/java


```
grpcurl -d '{"name":"Hi"}' -plaintext localhost:9090 Simple.SayHello
```
