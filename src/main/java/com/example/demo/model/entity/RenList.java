package com.example.demo.model.entity;

import lombok.Data;

@Data
public class RenList {

	private Integer rentlistId;
	private Integer userId;
	private String rentListDate;
	private Integer bookId;
	private Integer quantity;
	private Double unitPrice;
	private Integer subtotal;
	private String rentList;
	
	private String bookName;
}
