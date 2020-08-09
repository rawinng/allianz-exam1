package org.rawin.allianz.exam1.service;

import org.rawin.allianz.exam1.entity.Employee;
import org.rawin.allianz.exam1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public Employee findOneEmployee(Integer id) {
        return null;
    }

    @Override
    public void deleteEmployee(Integer id) {

    }

    @Override
    public Employee createEmployee(Employee employee) {
        return null;
    }
}
