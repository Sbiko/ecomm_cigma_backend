package org.cigma.dev.model.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity(name="users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 6771624715841380030L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=false, length=50)
	private String firstName;
	
	@Column(nullable=false, length= 50)
	private String lastName;
	
	@Column(nullable=false, length=120, unique = true)
	private String email;
	
	@Column(nullable=false)
	private String encryptedPassword;
	
	@Column(nullable=false)
	private String phoneNumber;
	
	@Column(nullable=false, unique=true)
	private String nickname;
	
	
//	@Column(nullable=false)
//	private Boolean emailVerificationStatus = false;
	
//	@ManyToMany(cascade= {CascadeType.PERSIST}, fetch= FetchType.EAGER)
//	@JoinTable(name="users_roles",
//			joinColumns=@JoinColumn(name="users_id", referencedColumnName="id"),
//			inverseJoinColumns=@JoinColumn(name="roles_id", referencedColumnName="id")
//	private Collection<RoleEntity> roles;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

//	public String getEmailVerificationToken() {
//		return emailVerificationToken;
//	}
//
//	public void setEmailVerificationToken(String emailVerificationToken) {
//		this.emailVerificationToken = emailVerificationToken;
//	}

//	public Boolean getEmailVerificationStatus() {
//		return emailVerificationStatus;
//	}
//
//	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
//		this.emailVerificationStatus = emailVerificationStatus;
//	}

//	private String emailVerificationToken;
	
	
}
