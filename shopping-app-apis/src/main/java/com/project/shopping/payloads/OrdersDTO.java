package com.project.shopping.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.project.shopping.entity.OrderItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class OrdersDTO {
private int orderId;
	
	private String orderStatus;
	private Date orderDelivered;
	private double orderAmt;
	private String billingAdrress;
	private UserDTO user;
	private Set<OrderItem> orderItem = new HashSet<>();
	
}
