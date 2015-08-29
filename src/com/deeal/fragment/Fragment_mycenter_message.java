package com.deeal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_mycenter_message extends Fragment {
	private PullToRefreshListView lv_messages;
	private List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =inflater.inflate(R.layout.fragment_center_messages, null);
		lv_messages = (PullToRefreshListView) view.findViewById(R.id.lv_mycenter_message);
		for(int i=0;i<5;i++){
			HashMap<String,Object> map =new HashMap<String, Object>();
			map.put("test", "test"+i);
			data.add(map);
		}
		String[] from={};
		int[] to={};
		SimpleAdapter adapter =  new SimpleAdapter(getActivity(), data, R.layout.info_replay_item, from, to);
		lv_messages.setAdapter(adapter);
		
		return view;
	}

}
