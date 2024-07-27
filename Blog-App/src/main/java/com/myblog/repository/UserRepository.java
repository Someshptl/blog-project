package com.myblog.repository;

import com.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
