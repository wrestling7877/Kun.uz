package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity,Integer> {


    List<EmailHistoryEntity> findByEmail(String email);

    List<EmailHistoryEntity> findByCreatedDate(LocalDateTime localDateTime);

}
