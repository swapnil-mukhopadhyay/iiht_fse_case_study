package com.digitalbooks.author.entities.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TblAuthorCredential {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTHOR_CREDENTIAL_ID")
	private Long authorCredentialId;
	
	@Column(name = "USERNAME", unique = true)
	private String username;
	
	@Column(name = "PASSWORD")
	private String password;

	public Long getAuthorCredentialId() {
		return authorCredentialId;
	}

	public void setAuthorCredentialId(Long authorCredentialId) {
		this.authorCredentialId = authorCredentialId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
