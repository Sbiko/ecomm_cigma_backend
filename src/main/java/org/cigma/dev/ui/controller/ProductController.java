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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		FeedbackMessage returnValue = productService.addProduct(productDto);
		return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping(URL_PRODUCT + "/{id}")
	public ResponseEntity<FeedbackMessage> deleteProduct(@PathVariable String id) {
		FeedbackMessage returnValue = productService.deleteProduct(id);
		return ResponseEntity.ok(returnValue);
	}
	

	@PutMapping(URL_PRODUCT + "/{id}")
	public ResponseEntity<FeedbackMessage> updateProduct(@PathVariable String id, @RequestBody ProductCDTO product) {
		ProductDTO productDto = new ProductDTO();
		productDto.setName(product.getName());
		productDto.setUnitPrice(product.getUnitPrice());
		FeedbackMessage returnValue = productService.updateProduct(id, productDto);
		return ResponseEntity.ok(returnValue);
	}
	
	

	@GetMapping(URL_PRODUCT + "/{id}")
	public ResponseEntity<ProductCDTO> getProduct(@PathVariable String id) {
		ProductDTO productDto = productService.getProduct(id);
		ProductCDTO returnValue = modelMapper.map(productDto, ProductCDTO.class);
		return ResponseEntity.ok(returnValue);
	}
	
	// GET LIST OF PRODUCTS
}
