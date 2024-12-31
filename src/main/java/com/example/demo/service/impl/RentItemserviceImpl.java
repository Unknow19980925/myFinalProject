package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.dto.RentItemDto;
import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.BookStatus;
import com.example.demo.model.entity.RentItem;
import com.example.demo.model.entity.RentList;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.RentItemRepository;
import com.example.demo.repository.RentListRepository;
import com.example.demo.service.RentItemService;
import com.example.demo.service.RentListService;
@Service
public class RentItemserviceImpl implements RentItemService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private RentItemRepository rentItemRepository;
	
	@Autowired
	private RentListRepository rentListRepository;
	
	@Autowired 
	private	ModelMapper modelMapper;
	
	@Override
	public void rentBookStatusToRented(BookDto bookDto) {
		 updateBookStatus(bookDto, true); 
	}

	@Override
	public void rentBookStatusToAvailable(BookDto bookDto) {
        updateBookStatus(bookDto, false); 

	}

	@Override
	public void updateBookStatus(BookDto bookDto, Boolean status) {
		Book book=modelMapper.map(bookDto, Book.class);   
		if (book == null) {
	            throw new IllegalArgumentException("書籍不能為空");
	        }
	        BookStatus bookStatus =book.getBookStatus();
	        if (bookStatus == null) {
	            bookStatus = new BookStatus();  
	            book.setBookStatus(bookStatus); 
	        }
	        bookStatus.setStatusName(status);  
	        bookRepository.save(book);  
	    }

	@Override
	public RentItemDto createRentItem(BookDto bookDto) {
		Book book=bookRepository.findById(bookDto.getBookId())
				.orElseThrow(()->new BookNotFoundException("找不到書籍"+bookDto.getBookId()));
		if(!book.getBookStatus().getStatusName()) {
			throw new IllegalArgumentException("書籍無法租借: "+book.getBookId());
		}
		RentList rentList = rentListRepository.findByRentStatus("pending");
		if (rentList == null) {
			RentItem newRentItem=new RentItem();
			newRentItem.setBooks(Collections.singletonList(book));
			rentItemRepository.save(newRentItem);
			book.setRentItem(newRentItem);
			book.getBookStatus().setStatusName(false);
			bookRepository.save(book);
			return modelMapper.map(newRentItem, RentItemDto.class);
		}
		
		RentItem PendingRentItem = rentList.getRentItem();
		PendingRentItem.getBooks().add(book);
		rentItemRepository.save(PendingRentItem);
		book.setRentItem(PendingRentItem);
		book.getBookStatus().setStatusName(false);
		bookRepository.save(book);
		return modelMapper.map(PendingRentItem, RentItemDto.class);
	}

	@Override
	public void addBookToRentItem(List<BookDto> bookDtos, RentItemDto rentItemDto) {
		RentItem rentItem=modelMapper.map(rentItemDto, RentItem.class);
		if(rentItem.getBooks()==null) {
			rentItem.setBooks(new ArrayList<>());
		}
		for(BookDto bookDto:bookDtos) {
			Book book=modelMapper.map(bookDto, Book.class);
			
			book.setRentItem(rentItem);
			rentItem.getBooks().add(book);
		}
		rentItemRepository.save(rentItem);
	}
}


