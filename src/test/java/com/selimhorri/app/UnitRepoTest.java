package com.selimhorri.app;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest(showSql = true)
class UnitRepoTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	private Employee savedEmployee;
	
	@BeforeEach
	void setUp() {
		savedEmployee = this.testEntityManager.persist(Employee.builder().name("Yasmine").build());
	}
	
	@Test
	void givenNewEmployee_whenPersisted_thenShouldBeFound() {
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isNotNull();
		assertThat(savedEmployee.getName()).isEqualTo("Yasmine");
	}
	
	@Test
	void givenName_whenFindByName_thenNameShouldMatch() {
		
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isNotNull();
		assertThat(savedEmployee.getName()).isEqualTo("Yasmine");
		
		final Employee findByNameIgnoreCase = this.employeeRepository.findByNameIgnoreCase(savedEmployee.getName())
				.orElseGet(Employee::new);
		
		assertThat(findByNameIgnoreCase).isNotNull();
		assertThat(findByNameIgnoreCase.getName())
				.isNotBlank()
				.isNotEqualTo("Yasmine".toLowerCase())
				.isEqualToIgnoringCase("Yasmine");
	}
	
	
	
}









