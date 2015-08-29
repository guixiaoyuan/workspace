package com.deeal.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class Fragment_order_unpay extends Fragment {
	private PullToRefreshListView lvOrderUnpay;
	private ArrayList<HashMap<String , Object>> data =new ArrayList<HashMap<String,Object>>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		for(int i=0;i<3;i++){
			HashMap<String,Object> map =new HashMap<String, Object>();
			map.put("id", "test"+00+i);
			data.add(map);
		}
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_order_unpay, null);
		lvOrderUnpay=(PullToRefreshListView) view.findViewById(R.id.lv_order_unpay);
		
		String[] from ={"id"};
		int[] to ={R.id.order_id};
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.lv_order_unpay_item,from, to);
		lvOrderUnpay.setAdapter(adapter);
		return view;
	}
}
