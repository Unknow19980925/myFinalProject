package com.example.demo.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.dto.RentListDto;
import com.example.demo.model.entity.RentList;
import com.example.demo.repository.RentListRepositoryJdbc;
@Repository
@PropertySource("classpath:sql.properties")
public class RentListRepositoryJdbcImpl implements RentListRepositoryJdbc {

	
	private static final Logger logger=LoggerFactory.getLogger(RentListRepositoryJdbcImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${rentlist.sql.findAll}")
	private String findAllSql;
	
	@Value("${rentlist.sql.addList}")
	private String addList;

	@Override
	public List<RentList> findAllRentList(Integer userId, String orderStatus) {
		return jdbcTemplate.query(findAllSql,new BeanPropertyRowMapper<>(RentList.class));
	}

	@Override
		public void batchAddRentList(List<RentList> rentLists) {
			
		}
	
		@Override
		public void batchUpdateRentListsStatus(List<Integer> rentIds, String rentliststatus) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void saveAll(List<RentListDto> rentLists) {
			// TODO Auto-generated method stub
			
		}
	
	


}
