package kr.co.coinone.springgrpcdemo

import io.grpc.stub.StreamObserver
import kr.co.coinone.proto.HelloReply
import kr.co.coinone.proto.HelloRequest
import kr.co.coinone.proto.SimpleGrpc
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GrpcService: SimpleGrpc.SimpleImplBase() {
    private val logger = LoggerFactory.getLogger(GrpcService::class.java)

    override fun sayHello(
        req: HelloRequest,
        responseObserver: StreamObserver<HelloReply>
    ) {
        logger.info("Hello " + req.name)
        require(!req.getName().startsWith("error")) { "Bad name: " + req.getName() }
        if (req.getName().startsWith("internal")) {
            throw RuntimeException()
        }
        val reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName()).build()
        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }

    override fun streamHello(
        req: HelloRequest,
        responseObserver: StreamObserver<HelloReply>
    ) {
        logger.info("Hello " + req.getName())
        var count = 0
        while (count < 10) {
            val reply = HelloReply.newBuilder().setMessage("Hello(" + count + ") ==> " + req.getName()).build()
            responseObserver.onNext(reply)
            count++
            try {
                Thread.sleep(1000L)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                responseObserver.onError(e)
                return
            }
        }
        responseObserver.onCompleted()

    }
}