package com.deeal.view;

import com.deeal.model.maleRes;
import com.example.deeal.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

@SuppressLint("DrawAllocation")
public class MaleCustomView extends View{
	public Bitmap[] bms;
	public maleRes res;
	public int[] resource;
	public Bitmap bmfont,bmback;
	public MaleCustomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		resource = res.getDefult();
		bms = new Bitmap[resource.length];
		for (int i = 0; i < resource.length; i++) {
			Bitmap bm = BitmapFactory.decodeResource(getResources(),
					resource[i]);
			bms[i] = bm;
		}
		bmfont = BitmapFactory.decodeResource(getResources(), R.drawable.shirt_font);
		bmback = BitmapFactory.decodeResource(getResources(), R.drawable.shirt_back);
	}	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int width = this.getWidth();
		int height = this.getHeight();
		bms[0] = Bitmap.createScaledBitmap(bms[0], width/8, height/5, false);//左袖子
		bms[1] = Bitmap.createScaledBitmap(bms[1], width/8, height/5, false);//左后袖子
		bms[2] = Bitmap.createScaledBitmap(bms[2], width/9, height/9, false);//口袋
		bms[3] = Bitmap.createScaledBitmap(bms[3],  width/8, height/5, false);//右袖子
		bms[4] = Bitmap.createScaledBitmap(bms[4],  width/8, height/5, false);//右后袖子
		bmback = Bitmap.createScaledBitmap(bmback, width/2, 3*height/5, false);
		bmfont = Bitmap.createScaledBitmap(bmfont, width/2, 3*height/5, false);
		canvas.drawBitmap(bmfont,0,0,null);
		canvas.drawBitmap(bmback, width/2, 0, null);
		canvas.drawBitmap(bms[0], 0, height/30, null);
		canvas.drawBitmap(bms[1], width/2, height/30, null);
		canvas.drawBitmap(bms[2], width*11/40, height/8, null);
		canvas.drawBitmap(bms[3], width*2/5, height/30, null);
		canvas.drawBitmap(bms[4], width*2/5+width/2, height/30, null);
		super.onDraw(canvas);
	}
	
}
