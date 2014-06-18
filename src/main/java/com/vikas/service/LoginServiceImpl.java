package com.vikas.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.maxmind.geoip.LookupService;
import com.vikas.dao.LoginDAO;
import com.vikas.domain.Person;
import com.vikas.domain.PersonRole;

/**
 * 
 * @author Vikas Sharma
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	private static Logger LOGGER = LoggerFactory
			.getLogger(LoginServiceImpl.class);

	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	@Value("${context.path}")
	private String contextPath;

	@Autowired
	private LoginDAO loginDAO;

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

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(person.getPassword());
		person.setEncodedPassword(encodedPassword);

		person.setStatus("InActive");
		person.setCreated(new Date());

		PersonRole personRole = new PersonRole();
		personRole.setRole(1);

		person.setPersonRole(personRole);

		loginDAO.addPerson(person);

		try {
			Object[] args = new Object[] { person.getFirstName(),
					person.getLastName(), contextPath,
					String.valueOf(person.getPersonId()),
					String.valueOf(person.getAuthKey()) };

			String message = messageSource.getMessage("mail.activation", args,
					Locale.US);
			sendMail(person, message);
		} catch (MailException e) {
			LOGGER.error("failed to send email, exception: {}", e.toString());
		}
	}

	private void createAuthKey(Person person) {

		int authKey = 10000000 + (int) (Math.random() * 99999999);
		person.setAuthKey(authKey);
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
	public boolean sendResetPasswordMail(Person person) {

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

		try {
			Object[] args = new Object[] { p.getFirstName(), p.getLastName(),
					contextPath, String.valueOf(p.getPersonId()),
					String.valueOf(p.getAuthKey()) };

			String message = messageSource.getMessage("mail.resetPassword",
					args, Locale.US);
			sendMail(p, message);
		} catch (MailException e) {
			LOGGER.error("failed to send email, exception: {}", e.toString());
			return false;
		}

		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Person validatePerson(long pid, Integer authKey) {

		Person person = loginDAO.findByPID(pid);

		if (person == null || !authKey.equals(person.getAuthKey())) {
			LOGGER.error("pid {} with authKey {} not found.", pid, authKey);
			return null;
		}

		return person;
	}

	@Override
	@Transactional
	public void resetPassword(Person person) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(person.getPassword());

		loginDAO.updatePassword(person.getPersonId(), encodedPassword);
	}

	@Override
	public List<String> getCountryList() {

		List<String> countries = new ArrayList<String>();

		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales) {

			String name = locale.getDisplayCountry();
			if (!"".equals(name) && !countries.contains(name)) {

				countries.add(name);
			}
		}

		Collections.sort(countries);

		return countries;
	}

	@Override
	public String getCountry(String remoteAddr) {

		try {
			String dbfile = "/usr/share/GeoIP/GeoIP.dat";
			LookupService cl = new LookupService(dbfile,
					LookupService.GEOIP_MEMORY_CACHE);

			return cl.getCountry(remoteAddr).getName();
		} catch (Exception e) {
			return "India";
		}
	}

	private void sendMail(Person person, String message) throws MailException {

		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(person.getEmailAddress());
		msg.setText(message);
		this.mailSender.send(msg);
	}
}
