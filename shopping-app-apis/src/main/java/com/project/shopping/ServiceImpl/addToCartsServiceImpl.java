package com.project.shopping.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shopping.entity.Products;
import com.project.shopping.entity.User;
import com.project.shopping.entity.addToCart;
import com.project.shopping.exceptions.ResourceNotFoundException;
import com.project.shopping.payloads.CartDto;
import com.project.shopping.payloads.CartItemDto;
import com.project.shopping.payloads.addTocartDTO;
import com.project.shopping.repo.ProductRepo;
import com.project.shopping.repo.UserRepo;
import com.project.shopping.repo.cartRepo;
import com.project.shopping.service.addToCartService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class addToCartsServiceImpl implements addToCartService {
	
//	Logger logger = LoggerFactory.getLogger(addToCartsServiceImpl.class);
	@Autowired
	private ProductRepo productrepo;
	
	@Autowired
	private cartRepo cartrepo;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	ModelMapper modelmapper;
	
	@Override
	public addTocartDTO addProduct(Integer productId, Integer quantity, Integer userId) {
		log.info("\n\nstart of the service impl with addtocartvalue as :");
		Integer addedQuantity = quantity;
		Products product = productrepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product","id", productId));
		User userID1 = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Product","id", userId));
		addToCart findByUserAndProducts = cartrepo.findByUserAndProduct(userID1, product);
		
		if(findByUserAndProducts != null)
		{	log.info("\n\ninside if");
			addedQuantity= findByUserAndProducts.getQuantity() + quantity;
			findByUserAndProducts.setQuantity(addedQuantity);
		}else {
			log.info("\n\ninside else");
			 findByUserAndProducts = new addToCart();
			 findByUserAndProducts.setQuantity(quantity);
			 findByUserAndProducts.setUser(userID1);
			 findByUserAndProducts.setProduct(product);
		}
		addToCart save = cartrepo.save(findByUserAndProducts);
		
		log.info("\n\nEnd of the service impl with addtocartvalue as : {}",save.toString());
		addTocartDTO map = modelmapper.map(save, addTocartDTO.class);
//		addTocartDTO map = DTOtoUser(save);
		log.info("\n\nEnd of the service impl with addtocartvalue after mapping as : {}");
	
		return map;
		
	}

	@Override
	public List<addTocartDTO> findByCustomer(Integer customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartDto listCartItems(Integer userId) {
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		List<addToCart> cartList = cartrepo.findAllByUser(user) ;
		List<CartItemDto> cartItems = new ArrayList<>();
		double totalCost=0;
		log.info("\n\nstrating of impl");
		for(addToCart cart: cartList)
		{
			CartItemDto cartitemdto = new CartItemDto(cart);
			totalCost += cartitemdto.getQuantity()*cart.getProduct().getProductPrice();
			cartItems.add(cartitemdto);
		}
		
		CartDto newcartdto = new CartDto();
		newcartdto.setCartItems(cartItems);
		log.info("\n\nCartItems as : {}",newcartdto.getCartItems().toString());
		newcartdto.setTotalCost(totalCost);
		log.info("\n\ntotal cost: {}", newcartdto.getTotalCost());
		return newcartdto;
	}

	@Override
	public void deleteCartById(Integer cartId, Integer userId) {
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		
		Optional<addToCart> optionalCart = cartrepo.findById(cartId);
		if(optionalCart.isEmpty())
		{
			throw new ResourceNotFoundException("cart Item id is invalid: ", null, cartId);
		}
		addToCart cart = optionalCart.get();
		
		if(cart.getUser() != user)
		{
			throw new ResourceNotFoundException("cart item does not belong to user : ", null, cartId);
		}
		cartrepo.delete(cart);
		
	}


	
//	private addTocartDTO DTOtoUser(addToCart userDTO)
//	{	
//		addTocartDTO addtocart = new addTocartDTO();
//		addtocart.setCartId(userDTO.getCartId());
//		addtocart.setProduct(userDTO.getProduct());
//		logger.info("\n\nEnd of the service impl with addtocartvalue after mapping as : {}",addtocart.getProduct().getProductName());
//		addtocart.setQuantity(userDTO.getQuantity());
//		addtocart.setUser(userDTO.getUser());
//		return addtocart;
//	}

}
