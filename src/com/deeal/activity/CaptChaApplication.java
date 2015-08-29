package com.deeal.activity;

import com.thinkland.sdk.util.CommonFun;

import android.app.Application;

public class CaptChaApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CommonFun.initialize(getApplicationContext(),false);  
	}
}
