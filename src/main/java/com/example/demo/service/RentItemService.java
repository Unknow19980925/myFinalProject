package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.BookDto;
import com.example.demo.model.dto.RentItemDto;


public interface RentItemService {

	public void addBookToRentItem(List<BookDto> bookDtos,RentItemDto rentItemDto);
	public RentItemDto createRentItem(BookDto bookDto) ;
	public void rentBookStatusToRented(BookDto bookDto); 
	public void rentBookStatusToAvailable(BookDto bookDto);
	public void updateBookStatus(BookDto bookDto, Boolean status);
}
