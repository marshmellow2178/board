package com.marshmellow.myboard.service;

import com.marshmellow.myboard.entity.Board;
import com.marshmellow.myboard.entity.User;
import com.marshmellow.myboard.repo.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //생성
    @Transactional
    public Long create(String title, String content, User user) {
        Board board = Board.create(title, content, user);
        return boardRepository.save(board).getId();
    }

    //읽기
    @Transactional(readOnly = true)
    public Board read(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("글이 존재하지 않습니다"));
    }

    @Transactional(readOnly = true)
    public Page<Board> readAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Board> readByTitleContaining(Pageable pageable, String keyword) {
        return boardRepository.findByTitleContaining(pageable, keyword);
    }

    @Transactional(readOnly = true)
    public Long countByUser(User user) {
        return boardRepository.countByUserId(user.getId());
    }

    //수정
    @Transactional
    public void update(Long boardId, String title, String content, User user){
        Board board = read(boardId);
        board.update(title, content);
    }

    //삭제
    @Transactional
    public void delete(Long boardId, User user) {
        Board board = read(boardId);
        boardRepository.deleteById(boardId);
    }

    //권한체크
    public void validate(Board board, User user){
         if(!board.getUser().getId().equals(user.getId())){
             throw new AccessDeniedException("권한이 없습니다");
         }
    }

    //조회수
    @Transactional
    public void incrementView(Long boardId) {
        Board board = read(boardId);
        board.incrementViewCount();
    }

    //좋아요
    @Transactional
    public void incrementLike(Long boardId) {
        Board board = read(boardId);
        board.incrementLikeCount();
    }

    @Transactional(readOnly = true)
    public Page<Board> readByAuthor(User user, Pageable pageable) {
        return boardRepository.findByUserId(user.getId(), pageable);
    }
}
