package com.deeal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.deeal.model.TagInfo;
import com.deeal.view.TagImageView;
import com.example.deeal.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@SuppressLint("NewApi")
public class AddTagActivity extends Activity {

	private Button button;
	private TagImageView tagImageView;

	private View l;

	@ViewInject(R.id.back_button)
	private ImageButton imgButtonBack;

	@ViewInject(R.id.btn_customed_oK)
	private Button btnCustomedOK;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_tag);
		ViewUtils.inject(this);

		button = (Button) findViewById(R.id.add_tag);
		tagImageView = (TagImageView) findViewById(R.id.image_layout);
		initImage(tagImageView);

		// 将view转换成bitmap
		//bitmap=view2bitmap(tagImageView);

		button.setOnClickListener(new OnClickListener() {
			int i = 0;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				l = tagImageView.addTextTag(300, 300);

			}
		});

	}

	@OnClick(R.id.back_button)
	private void imgButtonBackClick(View v) {
		finish();
	}

	@OnClick(R.id.btn_customed_oK)
	private void btnOKClick(View v) {
		EditText text = tagImageView.getFocusedEditText();
		if (null != text)
			text.clearFocus();
		HashMap<View, TagInfo> map = tagImageView.getTags();
		ArrayList<TagInfo> tagInfoList=new ArrayList<TagInfo>();
		if (null != map) {
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				TagInfo val = (TagInfo) entry.getValue();
				tagInfoList.add(val);
			}
		}
		Intent intent = new Intent();
		Bundle bundle=new Bundle();
		ArrayList<String> tagList = new ArrayList<String>();
		for(TagInfo i : tagInfoList){
			tagList.add(i.toString());
		}
		bundle.putSerializable("tagList", tagList);
		intent.putExtra("path", getIntent().getStringExtra("bitmap").toString());
		intent.putExtras(bundle);
		intent.setClass(AddTagActivity.this, VerifyActivity.class);
		startActivity(intent);
	}

	private void initImage(TagImageView img) {
		Intent intent = getIntent();
		String path = intent.getStringExtra("bitmap");
		img.setBackgroundDrawable(Drawable.createFromPath(path));
		this.imgButtonBack.getBackground().setAlpha(50);
	}

}
