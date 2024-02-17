package com.marshmellow.myboard.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marshmellow.myboard.Post.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	Page<Comment> findByPost(Post p, Pageable pageable);
	Page<Comment> findAll(Specification<Comment> spec, Pageable pageable);
}
