package com.marshmellow.myboard.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {
	Page<Post> findAll(Pageable pageable);
	Page<Post> findAll(Specification<Post> spec, Pageable pageable);
}
