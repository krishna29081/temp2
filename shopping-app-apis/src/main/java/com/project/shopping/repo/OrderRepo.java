package com.project.shopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopping.entity.Orders;

public interface OrderRepo extends JpaRepository<Orders, Integer> {

}
