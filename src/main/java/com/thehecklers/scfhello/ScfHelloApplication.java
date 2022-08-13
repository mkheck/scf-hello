package com.thehecklers.scfhello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@SpringBootApplication
public class ScfHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScfHelloApplication.class, args);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> uppercase() {
        return flux -> flux.map(String::toUpperCase);
    }

    @Bean
    public Function<Mono<User>, Mono<Greeting>> greet() {
        return mono -> mono.map(user -> new Greeting("Hello, " + user.getName() + "!"));
    }

    @Bean
    public Function<Mono<String>, Mono<String>> reverse() {
        return mono -> mono.map(s -> new StringBuffer(s).reverse().toString());
    }

}

//@Component
//class Hello implements Function<Mono<User>, Mono<Greeting>> {
//    public Mono<Greeting> apply(Mono<User> mono) {
//        return mono.map(user -> new Greeting("Hello, " + user.getName() + "!\n"));
//    }
//}

class User {
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Greeting {
    private String message;

    public Greeting() {
    }

    public Greeting(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}