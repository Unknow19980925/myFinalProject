package com.example.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_status")
public class BookStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name =  "status_id")
	private Integer statusId;
	
	@Column(name = "status_name",nullable = false)
	private Boolean statusName;
}