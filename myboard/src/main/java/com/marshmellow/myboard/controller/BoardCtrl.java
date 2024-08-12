package com.marshmellow.myboard.controller;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.marshmellow.myboard.entity.Like;
import com.marshmellow.myboard.entity.Board;
import com.marshmellow.myboard.entity.UserInfo;
import com.marshmellow.myboard.repo.BoardRepo;
import com.marshmellow.myboard.repo.LikeRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardCtrl {
	
	private final BoardRepo boardRepo;
	private final LikeRepo likeRepo;
	
	@GetMapping("/list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value= "keyword", defaultValue = "") String keyword,
			@RequestParam(value= "sort", defaultValue = "d") String sort,
			Model model){
		String properties = "createDate";
		if(sort.equals("r")) {
			properties = "like";
		}
		if(page<1) {page = 1;}
		Pageable pageable = PageRequest.of(page-1, 10, Direction.DESC, properties);
		Page<Board> boardPage = boardRepo.findByTitleContaining(pageable, keyword);
		model.addAttribute("boardPage", boardPage);
		return "board_list";
	}

	@GetMapping("/detail")
	public String detail(
			Model model,
			@RequestParam(name = "id") int seq
			){
		model.addAttribute("board", boardRepo.findBySeq(seq));
		return "board_detail";
	}
	
	@GetMapping("/create")
	public String create(
			HttpSession session,
			Model model) {
		UserInfo user = (UserInfo)session.getAttribute("userInfo");
		if(user==null) {
			return "redirect:/login";
		}
		return "board_form";
	}
	
	@PostMapping("/create")
	public String create(
			@RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content,
			HttpSession session
			) {
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setUser((UserInfo)session.getAttribute("userInfo"));
		return "redirect:/board/detail?id="+boardRepo.save(board).getSeq();
	}
	
	@GetMapping("/modify")
	public String modify(
			HttpSession session,
			@RequestParam(name = "id") int seq,
			Model model
			) {
		UserInfo user = (UserInfo)session.getAttribute("userInfo");
		if(user==null) {
			return "redirect:/login";
		}
		model.addAttribute("board", boardRepo.findBySeq(seq));
		return "board_form";
	}
	
	@PostMapping("/modify")
	public String modify(
			@RequestParam(name = "id") int seq,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content,
			HttpSession session
			) {
		Board board = boardRepo.findBySeq(seq);
		board.setTitle(title);
		board.setContent(content);
		board.setModifyDate(LocalDateTime.now());
		boardRepo.save(board);
		return "redirect:/board/detail?id="+board.getSeq();
	}
	
	@GetMapping("/delete")
	public String delete(
			@RequestParam(name = "id") int seq,
			HttpSession session
			){
		Board board = boardRepo.findBySeq(seq);
		boardRepo.delete(board);
		return "redirect:/board/list";
	}
	
	@GetMapping("/like")
	public int like(
			@RequestParam(name = "id") int seq,
			HttpSession session
			) {
		UserInfo user = (UserInfo)session.getAttribute("userInfo");
		if(user==null) {
			return -1;
		}
		Board board = boardRepo.findBySeq(seq);
		Like like = likeRepo.findByUserSeqAndBoardSeq(user.getSeq(), seq);
		if(like == null) {
			like = new Like();
			like.setLike(user, board);
			likeRepo.save(like);
			return 1;
		}else {
			likeRepo.delete(like);
			return 0;
		}
	}
}
