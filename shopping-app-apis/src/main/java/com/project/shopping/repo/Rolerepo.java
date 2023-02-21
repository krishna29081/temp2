package com.project.shopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shopping.entity.Role;

@Repository
public interface Rolerepo extends JpaRepository<Role, Integer> {

}
