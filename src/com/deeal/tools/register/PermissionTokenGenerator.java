package com.deeal.tools.register;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PermissionTokenGenerator {
	
	private String date;
	private String KEY = "d287a7a3f96991552e9ef74c79878d04";
	private String formatNormal = "yyyy-MM-dd";
	
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	public static String MD5(String text) {
		MessageDigest md5;
		
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return text;
		}
		md5.update(text.getBytes());
		byte[] bytes = md5.digest();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >> 4] + ""
					+ HEX_DIGITS[bytes[i] & 0xf]);
		}
		return sb.toString();
	}
	
	public static String Encrypt(String text){
		return MD5(text);
	}
	
	public PermissionTokenGenerator() {
		// TODO Auto-generated constructor stub
		this.updateDate();
	}
	
	private void updateDate(){
		SimpleDateFormat df = new SimpleDateFormat(formatNormal);
		date = df.format(new Date());
	}
	
	private String getYearAndMonth(String date){
		
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		
		return year + "-" + month;
	}
	private String getDay(String date){
		String day = date.substring(8, 10);
		
		return "-" + day;
	}
	
	private String getCombinedString(){
		return this.getYearAndMonth(this.date) + this.KEY + this.getDay(this.date);
	}
	
	public String getRegToken(){
		return PermissionTokenGenerator.MD5(this.getCombinedString());
	}
	


}
