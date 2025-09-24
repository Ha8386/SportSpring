package com.example.SportSpring.service.impl;

import com.example.SportSpring.dto.response.ImageResponse;
import com.example.SportSpring.entity.ImageEntity;
import com.example.SportSpring.mapper.ImageMapper;
import com.example.SportSpring.repository.ImageRepository;
import com.example.SportSpring.repository.ProductRepository;
import com.example.SportSpring.service.CloudinaryService;
import com.example.SportSpring.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageMapper imageMapper;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public List<ImageResponse> getImageByProductId(Long productId) {
        List<ImageResponse> images = new ArrayList<>();

        for(ImageEntity image : imageRepository.findByProduct_Id(productId)){
            images.add(imageMapper.toImageResponse(image));
        }

        return  images;
    }

    @Override
    public void createImage(MultipartFile file, Long productId){
        if(!file.isEmpty() && file != null){
            try{
                ImageEntity newImage = new ImageEntity();
                newImage.setImageLink(cloudinaryService.uploadFile(file));
                newImage.setProduct(productRepository.findById(productId).get());
                imageRepository.save(newImage);
            } catch (Exception e){
                e.printStackTrace();
            }
        }  
    }

    @Override
    public String createImage(MultipartFile file) {
        try{
            ImageEntity newImage = new ImageEntity();
            String link = cloudinaryService.uploadFile(file);
            newImage.setImageLink(link);
            imageRepository.save(newImage);
            return link;
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void createImage(List<MultipartFile> files, Long productId) {
        for (MultipartFile item : files){
            if(!item.isEmpty() && item != null){
                try{
                    ImageEntity newImage = new ImageEntity();
                    newImage.setImageLink(cloudinaryService.uploadFile(item));
                    newImage.setProduct(productRepository.findById(productId).get());
                    imageRepository.save(newImage);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }  
        }
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.deleteById(imageId);
    }
}
