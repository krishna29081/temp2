package com.project.shopping.payloads;

import com.project.shopping.entity.Products;
import com.project.shopping.entity.addToCart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartItemDto {
private Integer id;
private Integer quantity;
private Products product;
//private Products product;
public CartItemDto(addToCart addtocart)
{
	this.id = addtocart.getCartId();
	this.quantity= addtocart.getQuantity();
	this.setProduct(addtocart.getProduct());
	}
}
