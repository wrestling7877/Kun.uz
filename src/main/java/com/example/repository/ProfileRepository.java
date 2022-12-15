package com.example.repository;

import com.example.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer> {

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByPhone(String phone);

    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String password);


}
