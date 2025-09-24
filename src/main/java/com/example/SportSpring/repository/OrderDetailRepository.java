package com.example.SportSpring.repository;

import com.example.SportSpring.dto.response.OrderItemRowResponse;
import com.example.SportSpring.entity.OrderDetailEntity;
import com.example.SportSpring.enums.StatusOrderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    List<OrderDetailEntity> findByOrderId(Long orderId);

    List<OrderDetailEntity> findByProductId(Long productId);

    /* ================== Doanh thu theo SẢN PHẨM (Top 15) ================== */
    @Query(value = """
    SELECT p.id, p.name,
           COALESCE(SUM(od.quantity), 0) AS qty_sold,
           COALESCE(SUM(od.total),    0) AS revenue
    FROM order_detail od
    JOIN orders  o ON od.order_id   = o.id
    JOIN product p ON od.product_id = p.id
    WHERE (:from IS NULL OR o.`date` >= :from)
      AND (:to   IS NULL OR o.`date` < DATE_ADD(:to, INTERVAL 1 DAY))
      AND o.status_order = 'Da_Giao'
    GROUP BY p.id, p.name
    HAVING qty_sold > 0
    ORDER BY qty_sold DESC, revenue DESC
    LIMIT 10
""", nativeQuery = true)
    List<Object[]> topProductsByQuantity(@Param("from") LocalDate from,
                                         @Param("to")   LocalDate to);


    /* ================== Doanh thu theo DANH MỤC ==================
       - LEFT JOIN để không bỏ sót sản phẩm chưa gán danh mục
       - Doanh thu = SUM(od.total)
       - Lọc đơn đã giao
       - Khoảng ngày end-exclusive: to + 1 day
       - Gộp tên null thành 'Khác' để tránh group bị tách
    ================================================================= */
    @Query(value = """
        SELECT COALESCE(cp.name_category, 'Khác') AS category_name,
               COALESCE(SUM(od.total), 0)        AS revenue
        FROM orders o
        JOIN order_detail od  ON od.order_id  = o.id
        JOIN product p        ON p.id         = od.product_id
        LEFT JOIN category_product cp ON cp.id = p.category_id
        WHERE (:from IS NULL OR o.`date` >= :from)
          AND (:to   IS NULL OR o.`date` < DATE_ADD(:to, INTERVAL 1 DAY))
          AND o.status_order = 'Da_Giao'
        GROUP BY COALESCE(cp.name_category, 'Khác')
        HAVING revenue > 0
        ORDER BY revenue DESC
    """, nativeQuery = true)
    List<Object[]> revenueByCategory(@Param("from") LocalDate from,
                                     @Param("to")   LocalDate to);

    /* ================== DTO: chi tiết đơn của user theo trạng thái ================== */
    @Query("""
        select new com.example.SportSpring.dto.response.OrderItemRowResponse(
            o.id, o.date, p.name, od.quantity, od.price, od.total, o.status
        )
        from OrderEntity o
        left join o.items od
        left join od.product p
        where o.user.id = :userId
          and o.status   = :status
        order by o.date desc, o.id desc, od.id asc
    """)
    List<OrderItemRowResponse> findUserOrderItemsByStatusDto(@Param("userId") Long userId,
                                                             @Param("status") StatusOrderEnum status);



// Bản mở rộng (10 tham số) kèm reviewDate = max(c.createDate)
    @Query("""
        select new com.example.SportSpring.dto.response.OrderItemRowResponse(
            o.id,
            o.date,
            p.name,
            od.quantity,
            od.price,
            od.total,
            o.status,
            p.id,
            od.id,
            (
               select max(c.createDate)
               from CommentEntity c
               where c.user.id    = o.user.id
                 and c.product.id = p.id
            )
        )
        from OrderEntity o
        join o.items od
        join od.product p
        where o.user.id = :userId
          and o.status   = :status
        order by o.date desc, o.id desc, od.id asc
    """)
    List<OrderItemRowResponse> findUserOrderItemsByStatusWithReviewDate(@Param("userId") Long userId,
                                                                        @Param("status") StatusOrderEnum status);


}
