package com.example.SportSpring.service;

import com.example.SportSpring.dto.request.BrandRequest;
import com.example.SportSpring.dto.response.BrandResponse;
import com.example.SportSpring.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface BrandService {
    Page<BrandResponse> getAllPaginated(
            int page, int size,
            String sortField, String sortDir,
            String name, StatusEnum status
    );

    BrandResponse getBrandById(Long brandId);

    BrandResponse createBrand(BrandRequest brandRequest, MultipartFile file);

    BrandResponse updateBrand(Long brandId, BrandRequest brandRequest, MultipartFile file);

    void softDeleteBrand(Long brandId);

    void restoreBrand(Long brandId);

    void deleteBrand(Long brandId);

    void updateImage(Long brandId, String image);

    void deleteImage(Long brandId);
}
