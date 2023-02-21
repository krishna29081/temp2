package com.project.shopping.payloads;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.project.shopping.entity.addToCart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
	 private Integer productId;
	 @NotEmpty
	 @Size(min = 4,message = "Name should be greater than 4")
	 private String productName;
	 @NotNull(message = "Price must not be Empty")
	 private Integer productPrice;
	 @NotNull(message = "Quantity must not be Empty")
	 private Integer productQuantity;
	 @NotEmpty
	 @Size(message = "Description must be greater than 5 and smaller than 16")
	 private String productDescription;
	 private Integer productCartQuantity;
	 private String Size;
	 private Boolean inStock;
	 private String imgUrl;
	 private String productImageName;
	 private CategoryDTO categories;
	 private UserDTO user;
	// private addToCart addtocart;
	 
		/*
		 * { "Product_name":"Product1", "Product_price":12, "Product_quantity":10,
		 * "Product_description":"Product1description" }
		 */
	 	 	 
}
