package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.UserCert;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CertService;
import com.example.demo.util.Hash;

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
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping
	public String loginPage() {
		return "login";
	} 
	@PostMapping
	public String checkLogin(@RequestParam String username,@RequestParam String password, HttpSession session,HttpServletRequest req, Model model) {
		//UserCert usercert=null;
		User user=userRepository.findByUsername(username).orElse(null);
		if(user==null||!user.getPasswordHash().equals(Hash.getHash(password,user.getSalt()))) {
			model.addAttribute("message","使用者名稱或密碼錯誤");
			return"error";
		}
		UserCert usercert=new UserCert(user.getUserId(), user.getUsername(), user.getRole());
		
		
		try {
			usercert=certService.getceCert(username, password);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error";
		}
		
		session.setAttribute("userCert", usercert);
		session.setAttribute("locale", req.getLocale());
		return "index";//登入後返回首頁
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return"index";
	}
	
}
	

