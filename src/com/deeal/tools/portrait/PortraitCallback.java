package com.deeal.tools.portrait;

import java.util.ArrayList;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.deeal.model.login.UserInfo;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class PortraitCallback extends RequestCallBack{
	private ImageView imgPortrait;
	private Context context;
	private ArrayList<Button> btnList;
	
	public PortraitCallback(ImageView img, Context context, ArrayList<Button> btnList) {
		// TODO Auto-generated constructor stub
		this.imgPortrait = img;
		this.context = context;
		this.btnList = btnList;
	}
	
	@Override
	public void onFailure(HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(ResponseInfo serverResult) {
		// TODO Auto-generated method stub
		if(null == serverResult || null == serverResult.result || "" == serverResult.result){
			doLoginFailure();
		}else{
			doLoginSuccess(serverResult.result.toString());
		}
	}

	private void doLoginSuccess(String usrInfo) {
		// TODO Auto-generated method stub
		//获取UserInfo，该用户的基本信息
		UserInfoParser parser = new UserInfoParser(usrInfo);
		UserInfo userInfo = parser.getUserInfo();
		
		//设置头像
		BitmapUtils bitmapUtils = new BitmapUtils(this.context);
		bitmapUtils.display(this.imgPortrait, userInfo.getImg());
		
		//将slidingMenu里的注册和登录按钮设置为隐藏
		for(Button btn : this.btnList){
			btn.setVisibility(Button.INVISIBLE);
		}
	}

	private void doLoginFailure() {
		// TODO Auto-generated method stub
		System.out.println("Get Portrait ERROR");
	}

}
