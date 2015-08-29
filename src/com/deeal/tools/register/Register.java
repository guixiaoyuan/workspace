package com.deeal.tools.register;

import android.app.Activity;
import android.content.Intent;

import com.deeal.activity.RegisterActivity;
import com.deeal.model.RegisterInfo;
import com.deeal.tools.MethodSet;
import com.deeal.tools.URL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;

public class Register {
	
	private RegisterInfo registerInfo;
	private Activity activity;
	private Intent lastIntent;
	
	public Register(RegisterInfo registerInfo, Activity activity, Intent intent) {
		// TODO Auto-generated constructor stub
		this.registerInfo = registerInfo;
		this.activity = activity;
		this.lastIntent = intent;
	}

	@SuppressWarnings("unchecked")
	public void doRegister() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.addBodyParameter("method", MethodSet.REGISTER);
		params.addBodyParameter("arg0", this.registerInfo.getName());
		params.addBodyParameter("arg1", this.registerInfo.getPwd());
		params.addBodyParameter("arg2", this.registerInfo.getRepwd());
		params.addBodyParameter("arg3", this.registerInfo.getTel());
		params.addBodyParameter("arg4", this.registerInfo.getPermission_token());
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, 
				URL.URL_LOGIN,
				params, new RegisterCallback(this.activity,this.registerInfo, this.lastIntent));
	}
}
