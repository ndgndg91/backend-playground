package com.ndgndg91.springreactivedataredisplayground.fib.controller;

import com.ndgndg91.springreactivedataredisplayground.fib.service.FibService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class FibController {

    private final FibService service;

    @GetMapping("/fib/{index}")
    public Mono<Long> getFib(@PathVariable int index) {
        return Mono.fromSupplier(() -> service.getFib(index));
    }

    @GetMapping("/fib/{index}/clear")
    public Mono<Void> clearCache(@PathVariable int index) {
        return Mono.fromRunnable(() -> service.clearCache(index));
    }

}
