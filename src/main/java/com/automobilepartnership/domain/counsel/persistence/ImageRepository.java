package com.automobilepartnership.domain.counsel.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByCounsel(Counsel counsel);
}