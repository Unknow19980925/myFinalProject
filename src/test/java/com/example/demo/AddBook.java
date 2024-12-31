package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.BookStatus;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookStatusRepository;
@SpringBootTest
public class AddBook {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	BookStatusRepository bookStatusRepository;
	@Test
	void addBook() {
		BookStatus bookStatus=new BookStatus();
		bookStatus.setStatusName(true);
		
		Book book1=new Book();
		book1.setBookName("C#");
		book1.setAuthor("ASDA");
		book1.setPublisher("AES");
		book1.setBookPrice(53.0);
		book1.setBookStatus(bookStatus);
		bookRepository.save(book1);
		
		
	}
}
