package com.example.demo.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.RentList;


@Repository
public interface RentListRepository extends JpaRepository<RentList, Integer> {

	List<RentList> findByUserUserId(Integer userId);
	List<RentList> findByUserUserIdAndRentStatus(Integer userId, String rentStatus);
	RentList findByRentStatus(String rentStatus);
	
	
}
