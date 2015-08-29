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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.deeal.callback.FashionTalkCallback;
import com.deeal.tools.URL;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class Fragment_fashionshuo extends Fragment {
	private PullToRefreshListView lv_fashionshuo;
	private View fragmentView, itemView;
	// 自定义的holder
	private FashionTalkHolder holder;
	public ImageView img_head;
	public TextView fashionshuo_title, fashionshuo_publish_date,
			fashionshuo_editor, tv_fashionshuo_content, user_id, comments;

	private LinkedList<HashMap<String, Object>> data = new LinkedList<HashMap<String, Object>>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_bigfashion_shuo,
				container, false);
		itemView = inflater.inflate(R.layout.bigfashionshuo_item, null);
		inititem();
		// 加载数据
		getData();
		return fragmentView;
	}

	private void inititem() {
		lv_fashionshuo = (PullToRefreshListView) fragmentView
				.findViewById(R.id.lv_fashion_shuo);

		img_head = (ImageView) itemView.findViewById(R.id.img_head);
		fashionshuo_title = (TextView) itemView
				.findViewById(R.id.fashionshuo_title);
		fashionshuo_publish_date = (TextView) itemView
				.findViewById(R.id.fashionshuo_publish_date);
		fashionshuo_editor = (TextView) itemView
				.findViewById(R.id.fashionshuo_editor);
		tv_fashionshuo_content = (TextView) itemView
				.findViewById(R.id.tv_fashionshuo_content);
		user_id = (TextView) itemView.findViewById(R.id.user_id);
		comments = (TextView) itemView.findViewById(R.id.comments);
		holder = new FashionTalkHolder(img_head, fashionshuo_title,
				fashionshuo_publish_date, fashionshuo_editor,
				tv_fashionshuo_content, user_id, comments);

		lv_fashionshuo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
		lv_fashionshuo.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
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

					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						lv_fashionshuo.onRefreshComplete();
					};
				}.execute();
			}
		});
	}

	public void getData() {
		FashionTalkCallback callback = new FashionTalkCallback(getActivity(),
				holder, data, lv_fashionshuo);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, URL.URL_GetTalkList, callback);
	}
	// 定义一个holder
	public class FashionTalkHolder {

		public FashionTalkHolder(ImageView img_head,
				TextView fashionshuo_title, TextView fashionshuo_publish_date,
				TextView fashionshuo_editor, TextView tv_fashionshuo_content,
				TextView user_id, TextView comments) {
			super();
			this.img_head = img_head;
			this.fashionshuo_title = fashionshuo_title;
			this.fashionshuo_publish_date = fashionshuo_publish_date;
			this.fashionshuo_editor = fashionshuo_editor;
			this.tv_fashionshuo_content = tv_fashionshuo_content;
			this.user_id = user_id;
			this.comments = comments;
		}

		public ImageView img_head;
		public TextView fashionshuo_title;// 标题
		public TextView fashionshuo_publish_date;// 发布时间
		public TextView fashionshuo_editor;// 作者
		public TextView tv_fashionshuo_content;// 内容
		public TextView user_id;// 评论的人的id
		public TextView comments;// 评论的内容

	}

}
