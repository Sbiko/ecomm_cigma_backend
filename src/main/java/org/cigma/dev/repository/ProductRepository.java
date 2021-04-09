package org.cigma.dev.repository;


import org.cigma.dev.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	
//	@Query("SELECT p from ProductEntity p WHERE p.name LIKE %?1%")
//	List<ProductEntity> findWithKeyword(String keyword);
	Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
	ProductEntity findByProductID(String productId);
	ProductEntity findBySku(String sku);
}
