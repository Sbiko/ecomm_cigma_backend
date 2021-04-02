package org.cigma.dev.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.cigma.dev.model.response.CredentialsCDTO;
import org.cigma.dev.service.MailService;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.slf4j.Logger;

@Service
public class MailServiceImpl implements MailService {

	private final TemplateEngine templateEngine;

	private final JavaMailSender javaMailSender;

	Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	public MailServiceImpl(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
		this.templateEngine = templateEngine;
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendUserCredentials(CredentialsCDTO user) {
		try {
			Context context = new Context();
			context.setVariable("user", user);
			String process = templateEngine.process("emails/wel", context);
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject("Welcome " + user.getEmail());
			helper.setText(process, true);
			helper.setTo(user.getEmail());
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("Failed to send email", e);
			throw new IllegalStateException("failed to send email");

		}

	}

	public void sendEmail(String token, String email) {
		try {
			Context context = new Context();
			context.setVariable("token", token);
			String process = templateEngine.process("emails/reset_password", context);
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject("Reset password request ");
			helper.setText(process, true);
			helper.setTo(email);
			javaMailSender.send(mimeMessage);

		} catch (MessagingException e) {
			logger.error("Failed to send email", e);
			throw new IllegalStateException("failed to send email");
		}
	}

}
