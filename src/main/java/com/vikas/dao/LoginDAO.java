package com.vikas.dao;

import com.vikas.domain.Person;

/**
 * 
 * @author Vikas Sharma
 */
public interface LoginDAO {

	void addPerson(Person person);

	boolean activatePerson(long pid, Integer authKey);

	Person findByUsername(String username);

	Person findByEmailAddress(String emailAddress);

	Person findByPID(long pid);

	void updatePassword(long pid, String encodedPassword);
}
