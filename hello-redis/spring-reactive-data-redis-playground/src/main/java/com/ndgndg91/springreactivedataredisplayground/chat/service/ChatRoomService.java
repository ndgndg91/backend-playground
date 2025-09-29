package com.ndgndg91.springreactivedataredisplayground.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService implements WebSocketHandler {

    private final RedissonReactiveClient client;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var room = getRoom(session);
        RTopicReactive topic = client.getTopic(room, StringCodec.INSTANCE);
        RListReactive<String> history = this.client.getList("history:" + room, StringCodec.INSTANCE);

        // subscribe
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(msg -> history.add(msg).then(topic.publish(msg)))
                .doOnError(e -> log.error("error when receive", e))
                .doFinally(s -> log.info("Subscribe finally {} ", s))
                .subscribe();

        // publisher
        Flux<WebSocketMessage> flux = topic.getMessages(String.class)
                .startWith(history.iterator())
                .map(session::textMessage)
                .doOnError(e -> log.error("error when receive", e))
                .doFinally(s -> log.info("Publisher finally {} ", s));


        return session.send(flux);
    }

    private String getRoom(WebSocketSession session) {
        URI uri = session.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }
}
