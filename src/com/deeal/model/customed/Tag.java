package com.deeal.model.customed;

public class Tag {
	private int tagID;
	private int imgID;
	private int offsetX;
	private int offsetY;
	private String description;
	private String action;

	public Tag(int tagID, int imgID, int offsetX, int offsetY,
			String description, String action) {
		this.tagID = tagID;
		this.imgID = imgID;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.description = description;
		this.action = action;
	}

	public Tag() {
		// TODO Auto-generated constructor stub
		this(-1, -1, -1, -1, "", "");
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

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAction() {
		return action;
	}
  
	public void setAction(String action) {
		this.action = action;
	}
}
