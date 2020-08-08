package org.rawin.allianz.exam1.repository;

import org.rawin.allianz.exam1.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
