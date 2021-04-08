package org.cigma.dev.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.cigma.dev.exceptions.ProductServiceException;
import org.cigma.dev.model.entity.CartEntity;
import org.cigma.dev.model.entity.ProductEntity;
import org.cigma.dev.model.request.ProductCDTO;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.model.response.RequestOperationStatus;
import org.cigma.dev.repository.CarteRepository;
import org.cigma.dev.repository.ProductRepository;
import org.cigma.dev.rules.ProductRules;
import org.cigma.dev.service.ProductService;
import org.cigma.dev.shared.Utils;
import org.cigma.dev.shared.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	Utils utils;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ProductRules productRules;

	@Autowired
	CarteRepository cartRepo;
	
	
	@Override
	public FeedbackMessage addProduct(ProductDTO productDto) {
		
		ProductEntity product = productRepo.findBySku(productDto.getSku());
		Optional.ofNullable(product).ifPresent(p -> {
			throw new ProductServiceException("Product already exists");
		});
		ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
		productEntity.setProductID(utils.generateID(12));
		 productRepo.save(productEntity);
		FeedbackMessage feedback = new FeedbackMessage();
		feedback.setOperationName("Product was added to database");
		feedback.setOperationResult(RequestOperationStatus.SUCCESS.name());
		
		return feedback;
	}


	@Override
	public FeedbackMessage deleteProduct(String id) {
		
		productRules.productExists(id);
		ProductEntity product = productRepo.findByProductID(id);
		List<CartEntity> carts = cartRepo.findByProduct(product);
		FeedbackMessage feedback = new FeedbackMessage();
		if(!carts.isEmpty()) {
			feedback.setOperationName("Product cant be deleted from database");
			feedback.setOperationResult(RequestOperationStatus.ERROR.name());
			return feedback;
		}
		productRepo.delete(product);
		feedback.setOperationName("Product was deleted from database");
		feedback.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return feedback;
	}


	@Override
	public FeedbackMessage updateProduct(String id, ProductDTO product) {
		
		productRules.productExists(id);

		ProductEntity productToSave = productRepo.findByProductID(id);
		productToSave.setUnitPrice(product.getUnitPrice());
		productToSave.setUnitsInStock(product.getUnitsInStock());

		productToSave.setName(product.getName());
		productRepo.save(productToSave);
		
		FeedbackMessage feedback = new FeedbackMessage();
		feedback.setOperationName("Product was updated successfully");
		feedback.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return feedback;
	}


	@Override
	public ProductDTO getProduct(String id) {
		productRules.productExists(id);
		ProductEntity product = productRepo.findByProductID(id);
		ProductDTO productDto = modelMapper.map(product, ProductDTO.class);

		return productDto;
	}


	@Override
	public List<ProductCDTO> getProducts(int page, int limit) {
		List<ProductCDTO> returnValue = new ArrayList<>();
		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<ProductEntity> productsPage = productRepo.findAll(pageableRequest);
		List<ProductEntity> products = productsPage.getContent();
		
		for (ProductEntity productEntity : products) {
			ProductCDTO productDto = modelMapper.map(productEntity, ProductCDTO.class);
			returnValue.add(productDto);
		}
		return returnValue; 
	}




}
