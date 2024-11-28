package com.example.demo.model.dto;

public class UserCert {

	private Integer userId;
	private String username;
	private String role;
	
	public UserCert(Integer userId,String username,String role) {
		this.userId=userId;
		this.username=username;
		this.role=role;
	} 
	
	public Integer getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
	return "UserCert[userId="+userId+",username= "+username+", role="+role+"]";	
	}
	
		
	
}
