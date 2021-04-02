package org.cigma.dev.service;

import org.cigma.dev.model.request.Purchase;

public interface CheckoutService {
	
	void placeOrder(Purchase purchase);

}
