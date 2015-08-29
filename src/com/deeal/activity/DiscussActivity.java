package com.deeal.activity;
import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class DiscussActivity extends Activity implements OnClickListener {
	
	private ImageView iv_photo;
	/** 选择文件 */
	public static final int TO_SELECT_PHOTO = 1;
	/** 图片路径 */
	private String picPath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);
		iv_photo  = (ImageView) findViewById(R.id.iv_photo);
		iv_photo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		intent=new Intent(this, SelectPhotoActivity.class) ;
		startActivityForResult(intent, TO_SELECT_PHOTO);
		intent=null;
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
			picPath = data.getStringExtra(SelectPhotoActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapFactory.decodeFile(picPath);
			Bitmap zoomBitmap = zoomBitmap(bm, 300, 300); // 将图片设置到ImageView
			iv_photo.setImageDrawable(new BitmapDrawable(bm));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private BitmapDrawable BitmapDrawable(Bitmap bm) {
		// TODO Auto-generated method stub
		return null;
	}




	/**
	 * 将原图按照指定的宽高进行缩放
	 * 
	 * @param oldBitmap
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	private Bitmap zoomBitmap(Bitmap oldBitmap, int newWidth, int newHeight) {
		int width = oldBitmap.getWidth();
		int height = oldBitmap.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
		Canvas canvas = new Canvas(newBitmap);
		canvas.drawBitmap(newBitmap, matrix, null);
		return newBitmap;
	}

	

}
