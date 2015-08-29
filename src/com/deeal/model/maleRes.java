package com.deeal.model;

import com.example.deeal.R;

public class maleRes {
	private static int[] shirt_cloth_font = new int[]{R.drawable.shirt_font,R.drawable.shirt_font_0};
	public static int[] getShirt_cloth_font() {
		return shirt_cloth_font;
	}

	public static void setShirt_cloth_font(int[] shirt_cloth_font) {
		maleRes.shirt_cloth_font = shirt_cloth_font;
	}

	public static int[] getShirt_cloth_back() {
		return shirt_cloth_back;
	}

	public static void setShirt_cloth_back(int[] shirt_cloth_back) {
		maleRes.shirt_cloth_back = shirt_cloth_back;
	}

	private static int[] shirt_cloth_back = new int[]{R.drawable.shirt_back,R.drawable.shirt_back_0};
	private static int[] shirt_left_sleeve = new int[] {
			 R.drawable.shirt_left_sleeve_0,
			R.drawable.shirt_left_sleeve_1, R.drawable.shirt_left_sleeve_2 };
	private static int[] shirt_left_sleeveback = new int[] {
			 R.drawable.shirt_left_sleeveback_0,
			R.drawable.shirt_left_sleeveback_1,
			R.drawable.shirt_left_sleeveback_2 };
	private static int[] shirt_right_sleeve = new int[] {
			 R.drawable.shirt_right_sleeve_0,
			R.drawable.shirt_right_sleeve_1, R.drawable.shirt_right_sleeve_2 };
	private static int[] shirt_right_sleeveback = new int[] {
			 R.drawable.shirt_right_sleeveback_0,
			R.drawable.shirt_right_sleeveback_1,
			R.drawable.shirt_right_sleeveback_2 };
	private static int[] shirt_pocket = new int[] { 
			R.drawable.shirt_pocket_0, R.drawable.shirt_pocket_1,
			R.drawable.shirt_pocket_2, R.drawable.shirt_pocket_3,
			R.drawable.shirt_pocket_4 };

	public static int[] getShirt_left_sleeve() {
		return shirt_left_sleeve;
	}

	public static int[] getShirt_left_sleeveback() {
		return shirt_left_sleeveback;
	}

	public static int[] getShirt_right_sleeve() {
		return shirt_right_sleeve;
	}

	public static int[] getShirt_right_sleeveback() {
		return shirt_right_sleeveback;
	}

	public static int[] getShirt_pocket() {
		return shirt_pocket;
	}

	private static int[] def;

	public static int[] getDefult() {
		def = new int[] { getShirt_left_sleeve()[0],
				getShirt_left_sleeveback()[0], getShirt_pocket()[0],
				getShirt_right_sleeve()[0], getShirt_right_sleeveback()[0] };
		return def;
	}
}
