package com.project.shopping.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemsDTO {
	private ProductDTO product;
	private OrdersDTO order;
}
