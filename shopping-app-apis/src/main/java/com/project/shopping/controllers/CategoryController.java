package com.project.shopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopping.payloads.ApiResponse;
import com.project.shopping.payloads.CategoryDTO;
import com.project.shopping.service.CategoryService;
import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryservice;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category)
	{	
		CategoryDTO cat = categoryservice.addCategory(category);
		return new ResponseEntity<>(cat,HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategories(@RequestBody CategoryDTO category, @PathVariable Integer id)
	{
		CategoryDTO cat = this.categoryservice.updateCategory(category,id);
		return new ResponseEntity<>(cat,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id)
	{
		categoryservice.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Succesfully",true), HttpStatus.OK);
	}
	//find all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> findallCategory()
	{
		return ResponseEntity.ok(categoryservice.getAllCategories());
	}
	
	//find by id
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Integer id)
	{
		return ResponseEntity.ok(categoryservice.getCategoryById(id));
	}
	
	
}
