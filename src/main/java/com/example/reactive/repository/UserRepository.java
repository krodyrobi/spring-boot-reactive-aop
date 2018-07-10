package com.example.reactive.repository;

import com.example.reactive.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Octavian Krody, octavian.krody@sap.com
 * @since 10/07/2018
 */
@Repository
public class UserRepository {
  public static String CONTEXT_CLIENT_KEY = "UserClient";

  public Flux<User> all() {
    return
        getClient()
            .flatMapMany(client ->
              client
                  .get()
                  .uri("/users")
                  .retrieve()
                  .bodyToFlux(User.class)
            );
  }

  private Mono<WebClient> getClient() {
    return Mono
        .subscriberContext()
        .map(context -> context.get(CONTEXT_CLIENT_KEY));
  }
}
