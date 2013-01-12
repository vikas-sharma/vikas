package com.vikas.service;

import java.util.List;

import com.vikas.domain.Person;

/**
 * 
 * @author Vikas Sharma
 */
public interface LoginService {

	void createAccount(Person person, String role);

	boolean activatePerson(long pid, int authKey);

	boolean sendResetPasswordMail(Person person);

	Person validatePerson(long pid, Integer authKey);

	void resetPassword(Person person);

	List<String> getCountryList();

	String getCountry(String remoteAddr);

	Person findByUsername(String username);
}
