package org.cigma.dev.service.impl;

import java.util.Optional;

import org.cigma.dev.exceptions.ProductServiceException;
import org.cigma.dev.model.entity.ProductEntity;
import org.cigma.dev.model.response.ErrorMessages;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.model.response.RequestOperationStatus;
import org.cigma.dev.repository.ProductRepository;
import org.cigma.dev.rules.ProductRules;
import org.cigma.dev.service.ProductService;
import org.cigma.dev.shared.Utils;
import org.cigma.dev.shared.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

		FeedbackMessage feedback = new FeedbackMessage();
		feedback.setOperationName("Product was deleted from database");
		feedback.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return feedback;
	}


	@Override
	public FeedbackMessage updateProduct(String id, ProductDTO product) {
		
		productRules.productExists(id);

		ProductEntity productToSave = productRepo.findByProductID(id);
		productToSave.setUnitPrice(product.getUnitPrice());
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

}
