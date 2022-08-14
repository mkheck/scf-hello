package com.thehecklers.scfhello;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class Greet implements Function<Mono<User>, Mono<Greeting>> {
    public Mono<Greeting> apply(Mono<User> monoUser) {
        return monoUser.map(user -> new Greeting("Hello, " + user.getName() + "!\n"));
    }
}
