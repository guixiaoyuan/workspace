package com.deeal.activity;

import com.example.deeal.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalorderActivity extends Activity implements OnClickListener {

	private TextView tvCustom;
	private Button bt_order;
	private ImageButton btn_return;
	private ImageView imgSample;
	private Uri photoUri;
	private String bmPath;
	
	/** 使用照相机拍照获取图片 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/** 使用相册中的图片 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	public static final int TO_SELECT_PHOTO = 1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personalorder);
		inititems();
	}

	private void inititems() {
		this.tvCustom = (TextView) findViewById(R.id.tv_customer_service);
		this.bt_order = (Button) findViewById(R.id.bt_sample_order);
		this.btn_return = (ImageButton) findViewById(R.id.btn_personalorder_return);
		this.imgSample = (ImageView) findViewById(R.id.img_personal_sample);
		tvCustom.setOnClickListener(this);
		bt_order.setOnClickListener(this);
		btn_return.setOnClickListener(this);
		imgSample.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_customer_service:
			startCustomerServiceMessage();
			break;
		case R.id.bt_sample_order:
			startDesideActivity();
			break;
		case R.id.btn_personalorder_return:
			finish();
			break;
		case R.id.img_personal_sample:
//			if (imgSample.getBackground().equals(
//					getResources().getDrawable(R.drawable.up_sample_picture))) {
//				Toast.makeText(this, "请上传您的样稿", 0).show();
//				imgSample.setBackground(getResources().getDrawable(
//						R.drawable.img_cloth));
//			} else {
//				startAddTagActivity();
//			}
			doClickImagePersonal();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
			String picPath = data.getStringExtra(SelectPhotoActivity.KEY_PHOTO_PATH);
			this.bmPath = picPath;
			Bitmap bm = BitmapFactory.decodeFile(picPath);
			/*int width = imgSample.getWidth();
			int height = imgSample.getHeight();
			Bitmap zoomBitmap = zoomBitmap(bm, width, height); // 将图片设置到ImageView
*/			
			imgSample.setImageDrawable(new BitmapDrawable(bm));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void doClickImagePersonal() {
		Intent intent = new Intent();
		intent.setClass(this, SelectPhotoActivity.class);
		startActivityForResult(intent, TO_SELECT_PHOTO);
		intent = null;
	}
	

	public void startCustomerServiceMessage() {
		Intent intent = new Intent();
		intent.setClass(this, CustomerServiceMessageActivity.class);
		startActivity(intent);
	}

	private void startAddTagActivity(String path) {
		Intent intent = new Intent(PersonalorderActivity.this, AddTagActivity.class);
		intent.putExtra("bitmap", path);
		
		startActivity(intent);
	}

	public void startDesideActivity() {
//		Intent intent = new Intent();
//		intent.setClass(this, DesideActivity.class);
//		startActivity(intent);
		
		if((null == this.bmPath) || (this.bmPath.equals(""))){
			Toast.makeText(this, R.string.warn_select_photo, Toast.LENGTH_SHORT).show();
		}
		else{
			startAddTagActivity(this.bmPath);
		}
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
