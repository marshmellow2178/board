package com.marshmellow.myboard.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.marshmellow.myboard.Post.Post;
import com.marshmellow.myboard.Reply.Reply;
import com.marshmellow.myboard.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	
	@OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
	private List<Reply> replyList;
	
	@ManyToOne
	private Post post;
	
	@ManyToOne
	private Member member;
	
	@ManyToMany
	Set<Member> voter;
	
	@Transient
	boolean recommend;
}
