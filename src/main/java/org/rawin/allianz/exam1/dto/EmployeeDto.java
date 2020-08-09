package org.rawin.allianz.exam1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

public class EmployeeDto implements Validator<EmployeeDto> {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean validated() {
        return validate(this);
    }

    public boolean validate(EmployeeDto employeeDto) {
        return !(StringUtils.isEmpty(employeeDto.firstName) || StringUtils.isEmpty(employeeDto.lastName));
    }

    @Override
    public String toString() {
        return super.toString() + "[id=" + this.id + ",firstName=" + firstName + ",lastName=" + lastName + "]";
    }
}
