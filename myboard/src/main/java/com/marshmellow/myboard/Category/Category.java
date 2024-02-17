package com.marshmellow.myboard.Category;

import java.util.List;

import com.marshmellow.myboard.Post.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 200)
	private String name;
	
	@OneToMany(mappedBy="category", cascade = CascadeType.REMOVE)
	List<Post> postList;
}
