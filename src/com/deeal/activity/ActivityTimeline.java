package com.deeal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.deeal.R;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityTimeline extends Activity {

	private ListView listView;
	List<String> data ;
	private TimelineAdapter timelineAdapter;
	private ImageButton bt_back;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline_listview);
		bt_back=(ImageButton) findViewById(R.id.btn_timeline_back);
		
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});

		listView = (ListView) this.findViewById(R.id.timeline_listview);
		listView.setDividerHeight(0);
		timelineAdapter = new TimelineAdapter(this, getData());
		listView.setAdapter(timelineAdapter);

	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "����1");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "����2");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "����3");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "����4");
		list.add(map);
		return list;
	}

}
