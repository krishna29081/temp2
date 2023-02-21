package com.project.shopping.service;

import java.util.List;

import com.project.shopping.payloads.CartDto;
import com.project.shopping.payloads.UserDTO;
import com.project.shopping.payloads.addTocartDTO;

public interface addToCartService {

		addTocartDTO addProduct(Integer productId, Integer quantity,Integer userId);
		List<addTocartDTO> findByCustomer(Integer customerId);
		CartDto listCartItems(Integer userId);
		void deleteCartById(Integer cartId, Integer userId);
		
		
}
