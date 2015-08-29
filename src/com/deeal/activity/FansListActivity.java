package com.deeal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.deeal.callback.FansListCallback;
import com.deeal.model.mycenter.MyFansInfo;
import com.deeal.tools.URL;
import com.deeal.tools.mycenter.MyFansManager;
import com.deeal.view.RoundImageView;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class FansListActivity extends Activity {
	//定义FansItemHolder需要的一些东西
	private FansItemHolder holder;
	// 粉丝头像  粉丝id  粉丝个性签名
	private RoundImageView img_fans_user_head;
	private TextView tv_fansid,tv_fans_personalwords;
	private View itemView;
	private PullToRefreshListView lvFans;
	private LinkedList<HashMap<String, Object>> data = new LinkedList<HashMap<String, Object>>();

	@ViewInject(R.id.btn_fanslist_return)
	private ImageButton imagebtn_fanslist_returnButton;

	@OnClick(R.id.btn_fanslist_return)
	public void imagebtn_fanslist_returnButton(View v) {
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fanslist);
		
		initItems();
	
	}

	public void initItems(){
		
		this.lvFans = (PullToRefreshListView) findViewById(R.id.lv_fans);

		itemView=LayoutInflater.from(this).inflate(R.layout.fans_list_item, null);
		img_fans_user_head=(RoundImageView) itemView.findViewById(R.id.img_fans_user_head);
		tv_fansid=(TextView) itemView.findViewById(R.id.tv_fansid);
		tv_fans_personalwords=(TextView) itemView.findViewById(R.id.tv_fans_personalwords);
		holder=new FansItemHolder(img_fans_user_head, tv_fansid, tv_fans_personalwords);
		
		this.lvFans.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				startFriendCenterActivity();
			}
		});

		// 设定下拉监听函数
		this.lvFans.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				//lvFans.onRefreshComplete();
				new GetDataTask().execute();
			}
		});
		
		getData();
	}
	
	private void getData() {
		FansListCallback callback=new FansListCallback(this, holder, data, lvFans);
		HttpUtils httpUtils=new HttpUtils();
		httpUtils.send(HttpMethod.GET, URL.URL_GetFansList, callback);
	}

	public void startFriendCenterActivity() {
		Intent i = new Intent();
		i.setClass(this, FriendCenterActivity.class);
		startActivity(i);
	}

	private class GetDataTask extends
			AsyncTask<Void, Void, HashMap<String, Object>> {

		// 后台处理部分
		@Override
		protected HashMap<String, Object> doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			try {
				map = new HashMap<String, Object>();
				map.put("id", "anthow");
				map.put("personalwords", "222222");
			} catch (Exception e) {
				// TODO: handle exception
				setTitle("map出错了");
				return null;
			}
			return map;
		}

		// 这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
		// 根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			// 在头部增加新添内容

			try {
				//data.addFirst(result);

				// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
				//adapter.notifyDataSetChanged();
				lvFans.onRefreshComplete();
			} catch (Exception e) {
				// TODO: handle exception
				setTitle(e.getMessage());
			}
			super.onPostExecute(result);
		}

	}

	public class FansItemHolder {
		public FansItemHolder(RoundImageView img_fans_user_head,
				TextView tv_fansid, TextView tv_fans_personalwords) {
			this.img_fans_user_head=img_fans_user_head;
			this.tv_fansid=tv_fansid;
			this.tv_fans_personalwords=tv_fans_personalwords;
		}
		/* @ViewInject(R.id.img_fans_user_head) */
		public RoundImageView img_fans_user_head;

		/* @ViewInject(R.id.tv_fansid) */
		public TextView tv_fansid;

		/* @ViewInject(R.id.tv_fans_personalwords) */
		public TextView tv_fans_personalwords;
	}
}
