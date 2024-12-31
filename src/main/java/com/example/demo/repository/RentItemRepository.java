package com.example.demo.repository;

import java.awt.print.Book;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.RentItem;


@Repository
public interface RentItemRepository extends JpaRepository<RentItem, Integer> {
	List<Book> getAllBooksByrentItemId(Integer rentItemId);
}
