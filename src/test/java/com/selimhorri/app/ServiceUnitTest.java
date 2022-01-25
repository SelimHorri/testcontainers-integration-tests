package com.selimhorri.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ServiceUnitTest {
	
	@Autowired
	private EmployeeService employeeService;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	private Employee employee;
	
	@BeforeEach
	void setUp() {
		this.employee = new Employee(null, "selim");
		when(this.employeeRepository.findByNameIgnoreCase("Selim"))
				.thenReturn(Optional.of(this.employee));
		when(this.employeeRepository.findAll())
				.thenReturn(List.of(new Employee(null, ""), 
						new Employee(null, "selim"), 
						new Employee(null, "dgdfg "), 
						new Employee(null, " maf")));
	}
	
	@Test
	void givenName_whenHittingMethod_thenEmployeeShouldBeFound() {
		var outEmployee = this.employeeService.findByNameIgnoreCase("Selim");
		assertThat(outEmployee).isNotNull();
		assertThat(outEmployee.getName()).isEqualTo(this.employee.getName());
	}
	
	@Test
	void whenFindAll_thenReturnAllRecords() {
		final var list = this.employeeService.findAll();
		assertThat(list).isNotNull();
		assertThat(list.size()).isEqualTo(4);
	}
	
	
	
}








