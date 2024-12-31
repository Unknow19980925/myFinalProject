package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Book;
import com.example.demo.repository.BookRepository;

@SpringBootTest
public class DeleteBookTest {

	@Autowired
	BookRepository bookRepository;
	
	@Test
	void delete() {
		 Book book=bookRepository.findById(1).get();
		 
		 bookRepository.delete(book);
	}
}
