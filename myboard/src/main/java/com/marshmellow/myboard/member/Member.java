package com.marshmellow.myboard.member;

import java.util.List;

import com.marshmellow.myboard.Comment.Comment;
import com.marshmellow.myboard.Post.Post;
import com.marshmellow.myboard.Reply.Reply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String uid;
	
	private String pw;
	
	private String email;
	
	@OneToMany(mappedBy = "member")
	List<Post> postList;
	
	@OneToMany(mappedBy = "member")
	List<Comment> commentList;
	
	@OneToMany(mappedBy = "member")
	List<Reply> replyList;
} 
