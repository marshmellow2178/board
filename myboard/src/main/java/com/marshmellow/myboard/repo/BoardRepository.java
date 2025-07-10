package com.marshmellow.myboard.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marshmellow.myboard.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	Page<Board> findByTitleContaining(Pageable pageable, String keyword);

    Page<Board> findByUserId(Long id, Pageable pageable);
    Long countByUserId(Long id);
}
