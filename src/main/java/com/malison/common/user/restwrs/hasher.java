package com.malison.common.user.restwrs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hasher {
	
	static String generatedPassword = null;
	
	public static String hashPassword(String password, byte[] salt){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt);
			byte[] bytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<bytes.length;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		}catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
	return generatedPassword;
	}

}
