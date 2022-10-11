package com.automobilepartnership.domain.counsel.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounselRepository extends JpaRepository<Counsel, Long>, CounselCustomRepository {

    List<Counsel> findByEmployeeId(String employeeId);
}