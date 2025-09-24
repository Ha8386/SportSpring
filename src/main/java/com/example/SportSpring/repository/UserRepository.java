package com.example.SportSpring.repository;

import com.example.SportSpring.entity.UserEntity;
import com.example.SportSpring.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findByStatus(StatusEnum status);
    boolean existsByIdAndStatus(Long id, StatusEnum status);
    boolean existsByUsernameAndStatus(String username, StatusEnum status);
    UserEntity findByUsernameAndStatus(String username, StatusEnum status);
    UserEntity findByIdAndStatus(Long userId, StatusEnum status);

        Optional<UserEntity> findByEmail(String email);
        boolean existsByUsername(String username);
        boolean existsByEmail(String email);


}
