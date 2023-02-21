package com.project.shopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopping.payloads.OrderRequest;
import com.project.shopping.payloads.orderResponse;
import com.project.shopping.service.OrderService;

@RestController
@RequestMapping("/Api/order")
public class OrderController {
	@Autowired
	OrderService orderservice;
	
	@PostMapping("/createorder")
	public ResponseEntity<orderResponse> createOrder(@RequestBody OrderRequest orderrequest)
	{	
		orderResponse orderreponse = orderservice.orderCreate(orderrequest);
		return new ResponseEntity<>(orderreponse,HttpStatus.CREATED);
	}
	
}
