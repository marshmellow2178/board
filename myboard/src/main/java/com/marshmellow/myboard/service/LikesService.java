package com.marshmellow.myboard.service;

import com.marshmellow.myboard.entity.Board;
import com.marshmellow.myboard.entity.Likes;
import com.marshmellow.myboard.entity.User;
import com.marshmellow.myboard.repo.BoardRepository;
import com.marshmellow.myboard.repo.LikesRepository;
import com.marshmellow.myboard.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean toggleLike(Long boardId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));

        // 이미 좋아요를 눌렀는지 확인
        Optional<Likes> existing = likesRepository.findByUserAndBoard(user, board);

        if (existing.isPresent()) {
            // 눌러져 있으면 취소
            likesRepository.delete(existing.get());
            board.decrementLikeCount();
            return false; // false = “좋아요 취소됨”
        } else {
            // 없으면 새로 생성
            Likes like = Likes.create(user, board);
            board.incrementLikeCount();
            likesRepository.save(like);
            return true; // true = “좋아요 완료”
        }
    }

    @Transactional(readOnly = true)
    public List<Likes> findByUser(User user) {
        return likesRepository.findByUserId(user.getId());
    }

    public Long countByUser(User user) {
        return likesRepository.countByUserId(user.getId());
    }
}
