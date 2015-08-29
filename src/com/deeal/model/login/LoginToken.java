package com.deeal.model.login;

public class LoginToken {
	private String token;
	//服务器用此命名方式，所以与服务器对齐， 否则jackson报错，
	private String user_id;

	public LoginToken() {
		// TODO Auto-generated constructor stub
		this.token = "";
		this.user_id = "";
	}	

	public LoginToken(String token, String userId) {
		// TODO Auto-generated constructor stub
		this.token = token;
		this.user_id = userId;
	}	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.token + "#" + this.user_id;
	}
}
