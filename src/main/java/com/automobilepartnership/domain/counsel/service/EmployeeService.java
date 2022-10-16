package com.automobilepartnership.domain.counsel.service;

import com.automobilepartnership.api.dto.employee.EmployeeRequestDto;
import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.domain.counsel.service.exception.CounselNotFoundException;
import com.automobilepartnership.domain.counsel.service.exception.EmployeeDifferentException;
import com.automobilepartnership.domain.counsel.service.exception.EmployeeNotFoundException;
import com.automobilepartnership.domain.counsel.dto.EmployeeResponseDto;
import com.automobilepartnership.domain.counsel.persistence.entity.Counsel;
import com.automobilepartnership.domain.counsel.persistence.repository.CounselRepository;
import com.automobilepartnership.domain.counsel.persistence.entity.Employee;
import com.automobilepartnership.domain.counsel.persistence.repository.EmployeeRepository;
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

        // employee id 값이 null 이거나 값이 다른 경우
        if (counsel.getEmployeeId() == null || !counsel.getEmployeeId().equals(employeeId)) {
            throw new EmployeeDifferentException(ErrorCode.EMPLOYEE_DIFFERENT);
        }
        counsel.resolvedByEmployee(employee);
    }

    @Transactional
    public void withdraw(Long employeeId) {
        Employee employee = findEmployee(employeeId);
        employeeRepository.delete(employee);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto findEmployeeDto(Long employeeId) {
        return new EmployeeResponseDto(findEmployee(employeeId));
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> findEmployeeDtoList() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Employee findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }
}