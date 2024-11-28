package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.CertException;
import com.example.demo.exception.PasswordInvaliidException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.UserCert;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepositoryJdbc;
import com.example.demo.service.CertService;
import com.example.demo.util.Hash;
@Service
public class CertServiceImpl implements CertService {
	@Autowired
	private UserRepositoryJdbc userRepositoryJdbc;
	@Override
	public UserCert getceCert(String username, String password) throws CertException {
		User user=userRepositoryJdbc.getUser(username);
		if(user==null) {
			throw new UserNotFoundException();
		} 
		String passwordHash=Hash.getHash(password,user.getSalt());
		if(!passwordHash.equals(user.getPasswordHash())) {
			throw new PasswordInvaliidException();
		}
		UserCert userCert=new UserCert(user.getUserId(), user.getUsername(), user.getRole());
		return userCert;
	}
	
	
}
