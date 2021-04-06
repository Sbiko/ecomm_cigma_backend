package org.cigma.dev.service;

import org.cigma.dev.model.request.Purchase;
import org.cigma.dev.model.response.FeedbackMessage;

public interface CheckoutService {
	
	FeedbackMessage placeOrder(Purchase purchase);

}
