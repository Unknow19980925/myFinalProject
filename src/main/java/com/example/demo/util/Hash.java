package com.example.demo.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Hash {

	public static String getHash(String password,String salt) {
		try {
			MessageDigest md=MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes());
			byte[]bytes=md.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(bytes);
		}catch(Exception e) {
			e.printStackTrace();
		}
			return null;
		}
	
	public static String getSalt() {
		SecureRandom secureRandom=new SecureRandom();
		byte[]salt=new byte[16];
		secureRandom.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	public static String getHash(String password) {
		try {
			MessageDigest md=MessageDigest.getInstance("SHA-256");
			byte[]bytes=md.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(bytes);
	}catch(Exception e) {
		e.printStackTrace();
	}
		return null;
	}
}