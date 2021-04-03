package org.cigma.dev.repository;


import org.cigma.dev.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	ProductEntity findByProductID(String productId);
	ProductEntity findBySku(String sku);
}
