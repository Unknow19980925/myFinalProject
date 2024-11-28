package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.mapper.BookMapper;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.entity.Book;

@SpringBootTest
class SpringbootSsrBookApplicationTests {

	@Autowired
	private BookMapper bookMapper;
	@Test
	void testBookMapper() {
		Book book=new Book(203,"Java","Amy","Lom",30.0);
		System.out.println("原始 Book: "+book);
		
		BookDto bookDto=bookMapper.toDto(book);
		System.out.println("測試toDto: "+bookDto);
		
		book=bookMapper.toEntity(bookDto);
		System.out.println("測試toEntity: "+book);
	}

}
