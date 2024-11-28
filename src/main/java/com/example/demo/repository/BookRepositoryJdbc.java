package com.example.demo.repository;

import java.util.List;
import java.util.Optional;


import com.example.demo.model.entity.Book;

public interface BookRepositoryJdbc  {

	List<Book> findAll();
	Optional<Book> findById(Integer bookId);
	int save(Book book); 
	int update(Book book);
	int deleteById(Integer bookId);
}
