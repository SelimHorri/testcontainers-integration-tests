package com.selimhorri.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class WebTestClientIntegrationTest extends AbstractTestSharedContainer {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void givenName_whenNameMatchIgnoringCase_thenEmployeeShouldBeFound() {
		this.webTestClient
				.get()
				.uri("/SElim")
				.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.exchange()
				.expectBody(Employee.class).value(e -> {
					assertThat(e).isNotNull();
					assertThat(e.getId()).isNotNull();
					assertThat(e.getName()).isEqualTo("selim");
				});
	}
	
	@Test
	void givenFindAllApiUrl_whenReturnList_thenListSizeShouldMatch() {
		
		this.webTestClient
				.get()
				.uri("/")
				.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.exchange()
				.expectBody(List.class).value(l -> {
					jsonPath("$.size()", is(l.size()));
				});
		
	}
	
	
	
}









