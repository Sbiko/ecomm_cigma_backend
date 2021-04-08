package org.cigma.dev.service;

import java.util.List;

import org.cigma.dev.model.request.ProductCDTO;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.shared.dto.CartDto;

public interface CarteService {
	
	CartDto createCart(CartDto cart);
	FeedbackMessage deleteCart(String id);
	int getBasketQuantity(String userId);
	List<ProductCDTO> getAllProducts(String userId);

}
