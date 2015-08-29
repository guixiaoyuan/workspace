package com.deeal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class FriendCenterActivity extends Activity {
	
	private PullToRefreshListView lv_things;
	private List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendcenter);
		lv_things = (PullToRefreshListView)findViewById(R.id.lv_friend_things);
		for(int i=0;i<5;i++){
			HashMap<String,Object> map =new HashMap<String, Object>();
			map.put("test", "test"+i);
			data.add(map);
		}
		String[] from={};
		int[] to={};
		SimpleAdapter adapter =  new SimpleAdapter(this, data, R.layout.info_item, from, to);
		lv_things.setAdapter(adapter);
		lv_things.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				startTalkActivity();
			}
		});
		
	}
	private void startTalkActivity(){
		Intent i = new Intent();
		i.setClass(this, TalkActivity.class);
		startActivity(i);
	}
}
