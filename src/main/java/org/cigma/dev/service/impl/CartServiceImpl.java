package org.cigma.dev.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.cigma.dev.model.entity.CartEntity;
import org.cigma.dev.model.entity.ProductEntity;
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
		cartEntity.setCartID(utils.generateID(6));
		ProductEntity product = productRepo.findByProductID(cart.getProductId());
		cartEntity.setProduct(product);
		cartEntity.setUserId(cart.getUserID());
		cartEntity.setQuantity(cart.getQuantity());
		CartEntity savedCart = cartRepo.save(cartEntity);
		CartDto cartDto = modelMapper.map(savedCart, CartDto.class);

		return cartDto;
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

}
