package com.marshmellow.myboard.service;

import com.marshmellow.myboard.entity.Board;
import com.marshmellow.myboard.entity.Reply;
import com.marshmellow.myboard.entity.User;
import com.marshmellow.myboard.repo.BoardRepository;
import com.marshmellow.myboard.repo.RepliesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepliesService {

    private final RepliesRepository repliesRepository;
    private final BoardRepository boardRepository;

    //작성
    @Transactional
    public Long create(String content, Long boardId, User user){
        Board board = boardRepository.findById(boardId).orElseThrow(()->new EntityNotFoundException("글이 존재하지 않습니다"));
        Reply reply = Reply.create(content, board, user);
        repliesRepository.save(reply);
        return board.getId();
    }

    //수정
    @Transactional
    public void update(String content, Long replyId, User user){
        Reply reply = findById(replyId);
        validate(reply, user);
        reply.update(content);
    }

    //조회
    @Transactional(readOnly = true)
    public Reply findById(Long id){
        return repliesRepository.findById(id).orElseThrow(()->new EntityNotFoundException("댓글이 존재하지 않습니다"));
    }

    @Transactional(readOnly = true)
    public List<Reply> findAll(Long boardId){
        return repliesRepository.findByBoardId(boardId);
    }

    //삭제
    @Transactional
    public void delete(Long id, User user){
        Reply reply = findById(id);
        validate(reply, user);
        repliesRepository.delete(reply);
    }

    //권한 체크
    public void validate(Reply reply, User user){
        if(!reply.getBoard().getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("권한이 없습니다");
        }
    }

    @Transactional(readOnly = true)
    public List<Reply> findByUser(User user) {
        return repliesRepository.findByUserId(user.getId());
    }

    public Long countByUser(User user) {
        return repliesRepository.countByUserId(user.getId());
    }
}
