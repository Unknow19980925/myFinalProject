package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByBookStatusStatusName(Boolean satatusName);
	List<Book>findByRentItem_RentList_RentId(Integer rentId);
}
