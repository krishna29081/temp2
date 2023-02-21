package com.project.shopping.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shopping.entity.Categories;
import com.project.shopping.entity.Products;
import com.project.shopping.entity.User;

@Repository
public interface ProductRepo extends JpaRepository<Products,Integer> {
	
	List<Products> findAllByUser(User user);
	List<Products> findByCategories(Categories categories);
	
	List<Products> findByproductNameContaining(String name);
}
