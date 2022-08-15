package com.thehecklers.scfhello;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class Greet implements Function<Mono<User>, Mono<Greeting>> {
    private final PersonRepository repo;

    public Greet(PersonRepository repo) {
        this.repo = repo;
    }

    public Mono<Greeting> apply(Mono<User> monoUser) {
        //return monoUser.map(user -> new Greeting("Hello, " + user.getName() + "!"));
        var firstPerson = repo.findAll().get(0);
        System.out.println("First person: " + firstPerson.toString());
        return monoUser.map(user -> new Greeting("Hello, " + firstPerson.getName() + "!"));
    }
}
