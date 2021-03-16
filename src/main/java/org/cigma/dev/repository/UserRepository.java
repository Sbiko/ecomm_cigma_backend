package org.cigma.dev.repository;

import org.cigma.dev.model.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>{

	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	UserEntity findByNickname(String username);
}
