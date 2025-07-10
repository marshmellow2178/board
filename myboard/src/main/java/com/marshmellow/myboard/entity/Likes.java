package com.marshmellow.myboard.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "likes",
		uniqueConstraints = @UniqueConstraint(
				name = "uniq_likes_user_board",
				columnNames = {"user_id", "board_id"}
		)
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id", nullable = false)
	private Board board;
	
	public static Likes create(User user, Board board) {
		Likes likes = new Likes();
		likes.user = user;
		likes.board = board;
		return likes;
	}
}
