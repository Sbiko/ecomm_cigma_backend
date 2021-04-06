package org.cigma.dev.service;

import java.util.List;

import org.cigma.dev.model.request.ProductCDTO;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.shared.dto.ProductDTO;

public interface ProductService {

	FeedbackMessage addProduct(ProductDTO product);
	FeedbackMessage deleteProduct(String id);
	FeedbackMessage updateProduct(String id, ProductDTO product);
	ProductDTO getProduct(String id);
	List<ProductCDTO> getProducts(int page, int limit);
}
