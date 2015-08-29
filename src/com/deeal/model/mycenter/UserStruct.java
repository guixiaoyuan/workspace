package com.deeal.model.mycenter;

public class UserStruct {
	//用户ID
	private int id;
	//头像相对路径
	private String img;
	//昵称(即用户名)
	private String name;
	//个人简介
	private String info;
	
	
	
	public UserStruct(int id, String img, String name, String info) {
		super();
		this.id = id;
		this.img = img;
		this.name = name;
		this.info = info;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
