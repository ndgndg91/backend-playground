# JVM option guide

## 현재 기본으로 설정되고 있는 메모리 설정 
- 각 어플리케이션 Dockerfile 에서 `JAVA_MAX_MEM_RATIO` 환경변수는 run-java.sh 에서 50% 로 설정되고 있음.
```Dockerfile
ENV JAVA_INIT_MEM_RATIO ${JAVA_INIT_MEM_RATIO:-50}
```

## run-java-options 가이드
- `run-java.sh` 파일을 사용하여 컨테이너 환경에서 application 을 시작합니다.
  1. 메모리 관리:
     - 메모리 제한 확인: 컨테이너의 메모리 제한을 읽어 CONTAINER_MAX_MEMORY 환경 변수에 저장합니다.
     - 힙 메모리 설정: JAVA_MAX_MEM_RATIO 및 JAVA_INIT_MEM_RATIO 환경 변수에 따라 최대 힙 메모리(-Xmx)와 초기 힙 메모리(-Xms)를 계산하여 설정합니다.
     - C2 컴파일러 비활성화: 컨테이너 메모리가 300MB 이하일 경우 C2 컴파일러를 비활성화하여 메모리 사용을 줄입니다.
  2. CPU 관리:
     - 코어 제한 확인: 컨테이너의 CPU 코어 제한을 읽어 CONTAINER_CORE_LIMIT 환경 변수에 저장합니다.
     - 병렬 GC 스레드 설정: JVM 버전 10 미만일 경우 -XX:ParallelGCThreads와 -XX:ConcGCThreads 옵션을 설정하여 GC 스레드 수를 제한합니다.
  3. 디버깅 및 진단:
     - 디버깅 옵션: JAVA_ENABLE_DEBUG, JAVA_DEBUG_ENABLE, JAVA_DEBUG 환경 변수에 따라 디버깅 옵션을 활성화합니다.
     - 단 옵션: JAVA_DIAGNOSTICS 환경 변수에 따라 진단 옵션을 설정합니다.
  4. 프록시 설정:
     - 프록시 옵션 설정: http_proxy, https_proxy, no_proxy 환경 변수에 따라 Java의 프록시 설정을 구성합니다.
  5. 자동 JAR 파일 탐지:
     - 자동 JAR 파일 탐지: JAVA_APP_JAR 변수가 설정되지 않은 경우, 실행 디렉토리에서 JAR 파일을 자동으로 탐지합니다.
  6. Java 옵션 관리:
     - 사용자 정의 옵션: JAVA_OPTIONS 환경 변수에 사용자 정의 Java 옵션을 포함합니다.
     - 기본 옵션: 스크립트에서 계산한 메모리, JIT, CPU, GC 관련 기본 Java 옵션을 설정합니다.
```
주요 환경 변수
1. JAVA_OPTIONS
  - Java 실행 옵션을 직접 설정할 수 있습니다.
  - 예: export JAVA_OPTIONS="-Dexample.option=true"
2. JAVA_MAX_MEM_RATIO
  - 최대 힙 메모리를 설정하는 비율(%).
  - 기본값: 300MB 이하에서는 25%, 그 이상에서는 50%.
  - 예: export JAVA_MAX_MEM_RATIO=50
3. JAVA_INIT_MEM_RATIO
  - 초기 힙 메모리를 설정하는 비율(%).
  - 기본값: 설정되지 않음.
  - 예: export JAVA_INIT_MEM_RATIO=25
4. CONTAINER_MAX_MEMORY
  - 컨테이너에 할당된 최대 메모리 (자동 설정됨).
5. CONTAINER_CORE_LIMIT
  - 컨테이너에 할당된 CPU 코어 수 (자동 설정됨).
6. JAVA_MAIN_CLASS
  - 실행할 메인 클래스를 지정합니다.
  - 예: export JAVA_MAIN_CLASS="com.example.Main"
7. JAVA_APP_JAR
  - 실행할 JAR 파일을 지정합니다.
  - 예: export JAVA_APP_JAR="app.jar"
8. JAVA_APP_NAME
  - Java 애플리케이션의 이름을 설정합니다.
  - 예: export JAVA_APP_NAME="MyApp"
9. JAVA_ENABLE_DEBUG
  - 디버깅을 활성화합니다.
  - 예: export JAVA_ENABLE_DEBUG=true
10. JAVA_DEBUG_PORT
  - 디버깅 포트를 설정합니다.
  - 기본값: 5005.
  - 예: export JAVA_DEBUG_PORT=5005
11. JAVA_DEBUG_SUSPEND
  - 디버깅 시 시작을 지연할지 여부를 설정합니다.
  - 예: export JAVA_DEBUG_SUSPEND=n
12. JAVA_DIAGNOSTICS
  - JVM 진단 옵션을 활성화합니다.
  - 예: export JAVA_DIAGNOSTICS=true
13. JAVA_CLASSPATH
  - 클래스패스를 설정합니다.
  - 예: export JAVA_CLASSPATH="lib/*"
14. JAVA_LIB_DIR
  - 라이브러리 디렉토리를 설정합니다.
  - 기본값: JAVA_APP_DIR.
  - 예: export JAVA_LIB_DIR="/path/to/lib"
15. JAVA_APP_DIR
  - 애플리케이션 디렉토리를 설정합니다.
  - 예: export JAVA_APP_DIR="/path/to/app"
16. http_proxy / https_proxy
  - HTTP/HTTPS 프록시 설정.
  - 예: export http_proxy="http://proxy.example.com:8080"
17. no_proxy
  - 프록시를 사용하지 않을 호스트를 설정합니다.
  - 예: export no_proxy="localhost,127.0.0.1"
18. JAVA_MAJOR_VERSION
  - Java 주요 버전을 설정합니다 (자동 설정됨).
```


## Heap size 가이드
- K8S 환경 QoS 를 고려하여 메모리를 설정한다. 코인원에서 Guaranteed 혹은 Burstable 로 설정하고 있다.
- max heap size 는 컨테이너 limit 의 50% ~ 80% 로 설정 20%는 나머지 영역이 사용할 수 있도록 하기 위함.
  - 메타스페이스 
    - -XX:MaxMetaspaceSize : 메타스페이스 최대 크기 설정. 기본값 -1 로 제한이 없다. "네이티브 메모리 크기가 허용하는 만큼"
    - -Xx:MetaspaceSize: 메타스페이스 초기 크기를 바이트 단위로 지정. 해당 크기가 가득 차면 GC 가 클래스 언로딩을 시도한 다음 크기를 조정. 클래스 언로딩 후 공간이 넉넉하게 확보되면 이 값을 줄이고, 확보 하지 못했다면 적절한 값으로 증가. -XX:MaxMetaspaceSize 를 설정한 경우 해당 값을 초과할 수 없음. 
    - -XX:MinMetaspaceFreeRatio: GC 후 가장 작은 메타스페이스 여유 공간의 비율을 지정. 메타스페이스 공간이 부족해 발생하는 GC 빈도를 줄일 수 있다.
  - 스레드 스택
    - 스레드당 스택 크기 (-Xss): 기본값은 운영체제와 JVM 구현에 따라 상이. 일반적으로 리눅스에서는 1MB, 윈도우에서는 1MB ~ 2MB 사이로 설정.
  - 네이티브 메모리(direct memory)
    - 고성능 I/O 작업:
      - DirectByteBuffer는 네이티브 메모리에 직접 데이터를 저장하여 I/O 작업에서 성능을 향상시킵니다. 
      - 네트워크 소켓이나 파일 시스템과의 직접적인 데이터를 교환할 때 사용됩니다. 
    - 메모리 매핑 파일(Memory Mapped File)
      - 파일의 일부 또는 전체를 메모리에 매핑하여 파일 I/O 성능을 극대화할 수 있습니다. 
      - MappedByteBuffer 클래스를 사용하여 메모리에 매핑된 파일을 처리합니다.
    - GC 오버헤드 감소
      - Direct Memory는 JVM 힙 메모리와 별도로 관리되므로, 대용량 데이터를 다루는 애플리케이션에서 가비지 컬렉션(GC) 오버헤드를 줄이는 데 도움이 됩니다.
    - `-XX:MaxDirectMemorySize` 옵션을 사용하여 Direct Memory 의 최대 크기를 설정할 수 있습니다. 이 옵션을 명시적으로 설정하지 않으면, 기본적으로 최대 힙 크기와 동일한 크기로 설정됩니다.
  - 코드 캐시 및 기타
    - 기본 코드 캐시 크기 (-XX:ReservedCodeCacheSize): 기본값은 약 48MB. JIT 컴파일러가 생성한 네이티브 코드를 저장하는 공간.
  - 컨테이너 오버헤드
- min heap size 는 Burstable 의 경우 컨테이너 request 의 100% (-Xmx)
- min heap size 는 Guaranteed 의 경우 컨테이너 limit 의 50% (-Xmx)


## GC 선택 가이드
- 최대 100MB 정도 작은 데이터 -> 시리얼 컬렉터 
  - 작은 힙 크기: 일반적으로 최대 힙 크기가 100MB 이하인 경우 시리얼 컬렉터 사용을 고려할 수 있습니다.
```Dockerfile
ENV JAVA_OPTIONS -XX:+UseSerialGC
```
- 단일 프로세서만 이용하고 일시 정지 시간 관련 제약이 없다면 -> 시리얼 컬렉터
  - 멀티스레드 오버헤드가 없으므로 시리얼 컬렉터가 적합합니다.
```Dockerfile
ENV JAVA_OPTIONS -XX:+UseSerialGC
```
- 최대 성능이 중요하고, 지연 시간 관련 제약이 없거나 1초 이상의 지연 시간도 허용 -> 기본 컬렉터 or 패럴렐 컬렉터
  - 기본 컬렉터: HotSpot JVM 의 기본 GC는 Java 버전에 따라 다를 수 있습니다.
  - 패럴렐 컬렉터: Throughput 을 최대화하고자 할 때 사용.
```Dockerfile
ENV JAVA_OPTIONS -XX:+UseParallelGC
```
- 응답 시간 > 처리량 중요도, 일시 정지가 짧아야 한다면 -> G1
  - G1 GC: 낮은 일시 정지 시간을 목표로 하며, 비교적 큰 힙에서도 좋은 성능을 보입니다.

JDK7 부터 사용 가능. JDK9 부터 ParallelGC 를 대체하여 기본 GC
```Dockerfile 
ENV JAVA_OPTIONS -XX:+UseG1GC
```
- 응답 시간이 매우 중요하면 -> 세대 구분 ZGC 혹은 Shenandoah GC
  - ZGC: 매우 낮은 일시 정지 시간(<10ms)을 목표로 하며, 큰 힙에서도 잘 작동.
  - Shenandoah GC: ZGC 와 동일하게 <10ms 목표로 하며, 큰 힙에서도 잘 작동.

JDK15 부터 사용가능
```Dockerfile
ENV JAVA_OPTIONS  -XX:+UseZGC
```
JDK21 부터 사용가능
```Dockerfile
ENV JAVA_OPTIONS -XX:+UseZGC -XX:+ZGenerational
```
JDK11 부터 사용가능
```Dockerfile
ENV JAVA_OPTIONS -XX:+UseShenandoahGC
```


## 결론
1) request / limit memory, cpu 가능한 Guaranteed 로 설정한다.
2) -xss(ThreadStackSize) 는 default 값이 1M 이므로 특이한 경우가 아닌이상 변경하지 않는다.
3) 힙 크기가 초기 할당 크기보다 커질 때 마다 JVM 이 일시 중지 되기 때문에 xms / -xmx 을 동일한 값으로 설정한다.
   (단, 최대 힙 크기를 고정한다면 request / limit memory 의 60 ~ 80% 으로 맞추자.)
4) GC 는 jdk9 ~ 17 은 G1, jdk21 부터는 세대별 zgc 를 사용하자.

## 참고 
- https://xebia.com/blog/guide-kubernetes-jvm-integration/
- https://kubernetes.io/docs/tasks/configure-pod-container/quality-service-pod/
- https://blogs.oracle.com/javamagazine/post/java-garbage-collectors-evolution
- https://kstefanj.github.io/2021/11/24/gc-progress-8-17.html