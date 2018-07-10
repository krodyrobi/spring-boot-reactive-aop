package com.example.reactive;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ReactiveApplicationTests {
  @Rule
	public WireMockRule wireMockRule = new WireMockRule(9999);

	@Autowired
	private WebTestClient webClient;

	@Before
  public void setUp() {
    stubFor(get(urlEqualTo("/users"))
        .withHeader(HttpHeaders.ACCEPT, equalTo(APPLICATION_JSON_UTF8_VALUE))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
            .withBody("[{\"name\": \"test\"}]")));
  }


	@Test
	public void fetchAll() {
		webClient
				.get()
				.uri("/users")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].name").isEqualTo("test");
	}

}
