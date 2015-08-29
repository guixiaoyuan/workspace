package com.deeal.model;

import com.thinkland.sdk.sms.SMSCaptcha;

public class RegisterInfo {
	private String name;
	private String pwd;
	private String repwd;
	private String tel;
	private String permission_token;
	public String getPermission_token() {
		return permission_token;
	}
	public void setPermission_token(String permission_token) {
		this.permission_token = permission_token;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	private SMSCaptcha captcha;
	
	
	
	public RegisterInfo(String name, String pwd, String repwd, 
			String tel,String permission_token) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.repwd = repwd;
		this.tel = tel;
		this.permission_token = permission_token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRepwd() {
		return repwd;
	}
	public void setRepwd(String repwd) {
		this.repwd = repwd;
	}
	
	public SMSCaptcha getCaptcha() {
		return captcha;
	}
	public void setCaptcha(SMSCaptcha captcha) {
		this.captcha = captcha;
	}
	
}
