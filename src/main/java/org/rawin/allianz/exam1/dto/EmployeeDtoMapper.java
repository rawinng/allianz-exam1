package org.rawin.allianz.exam1.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rawin.allianz.exam1.entity.Employee;

@Mapper
public interface EmployeeDtoMapper {
    EmployeeDtoMapper INSTANCE = Mappers.getMapper(EmployeeDtoMapper.class);

    EmployeeDto fromEmployee(Employee employee);
}
