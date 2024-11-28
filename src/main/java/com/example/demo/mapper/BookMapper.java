package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.BookDto;
import com.example.demo.model.entity.Book;

@Component
public class BookMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public BookDto toDto(Book book) {
		return modelMapper.map(book, BookDto.class);
	}
	public Book toEntity(BookDto bookDto) {
		return modelMapper.map(bookDto, Book.class);
	}
}
