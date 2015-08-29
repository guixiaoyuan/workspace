package com.deeal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.deeal.callback.FashionShowCallback;
import com.deeal.tools.URL;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class Fragment_fashionshow extends Fragment {
	private PullToRefreshListView lv_bigfashionshow;
	private View fragmentView, itemView;
	private FashionShowHolder holder;
	// 定义holder的属性
	private ImageView img_show1, img_show2;
	private TextView cloth_name1, cloth_name2;
	private TextView designer_name1, designer_name2;
	private TextView fashionshow_publish_time1, fashionshow_publish_time2;
	private LinkedList<HashMap<String, Object>> data = new LinkedList<HashMap<String, Object>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_bigfashion_show,
				container, false);
		itemView = inflater.inflate(R.layout.bigfashionshow_item, null);
		inititems();
		getData();
		return fragmentView;
	}

	public void inititems() {
		lv_bigfashionshow = (PullToRefreshListView) fragmentView
				.findViewById(R.id.lv_fashion_show);
		img_show1 = (ImageView) itemView.findViewById(R.id.img_show1);
		img_show2 = (ImageView) itemView.findViewById(R.id.img_show2);
		cloth_name1 = (TextView) itemView.findViewById(R.id.cloth_name1);
		cloth_name2 = (TextView) itemView.findViewById(R.id.cloth_name2);
		designer_name1 = (TextView) itemView.findViewById(R.id.designer_name1);
		designer_name2 = (TextView) itemView.findViewById(R.id.designer_name2);
		fashionshow_publish_time1 = (TextView) itemView
				.findViewById(R.id.fashionshow_publish_time1);
		fashionshow_publish_time2 = (TextView) itemView
				.findViewById(R.id.fashionshow_publish_time2);
		
		holder = new FashionShowHolder(img_show1, img_show2, cloth_name1,
				cloth_name2, designer_name1, designer_name2,
				fashionshow_publish_time1, fashionshow_publish_time2);
		
		
		
		lv_bigfashionshow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
		lv_bigfashionshow
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {

						new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... arg0) {

								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {

									e.printStackTrace();
								}
								return null;
							}

							@Override
							protected void onPostExecute(Void result) {
								// TODO Auto-generated method stub
								super.onPostExecute(result);
								lv_bigfashionshow.onRefreshComplete();
							};
						}.execute();
					}
				});
	}

	public void getData() {
		FashionShowCallback callback = new FashionShowCallback(getActivity(), holder, data, lv_bigfashionshow);
		HttpUtils httpUtils=new HttpUtils();
		httpUtils.send(HttpMethod.GET,URL.URL_GetShowList,callback);
	}

	// 定义一个holder
	public class FashionShowHolder {

		public FashionShowHolder(ImageView img_show1, ImageView img_show2,
				TextView cloth_name1, TextView cloth_name2,
				TextView designer_name1, TextView designer_name2,
				TextView fashionshow_publish_time1,
				TextView fashionshow_publish_time2) {
			super();
			this.img_show1 = img_show1;
			this.img_show2 = img_show2;
			this.cloth_name1 = cloth_name1;
			this.cloth_name2 = cloth_name2;
			this.designer_name1 = designer_name1;
			this.designer_name2 = designer_name2;
			this.fashionshow_publish_time1 = fashionshow_publish_time1;
			this.fashionshow_publish_time2 = fashionshow_publish_time2;
		}

		public ImageView img_show1, img_show2;
		public TextView cloth_name1, cloth_name2;
		public TextView designer_name1, designer_name2;
		public TextView fashionshow_publish_time1, fashionshow_publish_time2;
	}

}
