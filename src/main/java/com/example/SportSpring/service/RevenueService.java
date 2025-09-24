package com.example.SportSpring.service;

import com.example.SportSpring.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RevenueService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public RevenueService(OrderRepository orderRepository,
                          OrderDetailRepository orderDetailRepository,
                          UserRepository userRepository,
                          ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Map<String, Object> getSummary(LocalDate from, LocalDate to) {
        long userQty    = userRepository.count();
        long productQty = productRepository.count();
        long orderQty   = orderRepository.countOrdersInRange(from, to);
        double totalRevenue = Optional.ofNullable(orderRepository.sumRevenue(from, to)).orElse(0.0);

        Map<String, Object> map = new HashMap<>();
        map.put("userQuantity", userQty);
        map.put("productQuantity", productQty);
        map.put("orderQuantity", orderQty);
        map.put("increase", totalRevenue);
        return map;
    }

    private List<Map<String, Object>> toList(List<Object[]> rows, String k1, String k2) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (Object[] r : rows) {
            Map<String, Object> it = new HashMap<>();
            it.put(k1, String.valueOf(r[0]));
            it.put(k2, ((Number) r[1]).doubleValue());
            out.add(it);
        }
        return out;
    }

    public List<Map<String, Object>> getRevenueByDay(LocalDate from, LocalDate to)   { return toList(orderRepository.revenueByDay(from, to), "date", "revenue"); }
    public List<Map<String, Object>> getRevenueByMonth(LocalDate from, LocalDate to) { return toList(orderRepository.revenueByMonth(from, to), "month", "revenue"); }
    public List<Map<String, Object>> getRevenueByYear(LocalDate from, LocalDate to)  { return toList(orderRepository.revenueByYear(from, to), "year", "revenue"); }
    public List<Map<String, Object>> getTopProducts(LocalDate from, LocalDate to) {
        List<Object[]> rows = orderDetailRepository.topProductsByQuantity(from, to);
        List<Map<String, Object>> out = new ArrayList<>();
        for (Object[] r : rows) {
            Map<String, Object> m = new HashMap<>();
            m.put("id",       ((Number) r[0]).longValue());
            m.put("name",     String.valueOf(r[1]));
            m.put("quantity", ((Number) r[2]).longValue()); // số lượng đã bán
            m.put("revenue",  ((Number) r[3]).longValue()); // doanh thu
            out.add(m);
        }
        return out;
    }



    public List<Map<String, Object>> getRevenueByCategory(LocalDate from, LocalDate to) {
        List<Object[]> rows = orderDetailRepository.revenueByCategory(from, to);
        List<Map<String, Object>> out = new ArrayList<>();
        for (Object[] r : rows) {
            Map<String, Object> m = new HashMap<>();
            m.put("category", String.valueOf(r[0]));
            m.put("revenue",  ((Number) r[1]).longValue());
            out.add(m);
        }
        return out;
    }

}
