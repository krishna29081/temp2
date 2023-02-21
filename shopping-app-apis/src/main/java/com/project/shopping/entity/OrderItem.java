package com.project.shopping.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int orderItemId;

@OneToOne
private Products product;

@ManyToOne
private Orders order;


}
