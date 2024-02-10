package com.marshmellow.myboard.Comment;

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

import com.marshmellow.myboard.Post.Post;
import com.marshmellow.myboard.Post.PostSvc;
import com.marshmellow.myboard.member.Member;
import com.marshmellow.myboard.member.MemberSvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentCtrl {
	private final PostSvc pSvc;
	private final CommentSvc cSvc;
	private final MemberSvc mSvc;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String create(
			Model model,
			@PathVariable("id") Integer id,
			@Valid CommentForm commentForm,
			BindingResult res,
			Principal principal
			) {
		Post p = this.pSvc.getPost(id);
		Member m = this.mSvc.getMember(principal.getName());
		if(res.hasErrors()) {
			model.addAttribute("post", p);
			return "post_detail";
		}
		Comment c = this.cSvc.create(p, commentForm.getContent(), m);
		return String.format("redirect:/post/detail/%s#comment_%s", id, c.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modify(
			@PathVariable("id") Integer id,
			CommentForm commentForm,
			Principal principal
			) {
		Comment c = this.cSvc.getComment(id);
		if(!c.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		commentForm.setContent(c.getContent());
		return "comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(
			@PathVariable("id") Integer id,
			@Valid CommentForm commentForm,
			BindingResult res,
			Principal principal
			) {
		if(res.hasErrors()) {
			return "comment_form";
		}
		Comment c = this.cSvc.getComment(id);
		if(!c.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		this.cSvc.modify(c, commentForm.getContent());
		return String.format("redirect:/post/detail/%s#comment_%s", c.getPost().getId(), c.getId());	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(
			@PathVariable("id") Integer id,
			Principal principal
			) {
		Comment c = this.cSvc.getComment(id);
		if(!c.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		this.cSvc.delete(c);
		return String.format("redirect:/post/detail/%s", c.getPost().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/like/{id}")
	public String voteToggle(
			@PathVariable("id") Integer id,
			Principal principal
			) {
		Comment c = this.cSvc.getComment(id);
		Member m = this.mSvc.getMember(principal.getName());
		this.cSvc.voteToggle(c, m);
		return String.format("redirect:/post/detail/%s#comment_%s", c.getPost().getId(), c.getId());	
	}
}
