package com.example.demo.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentListDto {

	private Integer rentId;
	
	private Integer userId;
	
	private Integer bookId;
	
	private String rentDate;
	
	private String returnDate;
	
	private String dueDate;
	
	private Double unitPrice;
	
	private Double subtotal;
	
	private String rentStatus;
	
	private Double latefee;
	
	private String bookName;
	
	private RentItemDto rentItemDto;
	
}
