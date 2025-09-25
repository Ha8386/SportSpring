package com.example.SportSpring.repository;

import com.example.SportSpring.dto.response.AdminCommentRow;
import com.example.SportSpring.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByProductId(Long productId);
    @Query("""
           select new com.example.SportSpring.dto.response.AdminCommentRow(
               c.id,
               u.username,
               c.rate,
               c.messages,
               c.mediaUrl,
               c.createDate,
               c.adminReply
           )
           from CommentEntity c
           join c.user u
           order by c.createDate desc
           """)
    List<AdminCommentRow> findAllAdminRows();
    Page<CommentEntity> findByProduct_IdOrderByCreateDateDesc(Long productId, Pageable pageable);
    List<CommentEntity> findByProduct_Id(Long productId);
    long countByProduct_Id(Long productId);
    @Query("select avg(c.rate) from CommentEntity c where c.product.id = :productId")
    Double avgRateByProductId(@Param("productId") Long productId);

    // Đếm theo sao (group by rate)
    @Query("select c.rate as rate, count(c) as cnt " +
            "from CommentEntity c " +
            "where c.product.id = :productId " +
            "group by c.rate")
    List<Object[]> countByRate(@Param("productId") Long productId);
    Optional<CommentEntity> findByUserIdAndProductId(Long userId, Long productId);
}
