package com.ndgndg91.springreactivedataredisplayground.chat.config;

import com.ndgndg91.springreactivedataredisplayground.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ChatRoomSocketConfig {

    private final ChatRoomService service;

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, WebSocketHandler> map = Map.of(
                "/chat", service
        );
        return new SimpleUrlHandlerMapping(map, -1);
    }
}
