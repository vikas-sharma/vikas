package com.vikas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * A JPA entity for the security authorities table.
 * 
 * @author Vikas Sharma
 * 
 */
@Entity
public class PersonRole {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "person"))
	private Long personId;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Person person;

	private Integer role;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonRoleId(Long personId) {
		this.personId = personId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
}