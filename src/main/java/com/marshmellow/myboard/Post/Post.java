package com.marshmellow.myboard.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import com.marshmellow.myboard.Category.Category;
import com.marshmellow.myboard.Comment.Comment;
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
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Comment> commentList;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Reply> replyList;
	
	@ManyToOne
	private Member member;
	
	@ManyToMany
	Set<Member> voter;
	
	@ManyToOne
	private Category category;
	
	@Transient
	boolean recommend;
	
	@ColumnDefault(value = "0")
	private int read;
}
