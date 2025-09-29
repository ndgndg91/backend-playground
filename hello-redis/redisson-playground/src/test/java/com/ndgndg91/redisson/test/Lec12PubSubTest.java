package com.ndgndg91.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RPatternTopicReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.listener.PatternMessageListener;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

// Broad Casting
public class Lec12PubSubTest extends BaseTest {

    //127.0.0.1:6379> PUBLISH slack-room hell
    //(integer) 2
    @Test
    void pub() {
        // given
        RTopicReactive topic = client.getTopic("slack-room", StringCodec.INSTANCE);

        // when
        Mono<Void> pub = topic.publish("hi").repeat(10).then();

        // then
        StepVerifier.create(pub)
                .verifyComplete();
    }

    @Test
    void sub1() {
        // given
        RTopicReactive topic = client.getTopic("slack-room", StringCodec.INSTANCE);

        // when
        topic.getMessages(String.class)
                .doOnError(System.out::println)
                .doOnNext(System.out::println)
                .subscribe();

        // then
        sleep(600_000);
    }

    @Test
    void sub2() {
        // given
        RTopicReactive topic = client.getTopic("slack-room", StringCodec.INSTANCE);

        // when
        topic.getMessages(String.class)
                .doOnError(System.out::println)
                .doOnNext(System.out::println)
                .subscribe();

        // then
        sleep(600_000);
    }

    @Test
    void subPattern() {
        // given
        RPatternTopicReactive patternTopic = client.getPatternTopic("slack-room*", StringCodec.INSTANCE);

        // when
        patternTopic.addListener(String.class, new PatternMessageListener<String>() {
            @Override
            public void onMessage(CharSequence pattern, CharSequence topic, String msg) {
                System.out.println(pattern + ":" + topic + ":" + msg);
            }
        }).subscribe();

        // then
        sleep(600_000);
    }
}
