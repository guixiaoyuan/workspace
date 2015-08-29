package com.deeal.tools.portrait;

import java.util.ArrayList;

import com.deeal.tools.MethodSet;
import com.deeal.tools.URL;
import com.deeal.tools.login.LoginCallback;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRouter;
import android.media.MediaRouter.Callback;
import android.media.MediaRouter.RouteGroup;
import android.media.MediaRouter.RouteInfo;
import android.widget.Button;
import android.widget.ImageView;

public class Portrait{
	private String portraitURL;
	private Context context;
	private ImageView imgPortrait;
	private String token;
	private String userId;
	private ArrayList<Button> btnList;
	
	public Portrait(String url, Context context, ImageView img, String token, ArrayList<Button> btnList) {
		// TODO Auto-generated constructor stub
		this.portraitURL = url;
		this.context = context;
		this.imgPortrait = img;
		this.token = token;
		this.btnList = btnList;
	}
	
	public String getPortraitURL() {
		return portraitURL;
	}
	
	public void setPortraitURL(String portraitURL) {
		this.portraitURL = portraitURL;
	}
	
	private String tokenToUserID(String token){
		String userid = "";
		
		return userid;
	}
	
	private void displayPortrait(){
		RequestParams params = new RequestParams();
		params.addBodyParameter("method", MethodSet.GETUSERINFO);
		params.addBodyParameter("arg0", this.tokenToUserID(this.token));

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, 
				URL.URL_LOGIN,
				params, new PortraitCallback(this.imgPortrait, this.context, this.btnList));
	}
}
