package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.exception.PasswordInvaliidException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.UserCert;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@GetMapping("/delete{userId}")
	public String deleteUser(@PathVariable Integer userId) throws UserNotFoundException {
		userService.deleteUser(userId);	
		return "redirect:/delete";
	}
	
		@GetMapping("/register")
		public String showRegistrationForm(Model model) {
			model.addAttribute("userDto",new UserDto());
			return "register";
		}
		
		@PostMapping("/add")
		public String addUser(@ModelAttribute UserDto userDto) {//logincon
			userService.appendUser(userDto);
			return"redirect:/login";
		}
	
	@GetMapping("/update_password")
	public String updatePassword(Model model,@ModelAttribute UserDto userDto) {
		model.addAttribute("userDto",userDto);
		return"update_password";
	}
	@PostMapping("/update")
	public String updateUser(@RequestParam String oldPassword,
			@RequestParam String newPassword,HttpSession session,
			@ModelAttribute UserDto userDto,Model model) {
		UserCert userCert=(UserCert)session.getAttribute("userCert");
	
		if(userCert==null) {
			model.addAttribute("error","無法驗證使用者,請重新登入");
			return "update_password";
		}
		try {
		userService.updatePassword(userCert.getUserId(), oldPassword, newPassword);
		model.addAttribute("Success","密碼更新成功");
		}catch (UserNotFoundException|PasswordInvaliidException e) {
			model.addAttribute("error",e.getMessage());
		}
		return "redirect:/login";
		
	}
	
	@ExceptionHandler({UserNotFoundException.class})
	public String handleUserException(UserNotFoundException e,Model model) {
		model.addAttribute("message",e.getMessage());
		return"error";
	}
}
