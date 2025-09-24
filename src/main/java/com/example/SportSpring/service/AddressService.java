package com.example.SportSpring.service;

import com.example.SportSpring.entity.AddressEntity;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    AddressEntity save(AddressEntity address);
    void deleteById(Long id);
} 