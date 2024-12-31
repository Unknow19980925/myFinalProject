package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.dto.RentListDto;
import com.example.demo.model.entity.RentList;

public interface RentListRepositoryJdbc {

	public List<RentList>findAllRentList(Integer userId,String orderStatus);
	
	public void batchAddRentList(List<RentList>rentLists);
	
	public void batchUpdateRentListsStatus(List<Integer>rentIds,String rentliststatus);

	public void saveAll(List<RentListDto> rentLists);

	
}
