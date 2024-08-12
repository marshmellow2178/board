package com.marshmellow.myboard.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq")
	private int seq;
	
	@Column(name = "user_id")
	private String id;
	
	@Column(name = "user_pw")
	private String pw;
	
	@Column(name = "user_nickname")
	private String nickname;
	
	@Column(name = "user_phone")
	private String phone;
	
	@Column(name = "user_email")
	private String email;
	
	@Column(name = "user_point")
	private int point;
	
	@Column(name = "user_state")
	private int state;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
} 
