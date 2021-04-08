package org.cigma.dev.repository;

import java.util.List;

import org.cigma.dev.model.entity.CartEntity;
import org.cigma.dev.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteRepository extends JpaRepository<CartEntity, Integer> {
	List<CartEntity> findByUserId(String userId);
	List<CartEntity> findByProduct(ProductEntity product);
	CartEntity findByCartId(String cartID);
	CartEntity findFirstByUserId(String userId);
}
