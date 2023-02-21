package com.project.shopping.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shopping.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
     Optional<User> findByUseremailId(String email);
}
