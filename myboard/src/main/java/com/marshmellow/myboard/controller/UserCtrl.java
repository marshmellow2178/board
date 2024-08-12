package com.marshmellow.myboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marshmellow.myboard.entity.UserInfo;
import com.marshmellow.myboard.repo.UserRepo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserCtrl {
	private final UserRepo userRepo;
	
	@PostMapping("/login")
	public String login(
			@RequestParam(name = "id") String id,
			@RequestParam(name = "pw") String pw,
			HttpSession session
			) {
		UserInfo user = userRepo.findByIdAndPw(id, pw);
		if(user!=null) {
			session.setAttribute("userInfo", user);
		}
		return "redirect:/";
	}
	
	@GetMapping("/info")
	public String get(
			HttpSession session
			) {
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		if(user==null) {
			return "redirect:/login_form";
		}
		return "mypage_info";
	}
	
}
