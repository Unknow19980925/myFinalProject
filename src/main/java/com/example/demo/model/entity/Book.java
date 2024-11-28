package com.example.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

	@Id
	@Column(name = "book_id")
	private Integer bookId;
	
	@Column(name = "book_name",nullable = false)
	private String bookName;
	
	@Column(name = "author",nullable = false)
	private String author;
	
	@Column(name = "publisher",nullable = false)
	private String publisher;
	
	@Column(name = "book_price")
	private Double bookPrice;
	
	
}
