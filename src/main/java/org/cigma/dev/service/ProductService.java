package org.cigma.dev.service;

import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.shared.dto.ProductDTO;

public interface ProductService {

	FeedbackMessage addProduct(ProductDTO product);
	FeedbackMessage deleteProduct(String id);
	FeedbackMessage updateProduct(String id, ProductDTO product);
	ProductDTO getProduct(String id);
}
