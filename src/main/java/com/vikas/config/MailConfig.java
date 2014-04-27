package com.vikas.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.vikas.service.LoginServiceImpl;

/**
 * 
 * @author Vikas Sharma
 * 
 */
@Configuration
public class MailConfig {

	@Value("${mail.host}")
	private String host;

	@Value("${mail.port}")
	private String port;

	@Value("${mail.protocol}")
	private String protocol;

	@Value("${mail.username}")
	private String username;

	@Value("${mail.password}")
	private String password;

	@Value("${mail.subject}")
	private String subject;

	@Bean
	public JavaMailSenderImpl mailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(Integer.parseInt(port));
		mailSender.setProtocol(protocol);
		mailSender.setUsername(username);
		mailSender.setPassword("password");

		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtps.auth", "true");
		javaMailProperties.setProperty("mail.smtps.starttls.enable", "true");
		javaMailProperties.setProperty("mail.smtps.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;
	}

	// this is a template message that we can pre-load with default state.
	@Bean
	public SimpleMailMessage templateMessage() {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(username);
		msg.setSubject(subject);

		return msg;
	}

	@Bean
	public LoginServiceImpl loginService() {

		LoginServiceImpl service = new LoginServiceImpl();
		service.setMailSender(mailSender());
		service.setTemplateMessage(templateMessage());

		return service;
	}
}
