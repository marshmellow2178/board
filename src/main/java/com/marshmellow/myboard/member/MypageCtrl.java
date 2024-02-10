package com.marshmellow.myboard.member;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marshmellow.myboard.Comment.Comment;
import com.marshmellow.myboard.Comment.CommentSvc;
import com.marshmellow.myboard.Post.Post;
import com.marshmellow.myboard.Post.PostSvc;
import com.marshmellow.myboard.Reply.Reply;
import com.marshmellow.myboard.Reply.ReplySvc;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageCtrl {
	private final MemberSvc mSvc;
	private final PostSvc pSvc;
	private final CommentSvc cSvc;
	private final ReplySvc rSvc;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/")
	public String mypage(
			Principal principal,
			Model model
			) {
		Member m = this.mSvc.getMember(principal.getName());
		model.addAttribute("member", m);
		return "mypage";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/post")
	public String mypost(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Principal principal,
			Model model) {
		Page<Post> postList = this.pSvc.getList(page-1, principal.getName());
		model.addAttribute("postList", postList);
		return "mypost";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/comment")
	public String mycomment(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Principal principal,
			Model model
			) {
		Page<Comment> cList = this.cSvc.getList(page-1, principal.getName());
		model.addAttribute("cList", cList);
		return "mycomment";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reply")
	public String myreply(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Principal principal,
			Model model
			) {
		Page<Reply> rList = this.rSvc.getList(page-1, principal.getName());
		model.addAttribute("rList", rList);
		return "myreply";
	}
}
