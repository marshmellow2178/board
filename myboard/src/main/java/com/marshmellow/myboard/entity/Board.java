package com.marshmellow.myboard.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_seq")
	private int seq;
	
	@Column(name = "board_title")
	private String title;
	
	@Column(name = "board_content")
	private String content;
	
	@Column(name = "board_like")
	private int like;
	
	@Column(name = "board_read")
	private int read;
	
	@Column(name = "board_state")
	private int state;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@Column(name = "modify_date")
	private LocalDateTime modifyDate;
	
	@Column(name = "user_seq")
	private int userSeq;
	
	@ManyToOne
	@JoinColumn(name = "user_seq", insertable=false, updatable=false)
	private UserInfo user;
	
	public void setUser(UserInfo user) {
		this.user = user;
		this.userSeq = user.getSeq();
	}
}
