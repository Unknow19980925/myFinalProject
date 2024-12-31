package com.example.demo.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.PasswordInvaliidException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.Hash;
import com.example.demo.model.dto.LoginDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Optional<UserDto> findByUsername(String username) {
		Optional<User>optUser=userRepository.findByUsername(username);
		if(optUser.isEmpty())Optional.empty();
		UserDto userDto=modelMapper.map(optUser.get(),UserDto.class);
		return Optional.of(userDto);
		}

	@Override
	public Optional<UserDto> saveUser(UserDto userDto) {
		
		User user=modelMapper.map(userDto, User.class);
		userRepository.save(user);
		return Optional.of(modelMapper.map(user, UserDto.class));
	}

	@Override
	public Optional<UserDto> login(LoginDto loginDto) {
		Optional<User>optUser=userRepository.findByUsername(loginDto.getUsername());
		if(optUser.isPresent()&&optUser.get().getPasswordHash().equals(loginDto.getPassword())) {
			return Optional.of(modelMapper.map(optUser.get(),UserDto.class));
		}
		return Optional.empty();
	}

	@Override
	public void appendUser(UserDto userDto) {
		User user =modelMapper.map(userDto, User.class);
		String salt=Hash.getSalt();
		String passwordHash=Hash.getHash(userDto.getPassword(),salt);
		user.setActive(false);
		user.setSalt(salt);
		user.setPasswordHash(passwordHash);
		userRepository.save(user);
	}

	@Override
	public void updatePassword(Integer userId, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordInvaliidException {
		User user=userRepository.findById(userId)
				.orElseThrow(()->new UserNotFoundException("查無使用者"));
		if(user==null) {
			throw new UserNotFoundException("查無使用者");
		}
		String oldPasswordHash=Hash.getHash(oldPassword,user.getSalt());
		
		if(!oldPasswordHash.equals(user.getPasswordHash())) {
			throw new PasswordInvaliidException("原密碼輸入錯誤");
		}
		String newPasswordHash=Hash.getHash(newPassword,user.getSalt());
		user.setPasswordHash(newPasswordHash);
		userRepository.save(user);
	
	}

	@Override
	public void deleteUser(Integer userId)throws UserNotFoundException {
		Optional<User>optUser=userRepository.findById(userId);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("刪除失敗: "+userId+"不存在");
		}
		userRepository.deleteById(userId);
	}
}
