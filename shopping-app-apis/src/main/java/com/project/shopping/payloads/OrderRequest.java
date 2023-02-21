package com.project.shopping.payloads;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
	private String email;
	private String address;
	private Integer cartTotalquantity;
	private Integer mobileno;
	private String city;
	private Integer Zipcode;
	private String state;
	private Integer totalAmount;
	private List<ProductDTO> cartItems = new ArrayList<>();

}
