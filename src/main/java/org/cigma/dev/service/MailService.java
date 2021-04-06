package org.cigma.dev.service;

import java.io.IOException;
import java.util.List;

import org.cigma.dev.model.entity.ProductEntity;
import org.cigma.dev.model.response.CredentialsCDTO;

public interface MailService {

	void sendUserCredentials(CredentialsCDTO user);

	void sendEmail(String token, String email);
	
	void sendCsvFile(String email, List<ProductEntity> products) throws IOException;
}
