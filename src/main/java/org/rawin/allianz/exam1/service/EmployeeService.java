package org.rawin.allianz.exam1.service;

import org.rawin.allianz.exam1.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findEmployee();
    Employee updateEmployee(Employee employee);
    Employee findOneEmployee(Integer id);
    void deleteEmployee(Integer id);
    Employee createEmployee(Employee employee);
}
