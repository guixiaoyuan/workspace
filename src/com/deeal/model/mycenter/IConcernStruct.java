package com.deeal.model.mycenter;

public class IConcernStruct {
	//关注了我
	private boolean is_concern_me;
	//最后一次记录
	private Record last_record;
	//主键
	private int social_id;
	//用户信息
	private UserStruct user;
	public IConcernStruct(boolean is_concern_me, Record last_record,
			int social_id, UserStruct user) {
		super();
		this.is_concern_me = is_concern_me;
		this.last_record = last_record;
		this.social_id = social_id;
		this.user = user;
	}
	public boolean isIs_concern_me() {
		return is_concern_me;
	}
	public void setIs_concern_me(boolean is_concern_me) {
		this.is_concern_me = is_concern_me;
	}
	public Record getLast_record() {
		return last_record;
	}
	public void setLast_record(Record last_record) {
		this.last_record = last_record;
	}
	public int getSocial_id() {
		return social_id;
	}
	public void setSocial_id(int social_id) {
		this.social_id = social_id;
	}
	public UserStruct getUser() {
		return user;
	}
	public void setUser(UserStruct user) {
		this.user = user;
	}
}
