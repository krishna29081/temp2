package com.project.shopping.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

private Integer categoryId;

@NotEmpty
@Size(min = 3,max = 16,message = "Title length must be min 3 and max 16")
private String categoryTitle;

@NotEmpty
private String categoryDescrip;

//List<Products> product;

}
