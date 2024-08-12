package com.marshmellow.myboard.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marshmellow.myboard.entity.Reply;

public interface ReplyRepo extends JpaRepository<Reply, Integer> {
	Page<Reply> findByBoardSeq(Pageable pageable, int boardSeq);
	Reply findBySeq(int seq);
}
