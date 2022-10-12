package com.automobilepartnership.domain.counsel.dto;

import com.automobilepartnership.domain.counsel.persistence.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EmployeeResponseDto {

    private Long id;
    private String name;
    private Integer age;
    private String residence;
    private Integer count;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public EmployeeResponseDto(Employee employee) {
        id = employee.getId();
        name = employee.getBaseInfo().getName();
        age = employee.getBaseInfo().getAge();
        residence = employee.getBaseInfo().getResidence();
        count = employee.getCount();
        createdDate = employee.getCreatedDate();
        lastModifiedDate = employee.getLastModifiedDate();
    }
}