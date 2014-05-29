package com.vikas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vikas.dao.GameDAO;
import com.vikas.domain.Person;

/**
 * 
 * @author Vikas Sharma
 */
@Service
@Transactional
public class GameServiceImpl implements LoginService {

//	@Autowired
	private GameDAO gameDAO;

	@Override
	public void createAccount(Person person, String role) {


	}

	@Override
	public boolean activatePerson(long pid, int authKey) {

		return false;
	}

	@Override
	public boolean sendResetPasswordMail(Person person) {

		return false;
	}

	@Override
	public Person validatePerson(long pid, Integer authKey) {

		return null;
	}

	@Override
	public void resetPassword(Person person) {

	}

	@Override
	public List<String> getCountryList() {

		return null;
	}

	@Override
	public String getCountry(String remoteAddr) {

		return null;
	}

	@Override
	public Person findByUsername(String username) {

		return null;
	}
}
