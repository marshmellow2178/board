package com.marshmellow.myboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeCtrl {

	@GetMapping("/")
	public String index() {
		return "redirect:/boards";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}

}
