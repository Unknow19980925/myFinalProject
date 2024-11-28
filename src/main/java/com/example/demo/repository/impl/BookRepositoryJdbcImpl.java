package com.example.demo.repository.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Book;
import com.example.demo.repository.BookRepositoryJdbc;


@Repository
@PropertySource("classpath:sql.properties")
public class BookRepositoryJdbcImpl implements BookRepositoryJdbc {

	private static final Logger logger=LoggerFactory.getLogger(BookRepositoryJdbcImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${book.sql.findAll}")
	private String findAllSql;
	
	@Value("${book.sql.findById}")
	private String findByIdSql;
	
	@Value("${book.sql.save}")
	private String saveSql;
	@Value("${book.sql.update}")
	private String updateSql;
	@Value("${book.sql.deleteById}")
	private String deleteByIdSql;
	
	@Override
	public List<Book> findAll() {
		return jdbcTemplate.query(findAllSql, new BeanPropertyRowMapper<>(Book.class));
	}

	@Override
	public Optional<Book> findById(Integer bookId) {
		try {
			Book book=jdbcTemplate.queryForObject(findByIdSql, new BeanPropertyRowMapper<>(Book.class),bookId);
			return Optional.of(book);
		} catch (Exception e) {
			logger.info(e.toString());		
			}
		return Optional.empty();
	}
	

	@Override
	public int save(Book book) {
		return jdbcTemplate.update(saveSql,book.getBookId(),book.getBookName(),book.getAuthor(),book.getPublisher(),book.getBookPrice());
	}

	@Override
	public int update(Book book) {
		return jdbcTemplate.update(updateSql,book.getBookName(),book.getAuthor(),book.getPublisher(),book.getBookPrice(),book.getBookId());
	}

	@Override
	public int deleteById(Integer bookId) {
		return jdbcTemplate.update(deleteByIdSql,bookId);
	}

	
}
