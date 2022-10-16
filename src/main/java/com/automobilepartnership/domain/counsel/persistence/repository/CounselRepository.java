package com.automobilepartnership.domain.counsel.persistence.repository;

import com.automobilepartnership.domain.counsel.persistence.entity.Counsel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CounselRepository extends JpaRepository<Counsel, Long>, CounselCustomRepository {

    List<Counsel> findByEmployeeId(String employeeId);

    @Query("select case when c.employeeId != null then true else false end from Counsel c where c.id = :id")
    boolean isEmployeeIdExist(@Param("id") Long counselId);
}