package com.vikas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikas.dao.LoginDAO;
import com.vikas.domain.Person;
import com.vikas.domain.PersonRole;

/**
 * 
 * @author Vikas Sharma
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static Logger LOGGER = LoggerFactory
			.getLogger(LoginServiceImpl.class);

	@Autowired
	private LoginDAO loginDAO;

	@Autowired
	PasswordEncoder passwordEncoder;

	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	private SimpleMailMessage templateMessage;

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	@Override
	public void createAuthKey(Person person) {

		int authKey = 10000000 + (int) (Math.random() * 99999999);
		person.setAuthKey(authKey);
	}

	@Override
	public void createAccount(Person person, String role) {

		String encodedPassword = passwordEncoder.encodePassword(
				person.getPassword(), null);
		person.setEncodedPassword(encodedPassword);

		person.setStatus("InActive");
		person.setCreated(new Date());

		PersonRole personRole = new PersonRole();
		personRole.setRole(1);

		person.setPersonRole(personRole);

		loginDAO.persist(person);
	}

	@Override
	public boolean activatePerson(long pid, int authKey) {
		return loginDAO.activatePerson(pid, authKey);
	}

	@Override
	public Person findByUsername(String username) {
		return loginDAO.findByUsername(username);
	}

	@Override
	public Person findByEmailAddress(String emailAddress) {
		return loginDAO.findByEmailAddress(emailAddress);
	}

	@Override
	public void sendActivationMail(Person person) {

		StringBuilder message = new StringBuilder();
		message.append("Dear ")
				.append(person.getFirstName())
				.append(" ")
				.append(person.getLastName())
				.append(", welcome to vikasworld.com")
				.append("\n\nclick or copy this link to your browser to activate your account:")
				.append("\nhttp://localhost:8080/vikasworld/activate.htm?pid=")
				.append(person.getPersonId()).append("&authKey=")
				.append(person.getAuthKey())
				.append("\n\nRegards\n\nVikas Sharma");

		sendMail(person, message.toString());
	}

	@Override
	public void sendResetPasswordMail(Person person) {

		StringBuilder message = new StringBuilder();
		message.append("Forgot your password, ")
				.append(person.getFirstName())
				.append(" ")
				.append(person.getLastName())
				.append("\n\nWe received a request to reset the password for your account.")
				.append("\n\nTo reset your password, click on the link below (or copy and paste the URL into your browser):")
				.append("\nhttp://localhost:8080/vikasworld/password_reset.htm?pid=")
				.append(person.getPersonId()).append("&authKey=")
				.append(person.getAuthKey())
				.append("\n\nRegards\n\nVikas Sharma");

		sendMail(person, message.toString());
	}

	private void sendMail(Person person, String message) {

		LOGGER.debug("Mail message is {}", message);

		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(person.getEmailAddress());
		msg.setText(message);
		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	@Override
	public Person validatePerson(long pid, Integer authKey) {

		Person person = loginDAO.findByPID(pid);

		if (person == null || !authKey.equals(person.getAuthKey())) {
			return null;
		}

		return person;
	}

	@Override
	public void resetPassword(Person person) {

		String encodedPassword = passwordEncoder.encodePassword(
				person.getPassword(), null);
		person.setEncodedPassword(encodedPassword);

		loginDAO.update(person);
	}

	@Override
	public List<String> getCountryList() {

		List<String> countries = new ArrayList<String>();

		countries.add("India");
		countries.add("US");
		countries.add("UK");
		countries.add("Russia");
		countries.add("China");
		countries.add("Japan");
		countries.add("Spain");
		countries.add("Germany");

		return countries;
	}

	@Override
	public String getCountry(String remoteAddr) {
		return "India";
	}

}
