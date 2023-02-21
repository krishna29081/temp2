package com.project.shopping.service;


import java.util.List;

import com.project.shopping.payloads.CategoryDTO;

public interface CategoryService {
	CategoryDTO addCategory(CategoryDTO categoryDTO);
	void deleteCategory(int id);
	CategoryDTO updateCategory(CategoryDTO categoryDTO, int id);
	List<CategoryDTO> getAllCategories();
	CategoryDTO getCategoryById(int id);
}
