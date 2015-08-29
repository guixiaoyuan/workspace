package com.deeal.model.mycenter;

import java.io.Serializable;

import com.deeal.view.RoundImageView;


public class MyCenterInfo implements Serializable{
	private RoundImageView img_user_head;
	private String user_name;
	private String friends_number;
	private String fans_number;
	private String imgUrl;
	private IConcernStruct concernStruct;
	
	public MyCenterInfo(RoundImageView img_user_head, String user_name,
			String friends_number, String fans_number, String imgUrl,
			IConcernStruct concernStruct) {
		super();
		this.img_user_head = img_user_head;
		this.user_name = user_name;
		this.friends_number = friends_number;
		this.fans_number = fans_number;
		this.imgUrl = imgUrl;
		this.concernStruct = concernStruct;
	}

	public RoundImageView getImg_user_head() {
		return img_user_head;
	}

	public void setImg_user_head(RoundImageView img_user_head) {
		this.img_user_head = img_user_head;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getFriends_number() {
		return friends_number;
	}

	public void setFriends_number(String friends_number) {
		this.friends_number = friends_number;
	}

	public String getFans_number() {
		return fans_number;
	}

	public void setFans_number(String fans_number) {
		this.fans_number = fans_number;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public IConcernStruct getConcernStruct() {
		return concernStruct;
	}

	public void setConcernStruct(IConcernStruct concernStruct) {
		this.concernStruct = concernStruct;
	}
	
	

}
