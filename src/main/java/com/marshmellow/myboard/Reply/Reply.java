package com.marshmellow.myboard.Reply;

import java.time.LocalDateTime;
import java.util.Set;

import com.marshmellow.myboard.Comment.Comment;
import com.marshmellow.myboard.Post.Post;
import com.marshmellow.myboard.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Comment	 comment;
	
	@ManyToOne
	private Post post;
	
	@ManyToOne
	private Member member;
	
	@ManyToMany
	Set<Member> voter;
	
	@Transient
	boolean recommend;
}
