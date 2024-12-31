package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.RentListDto;
import com.example.demo.model.entity.RentList;

@Component
public class RentListMapper {

	@Autowired 
	private ModelMapper modelMapper;
	
	public RentListDto toDto(RentList rentList) {
		return modelMapper.map(rentList, RentListDto.class);
	}
	public RentList toEntity(RentListDto rentListDto) {
		return modelMapper.map(rentListDto, RentList.class);
	}
}
