package com.example.samuraieatout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	@GetMapping("/error")
	public String error(RedirectAttributes redirectAttributes) {
		return "redirect:/login";
	}
}
