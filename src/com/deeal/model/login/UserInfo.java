package com.deeal.model.login;


public class UserInfo {
	private int age;
	private Conste conste;
	private int id;
	private String img;
	private String job;
	private String pinfo;
	private Sex sex;
	private String user_name;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Conste getConste() {
		return conste;
	}
	public void setConste(Conste conste) {
		this.conste = conste;
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getPinfo() {
		return pinfo;
	}
	public void setPinfo(String pinfo) {
		this.pinfo = pinfo;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	private enum Conste {
		Aquarius,
		Aries,
		Cancer,
		capricorn, 
		Gemini,
		Leo,
		Libra, 
		Pisces,
		Sagittarius, 
		Scorpio,
		Taurus,
		Virgo;
	}
	private enum Sex{
		female,
		male;
	}
}
