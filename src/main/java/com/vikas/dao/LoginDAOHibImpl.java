package com.vikas.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vikas.domain.Person;

/**
 * 
 * @author Vikas Sharma
 */
public class LoginDAOHibImpl implements LoginDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addPerson(Person person) {

		Session session = sessionFactory.getCurrentSession();

		/*
		 * Because we are supposed to maintain the coherence of the object graph
		 * and, more important, because we configured the EmployeeDetail entity
		 * with an ID generator which gets the ID of PersonRole from its person
		 * property. So obviously, if this property is null, Hibernate can't
		 * generate the ID.
		 */
		person.getPersonRole().setPerson(person);

		session.save(person);
	}

	@Override
	public boolean activatePerson(long pid, Integer authKey) {

		Session session = sessionFactory.getCurrentSession();

		Person person = (Person) session.get(Person.class, pid);

		if (person == null || !authKey.equals(person.getAuthKey())) {
			return false;
		}

		person.setStatus("Active");
		person.setActivated(new Date());

		return true;
	}

	@Override
	public Person findByUsername(String username) {

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Person where name = :name ");
		query.setParameter("name", username);

		List<Person> persons = query.list();

		if (persons.size() == 0) {
			return null;
		}

		return (Person) persons.get(0);
	}

	@Override
	public Person findByEmailAddress(String emailAddress) {

		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from Person where emailAddress = :emailAddress ");
		query.setParameter("emailAddress", emailAddress);

		List<Person> persons = query.list();

		if (persons.size() == 0) {
			return null;
		}

		return (Person) persons.get(0);
	}

	@Override
	public Person findByPID(long pid) {

		Session session = sessionFactory.getCurrentSession();

		return (Person) session.get(Person.class, pid);
	}

	@Override
	public void updatePassword(long pid, String encodedPassword) {

	}

}
