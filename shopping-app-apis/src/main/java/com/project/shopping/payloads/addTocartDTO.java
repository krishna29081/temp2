package com.project.shopping.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class addTocartDTO {
	private int cartId;
	private Integer quantity;
	private Integer productId;
	
	
}
