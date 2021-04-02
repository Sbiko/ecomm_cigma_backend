package org.cigma.dev.repository;

import java.util.Optional;

import org.cigma.dev.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	Optional<ProductEntity> findByProductID(String productId);
	Optional<ProductEntity> findBySku(String sku);
}
