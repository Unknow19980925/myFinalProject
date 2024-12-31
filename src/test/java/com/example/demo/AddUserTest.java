package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;

@SpringBootTest
public class AddUserTest {

	@Autowired
	UserRepository userRepository;
	
	@Test
	void add() {
		User user1 = new User();
		user1.setUsername("admin");
		user1.setPasswordHash("1234");
		
		userRepository.save(user1);
		
		User user2 = new User();
		user2.setUsername("mary");
		user2.setPasswordHash("1234");
		userRepository.save(user2);
		
	}
}
