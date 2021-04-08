package org.cigma.dev.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.cigma.dev.exceptions.ProductServiceException;
import org.cigma.dev.model.entity.CartEntity;
import org.cigma.dev.model.entity.ProductEntity;
import org.cigma.dev.model.request.ProductCDTO;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.model.response.RequestOperationName;
import org.cigma.dev.model.response.RequestOperationStatus;
import org.cigma.dev.repository.CarteRepository;
import org.cigma.dev.repository.ProductRepository;
import org.cigma.dev.service.CarteService;
import org.cigma.dev.shared.Utils;
import org.cigma.dev.shared.dto.CartDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CarteService {

	@Autowired
	CarteRepository cartRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	Utils utils;
	
	@Override
	public CartDto createCart(CartDto cart) {
		CartEntity cartEntity = new CartEntity();
		CartDto returnValue = new CartDto();
		ProductEntity theProduct = productRepo.findByProductID(cart.getProductId());
		if(Optional.ofNullable(theProduct).isPresent() && (theProduct.getUnitsInStock() < cart.getQuantity())) {
			throw new ProductServiceException("Product unavailable");
		}
		List<CartEntity> carts = cartRepo.findByUserId(cart.getUserID());
		CartEntity cartExists = null;
		for(CartEntity cartEn : carts) {
			if(cartEn.getProduct().getProductID().equals(cart.getProductId())) {
				cartEn.setQuantity(cartEn.getQuantity() + cart.getQuantity());
				cartExists = cartEn;
				cartRepo.save(cartEn);
				
			}
		}
		if(cartExists != null) {
			returnValue = modelMapper.map(cartExists, CartDto.class);
			return returnValue;
		}
		
		cartEntity.setCartId(utils.generateID(6));
		ProductEntity product = productRepo.findByProductID(cart.getProductId());
		cartEntity.setProduct(product);
		cartEntity.setUserId(cart.getUserID());
		cartEntity.setQuantity(cart.getQuantity());
		CartEntity savedCart = cartRepo.save(cartEntity);
		returnValue = modelMapper.map(savedCart, CartDto.class);
		return returnValue;
	}


	@Override
	@Transactional
	public FeedbackMessage deleteCart(String id) {
		FeedbackMessage feedback = new FeedbackMessage();
		List<CartEntity> carts = cartRepo.findByUserId(id);
		feedback.setOperationName(RequestOperationName.DELETE.name());
		feedback.setOperationResult(RequestOperationStatus.SUCCESS.name());
		try {
			cartRepo.deleteAll(carts);

		} catch(Exception ex) {
			feedback.setOperationResult(RequestOperationStatus.ERROR.name());
			throw new RuntimeException("Couldnt delete records");
		}
		return feedback;
	}


	@Override
	public int getBasketQuantity(String userId) {
		List<CartEntity> carts = cartRepo.findByUserId(userId);
		int totalQuantity = 0;
		for(CartEntity cart : carts) {
			totalQuantity += cart.getQuantity();
		}
		return totalQuantity;
	}


	@Override
	public List<ProductCDTO> getAllProducts(String userId){
		List<CartEntity> carts = cartRepo.findByUserId(userId);
		List<ProductCDTO> returnValue = new ArrayList<>();
		for(CartEntity cart : carts) {
			ProductCDTO product = new ProductCDTO();
			product = modelMapper.map(cart.getProduct(), ProductCDTO.class);
			product.setUnitsInStock(cart.getQuantity());
			returnValue.add(product);
		}
		return returnValue;
	}



}
