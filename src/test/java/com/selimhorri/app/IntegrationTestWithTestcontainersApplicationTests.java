package com.selimhorri.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class IntegrationTestWithTestcontainersApplicationTests {
	
	@Container
	private static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:5.7.33")
			.withUsername("test")
			.withPassword("test")
			.withDatabaseName("dbtest");
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	void givenFindAllApiUrl_whenReturnList_thenListSizeShouldMatch() throws Exception {
		this.mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", CoreMatchers.is(this.employeeRepository.findAll().size())));
	}
	
	@Test
	void givenName_whenNameMatchIgnoringCase_thenEmployeeShouldBeFound() throws Exception {
		this.mockMvc.perform(get("/Selim"))
			.andExpect(status().isOk())
			// .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("selim")));
			.andExpect(jsonPath("$.name").value("selim"));
	}
	
	@Test
	void givenFindAllWithApiResponse_whenReturnList_thenObjectShouldFound() throws Exception {
		this.mockMvc
				.perform(get("/custom"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalResult", CoreMatchers.is(3)))
				.andExpect(jsonPath("$.status", CoreMatchers.is("success".toUpperCase())))
				.andExpect(jsonPath("$.responseBody.size()", CoreMatchers.is(3)));
	}
	
	
	
}







