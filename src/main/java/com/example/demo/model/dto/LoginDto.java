package com.example.demo.model.dto;

import lombok.Data;

@Data
public class LoginDto {

	private String username;
	private String password;
	private Boolean isLoggedin;
}
