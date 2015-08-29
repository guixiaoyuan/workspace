package com.deeal.view;

import com.deeal.model.FemalRes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

@SuppressLint("DrawAllocation")
public class CustomView extends View {
	public Bitmap[] bms;
	public FemalRes res;
	public int[] resource;
	public CustomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		resource = res.getFemalDefult();
		bms = new Bitmap[resource.length];
		for (int i = 0; i < resource.length; i++) {
			Bitmap bm = BitmapFactory.decodeResource(getResources(),
					resource[i]);
			bms[i] = bm;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int width = this.getWidth();
		int height = this.getHeight();
		bms[0]= Bitmap.createScaledBitmap(bms[0], width/2	, height, false);
		bms[1]= Bitmap.createScaledBitmap(bms[1], width/2, height, false);
		bms[2]= Bitmap.createScaledBitmap(bms[2], width/10	, height*23/100, false);
		bms[3]= Bitmap.createScaledBitmap(bms[3], width*9/50	, height/7, false);
		bms[4]= Bitmap.createScaledBitmap(bms[4], width/10	, height*23/100, false);
		canvas.drawBitmap(bms[0], 0, 0, null);
		canvas.drawBitmap(bms[1], width/2, 0, null);
		canvas.drawBitmap(bms[2], width/12, 0, null);
		canvas.drawBitmap(bms[3], width*12/70, 0, null);
		canvas.drawBitmap(bms[4], width*41/120, 0, null);
		super.onDraw(canvas);
	}
	
	
}
