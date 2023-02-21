package com.project.shopping.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.shopping.payloads.ApiResponse;
import com.project.shopping.payloads.CartDto;
import com.project.shopping.payloads.addTocartDTO;
import com.project.shopping.service.addToCartService;

@Controller
@RequestMapping("/api/addtocart")
public class AddToCartController {
	Logger logger = LoggerFactory.getLogger(AddToCartController.class);
	@Autowired
	addToCartService addtocartservice;
	
	
	@PostMapping(path ="/productId/{productId}/quantity/{quantity}/user/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAddToCart(
			@PathVariable Integer productId,
			@PathVariable Integer quantity,
			@PathVariable Integer userId
			)
	{	logger.info("\n\ninside controller Addtocart");
		addTocartDTO addProduct = addtocartservice.addProduct(productId, quantity, userId);
		logger.info("\n\ninside controller Addtocart :- {}", addProduct.toString());
		 return new ResponseEntity<Object>(addProduct,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<CartDto> getCartItems(
			@PathVariable Integer userId
			)
	{	logger.info("\n\ninside controller Addtocart");
		CartDto cartdto = addtocartservice.listCartItems(userId);
		logger.info("\n\nafter function call controller Addtocart");
		return new ResponseEntity<>(cartdto,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/cartid/{cartId}/userId/{userId}")
	public ResponseEntity<ApiResponse> deleteCart(
			@PathVariable Integer cartId,
			@PathVariable Integer userId)
	{
		addtocartservice.deleteCartById(cartId,userId);
		return new ResponseEntity<>(new ApiResponse("Cart Item deleted Succesfully",true), HttpStatus.OK);
	}
	
	
}
