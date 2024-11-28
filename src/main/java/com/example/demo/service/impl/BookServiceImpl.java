package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BookAlreadyExistsException;
import com.example.demo.exception.BookException;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.entity.Book;
import com.example.demo.repository.BookRepositoryJdbc;
import com.example.demo.service.BookService;
@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepositoryJdbc bookRepositoryJdbc;
	
	@Autowired
	private BookMapper bookMapper;

	@Override
	public List<BookDto> getAllBooks() {
		return bookRepositoryJdbc.findAll()
				.stream()
				.map(bookMapper::toDto)
				.collect(toList());
	}

	@Override
	public BookDto getBookById(Integer bookId) {
		Book book=bookRepositoryJdbc.findById(bookId)
				.orElseThrow(()->new BookNotFoundException("找不到書籍: bookId: "+bookId));
		return bookMapper.toDto(book);
	}

	@Override
	public void addBook(BookDto bookDto) {
		Optional<Book>optBook=bookRepositoryJdbc.findById(bookDto.getBookId());
		if(optBook.isPresent()) {
			throw new BookAlreadyExistsException("新增失敗: "+bookDto.getBookId()+"已存在");
		}
		Book book=bookMapper.toEntity(bookDto);
		int rowcount=bookRepositoryJdbc.save(book);
		if(rowcount==0) {
			throw new BookException("無法新增");
		}
	}

	@Override
	public void addBook(Integer bookId, String bookName, String author, String publisher, Double price) {
		BookDto bookDto=new BookDto(bookId,bookName,author,publisher,price);
		addBook(bookDto);
	}

	@Override
	public void updateBook(Integer bookId, BookDto bookDto) {
		Optional<Book>optBook=bookRepositoryJdbc.findById(bookId);
		if(optBook.isEmpty()) {
			throw new BookNotFoundException("修改失敗: "+bookId+"不存在");
		}
		bookDto.setBookId(bookId);
		Book book=bookMapper.toEntity(bookDto);
		int rowcount=bookRepositoryJdbc.update(book);
		if(rowcount==0) {
			throw new BookException("無任何紀錄被修改");
		}
	}

	@Override
	public void updateBook(Integer bookId, String bookName, String author, String publisher, Double price) {
		BookDto bookDto=new BookDto(bookId,bookName,author,publisher,price);
		updateBook(bookId,bookDto);
		
	}

	@Override
	public void deleteBook(Integer bookId) {
		Optional<Book>optBook=bookRepositoryJdbc.findById(bookId);
		if(optBook.isEmpty()) {
			throw new BookNotFoundException("刪除失敗: "+bookId+"不存在");
		}
		
		int rowcount=bookRepositoryJdbc.deleteById(bookId);
		if(rowcount==0) {
			throw new BookException("無任何紀錄被修改");
		}
	}
		
	}
