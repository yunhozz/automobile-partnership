package com.automobilepartnership.domain.counsel.persistence.repository;

import com.automobilepartnership.domain.counsel.persistence.entity.Counsel;
import com.automobilepartnership.domain.counsel.persistence.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByCounsel(Counsel counsel);
}