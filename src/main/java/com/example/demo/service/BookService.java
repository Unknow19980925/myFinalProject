package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.BookDto;

public interface BookService {

	public List<BookDto> getBooksByRentListId(Integer rentId);
	public List<BookDto>getAllAvaliableBooks(Boolean statusName);
	public List<BookDto>getAllBooks();
	Optional<BookDto>getBookById(Integer bookId);
	public BookDto saveBook(BookDto bookDto);
	public BookDto addBook(BookDto bookDto);
	public void addBook(Integer bookId, String bookName, String author,String publisher,Double price,Boolean statusName); // 新增書籍
	public BookDto updateBook(BookDto bookDto);
	public void updateBook(Integer bookId, String bookName, String author,String publisher,Double price,Boolean statusName ); //修改書籍
	public void deleteBook(Integer bookId); 
}
