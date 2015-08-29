package com.deeal.model.customed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TagInfo implements Parcelable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point pt;
	private String info;
	private static int offsetX;
	private static int offsetY;
	private int tagID;

	private int imgID;
	private String action;

	public TagInfo(Point pt, String info) {
		this.pt = pt;
		this.info = info;
		offsetX = 0;
		offsetY = 0;
		tagID = -1;
		imgID = -1;
		action = "add";
	}

	public TagInfo() {
		offsetX = 0;
		offsetY = 0;
		pt = new Point(0, 0);
		info = "";
		tagID = -1;
		imgID = -1;
		action = "add";
	}

	public Point getPt() {
		return pt;
	}

	public void setPt(Point pt) {
		this.pt = pt;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(pt, flags);
		dest.writeString(info);

	}

	@Override
	public String toString() {
		int x = this.pt.x - offsetX;
		int y = this.pt.y - offsetY;
		
		return toJsonObject().toString();
		
//		return x + "#" + y + "#" + this.info + this.tagID + "#" + this.imgID
//				+ "#" + this.action;
	}
	
	public JSONObject toJsonObject(){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("x", pt.x);
			jsonObject.put("y", pt.y);
			jsonObject.put("info", info);
			jsonObject.put("tagID", tagID);
			jsonObject.put("imgID", imgID);
			jsonObject.put("action", action);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject;
	}

	public static TagInfo createFromString(String str) {
		TagInfo tinfo = new TagInfo();

//		String tem[] = str.split("#");
//		if (tem.length < 2) {
//			return null;
//		}
//		if (tem.length > 2) {
//			tinfo.setInfo(tem[2]);
//		}
//		tinfo.setPt(new Point(Integer.valueOf(tem[0]), Integer.valueOf(tem[1])));
//		tinfo.setTagID(Integer.valueOf(tem[3]));
//		tinfo.setImgID(Integer.valueOf(tem[4]));
//		tinfo.setAction(tem[5]);
		
		try {
			JSONObject obj = new JSONObject(str);
			int x = obj.getInt("x");
			int y = obj.getInt("y");
			String info = obj.getString("info");
			int tagID = obj.getInt("tagID");
			int imgID = obj.getInt("imgID");
			String action = obj.getString("action");
			
			tinfo.setPt(new Point(x, y));
			tinfo.setInfo(info);
			tinfo.setTagID(tagID);
			tinfo.setImgID(imgID);
			tinfo.setAction(action);
			
			return tinfo;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tinfo;
	}

	public static final Parcelable.Creator<TagInfo> CREATOR = new Parcelable.Creator<TagInfo>() {
		public TagInfo createFromParcel(Parcel in) {
			return new TagInfo(in);
		}

		public TagInfo[] newArray(int size) {
			return new TagInfo[size];
		}
	};

	private TagInfo(Parcel in) {
		info = in.readString();
		pt = in.readParcelable(Point.class.getClassLoader());
	}

	public static int getOffsetX() {
		return offsetX;
	}

	public static void setOffsetX(int X) {
		offsetX = X;
	}

	public static int getOffsetY() {
		return offsetY;
	}

	public static void setOffsetY(int Y) {
		offsetY = Y;
	}

	public int getTagID() {
		return tagID;
	}

	public void setTagID(int tagID) {
		this.tagID = tagID;
	}

	public int getImgID() {
		return imgID;
	}

	public void setImgID(int imgID) {
		this.imgID = imgID;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
