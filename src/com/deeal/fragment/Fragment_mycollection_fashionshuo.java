package com.deeal.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class Fragment_mycollection_fashionshuo extends Fragment {

	private PullToRefreshListView lv_mycolletiong_fashionshuo;
	private View view;

	private ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mycollection_fashion_shuo, container,
				false);
		inititem();
		return view;
	}

	private void inititem() {
		lv_mycolletiong_fashionshuo = (PullToRefreshListView) view.findViewById(R.id.lv_fashion_shuo);
		for (int i = 1; i < 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("username", "test" + i);
			data.add(map);
		}
		String[] from = { "username" };
		int[] to = { R.id.fashionshuo_editor };
		ListAdapter adapter = new SimpleAdapter(getActivity(), data,
				R.layout.bigfashionshuo_item, from, to);
		lv_mycolletiong_fashionshuo.setAdapter(adapter);
		lv_mycolletiong_fashionshuo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
		lv_mycolletiong_fashionshuo.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				
				/*
				 * �첽�����߳�
				 */
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... arg0) {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}
					
					/*
					 * 
					 * 
					 */

					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						lv_mycolletiong_fashionshuo.onRefreshComplete();
					};
				}.execute();
			}
		});
	}

}
