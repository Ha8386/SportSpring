package com.example.SportSpring.repository.customer;

import com.example.SportSpring.dto.request.SearchRequest;
import com.example.SportSpring.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryCustomer {
    Page<ProductEntity> search(SearchRequest request, Pageable pageable);
}
