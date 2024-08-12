package com.marshmellow.myboard.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marshmellow.myboard.entity.Board;

public interface BoardRepo extends JpaRepository<Board, Integer> {
	Page<Board> findByTitleContaining(Pageable pageable, String keyword);
	Board findBySeq(int seq);
}
