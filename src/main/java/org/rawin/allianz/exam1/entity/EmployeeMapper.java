package org.rawin.allianz.exam1.entity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rawin.allianz.exam1.dto.EmployeeDto;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee fromEmployeeDto(EmployeeDto employeeDto);
}
