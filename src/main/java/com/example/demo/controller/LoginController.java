package com.example.demo.controller;

import java.security.cert.Certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.UserCert;
import com.example.demo.service.CertService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private CertService certService;
	
	@GetMapping
	public String loginPage() {
		return "login";
	} 
	@PostMapping
	public String checkLogin(@RequestParam String username,@RequestParam String password, HttpSession session,HttpServletRequest req, Model model) {
		UserCert usercert=null;
		
		try {
			usercert=certService.getceCert(username, password);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error";
		}
		
		session.setAttribute("userCert", usercert);
		session.setAttribute("locale", req.getLocale());
		return "resirect:/books";//登入後返回首頁
	}
	}
	

