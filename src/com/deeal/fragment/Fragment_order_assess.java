package com.deeal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import com.deeal.activity.DiscussActivity;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class Fragment_order_assess extends Fragment {

	private PullToRefreshListView lvOrderAssess;
	private Button bt_assess_order;
	private View mParent;
	private FragmentActivity mActivity;
	private ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		for (int i = 0; i < 3; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", "test" + 00 + i);
			data.add(map);
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_order_assess, null);
		lvOrderAssess = (PullToRefreshListView) view
				.findViewById(R.id.lv_order_assess);

		String[] from = { "id" };
		int[] to = { R.id.order_id };
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
				R.layout.lv_order_assess_item, from, to);
		lvOrderAssess.setAdapter(adapter);
		
		
		return view;
	}



	
	
//	//使用baseadapter实现不同item的点击事件
//	public class MyAdapter extends BaseAdapter{
//		private LayoutInflater  mInflater;
//		public MyAdapter(Context context){ 
//			this.mInflater = LayoutInflater .from(context);
//		}
//
//		@Override
//		public int getCount() {
//			
//			return data.size();
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//		
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder = null; 
//			if (convertView == null) { 
//			holder=new ViewHolder(); 
//			convertView = mInflater.inflate(R.layout.lv_order_assess_item, null); 
//			holder.bt_assess_order=(Button) convertView.findViewById(R.id.bt_assess_order);
//			convertView.setTag(holder); }else { 
//				holder = (ViewHolder)convertView.getTag(); 
//			} 
//
//
//			holder.bt_assess_order.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//				starAssessActivity();
//					
//				}
//			});
//		}

//		
		
//	}
//	public final class ViewHolder{ 
//		
//		public Button bt_assess_order; 
//		}
	


}
