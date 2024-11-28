package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.BookDto;


public interface BookService {

	public List<BookDto>getAllBooks();
	public BookDto getBookById(Integer bookId);
	public void addBook(BookDto bookDto);
	public void addBook(Integer bookId, String bookName, String author,String publisher,Double price); // 新增房間
	public void updateBook(Integer bookId, BookDto bookDto);
	public void updateBook(Integer bookId, String bookName, String author,String publisher,Double price ); // 修改房間
	public void deleteBook(Integer bookId); 
}
