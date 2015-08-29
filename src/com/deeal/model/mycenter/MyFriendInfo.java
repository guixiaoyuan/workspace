package com.deeal.model.mycenter;

import java.io.Serializable;

import com.deeal.view.RoundImageView;

import android.widget.ImageView;

public class MyFriendInfo implements Serializable{
	private RoundImageView img_friend_user_head;
	private String friendid;
	private String friend_personalwords;
	
	public MyFriendInfo(RoundImageView img_friend_user_head, String friendid,
			String friend_personalwords) {
		super();
		this.img_friend_user_head = img_friend_user_head;
		this.friendid = friendid;
		this.friend_personalwords = friend_personalwords;
	}
	public RoundImageView getImg_friend_user_head() {
		return img_friend_user_head;
	}
	public void setImg_friend_user_head(RoundImageView img_friend_user_head) {
		this.img_friend_user_head = img_friend_user_head;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
	public String getFriend_personalwords() {
		return friend_personalwords;
	}
	public void setFriend_personalwords(String friend_personalwords) {
		this.friend_personalwords = friend_personalwords;
	}
		
	
}
