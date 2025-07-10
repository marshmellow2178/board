package com.marshmellow.myboard.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marshmellow.myboard.entity.Reply;

import java.util.List;

public interface RepliesRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBoardId(Long boardId);

    List<Reply> findByUserId(Long id);

    Long countByUserId(Long id);
}
