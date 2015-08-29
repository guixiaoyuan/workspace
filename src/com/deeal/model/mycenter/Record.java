package com.deeal.model.mycenter;

import java.sql.Timestamp;

public class Record {
	//日期
	private Timestamp datetime;
	//目标ID
	private int target_id;
	
	private Type type;

	public Record(Timestamp datetime, int target_id, Type type) {
		super();
		this.datetime = datetime;
		this.target_id = target_id;
		this.type = type;
	}

	public Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public int getTarget_id() {
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
}
