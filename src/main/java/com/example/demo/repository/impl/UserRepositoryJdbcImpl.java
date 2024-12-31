package com.example.demo.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepositoryJdbc;

@Service
public class UserRepositoryJdbcImpl implements UserRepositoryJdbc {

	private static final Logger Logger=LoggerFactory.getLogger(UserRepositoryJdbc.class);
	
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		@Value("${user.sql.getUser}")
		private String getUserSql;

		@Override
		public User getUser(String username) {
			try {
				return jdbcTemplate.queryForObject(getUserSql, new BeanPropertyRowMapper<>(User.class),username);
			} catch (Exception e) {
				Logger.info(e.toString());
			}
			return null;
		}
		
		
	
}
