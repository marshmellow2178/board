package com.marshmellow.myboard.Post;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.marshmellow.myboard.Category.Category;
import com.marshmellow.myboard.Category.CategorySvc;
import com.marshmellow.myboard.Comment.Comment;
import com.marshmellow.myboard.Comment.CommentForm;
import com.marshmellow.myboard.Comment.CommentSvc;
import com.marshmellow.myboard.Reply.Reply;
import com.marshmellow.myboard.Reply.ReplyForm;
import com.marshmellow.myboard.member.Member;
import com.marshmellow.myboard.member.MemberSvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostCtrl {
	
	private final PostSvc postSvc;
	private final MemberSvc mSvc;
	private final CommentSvc cSvc;
	private final CategorySvc ctgrSvc;
	
	@GetMapping("/list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value= "keyword", defaultValue = "") String keyword,
			@RequestParam(value= "ctgr", defaultValue = "0") int ctgr,
			Model model){
		Page<Post> postList = postSvc.getList(page-1, "%"+keyword+"%", this.ctgrSvc.get(ctgr));		
		List<Category> ctgrList = this.ctgrSvc.getList();
		model.addAttribute("ctgrList", ctgrList);
		model.addAttribute("postList", postList);
		
		return "post_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(
			Model model,
			Principal principal,
			ReplyForm replyForm,
			CommentForm commentForm,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@PathVariable("id") Integer id){
		Post post = this.postSvc.getPost(id);
		if(principal!=null) {
			Member m = this.mSvc.getMember(principal.getName());
			if(post.getVoter().contains(m)) {
				post.setRecommend(true);
			}
			
			for(Comment c: post.getCommentList()) {
				if(c.getVoter().contains(m)) {
					c.setRecommend(true);
				}
			}
			
			for(Reply r:post.getReplyList()) {
				if(r.getVoter().contains(m)) {
					r.setRecommend(true);
				}
			}
		}
		Page<Comment> cList = this.cSvc.getList(page-1, post);
		model.addAttribute("post", post);
		model.addAttribute("cList", cList);
		return "post_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String postCreate(PostForm postForm, Model model) {
		List<Category> ctgrList = this.ctgrSvc.getList();
		model.addAttribute("ctgrList", ctgrList);
		return "post_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String postCreate(
			@Valid PostForm postForm,
			BindingResult res,
			Principal principal
			) {
		if(res.hasErrors()) {
			return "post_form";
		}
		Member m = this.mSvc.getMember(principal.getName());
		Category ctgr = this.ctgrSvc.get(postForm.getCategory()).get();
		this.postSvc.createPost(postForm.getTitle(), postForm.getContent(), ctgr, m);
		return "redirect:/post/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String postModify(
			PostForm postForm,
			@PathVariable("id") Integer id,
			Principal principal,
			Model model
			) {
		Post p = this.postSvc.getPost(id);
		if(!p.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		System.out.println(postForm.getCategory());
		try {
			postForm.setCategory(p.getCategory().getId());
		}catch(Exception e){
			//
		}
		postForm.setContent(p.getContent());
		postForm.setTitle(p.getTitle());
		List<Category> ctgrList = this.ctgrSvc.getList();
		model.addAttribute("ctgrList", ctgrList);
		return "post_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String postModify(
			@Valid PostForm postForm,
			BindingResult res,
			Principal principal,
			@PathVariable("id") Integer id
			) {
		if(res.hasErrors()) {
			return "post_form";
		}
		Post p = this.postSvc.getPost(id);
		if(!p.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		Optional<Category> ctgr = this.ctgrSvc.get(postForm.getCategory());
		this.postSvc.modify(p, postForm.getTitle(), postForm.getContent(), ctgr.get());
		return String.format("redirect:/post/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String postDelete(
			Principal principal,
			@PathVariable("id") Integer id
			){
		Post p = this.postSvc.getPost(id);
		if(!p.getMember().getUid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다");
		}
		this.postSvc.delete(p);
		return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/like/{id}")
	public String postVote(
			Principal principal,
			@PathVariable("id") Integer id) {
		Post p = this.postSvc.getPost(id);
		Member m = this.mSvc.getMember(principal.getName());
		this.postSvc.voteToggle(p, m);
		return String.format("redirect:/post/detail/%s", id);
	}
}
