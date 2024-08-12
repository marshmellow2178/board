package com.marshmellow.myboard.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marshmellow.myboard.entity.Like;

public interface LikeRepo extends JpaRepository<Like, Integer> {
	Like findByUserSeqAndBoardSeq(int userSeq, int boardSeq);
	List<Like> findByUserSeq(int userSeq);
	int countByBoardSeq(int boardSeq);
}
