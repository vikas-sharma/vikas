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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
	@Transactional
	public void createAccount(Person person, String role) {

		createAuthKey(person);

		String encodedPassword = passwordEncoder.encodePassword(
				person.getPassword(), null);
		person.setEncodedPassword(encodedPassword);

		person.setStatus("InActive");
		person.setCreated(new Date());

		PersonRole personRole = new PersonRole();
		personRole.setRole(1);

		person.setPersonRole(personRole);

		loginDAO.persist(person);

		sendActivationMail(person);
	}

	private void createAuthKey(Person person) {

		int authKey = 10000000 + (int) (Math.random() * 99999999);
		person.setAuthKey(authKey);
	}

	private void sendActivationMail(Person person) {

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
	@Transactional
	public boolean activatePerson(long pid, int authKey) {
		return loginDAO.activatePerson(pid, authKey);
	}

	@Override
	@Transactional(readOnly = true)
	public Person findByUsername(String username) {
		return loginDAO.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean sendResetPasswordMail(Person person) throws MailException {

		Person p = null;
		if (StringUtils.hasText(person.getEmailAddress())) {
			p = loginDAO.findByEmailAddress(person.getEmailAddress());
		}

		if (p == null) {

			if (StringUtils.hasText(person.getName())) {
				p = loginDAO.findByUsername(person.getName());
			}
			if (p == null) {
				return false;
			}
		}

		StringBuilder message = new StringBuilder();
		message.append("Forgot your password, ")
				.append(p.getFirstName())
				.append(" ")
				.append(p.getLastName())
				.append("\n\nWe received a request to reset the password for your account.")
				.append("\n\nTo reset your password, click on the link below (or copy and paste the URL into your browser):")
				.append("\nhttp://localhost:8080/vikasworld/password_reset.htm?pid=")
				.append(p.getPersonId()).append("&authKey=")
				.append(p.getAuthKey()).append("\n\nRegards\n\nVikas Sharma");

		try {
			sendMail(p, message.toString());
		} catch (MailException e) {
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}

	private void sendMail(Person person, String message) throws MailException {

		LOGGER.debug("Mail message is {}", message);
		LOGGER.debug("Mail sent to {}", person.getEmailAddress());

		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(person.getEmailAddress());
		msg.setText(message);
		this.mailSender.send(msg);
	}

	@Override
	@Transactional(readOnly = true)
	public Person validatePerson(long pid, Integer authKey) {

		Person person = loginDAO.findByPID(pid);

		if (person == null || !authKey.equals(person.getAuthKey())) {
			return null;
		}

		return person;
	}

	@Override
	@Transactional
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
