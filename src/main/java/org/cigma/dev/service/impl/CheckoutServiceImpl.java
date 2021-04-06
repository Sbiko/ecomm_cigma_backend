package org.cigma.dev.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.cigma.dev.exceptions.ProductServiceException;
import org.cigma.dev.model.entity.OrderEntity;
import org.cigma.dev.model.entity.OrderItemEntity;
import org.cigma.dev.model.entity.ProductEntity;
import org.cigma.dev.model.entity.UserEntity;
import org.cigma.dev.model.request.OrderItemCDTO;
import org.cigma.dev.model.request.Purchase;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.model.response.RequestOperationName;
import org.cigma.dev.model.response.RequestOperationStatus;
import org.cigma.dev.repository.OrderRepository;
import org.cigma.dev.repository.ProductRepository;
import org.cigma.dev.repository.UserRepository;
import org.cigma.dev.service.CarteService;
import org.cigma.dev.service.CheckoutService;
import org.cigma.dev.service.MailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ProductRepository productRepo;

	@Autowired
	CarteService cartService;

	
	@Autowired
	MailService mailService;
	
	final Logger LOGGER = LoggerFactory.getLogger(CheckoutServiceImpl.class);

	@Autowired
	OrderRepository orderRepo;

	@Override
	@Transactional
	public FeedbackMessage placeOrder(Purchase purchase) {
		FeedbackMessage feedback = new FeedbackMessage();
		feedback.setOperationName(RequestOperationName.CHECKOUT.name());
		UserEntity user = userRepo.findByUserId(purchase.getCustomer().getUserID());

		if (user == null) {
			LOGGER.error("User not found with the id :" + purchase.getCustomer().getUserID());
			throw new UsernameNotFoundException(
					"Cannot find a user with this id: " + purchase.getCustomer().getUserID());
		}
		OrderEntity orderEntity = new OrderEntity();

		Set<OrderItemEntity> orderItems = new HashSet<>();
		List<ProductEntity> products = new ArrayList<>();
		for (OrderItemCDTO order : purchase.getOrder().getOrderItems()) {
			ProductEntity product = productRepo.findByProductID(order.getProductId());
			if (product.getUnitsInStock() < order.getQuantity()) {
				throw new ProductServiceException("Product is not available");
			}
			product.setUnitsInStock(product.getUnitsInStock() - order.getQuantity());
			products.add(product);
		}
		purchase.getOrder().getOrderItems().forEach(it -> {

			OrderItemEntity orderItem = new OrderItemEntity();
			orderItem.setProductId(it.getProductId());
			orderItem.setQuantity(it.getQuantity());
			orderItem.setUnitPrice(it.getUnitPrice());
			orderItems.add(orderItem);
		});

		orderEntity.setTotalPrice(purchase.getOrder().getTotalPrice());
		orderEntity.setTotalQuantity(purchase.getOrder().getTotalQuantity());
		orderItems.forEach(orderEntity::add);
		user.add(orderEntity);
		cartService.deleteCart(user.getUserId());
		UserEntity savedUser = userRepo.save(user);
		if(savedUser != null) {
			try {
				mailService.sendCsvFile(savedUser.getEmail(), products);
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			feedback.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}
		return feedback;

	}

}
