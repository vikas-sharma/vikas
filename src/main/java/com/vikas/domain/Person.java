package com.vikas.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author Vikas Sharma
 */
@Entity
public class Person implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long personId;

	@Size(min = 3, max = 20)
	@Pattern(regexp = "[a-z][0-9a-z_]*")
	@Column(unique = true)
	private String name;

	@Size(min = 6, max = 20)
	@Transient
	private String password;

	private String encodedPassword;

	@NotEmpty
	@Email
	private String emailAddress;

	@Email
	@Transient
	private String reenterEmailAddress;

	@Size(min = 1, max = 20)
	@Pattern(regexp = "[a-z]*")
	private String firstName;

	@Size(min = 1, max = 20)
	@Pattern(regexp = "[a-z]*")
	private String lastName;

	@NotNull
	private String gender;

	@NotNull
	private String country;

	private String ipAddress;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activated;

	@Transient
	private String captcha;

	private Integer authKey;
	private String status;

	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
	private PersonRole personRole;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getReenterEmailAddress() {
		return reenterEmailAddress;
	}

	public void setReenterEmailAddress(String reenterEmailAddress) {
		this.reenterEmailAddress = reenterEmailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getActivated() {
		return activated;
	}

	public void setActivated(Date activated) {
		this.activated = activated;
	}

	public Integer getAuthKey() {
		return authKey;
	}

	public void setAuthKey(Integer authKey) {
		this.authKey = authKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PersonRole getPersonRole() {
		return personRole;
	}

	public void setPersonRole(PersonRole personRole) {
		this.personRole = personRole;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	// work with EntityManager in JPA
	// @PrePersist
	// protected void onCreate() {
	// created = new Date();
	// }
	//
	// @PreUpdate
	// protected void onUpdate() {
	// updated = new Date();
	// }
}
