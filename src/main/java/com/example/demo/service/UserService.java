package com.example.demo.service;

import java.util.Optional;

import com.example.demo.exception.PasswordInvaliidException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.LoginDto;
import com.example.demo.model.dto.UserDto;

public interface UserService {
	
	public void deleteUser(Integer userId)throws UserNotFoundException;
	
	Optional<UserDto>findByUsername(String username);
	
	public Optional<UserDto>login(LoginDto loginDto);
	
	Optional<UserDto>saveUser(UserDto userDto);
	
	public void updatePassword(Integer userId,String oldPassword,String newPassword)throws UserNotFoundException, PasswordInvaliidException;
	
	public void appendUser(UserDto userDto);
}
