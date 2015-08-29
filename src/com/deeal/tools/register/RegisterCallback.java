package com.deeal.tools.register;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.deeal.model.RegisterInfo;
import com.deeal.model.login.LoginToken;
import com.deeal.model.login.UserInfo;
import com.deeal.tools.TokenParser;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class RegisterCallback extends RequestCallBack{

	private Activity activity;
	private RegisterInfo registerInfo;
	private Intent lastIntent;
	private String TOKEN = "";
	private String data = "";

	public RegisterCallback(Activity activity, RegisterInfo registerInfo,
			Intent lastIntent) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.registerInfo = registerInfo;
		this.lastIntent = lastIntent;
	}

	@Override
	public void onFailure(HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void onSuccess(ResponseInfo serverResult) {
		// TODO Auto-generated method stub
		if (null == serverResult || null == serverResult.result
				|| "" == serverResult.result) {
			doLoginFailure();
		}else{
			doLoginSuccess(serverResult.result.toString());
		}
	}

	private void doLoginSuccess(String token) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo = new JSONObject(token);
			data = jo.getString("data");

			if ("true" == data) {
				Toast.makeText(activity, "注册成功", 0).show();
				lastIntent.putExtra(TOKEN, data);
				this.activity.setResult(Activity.RESULT_OK, lastIntent);
				this.activity.finish();
			}else{
				Toast.makeText(activity, "该号码已经被注册", 0).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void doLoginFailure() {
		// TODO Auto-generated method stub
		Toast.makeText(this.activity, "Login err", Toast.LENGTH_SHORT).show();
	}

}
