package com.marshmellow.myboard.controller;

import java.util.List;

import com.marshmellow.myboard.dto.LikeResponse;
import com.marshmellow.myboard.entity.Reply;
import com.marshmellow.myboard.entity.User;
import com.marshmellow.myboard.service.BoardService;
import com.marshmellow.myboard.service.LikesService;
import com.marshmellow.myboard.service.RepliesService;
import com.marshmellow.myboard.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.marshmellow.myboard.entity.Board;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/boards")
public class BoardCtrl {
	
	private final BoardService boardService;
	private final UserService userService;
	private final RepliesService repliesService;
	private final LikesService likesService;

	@GetMapping
	public String showBoardList(
			@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(required = false) String keyword,
			Model model){
		Page<Board> boardPage;
		if(keyword!=null && !keyword.isEmpty()){
			boardPage = boardService.readByTitleContaining(pageable, keyword);
			model.addAttribute("keyword", keyword);
		}else{
			boardPage = boardService.readAll(pageable);
		}
		model.addAttribute("boardPage", boardPage);
		return "board_list";
	}

	@GetMapping("/{id}")
	public String showBoardDetail(
			Model model,
			@PathVariable(name = "id") Long id
			){
		List<Reply> replyList = repliesService.findAll(id);
		boardService.incrementView(id);
		model.addAttribute("board", boardService.read(id));
		model.addAttribute("replyList", replyList);
		return "board_detail";
	}
	
	@GetMapping("/new")
	public String showCreateForm() {
		return "board_form";
	}
	
	@PostMapping
	public String postCreateForm(
			@RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content,
			@AuthenticationPrincipal UserDetails userDetails
			) {
		User user = userService.findByUsername(userDetails);
		return "redirect:/boards/"+boardService.create(title, content, user);
	}
	
	@GetMapping("/{id}/edit")
	public String showModifyForm(
			@PathVariable(name = "id") Long id,
			@AuthenticationPrincipal UserDetails userDetails,
			Model model
			) {
		User user = userService.findByUsername(userDetails);
		Board board = boardService.read(id);
		boardService.validate(board, user);
		model.addAttribute("board", board);
		return "board_form";
	}
	
	@PostMapping("/{id}")
	public String postModifyForm(
			@PathVariable(name = "id") Long id,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content,
			@AuthenticationPrincipal UserDetails userDetails
			) {
		User user = userService.findByUsername(userDetails);
		Board board = boardService.read(id);
		boardService.validate(board, user);
		boardService.update(id, title, content, user);
		return "redirect:/boards/"+id;
	}
	
	@PostMapping("/{id}/delete")
	public String delete(
			@PathVariable(name = "id") Long id,
			@AuthenticationPrincipal UserDetails userDetails
			){
		User user = userService.findByUsername(userDetails);
		Board board = boardService.read(id);
		boardService.validate(board, user);
		boardService.delete(id, user);
		return "redirect:/boards/list";
	}

	/**
	 * AJAX용 좋아요 토글 API
	 * 요청: POST /boards/{id}/like
	 * 응답: { "liked": true|false, "likeCount": 123 }
	 */
	@PostMapping(path = "/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<LikeResponse> toggleLike(
			@PathVariable(name = "id") Long id,
			@AuthenticationPrincipal UserDetails userDetails
			) {
		User user = userService.findByUsername(userDetails);
		boolean liked = likesService.toggleLike(id, user.getId());
		int likeCount = boardService.read(id).getLikeCount();
		LikeResponse dto = new LikeResponse(liked, likeCount);
		return ResponseEntity.ok(dto);
	}
}
