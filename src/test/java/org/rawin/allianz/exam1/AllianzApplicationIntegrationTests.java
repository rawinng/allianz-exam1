package org.rawin.allianz.exam1;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.rawin.allianz.exam1.controller.EmployeeController;
import org.rawin.allianz.exam1.controller.LoginController;
import org.rawin.allianz.exam1.dto.EmployeeDto;
import org.rawin.allianz.exam1.dto.LoginRequestDto;
import org.rawin.allianz.exam1.dto.LoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AllianzApplicationIntegrationTests {

	@Autowired
	private LoginController loginController;
	@Autowired
	private EmployeeController employeeController;
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;

	@Test
	@Order(1)
	void testContextLoads() {
		assertThat(loginController).isNotNull();
		assertThat(employeeController).isNotNull();
	}

	@Test
	@Order(2)
	void testLogin() {
		String url = "http://localhost:" + port + "/login";

		// login
		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setUsername("rawin");
		loginRequestDto.setPassword("password");
		ResponseEntity<LoginResponseDto> response = this.restTemplate.postForEntity(url, loginRequestDto, LoginResponseDto.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		LoginResponseDto loginResponseDto = response.getBody();
		assertThat(loginResponseDto).isNotNull();
		assertThat(loginResponseDto.getGeneratedToken()).isNotEmpty();
	}

	@Test
	@Order(3)
	void testGetEmployeeList() {
		String token = login();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Authorization", token);

		// get all
		String urlForGetEmployee = "http://localhost:" + port + "/api/employee/";
		ResponseEntity<List<EmployeeDto>> responseForGetEmployee =
				this.restTemplate.exchange(urlForGetEmployee, HttpMethod.GET,
						new HttpEntity<>(headers), new ParameterizedTypeReference<List<EmployeeDto>>() {
						});
		assertThat(responseForGetEmployee.getStatusCodeValue()).isEqualTo(200);
		List<EmployeeDto> employeeDtoList = responseForGetEmployee.getBody();
		assertThat(employeeDtoList).isNotNull();
		assertThat(employeeDtoList).hasSize(5);
	}

	@Test
	@Order(4)
	void testDeleteEmployee() {
		String token = login();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Authorization", token);

		Integer empId = 2;

		// Delete
		String urlForDelete = "http://localhost:" + port + "/api/employee/" + empId;

		ResponseEntity<Void> responseForDelete =
				this.restTemplate.exchange(urlForDelete, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
		assertThat(responseForDelete.getStatusCodeValue()).isEqualTo(200);

		// Get (Should not found since it has already been deleted)
		String urlForGet = "http://localhost:" + port + "/api/employee/" + empId;
		ResponseEntity<EmployeeDto> responseForGetEmployeeSecondTime =
				this.restTemplate.exchange(urlForGet, HttpMethod.GET, new HttpEntity<>(headers), EmployeeDto.class);
		assertThat(responseForGetEmployeeSecondTime.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	@Order(5)
	void testCompleteFlowForAnEmployee() {
		String token = login();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Authorization", token);

		// Save
		String urlForSave = "http://localhost:" + port + "/api/employee/";

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setFirstName("Rawin");
		employeeDto.setLastName("Ngamloet");

		ResponseEntity<EmployeeDto> responseForSaveEmployee =
				this.restTemplate.exchange(urlForSave, HttpMethod.POST,
						new HttpEntity<>(employeeDto, headers),
						EmployeeDto.class);
		assertThat(responseForSaveEmployee.getStatusCodeValue()).isEqualTo(200);
		EmployeeDto employeeDtoSaved = responseForSaveEmployee.getBody();
		assertThat(employeeDtoSaved).isNotNull();
		assertThat(employeeDtoSaved.getId()).isNotNull();

		Integer empId = employeeDtoSaved.getId();

		// Get
		String urlForGet = "http://localhost:" + port + "/api/employee/" + empId;
		ResponseEntity<EmployeeDto> responseForGetEmployee =
				this.restTemplate.exchange(urlForGet, HttpMethod.GET, new HttpEntity<>(headers), EmployeeDto.class);
		assertThat(responseForGetEmployee.getStatusCodeValue()).isEqualTo(200);
		EmployeeDto employeeDtoGet = responseForGetEmployee.getBody();
		assertThat(employeeDtoGet).isNotNull();
		assertThat(employeeDtoGet.getId()).isNotNull();
		assertThat(employeeDtoGet.getId()).isEqualTo(empId);
		assertThat(employeeDtoGet.getFirstName()).isEqualTo("Rawin");
		assertThat(employeeDtoGet.getLastName()).isEqualTo("Ngamloet");

		// Update
		String urlForUpdate = "http://localhost:" + port + "/api/employee/" + empId;
		EmployeeDto updateEmployeeDto = new EmployeeDto();
		updateEmployeeDto.setId(empId);
		updateEmployeeDto.setFirstName("Ravin");
		updateEmployeeDto.setLastName("Ngamlert");

		ResponseEntity<EmployeeDto> responseForUpdate =
				this.restTemplate.exchange(urlForUpdate, HttpMethod.PUT, new HttpEntity<>(updateEmployeeDto, headers), EmployeeDto.class);

		assertThat(responseForUpdate.getStatusCodeValue()).isEqualTo(200);
		EmployeeDto employeeDtoUpdate = responseForUpdate.getBody();
		assertThat(employeeDtoUpdate).isNotNull();
		assertThat(employeeDtoUpdate.getId()).isNotNull();
		assertThat(employeeDtoUpdate.getId()).isEqualTo(employeeDtoSaved.getId());
		assertThat(employeeDtoUpdate.getFirstName()).isEqualTo("Ravin");
		assertThat(employeeDtoUpdate.getLastName()).isEqualTo("Ngamlert");

		// Delete
		String urlForDelete = "http://localhost:" + port + "/api/employee/" + empId;

		ResponseEntity<Void> responseForDelete =
				this.restTemplate.exchange(urlForDelete, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
		assertThat(responseForDelete.getStatusCodeValue()).isEqualTo(200);

		// Get (Should not found since it has already been deleted)
		ResponseEntity<EmployeeDto> responseForGetEmployeeSecondTime =
				this.restTemplate.exchange(urlForGet, HttpMethod.GET, new HttpEntity<>(headers), EmployeeDto.class);
		assertThat(responseForGetEmployeeSecondTime.getStatusCodeValue()).isEqualTo(404);
	}

	private String login() {
		// Login
		String urlForLogin = "http://localhost:" + port + "/login";
		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setUsername("rawin");
		loginRequestDto.setPassword("password");
		ResponseEntity<LoginResponseDto> responseForLogin = this.restTemplate.postForEntity(urlForLogin, loginRequestDto, LoginResponseDto.class);
		assertThat(responseForLogin.getStatusCodeValue()).isEqualTo(200);
		LoginResponseDto loginResponseDto = responseForLogin.getBody();
		assertThat(loginResponseDto).isNotNull();
		assertThat(loginResponseDto.getGeneratedToken()).isNotEmpty();
		return loginResponseDto.getGeneratedToken();
	}
}
