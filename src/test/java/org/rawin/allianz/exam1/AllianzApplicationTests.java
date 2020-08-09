package org.rawin.allianz.exam1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
class AllianzApplicationTests {

	@Autowired
	private LoginController loginController;
	@Autowired
	private EmployeeController employeeController;
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;

	@Test
	void testContextLoads() {
		assertThat(loginController).isNotNull();
		assertThat(employeeController).isNotNull();
	}

	@Test
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
	void testGetEmployeeList() {
		String urlForLogin = "http://localhost:" + port + "/login";

		// login
		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setUsername("rawin");
		loginRequestDto.setPassword("password");
		ResponseEntity<LoginResponseDto> responseForLogin = this.restTemplate.postForEntity(urlForLogin, loginRequestDto, LoginResponseDto.class);
		assertThat(responseForLogin.getStatusCodeValue()).isEqualTo(200);
		LoginResponseDto loginResponseDto = responseForLogin.getBody();
		assertThat(loginResponseDto).isNotNull();
		assertThat(loginResponseDto.getGeneratedToken()).isNotEmpty();

		String token = loginResponseDto.getGeneratedToken();
		String urlForGetEmployee = "http://localhost:" + port + "/api/employee/";
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Authorization", token);
		ResponseEntity<List<EmployeeDto>> responseForGetEmployee =
				this.restTemplate.exchange(urlForGetEmployee, HttpMethod.GET,
						new HttpEntity<>(headers), new ParameterizedTypeReference<List<EmployeeDto>>() {
						});
		assertThat(responseForGetEmployee.getStatusCodeValue()).isEqualTo(200);
		List<EmployeeDto> employeeDtoList = responseForGetEmployee.getBody();
		assertThat(employeeDtoList).isNotNull();
		assertThat(employeeDtoList).hasSize(5);
	}

}
