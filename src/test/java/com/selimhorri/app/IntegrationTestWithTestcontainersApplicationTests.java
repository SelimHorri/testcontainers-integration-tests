package com.selimhorri.app;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
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
	void givenListOfEmployees_whenSizeMatched_thenSizeShouldMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(this.employeeRepository.findAll().size())));
	}
	
	@Test
	void givenName_whenNameMatchIgnoringCase_thenEmployeeShouldBeFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/Selim"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			// .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("selim")));
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("selim"));
	}
	
	
	
}







