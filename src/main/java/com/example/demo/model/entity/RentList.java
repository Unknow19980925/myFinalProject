package com.example.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rentlist")
public class RentList {

	@Id
	@Column(name = "rent_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer rentId;
	
	@Column(name = "rent_date")
	private String rentDate;
	
	@Column(name = "return_date")
	private String returnDate;
	
	@Column(name = "due_date")
	private String dueDate;
	
	@Column(name="unit_price")
	private Double unitPrice;
	
	@Column(name = "subtotal")
	private Double subtotal;
	
	@Column(name = "rent_status",columnDefinition ="enum('finish','cancel','pending')not null")
	private String rentStatus;
	
	@Column(name = "latefee")
	private Double latefee;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "rentitem_id")
	private RentItem rentItem; 
}
