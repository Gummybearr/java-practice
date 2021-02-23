package com.ververica.demo.backend.users.repository;

import com.ververica.demo.backend.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}