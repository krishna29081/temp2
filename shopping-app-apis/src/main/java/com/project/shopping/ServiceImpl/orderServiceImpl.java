package com.project.shopping.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shopping.entity.Orders;
import com.project.shopping.entity.User;
import com.project.shopping.entity.addToCart;
import com.project.shopping.exceptions.ResourceNotFoundException;
import com.project.shopping.payloads.CartItemDto;
import com.project.shopping.payloads.OrderItemsDTO;
import com.project.shopping.payloads.OrderRequest;
import com.project.shopping.payloads.ProductDTO;
import com.project.shopping.payloads.orderResponse;
import com.project.shopping.repo.UserRepo;
import com.project.shopping.service.OrderService;

@Service
public class orderServiceImpl implements OrderService {
	
	@Autowired
	UserRepo userrepo;
	
	
	@Override
	public orderResponse orderCreate(OrderRequest request) {
		String userId = request.getEmail();
		User userID1 = userrepo.findByUseremailId(userId).orElseThrow(() -> new ResourceNotFoundException("User","id" ,userId)); 
		Orders order = new Orders();
		OrderItemsDTO orderItem = new OrderItemsDTO();
		order.setUser(userID1);
		order.setOrderAmt(request.getTotalAmount());
		order.setCartTotalQuantity(request.getCartTotalquantity());
		List<ProductDTO> cartItems = new ArrayList<>();
		
		
		return null;
	}
	
}
