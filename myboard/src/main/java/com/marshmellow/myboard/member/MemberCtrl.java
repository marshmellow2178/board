package com.marshmellow.myboard.member;

import java.security.Principal;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberCtrl {
	private final MemberSvc mSvc;
	
	@GetMapping("/signup")
	public String signup(SignUpForm signUpForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(
			SignUpForm signUpForm,
			BindingResult res
			) {
		if(res.hasErrors()) {
			return "signup_form";
		}
		if(!signUpForm.getPw().equals(signUpForm.getPw2())) {
			res.rejectValue("pw2", "pwChkFail", "비밀번호 확인 불일치");
			return "signup_form";
		}
		try {
			this.mSvc.create(signUpForm.getUid(), signUpForm.getPw(), signUpForm.getEmail());
		}catch(DataIntegrityViolationException e) {
			res.reject("signupFail", "사용중인 아이디입니다");
			return "signup_form";
		}catch(Exception e) {
			res.reject("signupFail", e.getMessage());
			return "signup_form";
		}
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
	@GetMapping("/pwfind")
	public String pwfind(
			PwFindForm pwFindForm
			) {
		return "pwfind_form";
	}
	
	@PostMapping("/pwfind")
	public String pwfind(
			@Valid PwFindForm pwFindForm,
			BindingResult res,
			Model model
			) {
		if(res.hasErrors()) {
			return "pwfind_form";
		}
		Member m = new Member();
		try {
			m = this.mSvc.getMember(pwFindForm.getUid());
		}catch(Exception e) {
			res.reject("no data", "사용자가 없습니다");
			return "pwfind_form";
		}
		if(!m.getEmail().equals(pwFindForm.getEmail())) {
			res.reject("no data", "이메일이 다릅니다");
			return "pwfind_form";
		}
		String newPw = this.mSvc.findPw(m);
		model.addAttribute("newPw", newPw);
		return "pwfind_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/pwchange")
	public String pwchange(
			PwChangeForm pwChangeForm
			) {
		return "pwchange_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/pwchange")
	public String pwchange(
			@Valid PwChangeForm pwChangeForm,
			BindingResult res,
			Principal principal
			) {
		if(res.hasErrors()) {
			return "pwchange_form";
		}
		if(!pwChangeForm.getNewPw1().equals(pwChangeForm.getNewPw2())) {
			res.reject("newPw1", "새 비밀번호 불일치");
			return "pwchange_form";
		}
		Member m = this.mSvc.getMember(principal.getName());
		try {
			this.mSvc.changePw(m, pwChangeForm.getOldPw(), pwChangeForm.getNewPw1());
		}catch(Exception e) {
			res.reject("oldPw", "비밀번호가 틀립니다");
			return "pwchange_form";
		}
		return "redirect:/member/logout";
	}
}
