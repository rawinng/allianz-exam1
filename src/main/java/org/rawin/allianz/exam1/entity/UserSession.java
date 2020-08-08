package org.rawin.allianz.exam1.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users_session")
public class UserSession {
	
	@Id
	@Column(name = "session_token")
	private String sessionToken;
	
	@ManyToOne
	@JoinColumn(name = "username")
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry")
	private Date expiry;

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
}
