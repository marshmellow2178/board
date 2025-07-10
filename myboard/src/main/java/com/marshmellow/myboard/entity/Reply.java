package com.marshmellow.myboard.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "replies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "content")
	private String content;

	@CreatedDate
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@LastModifiedDate
	@Column(name = "modify_date")
	private LocalDateTime modifyDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	//생성
	public static Reply create(
			String content,
			Board board,
			User user
	){
		Assert.notNull(board, "Board must not be null");
		Assert.notNull(user, "User must not be null");
		Assert.hasText(content, "Content cannot be empty");

		Reply reply = new Reply();
		reply.content = content;
		reply.board = board;
		reply.user = user;
		return reply;
	}

	//수정
	public void update(
			String content
	){
		Assert.hasText(content, "Content cannot be empty");

		this.content = content;
	}
}
