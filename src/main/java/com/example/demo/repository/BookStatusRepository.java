package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.BookStatus;
@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus, Integer>{

	
}
