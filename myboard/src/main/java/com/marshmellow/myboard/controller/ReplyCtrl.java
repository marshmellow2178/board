package com.marshmellow.myboard.controller;

import com.marshmellow.myboard.entity.User;
import com.marshmellow.myboard.service.RepliesService;
import com.marshmellow.myboard.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RequestMapping("/boards/{boardId}/replies")
@RequiredArgsConstructor
@Controller
public class ReplyCtrl {

	private final RepliesService repliesService;
	private final UserService userService;

	@PostMapping
	public String create(
			@RequestParam String content,
			@PathVariable Long boardId,
			@AuthenticationPrincipal UserDetails userDetails
			) {
		User user = userService.findByUsername(userDetails);
		repliesService.create(content, boardId, user);
		return "redirect:/boards/"+boardId;
	}

	@PostMapping("/{replyId}/edit")
	public String modify(
			@PathVariable Long boardId,
			@PathVariable Long replyId,
			@RequestParam String content,
			@AuthenticationPrincipal UserDetails userDetails
			) {
		User user = userService.findByUsername(userDetails);
		repliesService.update(content, replyId, user);
		return "redirect:/boards/"+boardId;
	}

	@PostMapping("/{replyId}/delete")
	public String delete(
			@PathVariable Long boardId,
			@PathVariable Long replyId,
			@AuthenticationPrincipal UserDetails userDetails
			) {
		User user = userService.findByUsername(userDetails);
		repliesService.delete(replyId, user);
		return "redirect:/boards/"+boardId;
	}
	
}
