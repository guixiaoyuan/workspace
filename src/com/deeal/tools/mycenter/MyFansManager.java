package com.deeal.tools.mycenter;

import com.deeal.model.mycenter.MyFansInfo;
import com.deeal.view.RoundImageView;
import com.example.deeal.R;

import android.content.Context;
import android.widget.ImageView;

public class MyFansManager {
	private MyFansInfo myFansInfo;
	private RoundImageView img_fans_user_head;
	private String fansid;
	private String fans_personalwords;
	private String imgUrl;
	
	public MyFansManager(RoundImageView img,String url) {
		super();
		this.img_fans_user_head=img;
		this.imgUrl=url;
	}
	
	public MyFansInfo setMyFansInfo(){
		fansid="222";
		fans_personalwords="gogogo";
		myFansInfo=new MyFansInfo(img_fans_user_head, fansid, fans_personalwords);
		return myFansInfo;
	}
	
}
