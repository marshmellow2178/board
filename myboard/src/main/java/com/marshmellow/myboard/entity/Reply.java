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
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reply_seq")
	private int seq;
	
	@Column(name = "reply_content")
	private String content;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@Column(name = "modify_date")
	private LocalDateTime modifyDate;
	
	@Column(name = "board_seq")
	private int boardSeq;
	
	@Column(name = "user_seq")
	private int userSeq;
	
	@Column(name = "reply_state")
	private int state;
	
	@ManyToOne
	@JoinColumn(name = "user_seq", insertable=false, updatable=false)
	private UserInfo user;
	
	@ManyToOne
	@JoinColumn(name = "board_seq", insertable=false, updatable=false)
	private Board board;
	
	public void setUser(UserInfo user) {
		this.user = user;
		this.userSeq = user.getSeq();
	}
}
