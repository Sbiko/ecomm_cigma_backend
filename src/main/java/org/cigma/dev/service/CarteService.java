package org.cigma.dev.service;

import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.shared.dto.CartDto;

public interface CarteService {
	
	CartDto createCart(CartDto cart);
	FeedbackMessage deleteCart(String id);

}
