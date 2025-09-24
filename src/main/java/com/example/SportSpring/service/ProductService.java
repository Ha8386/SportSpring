package com.example.SportSpring.service;

import com.example.SportSpring.dto.request.ProductRequest;
import com.example.SportSpring.dto.request.SearchRequest;
import com.example.SportSpring.dto.response.ProductResponse;
import com.example.SportSpring.enums.StatusEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    int countProduct();

    Page<ProductResponse> getAllProductsPaginated(
            int page, int size,
            String sortField, String sortDir,
            String name, StatusEnum status
    );

    ProductResponse getProductById(Long productId);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long productId, ProductRequest productRequest);

    void softDeleteProduct(Long productId);

    void restoreProduct(Long productId);

    void deleteProduct(Long productId);

    Page<ProductResponse> searchProduct(SearchRequest request, int page, int size);

    List<ProductResponse> getProductSale();

    List<ProductResponse> getProductNewest();


}
