package com.example.demo.model.dto;

import lombok.Data;

@Data
public class UserCert {

	private Integer userId;
	private String username;
	private String role;
	
	public UserCert(Integer userId,String username,String role) {
		this.userId=userId;
		this.username=username;
		this.role=role;
	} 
	
	public UserDto toUserDto() {
		UserDto userDto=new UserDto();
		userDto.setUserId(this.userId);
		userDto.setUsername(this.username);
		userDto.setRole(role);
		return userDto;
	}
	
	
	@Override
	public String toString() {
	return "UserCert[userId="+userId+",username= "+username+", role="+role+"]";	
	}
	
		
	
}
