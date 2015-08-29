package com.deeal.model.customed;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Design {
	private int userID;
	private String alias;
	private String classify;
	private ArrayList<Picture> listPicture;

	public Design(){
		this(-1, "", "normal", new ArrayList<Picture>());
	}
	
	public Design(int userID, String alias, String classify,
			ArrayList<Picture> listPicture) {
		super();
		this.userID = userID;
		this.alias = alias;
		this.classify = classify;
		this.listPicture = listPicture;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public ArrayList<Picture> getListPicture() {
		return listPicture;
	}

	public void setListPicture(ArrayList<Picture> listPicture) {
		this.listPicture = listPicture;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJsonObject().toString();
	}
	
	public JSONObject toJsonObject(){
		JSONObject jobject = new JSONObject();
		
		try {
			jobject.put("userID", userID);
			jobject.put("alias", alias);
			jobject.put("classify", classify);
			
			JSONArray jArray = new JSONArray();
			
			jobject.put("pics", jArray);
			
			for(Picture pic : listPicture){
				jArray.put(pic.toJsonObject());
			}
			
			return jobject;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
}
