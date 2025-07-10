package com.marshmellow.myboard.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "title", length = 50)
	private String title;

	@Column(name = "author", length = 30)
	private String author;
	
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	
	@Column(name = "like_count")
	private int likeCount = 0;
	
	@Column(name = "view_count")
	private int viewCount = 0;

	@CreatedDate
	@Column(name = "create_date")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "modify_date")
	private LocalDateTime modifiedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	//생성
	public static Board create(
			String title,
			String content,
			User user
	){
		Assert.hasText(title, "Title cannot be empty");
		Assert.hasText(content, "Content cannot be empty");
		Board board = new Board();
		board.title = title;
		board.content = content;
		board.user = user;
		board.author = user.getUsername();
		board.viewCount = 0;
		board.likeCount = 0;
		return board;
	}

	//수정
	public void update(
			String title,
			String content
	){
		Assert.hasText(title, "Title cannot be empty");
		Assert.hasText(content, "Content cannot be empty");
		this.title = title;
		this.content = content;
		this.modifiedAt = LocalDateTime.now();
	}

	//조회수
	public void incrementViewCount(){
		this.viewCount++;
	}

	/**
	 * Increment like count by one.
	 */
	public void incrementLikeCount() {
		this.likeCount++;
	}

	/**
	 * Decrement like count by one.
	 */
	public void decrementLikeCount() {
		if (this.likeCount > 0) {
			this.likeCount--;
		}
	}
}
