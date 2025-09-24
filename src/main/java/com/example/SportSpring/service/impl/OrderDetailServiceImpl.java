package com.example.SportSpring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SportSpring.dto.response.OrderDetailResponse;
import com.example.SportSpring.entity.CartEntity;
import com.example.SportSpring.entity.OrderDetailEntity;
import com.example.SportSpring.entity.OrderEntity;
import com.example.SportSpring.entity.ProductEntity;
import com.example.SportSpring.mapper.OrderDetailMapper;
import com.example.SportSpring.repository.OrderDetailRepository;
import com.example.SportSpring.repository.OrderRepository;
import com.example.SportSpring.repository.ProductRepository;
import com.example.SportSpring.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Override
    public void AddItem(List<CartEntity> carts, Long orderId) {

        for(CartEntity cart : carts){
            OrderDetailEntity newItem = new OrderDetailEntity();

            ProductEntity product = productRepository.findById(cart.getProduct().getId()).get();

            OrderEntity order = orderRepository.findById(orderId).get();

            newItem.setQuantity(cart.getQuantity());
            newItem.setDiscount(product.getDiscount());
            newItem.setPrice(product.getPrice());
            newItem.setTotal((product.getPrice() * (100 - product.getDiscount()) / 100) * cart.getQuantity());
            newItem.setProduct(product);
            newItem.setOrder(order);

            orderDetailRepository.save(newItem);
        }

    }

    @Override
    public List<OrderDetailResponse> getItemByOrderId(Long orderId) {
        List<OrderDetailResponse> result = new ArrayList<>();

        for (OrderDetailEntity detail : orderDetailRepository.findByOrderId(orderId)){
            result.add(orderDetailMapper.toOrderDetailResponse(detail));
        }
        return result;
    }
}
