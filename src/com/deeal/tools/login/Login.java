package com.deeal.tools.login;

import android.app.Activity;
import android.content.Intent;

import com.deeal.model.login.LoginUserInfo;
import com.deeal.tools.MethodSet;
import com.deeal.tools.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;

public class Login{
	private LoginUserInfo user;
	private Activity activity;
	private Intent lastIntent;
	
	public Login(LoginUserInfo user, Activity activity, Intent intent) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.activity = activity;
		this.lastIntent = intent;
	}
	
	
	@SuppressWarnings("unchecked")
	public void doLogin() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.addBodyParameter("method", MethodSet.LOGIN_TAKEN);
		//params.addBodyParameter("arg0", "yedelu");
		params.addBodyParameter("arg0", user.getUsername());
		params.addBodyParameter("arg1", user.getPassword());
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, 
				URL.URL_LOGIN,
				params, new LoginCallback(this.activity,this.user, this.lastIntent));
	}
}
