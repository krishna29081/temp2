package com.project.shopping.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.shopping.config.AppConstants;
import com.project.shopping.payloads.ApiResponse;
import com.project.shopping.payloads.PostResponse;
import com.project.shopping.payloads.ProductDTO;
import com.project.shopping.service.FileService;
import com.project.shopping.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productservce;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")
	private String path;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"},produces = MediaType.APPLICATION_JSON_VALUE,value = "/user/{userId}/category/{categoryId}/products")
	public ResponseEntity<ProductDTO> createProduct1(
			//@Valid
			@RequestBody ProductDTO productDTO,
			@PathVariable Integer userId, 
			@PathVariable Integer categoryId)
	{   System.out.println("\n\nproduct id " + productDTO.toString() +"\n\nUser ID "+ userId +"\n\nCategory ID " + categoryId);
		ProductDTO productDTO1= this.productservce.createProduct(productDTO, userId, categoryId);
		
		return new ResponseEntity<>(productDTO1,HttpStatus.CREATED);
	}
	
	@GetMapping("/category/{categoryId}/products")
	public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable Integer categoryId)
	{
		List<ProductDTO> productDTO =  productservce.getProductsByCategory(categoryId);
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userID}/products")
	public ResponseEntity<List<ProductDTO>> getProductByUser(@PathVariable Integer userID)
	{
		List<ProductDTO> productDTO = productservce.getProductsByUser(userID);
		return new ResponseEntity<>(productDTO,HttpStatus.OK);
	}
	
	@GetMapping("/products")
	public ResponseEntity<PostResponse> getAllProducts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			)
	{	
		PostResponse productDto= productservce.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	@GetMapping("/allproducts")
	public ResponseEntity<List<ProductDTO>> getAllProduct()
	{
				List<ProductDTO> productDto= productservce.getAllProducts2();
				return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId)
	{
		ProductDTO productDto = productservce.getProductById(productId);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id)
	{
		productservce.deleteProduct(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Succesfully",true), HttpStatus.OK);
	}
	
	@PutMapping("/productId/{productId}")
	public ResponseEntity<ProductDTO> updateProductById(@PathVariable Integer productId, @RequestBody ProductDTO productDTO)
	{
		ProductDTO productDTo1 = productservce.updateProduct(productDTO, productId);
		return new ResponseEntity<>(productDTo1,HttpStatus.OK);
	}
	
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<ProductDTO>> searchProductByName(
			@PathVariable("keywords") String keywords
			)
	{
		List<ProductDTO> productDto = productservce.searchProducts(keywords);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	
	//product image upload 
	@PostMapping("/image/upload/{productId}")
	public ResponseEntity<ProductDTO> uploadProductImage(
			@RequestParam("Image") MultipartFile image,
			@PathVariable Integer productId
			) throws IOException
	{
		String uploadImage = fileservice.uploadImage(path, image);
		
	    ProductDTO productById = productservce.getProductById(productId);
		
	    productById.setProductImageName(uploadImage);
	    
	    ProductDTO updateProduct = productservce.updateProduct(productById, productId);
	    return new ResponseEntity<>(updateProduct,HttpStatus.OK);
	}
	
	//Serve Images
	
	@GetMapping(value="/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException
	{
		InputStream resource = fileservice.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
}
