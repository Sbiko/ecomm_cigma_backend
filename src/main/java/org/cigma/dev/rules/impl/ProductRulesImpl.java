package org.cigma.dev.rules.impl;

import java.util.Optional;

import org.cigma.dev.exceptions.ProductServiceException;
import org.cigma.dev.model.entity.ProductEntity;
import org.cigma.dev.model.response.ErrorMessages;
import org.cigma.dev.repository.ProductRepository;
import org.cigma.dev.rules.ProductRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRulesImpl implements ProductRules {

	@Autowired
	ProductRepository productRepo;
	
	
	@Override
	public void productExists(String id) {
		ProductEntity productEntity = productRepo.findByProductID(id);
		Optional<ProductEntity> product = Optional.ofNullable(productEntity);
		if(!(product.isPresent())) {
			throw new ProductServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		
	}

}
