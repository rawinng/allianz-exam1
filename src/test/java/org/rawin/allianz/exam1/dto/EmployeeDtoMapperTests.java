package org.rawin.allianz.exam1.dto;

import org.junit.jupiter.api.Test;
import org.rawin.allianz.exam1.entity.Employee;

import static org.assertj.core.api.Assertions.*;
public class EmployeeDtoMapperTests {
    @Test
    public void testMapFromEmployee() {
        Employee employee = new Employee();
        employee.setLastName("Rawin");
        employee.setFirstName("Ngamloet");

        EmployeeDto employeeDto = EmployeeDtoMapper.INSTANCE.fromEmployee(employee);
        assertThat(employeeDto.getFirstName()).isEqualTo("Ngamloet");
        assertThat(employeeDto.getLastName()).isEqualTo("Rawin");
        assertThat(employeeDto.getId()).isNull();
    }
}
