package com.appleframework.qos.collector.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xusm(2012-11-22)
 *
 */
public class EncryptUtils {
	
	private final static String MD5 = "MD5";
	private final static String SHA1 = "SHA1";
	
	private final static String CHARSET = "UTF-8";
	
	public static String md5(String value){			
		return byte2hex(digest(value, MD5));
	}
	
	public static String sha1(String value){			
		return byte2hex(digest(value, SHA1));
	}
	
	public static String byte2hex(byte[] bytes){
		if(bytes == null || bytes.length == 0){
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {			
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if(hex.length() == 1){
				sb.append("0");
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	private static byte[] digest(String value, String algorithm){
		if(value == null || "".equals(value.trim())){
			return null;
		}		
		
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			bytes = md.digest(value.getBytes(CHARSET));
		} catch (NoSuchAlgorithmException e) {
			//NOP
		} catch (UnsupportedEncodingException e) {
			//NOP
		}
		
		return bytes;
	}
}
