package com.example.SportSpring.service.impl;

import com.example.SportSpring.entity.AddressEntity;
import com.example.SportSpring.repository.AddressRepository;
import com.example.SportSpring.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public AddressEntity save(AddressEntity address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
} 