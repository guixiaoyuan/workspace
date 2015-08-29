package com.deeal.model.customed;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Picture {
	private int picID;
	private int designID;
	private String imgPath;
	private ArrayList<TagInfo> listTag;

	public Picture(){
		this(-1, -1, "", new ArrayList<TagInfo>());
	}
	
	public Picture(int picID, int designID, String imgPath,
			ArrayList<TagInfo> listTag) {
		super();
		this.picID = picID;
		this.designID = designID;
		this.imgPath = imgPath;
		this.listTag = listTag;
	}
	
	public int getPicID() {
		return picID;
	}
	public void setPicID(int picID) {
		this.picID = picID;
	}
	public int getDesignID() {
		return designID;
	}
	public void setDesignID(int designID) {
		this.designID = designID;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public ArrayList<TagInfo> getListTag() {
		return listTag;
	}
	public void setListTag(ArrayList<TagInfo> listTag) {
		this.listTag = listTag;
	}
	
	@Override
	public String toString() {
		return toJsonObject().toString();
	}
	
	public JSONObject toJsonObject(){
		JSONObject jobject = new JSONObject();
		
		try {
			jobject.put("picID", picID);
			jobject.put("designID", designID);
			jobject.put("imgPath", imgPath);
			JSONArray jArray = new JSONArray();
			jobject.put("tags", jArray);
			
			for(TagInfo info : listTag){
				jArray.put(info.toJsonObject());
			}
			
			return jobject;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Picture createFromString(String str){
		Picture pic = new Picture();
		String []tmp = str.split("|");
		
		return pic;
	}
}
