package com.marshmellow.myboard.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.marshmellow.myboard.entity.Reply;
import com.marshmellow.myboard.entity.UserInfo;
import com.marshmellow.myboard.repo.ReplyRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class ReplyCtrl {

	private ReplyRepo replyRepo;
	
	@PostMapping("/create")
	public String create(
			HttpSession session,
			@RequestParam(name = "content")String content,
			@RequestParam(name = "id") int id
			) {
		UserInfo user = (UserInfo)session.getAttribute("userInfo");
		if(user==null) {
			return "redirect:/login";
		}
		Reply reply = new Reply();
		reply.setUser(user);
		reply.setBoardSeq(id);
		replyRepo.save(reply);
		return "redirect:/board/detail?id="+id;
	}
	

	@PostMapping("/modify")
	public String modify(
			HttpSession session,
			@RequestParam(name = "id") int id,
			@RequestParam(name = "content") String content
			) {
		UserInfo user = (UserInfo)session.getAttribute("userInfo");
		if(user==null) {
			return "redirect:/login";
		}
		Reply reply = replyRepo.findBySeq(id);
		reply.setContent(content);
		reply.setModifyDate(LocalDateTime.now());
		return "redirect:/board/detail?id="+id;
	}
	
	@GetMapping("/delete")
	public String delete(
			HttpSession session,
			@RequestParam(name = "id") int id
			) {
		UserInfo user = (UserInfo)session.getAttribute("userInfo");
		if(user==null) {
			return "redirect:/login";
		}
		Reply reply = replyRepo.findBySeq(id);
		replyRepo.delete(reply);
		return "redirect:/board/detail?id="+id;
	}
	
}
