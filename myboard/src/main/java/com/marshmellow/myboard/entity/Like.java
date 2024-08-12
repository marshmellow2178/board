package com.marshmellow.myboard.entity;

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
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_seq")
	private int seq;
	
	@Column(name = "board_seq")
	private int boardSeq;
	
	@Column(name = "user_seq")
	private int userSeq;
	
	@ManyToOne
	@JoinColumn(name = "user_seq", insertable=false, updatable=false)
	private UserInfo user;
	
	@ManyToOne
	@JoinColumn(name = "board_seq", insertable=false, updatable=false)
	private Board board;
	
	public void setLike(UserInfo user, Board board) {
		this.user = user;
		this.board = board;
		this.userSeq = user.getSeq();
		this.boardSeq = board.getSeq();
	}
}
