package com.deeal.activity;

import java.util.HashMap;
import java.util.LinkedList;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.deeal.callback.FriendListCallback;
import com.deeal.tools.URL;
import com.deeal.view.RoundImageView;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class FriendsListActivity extends Activity {
	//定义FriendItemHolder需要的一些东西
	private FriendItemHolder holder;
	// 好友头像  好友id  好友个性签名
	private RoundImageView img_friend_user_head;
	private TextView tv_friendid,tv_friend_personalwords;
	private View itemView;
	private PullToRefreshListView lvFriends;
	private LinkedList<HashMap<String, Object>> data = new LinkedList<HashMap<String, Object>>();
	private ImageButton bt_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendslist);
		bt_back = (ImageButton) findViewById(R.id.btn_friendlist_return);

		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		initItems();
	}

	public void initItems() {
		this.lvFriends = (PullToRefreshListView) findViewById(R.id.lv_friend_test);
		itemView = LayoutInflater.from(this).inflate(R.layout.friend_list_item,
				null);
		img_friend_user_head=(RoundImageView) itemView.findViewById(R.id.img_friend_user_head);
		tv_friendid=(TextView) itemView.findViewById(R.id.tv_friendid);
		tv_friend_personalwords=(TextView) itemView.findViewById(R.id.tv_friend_personalwords);
		holder=new FriendItemHolder(img_friend_user_head, tv_friendid, tv_friend_personalwords);
		
		lvFriends.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				startFriendCenterActivity();
			}
		});

		// 设定下拉监听函数
		this.lvFriends.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				new GetDataTask().execute();
			}
		});

		
		getData();
	}

	@SuppressWarnings("unchecked")
	private void getData() {
		FriendListCallback callback=new FriendListCallback(this, holder, data,lvFriends);
		HttpUtils httpUtils=new HttpUtils();
		httpUtils.send(HttpMethod.GET, URL.URL_GetFriendsList, callback);
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
			map = new HashMap<String, Object>();
			map.put("id", "anthow");
			map.put("personalwords", "111111");
			return map;
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			// 在头部增加新添内容
			//data.addFirst(result);
			// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			//adapter.notifyDataSetChanged();
			lvFriends.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	public void startFriendCenterActivity() {
		Intent i = new Intent();
		i.setClass(this, FriendCenterActivity.class);
		startActivity(i);
	}
	
	public class FriendItemHolder {
		public FriendItemHolder(RoundImageView img_friend_user_head,
				TextView tv_friendid, TextView tv_friend_personalwords) {
				this.img_friend_user_head=img_friend_user_head;
				this.tv_friendid=tv_friendid;
				this.tv_friend_personalwords=tv_friend_personalwords;
		}
		// @ViewInject(R.id.img_friend_user_head)
		public RoundImageView img_friend_user_head;

		// @ViewInject(R.id.tv_friendid)
		public TextView tv_friendid;

		// @ViewInject(R.id.tv_friend_personalwords)
		public TextView tv_friend_personalwords;
	}

}
