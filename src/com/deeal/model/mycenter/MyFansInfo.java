package com.deeal.model.mycenter;

import java.io.Serializable;

import com.deeal.view.RoundImageView;

import android.widget.ImageView;

public class MyFansInfo implements Serializable{
	private RoundImageView img_fans_user_head;
	private String fansid;
	private String fans_personalwords;
	
	
	
	public MyFansInfo(RoundImageView img_fans_user_head, String fansid,
			String fans_personalwords) {
		super();
		this.img_fans_user_head = img_fans_user_head;
		this.fansid = fansid;
		this.fans_personalwords = fans_personalwords;
	}
	public RoundImageView getImg_fans_user_head() {
		return img_fans_user_head;
	}
	public void setImg_fans_user_head(RoundImageView img_fans_user_head) {
		this.img_fans_user_head = img_fans_user_head;
	}
	public String getFansid() {
		return fansid;
	}
	public void setFansid(String fansid) {
		this.fansid = fansid;
	}
	public String getFans_personalwords() {
		return fans_personalwords;
	}
	public void setFans_personalwords(String fans_personalwords) {
		this.fans_personalwords = fans_personalwords;
	}
	
}
