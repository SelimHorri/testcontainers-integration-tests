package com.selimhorri.app;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class IntegrationTestWithTestcontainersApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IntegrationTestWithTestcontainersApplication.class, args);
	}
	
	
	
}

@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
}

interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	Optional<Employee> findByNameIgnoreCase(final String name);
	
}

@RestController
@RequiredArgsConstructor
class EmployeeResource {
	
	private final EmployeeRepository employeeRepository;
	
	@GetMapping
	public List<Employee> findAll() {
		return this.employeeRepository.findAll();
	}
	
	@GetMapping("/{name}")
	public Employee findByName(@PathVariable("name") final String name) {
		return this.employeeRepository.findByNameIgnoreCase(name.strip())
				.orElseThrow(NoSuchElementException::new);
	}
	
}










