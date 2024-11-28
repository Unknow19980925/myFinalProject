package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Book;
import com.example.demo.repository.BookRepositoryJdbc;

@SpringBootTest
public class BookJdbcTests {

	@Autowired BookRepositoryJdbc bookRepositoryJdbc;
	
	@Test
	void testBookAdd() {
		 Book book=new Book(11,"C#","Wed","Td",28.0);
		 int rowcount=bookRepositoryJdbc.save(book);
		 System.out.println("測試新增: " + book + " 結果回傳: " + rowcount + " (1 表示正確新增一筆)");
	}
	@Test
	void testFindAllBooks() {
		System.out.println("測試查詢全部: "+bookRepositoryJdbc.findAll());
	}
}
