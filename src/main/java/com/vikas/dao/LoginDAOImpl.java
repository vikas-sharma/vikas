package com.vikas.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vikas.domain.Person;

/**
 * 
 * @author Vikas Sharma
 */
@Repository
public class LoginDAOImpl implements LoginDAO {

	private static Logger LOGGER = LoggerFactory.getLogger(LoginDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void persist(Person person) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		/*
		 * Because we are supposed to maintain the coherence of the object graph
		 * and, more important, because we configured the EmployeeDetail entity
		 * with an ID generator which gets the ID of PersonRole from its person
		 * property. So obviously, if this property is null, Hibernate can't
		 * generate the ID.
		 */
		person.getPersonRole().setPerson(person);

		session.save(person);

		tx.commit();
	}

	@Override
	public boolean activatePerson(long pid, Integer authKey) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Person person = (Person) session.get(Person.class, pid);

		if (person == null || !authKey.equals(person.getAuthKey())) {
			return false;
		}

		person.setStatus("Active");
		person.setActivated(new Date());

		tx.commit();

		return true;
	}

	@Override
	public Person findByUsername(String username) {

		Session session = sessionFactory.openSession();
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

		Session session = sessionFactory.openSession();
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

		Session session = sessionFactory.openSession();

		Person person = (Person) session.get(Person.class, pid);

		return person;
	}

	@Override
	public void update(Person person) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(person);
		tx.commit();
	}

}
