package com.deeal.model.login;

public class LoginUserInfo {
	private String username;
	private String password;
	
	public LoginUserInfo(String name, String pwd) {
		// TODO Auto-generated constructor stub
		this.username = name;
		this.password = pwd;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
