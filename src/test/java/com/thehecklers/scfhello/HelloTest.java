package com.thehecklers.scfhello;

//import com.thehecklers.scfhello.Greeting;
//import com.thehecklers.scfhello.User;
import com.microsoft.azure.functions.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloTest {

    @Test
    public void test() {
        Mono<Greeting> result = new Greet().apply(Mono.just(new User("foo")));
        assertThat(result.block().getMessage()).isEqualTo("Hello, foo!\n");
    }

    @Test
    public void start() {
        FunctionInvoker<User, Greeting> handler = new FunctionInvoker<>(Greet.class);
//        HelloHandler handler = new HelloHandler();
        Greeting result = handler.handleRequest(new User("foo"), new ExecutionContext() {
            @Override
            public Logger getLogger() {
                return Logger.getLogger(HelloTest.class.getName());
            }

            @Override
            public String getInvocationId() {
                return "id1";
            }

            @Override
            public String getFunctionName() {
                return "greet";
            }
        });
        handler.close();
        assertThat(result.getMessage()).isEqualTo("Hello, foo!\n");
    }
}