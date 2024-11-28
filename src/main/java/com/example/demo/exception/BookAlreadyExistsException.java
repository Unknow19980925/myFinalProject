package com.example.demo.exception;

public class BookAlreadyExistsException extends BookException {

	public BookAlreadyExistsException(String message) {
	
		super(message);
	}
}
