package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Book;
import com.example.demo.repository.BookRepositoryJdbc;

@SpringBootTest
public class BookJPATest {

	@Autowired
	private BookRepositoryJdbc bookRepositoryJdbc;
	
	@Test void testBookAdd() {
		Book book=new Book(11,"C#","Wb","trt",28.0);
		int rowcount=bookRepositoryJdbc.save(book);
	}
}
