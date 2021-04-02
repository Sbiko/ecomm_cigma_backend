package org.cigma.dev.service;

import org.cigma.dev.model.response.CredentialsCDTO;

public interface MailService {

	void sendUserCredentials(CredentialsCDTO user);

	void sendEmail(String token, String email);
}
