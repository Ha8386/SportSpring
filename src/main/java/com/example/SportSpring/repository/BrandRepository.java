package com.example.SportSpring.repository;

import com.example.SportSpring.entity.BrandEntity;
import com.example.SportSpring.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    boolean existsByNameAndStatus(String name, StatusEnum status);

    BrandEntity findByIdAndStatus(Long brandId, StatusEnum status);

    Optional<BrandEntity> findByName(String name);

    // Thêm 2 methods này vào ProductRepository
    Page<BrandEntity> findByStatus(StatusEnum status, Pageable pageable);
    Page<BrandEntity> findByNameContainingIgnoreCaseAndStatus(String name, StatusEnum status, Pageable pageable);
}
