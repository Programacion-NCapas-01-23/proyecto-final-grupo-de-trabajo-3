package com.swifticket.web.models.entities;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "verify_account_tokens")
public class VerifyAccountToken {
	@Id
	private String code;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
	
	@Column(name = "verified_at", nullable = true)
	private Date verifiedAt;
	
	@Column(name = "expires_at", nullable = false)
	private Date expiresAt;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	public VerifyAccountToken(String code, User user, Date expiresAt) {
		super();
		this.code = code;
		this.user = user;
		this.expiresAt = expiresAt;
	}
	
}
