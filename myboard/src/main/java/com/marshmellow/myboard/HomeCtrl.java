package com.marshmellow.myboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeCtrl {

	@GetMapping("/")
	public String index() {
		return "redirect:/post/list";
	}
}
