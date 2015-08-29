package com.deeal.model.login;

public class LoginResult {
	private String status;
	private LoginToken token;

	public LoginResult() {
		// TODO Auto-generated constructor stub
		this.status = "";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LoginToken getToken() {
		return token;
	}

	public void setToken(LoginToken token) {
		this.token = token;
	}
}
