package org.cigma.dev.ui.controller;

import java.util.List;

import org.cigma.dev.model.request.ProductCDTO;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.service.CarteService;
import org.cigma.dev.shared.dto.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
	final String URL_CART = "/api/v0/carts";

	@Autowired
	CarteService cartService;
	
	
	@PostMapping(URL_CART)
	public ResponseEntity<CartDto> createCart(@RequestBody CartDto cart) {
		CartDto returnValue  = cartService.createCart(cart);
		return  new ResponseEntity<>(returnValue, HttpStatus.CREATED);
	}
	
	@DeleteMapping(URL_CART + "/{id}")
	public ResponseEntity<FeedbackMessage> deleteCart(@PathVariable(required = false) String id) {
		 FeedbackMessage returnValue = cartService.deleteCart(id);
		return ResponseEntity.ok(returnValue);
	}
	
	@GetMapping(URL_CART + "/{id}")
	public ResponseEntity<Integer> getCarts(@PathVariable() String id) {
		int qte = this.cartService.getBasketQuantity(id);
		return ResponseEntity.ok(qte);
	}
	
	@GetMapping(URL_CART + "/basket/{id}")
	public ResponseEntity<List<ProductCDTO>> getProductsOfBasket(@PathVariable() String id) {
		List<ProductCDTO> returnValue = this.cartService.getAllProducts(id);
		return ResponseEntity.ok(returnValue);
	}
	
}
