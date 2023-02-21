
package com.project.shopping.ServiceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.shopping.entity.Categories;
import com.project.shopping.entity.Products;
import com.project.shopping.entity.User;
import com.project.shopping.exceptions.ResourceNotFoundException;
import com.project.shopping.payloads.PostResponse;
import com.project.shopping.payloads.ProductDTO;
import com.project.shopping.repo.CategoryRepo;
import com.project.shopping.repo.ProductRepo;
import com.project.shopping.repo.UserRepo;
import com.project.shopping.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepo productrepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	@Override
	public ProductDTO createProduct(ProductDTO productDTO, Integer userId,Integer categoryId)
	{	//System.out.println("\n\n Inside service impl product " + productDTO.toString());
	
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		System.out.println("\n\nproduct " + user.toString());
		Categories category = categoryrepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","id", categoryId));
		System.out.println("\n\nproduct " + category.toString());
		log.info("before model mapper productDTO :- {}", productDTO.getProductName());;
		Products product = modelmapper.map(productDTO, Products.class);
		
//		System.out.println("product " + product.toString());
		
		product.setProductImageName("Default.img");
		product.setCategories(category);
		product.setUser(user);
//		System.out.println("product saving to database " + product.toString());
		Products newproduct = this.productrepo.save(product);

//		System.out.println("product getting from database " + newproduct.toString());
		return modelmapper.map(newproduct, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, Integer product_id) {
		Products product = productrepo.findById(product_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", product_id));
		Products newProduct = modelmapper.map(product, Products.class);
		newProduct.setProductImageName(productDTO.getProductImageName());
		newProduct.setProductDescription(productDTO.getProductDescription());		
		newProduct.setProductName(productDTO.getProductName());
		newProduct.setProductPrice(productDTO.getProductPrice());
		newProduct.setProductQuantity(productDTO.getProductQuantity());
		newProduct.setSize(productDTO.getSize());
		newProduct.setInStock(productDTO.getInStock());
		newProduct.setImgUrl(productDTO.getImgUrl());
		productrepo.save(newProduct);
		
		return modelmapper.map(newProduct, ProductDTO.class);
	}

	@Override
	public void deleteProduct(Integer product_id) {
		Products product = this.productrepo.findById(product_id).orElseThrow(() -> new ResourceNotFoundException("Product","id",product_id));
		this.productrepo.delete(product);
			}

	@Override
	public PostResponse getAllProducts(Integer pageNumber,Integer pageSize, String sortBy, String sortDir) {
		
//		System.out.println("inside the impl ");
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort = Sort.by(sortBy).ascending();
		}else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
//		System.out.println(p.getPageNumber());
		Page<Products> listOfProducts = productrepo.findAll(p);
//		for(Products model : listOfProducts) {
//        System.out.println(model.getProduct_id() +" "+ model.getProduct_name());
//         }
		List<Products> allProducts = listOfProducts.getContent();
//		for(Products model : allProducts) {
//	        System.out.println(model.getProduct_id() +" "+ model.getProduct_name());
//	         }
		List<ProductDTO> listOfProductsDTO = allProducts.stream().map(product -> modelmapper.map(product, ProductDTO.class)).collect(Collectors.toList());
//		System.out.println("End of impl");
//		for(ProductDTO model : listOfProductsDTO) {
//	        System.out.println(model.getProduct_description() +" "+ model.getProduct_name());
//	         }
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setProductdetails(listOfProductsDTO);;
		postResponse.setPageNumber(listOfProducts.getNumber());
		postResponse.setPageSize(listOfProducts.getSize());
		postResponse.setTotalElements(listOfProducts.getTotalElements());
		postResponse.setTotalPages(listOfProducts.getTotalElements());
		postResponse.setLastPage(listOfProducts.isLast());
			
		return postResponse;
	}
	
	
	
	

	@Override
	public ProductDTO getProductById(Integer id) {
		Products product = productrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		return modelmapper.map(product, ProductDTO.class);
	}

	@Override
	public List<ProductDTO> getProductsByUser(Integer userId) {
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id",userId) );
		System.out.println("\n\nInside impl category found :- " + user.getId());
		List<Products> products = productrepo.findAllByUser(user);
		
		List<ProductDTO> productsDTO = products.stream().map((product) -> modelmapper.map(product, ProductDTO.class)).collect(Collectors.toList());	
		return productsDTO;
		}

	@Override
	public List<ProductDTO> getProductsByCategory(Integer category_id) {
		Categories cat = categoryrepo.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "id",category_id) );
//		System.out.println("\n\nInside impl category found :- " + cat.getCategoryTitle());
		List<Products> products = productrepo.findByCategories(cat);
//		for(Products model : products) {
//            System.out.println(model.getProduct_id() +" "+ model.getProduct_name());
//        }
		List<ProductDTO> productsDTO = products.stream().map((product) -> modelmapper.map(product, ProductDTO.class)).collect(Collectors.toList());	
//		for(ProductDTO model : productsDTO) {
//            System.out.println(model.getProduct_id() +" "+ model.getProduct_name());
//        }
		return productsDTO;
	}

	@Override
	public List<ProductDTO> searchProducts(String keyword) {
		List<Products> products =		productrepo.findByproductNameContaining(keyword);
		List<ProductDTO> listOfProductsDTO = products.stream().map(product -> modelmapper.map(product, ProductDTO.class)).collect(Collectors.toList());
		return listOfProductsDTO;
	}

	@Override
	public List<ProductDTO> getAllProducts2() {
		List<Products> findAll = this.productrepo.findAll();
		log.info("");
		List<ProductDTO> userDTO = findAll.stream().map(product -> modelmapper.map(product,ProductDTO.class)).collect(Collectors.toList());
		return userDTO;
	}

}
