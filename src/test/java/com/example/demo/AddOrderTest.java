package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.BookRepository;
import com.example.demo.repository.RentListRepository;
import com.example.demo.repository.UserRepository;

public class AddOrderTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	RentListRepository rentListRepository;
	
	@Test
	void add() {
	
	}
}
