package org.cigma.dev.ui.controller;

import org.cigma.dev.model.request.Purchase;
import org.cigma.dev.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

	
	@Autowired
	CheckoutService checkoutService;
	
	
	// ADD THE REST OF THE CODE 
	// RESPONSE
	
	
	@PostMapping("/add")
	public String saveCheckout(@RequestBody Purchase purchase) {
		
		checkoutService.placeOrder(purchase);
		return "yes saved";
	}
	
	
}
