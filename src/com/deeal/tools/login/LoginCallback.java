package com.deeal.tools.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.deeal.activity.MainActivity;
import com.deeal.model.login.LoginResult;
import com.deeal.model.login.LoginToken;
import com.deeal.model.login.LoginUserInfo;
import com.deeal.model.login.UserInfo;
import com.deeal.tools.TokenParser;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class LoginCallback extends RequestCallBack {
	private Activity activity;
	private LoginUserInfo user;
	private Intent lastIntent;
	private String TOKEN = "token";
	private String PWD_ERROR = "√‹¬Î¥ÌŒÛ£°";
	
	public LoginCallback(Activity activity,LoginUserInfo user, Intent intent) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.user =user;
		this.lastIntent = intent;
	}
	
	@Override
	public void onFailure(HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(ResponseInfo serverResult) {
		// TODO Auto-generated method stub
		
//		if(serverResult.result.equals("true")){
//			try {
//				doLoginSuccess();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		else{
//			doLoginFailure();
//		}
		if(null == serverResult || null == serverResult.result || "" == serverResult.result){
			doLoginFailure();
		}else{
			doLoginSuccess(serverResult.result.toString());
		}
	}

	private void doLoginSuccess(String token) {
		System.out.println(token);
		TokenParser parser = new TokenParser(token);
		LoginResult result = parser.getLoginResult();
		if (null == result){
			Toast.makeText(this.activity, PWD_ERROR, 0).show();
			return;
		}
		LoginToken lToken = result.getToken();
		
		lastIntent.putExtra(TOKEN, lToken.getToken());
		this.activity.setResult(Activity.RESULT_OK, lastIntent);
		this.activity.finish();
	}
	
	private void doLoginFailure() {
		Toast.makeText(this.activity, "Login err", Toast.LENGTH_SHORT).show();
	}
}
