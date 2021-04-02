package org.cigma.dev.ui.controller;

import javax.validation.Valid;

import org.cigma.dev.model.request.ProductCDTO;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.service.ProductService;
import org.cigma.dev.shared.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	final String URL_PRODUCT = "/api/v0/products";
	@Autowired
	ProductService productService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PostMapping(URL_PRODUCT)
	public ResponseEntity<FeedbackMessage> addProduct(@Valid @RequestBody ProductCDTO product ) {
		ProductDTO productDto = modelMapper.map(product, ProductDTO.class);
		FeedbackMessage feedback = productService.addProduct(productDto);
		return new ResponseEntity<>(feedback, HttpStatus.CREATED);
	}
	
	
	// DELETE PRODUCT
	
	// UPDATE PRODUCT
	
	// GET LIST OF PRODUCTS
	
	// GET PRODUCT

}
