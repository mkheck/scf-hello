package com.thehecklers.scfhello;

//import com.thehecklers.scfhello.Greeting;
//import com.thehecklers.scfhello.User;
import com.microsoft.azure.functions.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelloTest {
    @MockBean
    private PersonRepository repo;

    @Test
    public void test() {
        Mockito.when(repo.findAll()).thenReturn(List.of(new Person("Fred"), new Person("Wilma")));
        Mono<Greeting> result = new Greet(repo).apply(Mono.just(new User("foo")));
//        assertThat(result.block().getMessage()).isEqualTo("Hello, foo!");
        assertThat(result.block().getMessage()).isEqualTo("Hello, Fred!");
    }

    @Test
    public void start() {
        Mockito.when(repo.findAll()).thenReturn(List.of(new Person("Fred"), new Person("Wilma")));

        FunctionInvoker<User, Greeting> handler = new FunctionInvoker<>(Greet.class);
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
        //assertThat(result.getMessage()).isEqualTo("Hello, foo!");
        assertThat(result.getMessage()).isEqualTo("Hello, Fred!");
    }
}