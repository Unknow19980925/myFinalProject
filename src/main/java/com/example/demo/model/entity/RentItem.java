	package com.example.demo.model.entity;
	
	import java.util.ArrayList;
import java.util.List;
	
	import jakarta.persistence.CascadeType;
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	@Table(name = "rentitems")
	public class RentItem {
		
		@Id
		@Column(name = "rentitem_id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer rentItemId;
		
		@OneToOne(mappedBy = "rentItem")
	    private RentList rentList;
		
		@OneToMany(mappedBy = "rentItem",cascade = CascadeType.ALL)
		private List<Book> books;
	}
