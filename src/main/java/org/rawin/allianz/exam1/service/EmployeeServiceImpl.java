package org.rawin.allianz.exam1.service;

import org.rawin.allianz.exam1.entity.Employee;
import org.rawin.allianz.exam1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> employeeFromDbOpt = employeeRepository.findById(employee.getId());
        if(!employeeFromDbOpt.isPresent()) {
            throw new IllegalStateException("No employee in db");
        }
        Employee employeeFromDb = employeeFromDbOpt.get();
        employeeFromDb.setFirstName(employee.getFirstName());
        employeeFromDb.setLastName(employee.getLastName());
        return employeeRepository.save(employeeFromDb);
    }

    @Override
    public Employee findOneEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
