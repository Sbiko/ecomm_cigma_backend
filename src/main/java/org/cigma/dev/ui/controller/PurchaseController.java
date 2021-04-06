package org.cigma.dev.ui.controller;

import org.cigma.dev.model.request.Purchase;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {
	final String URL_CHECKOUT = "/api/v0/checkout";
	
	@Autowired
	CheckoutService checkoutService;
	
	
	@PostMapping(URL_CHECKOUT)
	public ResponseEntity<FeedbackMessage> saveCheckout(@RequestBody Purchase purchase) {
		
		FeedbackMessage returnValue = checkoutService.placeOrder(purchase);
		return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
	}
	
	
}
