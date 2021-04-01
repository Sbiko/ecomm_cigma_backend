package org.cigma.dev.service;

import javax.mail.MessagingException;

import org.cigma.dev.model.response.CredentialsCDTO;

public interface MailService {
	
	void sendUserCredentials(CredentialsCDTO user) throws MessagingException;
	 boolean sendEmail(String token, String email);
}
