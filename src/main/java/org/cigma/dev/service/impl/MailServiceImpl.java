package org.cigma.dev.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.cigma.dev.model.entity.ProductEntity;
import org.cigma.dev.model.response.CredentialsCDTO;
import org.cigma.dev.model.response.ExcelFileExporter;
import org.cigma.dev.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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

	@Override
	public void sendCsvFile(String email, List<ProductEntity> products) throws IOException {
		ByteArrayInputStream stream = ExcelFileExporter.contactListToExcelFile(products);

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.stmp.user", "xxxx");

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.password", "xxxx");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("sbikowak@gmail.com", "ccidbychlrattmwb");
			}
		});
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sbikowak@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Payment file");

			byte[] fileByteArray = IOUtils.toByteArray(stream);
			byte[] fileBase64ByteArray = java.util.Base64.getEncoder().encode(fileByteArray);
			InternetHeaders fileHeaders = new InternetHeaders();
			fileHeaders.setHeader("Content-Type", "application/vnd.ms-excel\r\n" + "" + "; name=\"" + products + "\"");
			fileHeaders.setHeader("Content-Transfer-Encoding", "base64");
			fileHeaders.setHeader("Content-Disposition", "attachment; filename=\"" + products + "\"");

			MimeBodyPart mbp = new MimeBodyPart(fileHeaders, fileBase64ByteArray);
			mbp.setFileName("orders.xlsx");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mbp);

			message.setContent(multipart);
			Transport.send(message);

		} catch (MessagingException e) {
			logger.error("Failed to send email", e);
			throw new IllegalStateException("failed to send email");
		}
	}

}
