package org.cigma.dev.repository;


import java.util.List;

import org.cigma.dev.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	
	@Query("SELECT p from ProductEntity p WHERE p.name LIKE %?1%")
	List<ProductEntity> findWithKeyword(String keyword);
	ProductEntity findByProductID(String productId);
	ProductEntity findBySku(String sku);
}
