package com.deeal.fragment;

import java.util.ArrayList;
import java.util.List;

import com.deeal.model.FemalRes;
import com.deeal.model.maleRes;
import com.deeal.view.CustomView;
import com.deeal.view.HorizontalListView;
import com.deeal.view.HorizontalListViewAdapter;
import com.deeal.view.MaleCustomView;
import com.example.deeal.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Fragment_clothtype_selector extends Fragment implements
		OnClickListener {

	private TextView tvTab1, tvTab2, tvTab3, tvTab4;
	private List<Drawable> imagesFront = new ArrayList<Drawable>();
	private List<Drawable> imagesBack = new ArrayList<Drawable>();
	private int type;
	private int[] cloth_font = FemalRes.getCloth_font();
	private int[] cloth_back = FemalRes.getCloth_back();
	private int[] sleeve = FemalRes.getSleeve();
	private int[] collar = FemalRes.getCollar();
	private int[] sleeve_right = FemalRes.getSleeveRight();
	private int[] shirt_sleeve_left = maleRes.getShirt_left_sleeve();
	private int[] shirt_sleeve_leftback = maleRes.getShirt_left_sleeveback();
	private int[] shirt_sleeve_right = maleRes.getShirt_right_sleeve();
	private int[] shirt_sleeve_rightback = maleRes.getShirt_right_sleeveback();
	private int[] shirt_pocket = maleRes.getShirt_pocket();
	private int[] shirt_font = maleRes.getShirt_cloth_font();
	private int[] shirt_back = maleRes.getShirt_cloth_back();
	private List<View> detailsimg = new ArrayList<View>();
	private HorizontalListView mhlv;
	public Bitmap[] bms;
	public FemalRes res;
	public int[] resource;
	private CustomView mCustomView;
	private MaleCustomView maleCustomView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.fragment_clothtype_selector, null);
		Intent intent = getActivity().getIntent();
		type = (Integer) intent.getExtras().get("cloth_type");

		tvTab1 = (TextView) view.findViewById(R.id.tv_tab1);
		tvTab2 = (TextView) view.findViewById(R.id.tv_tab2);
		tvTab3 = (TextView) view.findViewById(R.id.tv_tab3);
		tvTab4 = (TextView) view.findViewById(R.id.tv_tab4);
		mhlv = (HorizontalListView) view.findViewById(R.id.cloth_type_hlv);
		LinearLayout layout = (LinearLayout) getActivity().findViewById(
				R.id.layout);
		if (type == 0) {
			for (int i = 0; i < shirt_font.length; i++) {
				imagesFront.add(getResources().getDrawable(shirt_font[i]));
				imagesBack.add(getResources().getDrawable(shirt_back[i]));
			}
			tvTab1.setText("衣身");
			tvTab2.setText("左袖");
			tvTab3.setText("右袖");
			tvTab4.setText("口袋");
			maleCustomView = new MaleCustomView(getActivity());
			maleCustomView.setDrawingCacheEnabled(true);
			layout.addView(maleCustomView);
			HorizontalListViewAdapter adapter = new HorizontalListViewAdapter(
					getActivity(), imagesFront, shirt_font);
			mhlv.setAdapter(adapter);
			mhlv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Bitmap bmfront = BitmapFactory.decodeResource(
							getResources(), shirt_font[position]);
					Bitmap bmback = BitmapFactory.decodeResource(
							getResources(), shirt_back[position]);
					maleCustomView.bmfont = bmfront;
					maleCustomView.bmback = bmback;
					maleCustomView.invalidate();
				}
			});
		} else {
			for (int i = 0; i < cloth_font.length; i++) {
				imagesFront.add(getResources().getDrawable(cloth_font[i]));
				imagesBack.add(getResources().getDrawable(cloth_back[i]));
			}
			HorizontalListViewAdapter adapter = new HorizontalListViewAdapter(
					getActivity(), imagesFront, cloth_font);
			mhlv.setAdapter(adapter);
			mCustomView = new CustomView(getActivity());
			mCustomView.setDrawingCacheEnabled(true);

			layout.addView(mCustomView);
			mhlv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Bitmap bmfront = BitmapFactory.decodeResource(
							getResources(), cloth_font[position]);
					Bitmap bmback = BitmapFactory.decodeResource(
							getResources(), cloth_back[position]);
					mCustomView.resource[0] = cloth_font[position];
					mCustomView.resource[1] = cloth_back[position];
					mCustomView.bms[0] = bmfront;
					mCustomView.bms[1] = bmback;
					mCustomView.invalidate();
				}
			});
		}

		settab();
		tvTab1.setOnClickListener(this);
		tvTab2.setOnClickListener(this);
		tvTab3.setOnClickListener(this);
		tvTab4.setOnClickListener(this);

		return view;
	}

	public void resettab() {
		tvTab1.setBackgroundColor(Color.GRAY);
		tvTab2.setBackgroundColor(Color.GRAY);
		tvTab3.setBackgroundColor(Color.GRAY);
		tvTab4.setBackgroundColor(Color.GRAY);
		tvTab1.setTextColor(Color.WHITE);
		tvTab2.setTextColor(Color.WHITE);
		tvTab3.setTextColor(Color.WHITE);
		tvTab4.setTextColor(Color.WHITE);
	}

	public void settab() {
		tvTab1.setBackground(null);
		tvTab2.setBackgroundColor(Color.GRAY);
		tvTab3.setBackgroundColor(Color.GRAY);
		tvTab4.setBackgroundColor(Color.GRAY);
		tvTab1.setTextColor(Color.BLACK);
		tvTab2.setTextColor(Color.WHITE);
		tvTab3.setTextColor(Color.WHITE);
		tvTab4.setTextColor(Color.WHITE);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resettab();
		switch (v.getId()) {
		case R.id.tv_tab1:

			tvTab1.setBackground(null);
			tvTab1.setTextColor(Color.BLACK);
			if (type == 0) {
				setAdapter(shirt_font);
				mhlv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Bitmap bmfront = BitmapFactory.decodeResource(
								getResources(), shirt_font[position]);
						Bitmap bmback = BitmapFactory.decodeResource(
								getResources(), shirt_back[position]);
						maleCustomView.bmfont = bmfront;
						maleCustomView.bmback = bmback;
						maleCustomView.invalidate();
					}
				});
			} else {
				setAdapter(cloth_font);
				mhlv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Bitmap bmfront = BitmapFactory.decodeResource(
								getResources(), cloth_font[position]);
						Bitmap bmback = BitmapFactory.decodeResource(
								getResources(), cloth_back[position]);
						mCustomView.resource[0] = cloth_font[position];
						mCustomView.resource[1] = cloth_back[position];
						mCustomView.bms[0] = bmfront;
						mCustomView.bms[1] = bmback;
						mCustomView.invalidate();
					}
				});
			}
			break;
		case R.id.tv_tab2:

			tvTab2.setBackground(null);
			tvTab2.setTextColor(Color.BLACK);
			if (type == 0) {
				setAdapter(shirt_sleeve_left);
				mhlv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Bitmap bmfont = BitmapFactory.decodeResource(
								getResources(), shirt_sleeve_left[position]);
						maleCustomView.resource[0] = shirt_sleeve_left[position];
						maleCustomView.bms[0] = bmfont;
						Bitmap bmback = BitmapFactory.decodeResource(
								getResources(), shirt_sleeve_leftback[position]);
						maleCustomView.resource[1] = shirt_sleeve_leftback[position];
						maleCustomView.bms[1] = bmback;
						maleCustomView.invalidate();
					}
				});
			} else {
				setAdapter(collar);
				mhlv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Bitmap bm = BitmapFactory.decodeResource(
								getResources(), collar[position]);
						mCustomView.resource[3] = collar[position];
						mCustomView.bms[3] = bm;
						mCustomView.invalidate();
					}
				});
			}
			break;
		case R.id.tv_tab3:

			tvTab3.setBackground(null);
			tvTab3.setTextColor(Color.BLACK);
			if (type == 0) {
				setAdapter(shirt_sleeve_right);
				mhlv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Bitmap bmfont = BitmapFactory.decodeResource(
								getResources(), shirt_sleeve_right[position]);
						maleCustomView.resource[3] = shirt_sleeve_right[position];
						maleCustomView.bms[3] = bmfont;
						Bitmap bmback = BitmapFactory.decodeResource(
								getResources(), shirt_sleeve_rightback[position]);
						maleCustomView.resource[4] = shirt_sleeve_rightback[position];
						maleCustomView.bms[4] = bmback;
						maleCustomView.invalidate();
					}
				});
			} else {
				setAdapter(sleeve);
				mhlv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Bitmap bm = BitmapFactory.decodeResource(
								getResources(), sleeve[position]);
						mCustomView.resource[2] = sleeve[position];
						mCustomView.bms[2] = bm;
						mCustomView.invalidate();
					}
				});
			}
			break;
		case R.id.tv_tab4:
			tvTab4.setBackground(null);
			tvTab4.setTextColor(Color.BLACK);
			if (type == 0) {
				setAdapter(shirt_pocket);
				mhlv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Bitmap bmfont = BitmapFactory.decodeResource(
								getResources(), shirt_pocket[position]);
						maleCustomView.resource[2] = shirt_pocket[position];
						maleCustomView.bms[2] = bmfont;
						
						maleCustomView.invalidate();
					}
				});
			} else {
				setAdapter(sleeve_right);
				mhlv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Bitmap bm = BitmapFactory.decodeResource(
								getResources(), sleeve_right[position]);
						mCustomView.resource[4] = sleeve_right[position];
						mCustomView.bms[4] = bm;
						mCustomView.invalidate();
					}
				});
			}
			break;
		}
	}

	private void setAdapter(int[] arr) {
		List<Drawable> img = new ArrayList<Drawable>();
		for (int i = 0; i < arr.length; i++) {
			img.add(getResources().getDrawable(arr[i]));
		}
		HorizontalListViewAdapter adapter = new HorizontalListViewAdapter(
				getActivity(), img, arr);
		mhlv.setAdapter(adapter);
	}

}
