package com.vikas.service;

import com.vikas.domain.Person;

/**
 * 
 * @author Vikas Sharma
 */
public interface LoginService {

	void createAuthKey(Person person);

	void createAccount(Person person, String role);

	boolean activatePerson(long pid, int authKey);

	Person findByUsername(String username);

	Person findByEmailAddress(String emailAddress);

	void sendActivationMail(Person person);

	void sendResetPasswordMail(Person person);

	Person validatePerson(long pid, Integer authKey);

	void resetPassword(Person person);
}
