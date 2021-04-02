package org.cigma.dev.service.impl;


import java.util.HashSet;
import java.util.Set;

import org.cigma.dev.model.entity.OrderEntity;
import org.cigma.dev.model.entity.OrderItemEntity;
import org.cigma.dev.model.entity.UserEntity;
import org.cigma.dev.model.request.OrderItemCDTO;
import org.cigma.dev.model.request.Purchase;
import org.cigma.dev.repository.UserRepository;
import org.cigma.dev.service.CheckoutService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService{

	@Autowired
	UserRepository userRepo;
	@Autowired
	ModelMapper modelMapper;
	
	final Logger LOGGER = LoggerFactory.getLogger(CheckoutServiceImpl.class);

	
	// ADD MORE BUSSINESS LOGIC TO THIS METHOD
	
	
	@Override
	public void placeOrder(Purchase purchase) {
		
		UserEntity user = userRepo.findByUserId(purchase.getCustomer().getUserID());
		
		if(user == null) {
			LOGGER.error("User not found with the id :" + purchase.getCustomer().getUserID());
			throw new UsernameNotFoundException("Cannot find a user with this id: " + purchase.getCustomer().getUserID());		
		}
		OrderEntity orderEntity = new OrderEntity();
		
		Set<OrderItemEntity> orderItems = new HashSet<>();
		purchase.getOrder().getOrderItems().forEach(it -> orderItems.add(modelMapper.map(it, OrderItemEntity.class)));
//		for(OrderItemCDTO order: purchase.getOrder().getOrderItems()) {
//			OrderItemEntity item = modelMapper.map(order, OrderItemEntity.class);
//			orderItems.add(item);
//		}
		
		orderEntity.setTotalPrice(purchase.getOrder().getTotalPrice());
		orderEntity.setTotalQuantity(purchase.getOrder().getTotalQuantity());
		orderItems.forEach(orderEntity::add);
		user.add(orderEntity);
		 userRepo.save(user);
		
		
	}

}
