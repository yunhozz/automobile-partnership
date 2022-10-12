package com.automobilepartnership.domain.counsel.service;

import com.automobilepartnership.api.dto.employee.EmployeeRequestDto;
import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.common.exception.CounselNotFoundException;
import com.automobilepartnership.common.exception.EmployeeNotFoundException;
import com.automobilepartnership.domain.counsel.dto.EmployeeResponseDto;
import com.automobilepartnership.domain.counsel.persistence.Counsel;
import com.automobilepartnership.domain.counsel.persistence.CounselRepository;
import com.automobilepartnership.domain.counsel.persistence.Employee;
import com.automobilepartnership.domain.counsel.persistence.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CounselRepository counselRepository;

    @Transactional
    public Long join(EmployeeRequestDto employeeRequestDto) {
        Employee employee = new Employee(employeeRequestDto.getName(), employeeRequestDto.getAge(), employeeRequestDto.getResidence());
        return employeeRepository.save(employee).getId();
    }

    @Transactional
    public void allocateToCounsel(Long employeeId, Long counselId) {
        Employee employee = findEmployee(employeeId);
        Counsel counsel = counselRepository.findById(counselId)
                .orElseThrow(() -> new CounselNotFoundException(ErrorCode.COUNSEL_NOT_FOUND));

        counsel.allocateEmployee(employee);
    }

    @Transactional
    public void resolveCounsel(Long employeeId, Long counselId) {
        Employee employee = findEmployee(employeeId);
        Counsel counsel = counselRepository.findById(counselId)
                .orElseThrow(() -> new CounselNotFoundException(ErrorCode.COUNSEL_NOT_FOUND));

        counsel.resolvedByEmployee(employee);
    }

    @Transactional
    public void withdraw(Long employeeId) {
        Employee employee = findEmployee(employeeId);
        employeeRepository.delete(employee);
    }

    public EmployeeResponseDto findEmployeeDto(Long employeeId) {
        return new EmployeeResponseDto(findEmployee(employeeId));
    }

    public List<EmployeeResponseDto> findEmployeeDtoList() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponseDto::new)
                .collect(Collectors.toList());
    }

    private Employee findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }
}