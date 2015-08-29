package com.deeal.tools.mycenter;

import com.deeal.model.mycenter.MyFriendInfo;
import com.deeal.view.RoundImageView;
import com.example.deeal.R;

import android.content.Context;
import android.widget.ImageView;

public class MyFriendManager {
	private MyFriendInfo myFriendInfo;
	private RoundImageView img_friend_user_head;
	private String friendid;
	private String friend_personalwords;
	private String imgUrl;
	
	public MyFriendManager(RoundImageView img,String url) {
		super();
		this.img_friend_user_head=img;
		this.imgUrl=url;
		
	}

	
	//从本地加载
	public MyFriendInfo setMyFriendInfo(){
		friendid="111";
		friend_personalwords="ononon";
		myFriendInfo=new MyFriendInfo(img_friend_user_head, friendid, friend_personalwords);
		return myFriendInfo;
	}
}
