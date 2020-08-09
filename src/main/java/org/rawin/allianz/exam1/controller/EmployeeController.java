package org.rawin.allianz.exam1.controller;

import org.rawin.allianz.exam1.dto.EmployeeDto;
import org.rawin.allianz.exam1.dto.EmployeeDtoMapper;
import org.rawin.allianz.exam1.entity.Employee;
import org.rawin.allianz.exam1.entity.EmployeeMapper;
import org.rawin.allianz.exam1.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<List<EmployeeDto>> list() {
		List<Employee> employeeList = employeeService.findEmployee();
		List<EmployeeDto> employeeDtoList = employeeList
				.stream()
				.map(EmployeeDtoMapper.INSTANCE::fromEmployee)
				.collect(Collectors.toList());
		return ResponseEntity.ok(employeeDtoList);
	}

	@PostMapping
	public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
		LOGGER.info("*** Get employee dto = {}", employeeDto);
		if(employeeDto == null || !employeeDto.validated() || employeeDto.getId() != null) {
			return ResponseEntity.badRequest().build();
		}

		Employee employee = EmployeeMapper.INSTANCE.fromEmployeeDto(employeeDto);

		Employee employeeFromDb = employeeService.createEmployee(employee);
		EmployeeDto employeeDtoFromDb = EmployeeDtoMapper.INSTANCE.fromEmployee(employeeFromDb);
		return ResponseEntity.ok(employeeDtoFromDb);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> get(@PathVariable("id") Integer id) {
		if(id == null) {
			return ResponseEntity.badRequest().build();
		}
		Employee employee = employeeService.findOneEmployee(id);

		if(employee == null) {
			return ResponseEntity.notFound().build();
		}

		EmployeeDto employeeDto = EmployeeDtoMapper.INSTANCE.fromEmployee(employee);
		return ResponseEntity.ok(employeeDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> update(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto) {
		LOGGER.info("*** Get employee id = {}", id);
		LOGGER.info("*** Get employee dto = {}", employeeDto);
		if( id == null || employeeDto == null || !employeeDto.validated()) {
			return ResponseEntity.badRequest().build();
		}
		if( employeeDto.getId() != null && !employeeDto.getId().equals(id)) {
			return ResponseEntity.badRequest().build();
		};

		// validate existing
		Employee employeeExisting = employeeService.findOneEmployee(id);
		if(employeeExisting == null) {
			return ResponseEntity.notFound().build();
		}

		employeeDto.setId(id); // settle id

		Employee employee = EmployeeMapper.INSTANCE.fromEmployeeDto(employeeDto);
		Employee employeeFromDb = employeeService.updateEmployee(employee);
		EmployeeDto employeeDtoFromDb = EmployeeDtoMapper.INSTANCE.fromEmployee(employeeFromDb);
		return ResponseEntity.ok(employeeDtoFromDb);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		if(id ==null) {
			return ResponseEntity.badRequest().build();
		}

		// find
		Employee employeeExisting = employeeService.findOneEmployee(id);
		if(employeeExisting == null) {
			return ResponseEntity.notFound().build();
		}

		employeeService.deleteEmployee(id);
		return ResponseEntity.ok().build();
	}
}
