package com.project.shopping.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartDto {
	private List<CartItemDto> cartItems;
	private double totalCost;
}
