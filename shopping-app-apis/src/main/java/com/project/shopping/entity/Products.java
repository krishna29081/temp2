package com.project.shopping.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Products {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer productId;
 
 private String productName;
 private Integer productPrice;
 private Integer productQuantity;
 private String productDescription;
 private String productImageName;
 private String imgUrl;
 private String Size;
 private Integer productCartQuantity;
 private Boolean inStock;


@ManyToOne
@JsonIgnore
@JoinColumn(name = "category_id")
private Categories categories;

@ManyToOne
@JsonIgnore
@JoinColumn(name = "id")
private User user;


@JsonManagedReference
@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
@JsonIgnore
private List<addToCart> addtocart = new ArrayList<>(); 
 
}
