package com.example.SportSpring.repository;

import com.example.SportSpring.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {}
