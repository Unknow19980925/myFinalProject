package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.dto.RentItemDto;
import com.example.demo.model.dto.RentListDto;
import com.example.demo.model.entity.User;


public interface RentListService {

	public RentListDto rentBook(RentItemDto rentitems,Integer userId) throws UserNotFoundException;
	
	public RentListDto returnBook(Integer rentId);
	
	public RentListDto checkoutRentList(RentListDto rentListDto);
	
	public List<RentListDto> getAllRentLists(Integer userId,String rentStatus);	
	
	List<RentListDto>findListByUser(User users);
			
	public void batchUpdateRentListStatus(List<Integer> rentIds,String rentStatus);
	
	public RentListDto saveRentList(Integer userId,List<RentItemDto>rentitems);
	
	public void updateBookToRentList(RentItemDto rentItemDto, RentListDto rentListDto,Boolean statusName) ;
	
	public List<RentListDto> updateRentListStatus(Integer userId,String oldrentStatus,String newrentStatus);
	
	public RentListDto addBookToRent(RentItemDto rentItemDto, Integer userId);
	
	
	
	public RentListDto getPendingRentListByStatus(String rentStatus);
}
