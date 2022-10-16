package com.automobilepartnership.domain.counsel.persistence.repository;

import com.automobilepartnership.domain.counsel.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}