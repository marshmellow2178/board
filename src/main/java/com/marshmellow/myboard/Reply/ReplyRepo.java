package com.marshmellow.myboard.Reply;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepo extends JpaRepository<Reply, Integer> {
	Optional<Reply> findById(Integer id);
	Page<Reply> findAll(Specification<Reply> spec, Pageable pageable);
}
