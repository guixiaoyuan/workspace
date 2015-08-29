package com.deeal.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.deeal.model.customed.TagInfo;
import com.example.deeal.R;

public class TagImageView extends RelativeLayout {
	private Context context;

	private List<View> tagViewList;
	private HashMap<View, TagInfo> mapTags;
	private HashMap<EditText, ImageView> mapDic;
	private EditText focusedEditText;

	public TagImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mapDic = new HashMap<EditText, ImageView>();
	}

	public TagImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mapDic = new HashMap<EditText, ImageView>();
	}

	public TagImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mapDic = new HashMap<EditText, ImageView>();
	}

	public View addTextTag(int x, int y) {
		if (tagViewList == null)
			tagViewList = new ArrayList<View>();
		if (null == this.mapTags)
			this.mapTags = new HashMap<View,TagInfo>();

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = mInflater.inflate(R.layout.tag, null);
		EditText text = (EditText) view.findViewById(R.id.tag_text);
		ImageView dot = (ImageView) view.findViewById(R.id.img_transport);
		final RelativeLayout layout = (RelativeLayout) view
				.findViewById(R.id.tag_layout);
		this.mapTags.put(dot, new TagInfo(new Point(x, y), ""));
		this.mapDic.put(text, dot);
		dot.setOnTouchListener(new OnTouchListener() {
			int startx = 0;
			int starty = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startx = (int) event.getRawX();
					starty = (int) event.getRawY();
					updateTagPosition(v, new Point(startx, starty));
					break;
				case MotionEvent.ACTION_MOVE:
					
					int x = (int) event.getRawX();
					int y = (int) event.getRawY();
					int dx = x - startx;
					int dy = y - starty;

					
					setPosition(layout, dx, dy);

					
					startx = (int) event.getRawX();
					starty = (int) event.getRawY();

					break;

				case MotionEvent.ACTION_UP:
					int _x = (int) event.getRawX();
					int _y = (int) event.getRawY();
					updateTagPosition(v, new Point(_x, _y));
					break;
				}
				return true;
			}

		});

		text.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean focus) {
				// TODO Auto-generated method stub
				if (!focus){
					ImageView dv = mapDic.get(v);
					TagInfo info = mapTags.get(dv);
					info.setInfo(((EditText) v).getText().toString().trim());
				}
				else{
					focusedEditText = (EditText) v;
				}
				

			}
		});

		this.addView(layout);
		setPosition(layout, x, y);
		tagViewList.add(layout);

		return layout;
	}
	
	public EditText getFocusedEditText(){
		return this.focusedEditText;
	}

	public HashMap<View, TagInfo> getTags() {
		return this.mapTags;
	}

	private void updateTagPosition(View v, Point pt) {
		TagInfo info = this.mapTags.get(v);
		if (null == info)
			return;
		info.setPt(pt);
	}

	private void setPosition(View v, int dx, int dy) {
		int parentWidth = this.getWidth();
		int parentHeight = this.getHeight();
		int l = v.getLeft() + dx;
		int t = v.getTop() + dy;
		if(l<20 && t<20){
			l=20;
			t=20;
		}
		
		if (l < 0)
			l = 0;
		else if ((l + v.getWidth()) >= parentWidth) {
			l = parentWidth - v.getWidth();
		}
		if (t < 0)
			t = 0;
		else if ((t + v.getHeight()) >= parentHeight) {
			t = parentHeight - v.getHeight();
		}
		int r = l + v.getWidth();
		int b = t + v.getHeight();
		v.layout(l, t, r, b);
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) v
				.getLayoutParams();
		params.leftMargin = l;
		params.topMargin = t;
		v.setLayoutParams(params);
	}

	
}
