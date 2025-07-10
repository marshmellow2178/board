package com.marshmellow.myboard.controller;

import com.marshmellow.myboard.dto.SignUpForm;
import com.marshmellow.myboard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCtrl {
	private final UserService userService;

	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("signUpForm", new SignUpForm());
		return "signup_form";
	}

	@PostMapping("/signup")
	public String processSignupForm(
			@Valid @ModelAttribute SignUpForm form,
			BindingResult bindingResult,
			RedirectAttributes attrs
	){
		if(bindingResult.hasErrors()){
			return "signup_form";
		}
		if(!form.getPassword().equals(form.getPassword2())){
			return "signup_form";
		}
		userService.create(form.getUsername(), form.getPassword(), form.getEmail());
		attrs.addFlashAttribute("message", "회원가입 완료, 로그인해 주세요");
		return "redirect:/login";
	}

}
