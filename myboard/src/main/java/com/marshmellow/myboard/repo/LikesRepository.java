package com.marshmellow.myboard.repo;

import java.util.List;
import java.util.Optional;

import com.marshmellow.myboard.entity.Board;
import com.marshmellow.myboard.entity.Likes;
import com.marshmellow.myboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	//좋아요 확인
	boolean existsByUserAndBoard(User user, Board board);
	//좋아요 토글
	Optional<Likes> findByUserAndBoard(User user, Board board);
	//사용자로 찾기
	List<Likes> findByUserId(Long userId);

    Long countByUserId(Long id);
}
