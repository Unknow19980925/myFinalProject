package com.example.demo.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status_id")
	private BookStatus bookStatus;
	
	@ManyToOne
	@JoinColumn(name = "rentitem_id")
	private RentItem rentItem;
}
