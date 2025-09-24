package com.example.SportSpring.repository;

import com.example.SportSpring.entity.ProductEntity;
import com.example.SportSpring.enums.StatusEnum;
import com.example.SportSpring.repository.customer.ProductRepositoryCustomer;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductRepositoryCustomer {
    boolean existsByNameAndStatus(String name, StatusEnum status);

    List<ProductEntity> findByStatus(StatusEnum status);

    List<ProductEntity> findByCategoryId(Long categoryId);

    List<ProductEntity> findByBrandId(Long brandId);

    boolean existsByIdAndStatus(Long id, StatusEnum status);

    ProductEntity findByNameAndStatus(String name, StatusEnum status);

    ProductEntity findByIdAndStatus(Long productId, StatusEnum status);

    List<ProductEntity> findByNameContainingOrDescriptionContainingAndStatus(String name, String description, StatusEnum status);

    // Thêm 2 methods này vào ProductRepository
    Page<ProductEntity> findByStatus(StatusEnum status, Pageable pageable);
    Page<ProductEntity> findByNameContainingIgnoreCaseAndStatus(String name, StatusEnum status, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductEntity p where p.id = :id")
    Optional<ProductEntity> lockById(@Param("id") Long id);
}
