package org.cigma.dev.repository;

import org.cigma.dev.model.entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {
	PasswordResetTokenEntity findByToken(String token);

}
