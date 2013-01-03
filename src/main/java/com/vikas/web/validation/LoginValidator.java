package com.vikas.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.vikas.domain.Person;
import com.vikas.service.LoginService;

@Component("loginValidator")
public class LoginValidator {

	@Autowired
	LoginService loginService;

	public boolean supports(Class<?> klass) {
		return LoginValidator.class.isAssignableFrom(klass);
	}

	public void validate(Object target, Errors errors) {

		Person person = (Person) target;

		if (!person.getEmailAddress().equalsIgnoreCase(
				person.getReenterEmailAddress())) {
			errors.rejectValue("emailAddress", "emailAddress.mismatch");
			errors.rejectValue("reenterEmailAddress", "emailAddress.mismatch");
		} else if (loginService.findByUsername(person.getName()) != null) {
			errors.rejectValue("name", "name.already_exists");
		}

	}
}
