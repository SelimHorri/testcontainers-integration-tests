package com.selimhorri.app;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
class ApiResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd-MM-yyyy__HH:mm:ss:SSSSSS")
	private final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.systemDefault());
	private final Integer totalResult;
	private final HttpStatus httpStatus;
	private final String status;
	private final T responseBody;
	
}

@RestController
@RequiredArgsConstructor
class EmployeeResource {
	
	private final EmployeeRepository employeeRepository;
	
	@GetMapping
	public List<Employee> findAll() {
		return this.employeeRepository.findAll()
				.stream()
					.distinct()
					.collect(Collectors.toUnmodifiableList());
	}
	
	@GetMapping("/custom")
	public ResponseEntity<ApiResponse<List<Employee>>> findAllWithApiResponse() {
		final var employees = this.employeeRepository.findAll()
				.stream()
					.distinct()
					.collect(Collectors.toUnmodifiableList());
		return ResponseEntity.ok(new ApiResponse<>(employees.size(), HttpStatus.OK, "success".toUpperCase(), employees));
	}
	
	@GetMapping("/{name}")
	public Employee findByName(@PathVariable("name") final String name) {
		return this.employeeRepository.findByNameIgnoreCase(name.strip())
				.orElseThrow(NoSuchElementException::new);
	}
	
}










