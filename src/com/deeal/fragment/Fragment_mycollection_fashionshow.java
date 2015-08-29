package com.deeal.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fragment_mycollection_fashionshow extends Fragment {
	private  PullToRefreshListView lv_mycollection_fashionshow;
	private View view;
	private ArrayList<HashMap<String,Object>> data= new ArrayList<HashMap<String,Object>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_mycollection_fashion_show, container, false);
		inititems();
		return view;
	}
	
	public void inititems(){
		lv_mycollection_fashionshow=(PullToRefreshListView) view.findViewById(R.id.lv_myfashion_show);
		for(int i=0;i<5;i++){
			HashMap<String,Object> map=new HashMap<String, Object>();
			map.put("test", "test"+i);
			data.add(map);
		}
		String[] from={"test"};
		int[] to={R.id.designer_name1};
		SimpleAdapter adapter =new SimpleAdapter(getActivity(), data, R.layout.bigfashionshow_item, from, to);
		lv_mycollection_fashionshow.setAdapter(adapter);
		lv_mycollection_fashionshow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		lv_mycollection_fashionshow.setOnRefreshListener(new OnRefreshListener<ListView>() {

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
						lv_mycollection_fashionshow.onRefreshComplete();
					};
				}.execute();
			}
		});
	}
	
	
}
