package com.example.reactive.controller;

import com.example.reactive.model.User;
import com.example.reactive.repository.UserRepository;
import com.example.reactive.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;

/**
 * @author Octavian Krody, octavian.krody@sap.com
 * @since 10/07/2018
 */
@RestController
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {this.userService = userService;}

  @GetMapping("/users")
  public Mono<List<User>> list() {
    return userService.all();
  }
}
