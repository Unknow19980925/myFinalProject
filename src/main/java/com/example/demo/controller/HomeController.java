package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	public String home(Model model) {
		model.addAttribute("WelcomeMessage","歡迎來到書籍租書系統");
		return "index";
	}
}
