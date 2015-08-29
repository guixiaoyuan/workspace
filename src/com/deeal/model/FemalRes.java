package com.deeal.model;

import com.example.deeal.R;

public class FemalRes {
	private static int[] cloth_font = new int[] { R.drawable.femal_font0,
			R.drawable.femal_font1, R.drawable.femal_font2,
			R.drawable.femal_font3 };
	private static int[] cloth_back = new int[] { R.drawable.femal_bak0,
			R.drawable.femal_bak1, R.drawable.femal_bak2, R.drawable.femal_bak3 };
	private static int[] sleeve = new int[] { R.drawable.show_nothing,R.drawable.femal_sleeve0,
			R.drawable.femal_sleeve1, R.drawable.femal_sleeve2,
			R.drawable.femal_sleeve3 };
	private static int[] sleeve_right = new int[]{R.drawable.show_nothing,R.drawable.female_sleeve_right0,R.drawable.female_sleeve_right1,R.drawable.female_sleeve_right2,R.drawable.female_sleeve_right3};
	private static int[] collar = new int[] { R.drawable.show_nothing,R.drawable.femal_collar4,
			R.drawable.femal_collar5, R.drawable.femal_collar2,
			R.drawable.femal_collar3, R.drawable.femal_collar0,
			R.drawable.femal_collar1, R.drawable.femal_collar6,
			R.drawable.femal_collar7 };
	private static int[] def; 
	public static int[] getCloth_font() {
		return cloth_font;
	}

	public static int[] getCloth_back() {
		return cloth_back;
	}

	public static int[] getSleeve() {
		return sleeve;
	}

	public static int[] getCollar() {
		return collar;
	}
	public static int[] getSleeveRight(){
		return sleeve_right;
	}
	public static int[] getFemalDefult(){
		def= new int[]{getCloth_font()[0],getCloth_back()[0],getSleeve()[0],getCollar()[0],getSleeveRight()[0]};
		return def;
	}
	
	
}
