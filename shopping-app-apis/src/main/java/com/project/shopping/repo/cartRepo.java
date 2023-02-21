package com.project.shopping.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shopping.entity.Products;
import com.project.shopping.entity.User;
import com.project.shopping.entity.addToCart;

@Repository
public interface cartRepo extends JpaRepository<addToCart, Integer> {
	
	public addToCart findByUserAndProduct(User user,Products product);
	public List<addToCart> findAllByUser(User user);
	public List<addToCart> findAllByUserId(Integer userId);
}
