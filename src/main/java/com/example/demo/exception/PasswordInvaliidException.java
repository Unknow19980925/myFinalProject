package com.example.demo.exception;

public class PasswordInvaliidException extends CertException{

	public PasswordInvaliidException() {
		super("密碼無效");
	}
	public PasswordInvaliidException(String message) {
		super(message);
	}
	
}
