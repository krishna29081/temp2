package com.project.shopping.service;

import java.util.List;

import com.project.shopping.payloads.PostResponse;
import com.project.shopping.payloads.ProductDTO;

public interface ProductService {
	ProductDTO createProduct(ProductDTO productDTO, Integer userID, Integer categoryId);
	ProductDTO updateProduct(ProductDTO productDTO, Integer product_id);
	void deleteProduct(Integer product_id);
	PostResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	ProductDTO getProductById(Integer id);
	
	List<ProductDTO> getProductsByUser(Integer userId);
	List<ProductDTO> getProductsByCategory(Integer category_id);
	
	List<ProductDTO> searchProducts(String keyword);
	List<ProductDTO> getAllProducts2();
}
