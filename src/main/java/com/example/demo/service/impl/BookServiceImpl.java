package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.model.dto.BookDto;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.BookStatus;
import com.example.demo.repository.BookRepository;

import com.example.demo.service.BookService;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BookMapper bookMapper;

	@Override
	public List<BookDto> getAllBooks() {
		return bookRepository.findAll()
				.stream()
				.map(book->modelMapper.map(book, BookDto.class))
				.collect(toList());
	}

	@Override
	public Optional<BookDto> getBookById(Integer bookId) {
		Optional<Book>optBook=bookRepository.findById(bookId);
		if(optBook.isEmpty()) {
			return Optional.empty();
		}
		BookDto bookDto = modelMapper.map(optBook.get(), BookDto.class);
	    return Optional.of(bookDto);
	}

	@Override
	public BookDto updateBook(BookDto bookDto) {
		Optional<Book>optBook=bookRepository.findById(bookDto.getBookId());
		if(optBook.isEmpty()) {
			throw new BookNotFoundException("修改失敗: "+bookDto.getBookId()+"不存在");
		}
		 BookStatus bookStatus = new BookStatus();
		    bookStatus.setStatusName(bookDto.getStatusName());
		    Book book=bookMapper.toEntity(bookDto);
			book.setBookStatus(bookStatus);
		    bookRepository.save(book);
			return bookMapper.toDto(book);
	}

	
	@Override
	public void deleteBook(Integer bookId) {
		Optional<Book>optBook=bookRepository.findById(bookId);
		if(optBook.isEmpty()) {
			throw new BookNotFoundException("刪除失敗: "+bookId+"不存在");
		}
		bookRepository.deleteById(bookId);
	}

	@Override
	public BookDto saveBook(BookDto bookDto) {
		Book book=modelMapper.map(bookDto, Book.class);
		BookStatus bookStatus=new BookStatus();
		bookStatus.setStatusName(bookDto.getStatusName());
		book.setBookStatus(bookStatus);
		bookRepository.save(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	public BookDto addBook(BookDto bookDto) {
	    BookStatus bookStatus = new BookStatus();
	    bookStatus.setStatusName(bookDto.getStatusName());
	    Book book=bookMapper.toEntity(bookDto);
		book.setBookStatus(bookStatus);
	    bookRepository.save(book);
		return bookMapper.toDto(book);
	}

	@Override
	public void addBook(Integer bookId, String bookName, String author, String publisher, Double price,
			Boolean statusName) {
		BookDto bookDto=new BookDto(bookId,bookName,author,publisher,price,statusName);
		addBook(bookDto);
		
	}

	@Override
	public void updateBook(Integer bookId, String bookName, String author, String publisher, Double price,
			Boolean statusName) {
		BookDto bookDto=new BookDto(bookId,bookName,author,publisher,price,statusName);	
		updateBook(bookDto);
	}
	
	

	

	@Override
	public List<BookDto> getAllAvaliableBooks(Boolean statusName) {
		List<Book>books=bookRepository.findByBookStatusStatusName(statusName);
		return books.stream().map(book->new BookDto(book.getBookId(),book.getBookName(),
				book.getAuthor(),book.getPublisher(),book.getBookPrice(),
				book.getBookStatus().getStatusName())).collect(Collectors.toList());
	}
	@Override
	public List<BookDto> getBooksByRentListId(Integer rentId) {
		List<Book>books=bookRepository.findByRentItem_RentList_RentId(rentId);	
		return books.stream().map(book->modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
	}
	}
