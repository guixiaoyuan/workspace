package com.deeal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.deeal.activity.TalkActivity;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public class Fragment_mycenter_things extends Fragment {
	private PullToRefreshListView lv_things;
	private List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =inflater.inflate(R.layout.fragment_center_things, null);
		lv_things = (PullToRefreshListView) view.findViewById(R.id.lv_mycenter_things);
		for(int i=0;i<5;i++){
			HashMap<String,Object> map =new HashMap<String, Object>();
			map.put("test", "test"+i);
			data.add(map);
		}
		String[] from={};
		int[] to={};
		SimpleAdapter adapter =  new SimpleAdapter(getActivity(), data, R.layout.info_item, from, to);
		lv_things.setAdapter(adapter);
		lv_things.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				startTalkActivity();
			}
		});
		return view;
	}
	private void startTalkActivity(){
		Intent i = new Intent();
		i.setClass(getActivity(), TalkActivity.class);
		startActivity(i);
	}

}
