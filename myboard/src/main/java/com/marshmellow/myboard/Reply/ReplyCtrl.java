package com.marshmellow.myboard.Reply;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.marshmellow.myboard.Comment.Comment;
import com.marshmellow.myboard.Comment.CommentSvc;
import com.marshmellow.myboard.member.Member;
import com.marshmellow.myboard.member.MemberSvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class ReplyCtrl {
	
	private final ReplySvc rSvc;
	private final CommentSvc cSvc;
	private final MemberSvc mSvc;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String create(
			Model model,
			@PathVariable("id") Integer id,
			@Valid ReplyForm replyForm,
			BindingResult res,
			Principal principal
			) {
		Comment c = this.cSvc.getComment(id);
		Member m = this.mSvc.getMember(principal.getName());
		if(res.hasErrors()) {
			model.addAttribute("post", c.getPost());
			return "post_detail";
		}
		Reply r = this.rSvc.create(replyForm.getContent(), m, c);
		return String.format("redirect:/post/detail/%s#reply_%s", c.getPost().getId(), r.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modify(
			@PathVariable("id") Integer id,
			ReplyForm replyForm,
			Principal principal
			) {
		Reply r = this.rSvc.get(id);
		if(!r.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		replyForm.setContent(r.getContent());
		return "reply_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(
			@PathVariable("id") Integer id,
			@Valid ReplyForm replyForm,
			BindingResult res,
			Principal principal
			) {
		if(res.hasErrors()) {
			return "reply_form";
		}
		Reply r = this.rSvc.get(id);
		if(!r.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		this.rSvc.modify(r, replyForm.getContent());
		return String.format("redirect:/post/detail/%s#reply_%s", r.getPost().getId(), r.getId());	
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(
			@PathVariable("id") Integer id,
			Principal principal
			) {
		Reply r = this.rSvc.get(id);
		if(!r.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		this.rSvc.delete(r);
		return String.format("redirect:/post/detail/%s#comment_%s", r.getPost().getId(), r.getComment().getId());	
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/like/{id}")
	public String voteToggle(
			@PathVariable("id") Integer id,
			Principal principal
			) {
		Reply r = this.rSvc.get(id);
		Member m = this.mSvc.getMember(principal.getName());
		this.rSvc.voteToggle(r, m);
		return String.format("redirect:/post/detail/%s#reply_%s", r.getPost().getId(), r.getId());
	}
}
