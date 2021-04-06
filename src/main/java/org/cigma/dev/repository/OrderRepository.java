package org.cigma.dev.repository;

import java.util.List;

import org.cigma.dev.model.entity.OrderEntity;
import org.cigma.dev.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	List<OrderEntity> findByCustomer(UserEntity user);

}
