package com.example.reactive.aspect;

import com.example.reactive.repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * @author Octavian Krody, octavian.krody@sap.com
 * @since 10/07/2018
 */
@Aspect
@Component
public class ContextAspect {
  @Around("execution(reactor.core.publisher.Mono+ com.example.reactive.controller.UserController.*(..))")
  public Mono<?> addContextMono(ProceedingJoinPoint joinPoint) {
    try {
      return ((Mono<?>) joinPoint.proceed())
          .subscriberContext(Context.of(UserRepository.CONTEXT_CLIENT_KEY, getClient()));
    } catch (Throwable throwable) {
      return Mono.error(throwable);
    }
  }

  @Around("execution(reactor.core.publisher.Flux+ com.example.reactive.controller.UserController.*(..))")
  public Flux<?> addContextFlux(ProceedingJoinPoint joinPoint) {
    try {
      return ((Flux<?>) joinPoint.proceed())
          .subscriberContext(Context.of(UserRepository.CONTEXT_CLIENT_KEY, getClient()));
    } catch (Throwable throwable) {
      return Flux.error(throwable);
    }
  }

  private WebClient getClient() {
    return WebClient.builder()
        .baseUrl("http://localhost:9999")
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
        .build();
  }
}
