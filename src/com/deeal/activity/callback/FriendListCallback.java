package com.deeal.activity.callback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.activity.FriendsListActivity.FriendItemHolder;
import com.deeal.view.RoundImageView;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class FriendListCallback extends RequestCallBack {
	private Activity activity;
	private FriendItemHolder holder;
	private PullToRefreshListView lvFriends;
	private LinkedList<HashMap<String, Object>> data;
	private FriendInfoAdapter adapter;
	private BitmapUtils bitmapUtils;
	
	public FriendListCallback(Activity activity, FriendItemHolder holder,
			LinkedList<HashMap<String, Object>> data,
			PullToRefreshListView lvFriends) {
		this.activity = activity;
		this.holder = holder;
		this.lvFriends = lvFriends;
		this.data = data;
		bitmapUtils=new BitmapUtils(activity);
	}

	@Override
	public void onFailure(HttpException arg0, String arg1) {

	}

	@Override
	public void onSuccess(ResponseInfo serverResult) {
		if (null == serverResult || null == serverResult.result
				|| "" == serverResult.result) {
			doGetDataFailure();
		} else {
			doGetDataSuccess(serverResult.result.toString());
		}
	}

	private void doGetDataSuccess(String string) {
		// 解析数据

		try {
			JSONObject allJson = new JSONObject(string);
			String status = allJson.getString("status");
			if (status.equals("success")) {
				//data是个数组
				JSONArray dataArray=allJson.getJSONArray("data");
				int count=dataArray.length();
				for(int i=0;i<count;i++){
					Map<String, Object> friendsMap = new HashMap<String, Object>();
					JSONObject dataObject = dataArray.getJSONObject(i);
					JSONObject userObject=dataObject.getJSONObject("user");
					String imgUrl=userObject.getString("img");
					String name=userObject.getString("name");
					String pinfo=userObject.getString("pinfo");
					
					friendsMap.put("img", imgUrl);
					friendsMap.put("name", name);
					friendsMap.put("pinfo", pinfo);
					data.add((HashMap<String, Object>) friendsMap);
					
				}
			} else {
				Toast.makeText(activity, "获取FriendListCallback信息出错",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~~~~~~json err~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}
		adapter=new FriendInfoAdapter(activity, data, holder);
		lvFriends.setAdapter(adapter);
	}

	private void doGetDataFailure() {
		Toast.makeText(activity, "获取好友信息出错", Toast.LENGTH_SHORT).show();

	}

	public class FriendInfoAdapter extends BaseAdapter {
		private Activity activity;
		private LinkedList<HashMap<String, Object>> data;
		private FriendItemHolder holder;

		public FriendInfoAdapter(Activity activity,
				LinkedList<HashMap<String, Object>> data,
				FriendItemHolder holder) {
			this.activity = activity;
			this.data = data;
			this.holder = holder;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(
						R.layout.friend_list_item, null);
				holder.img_friend_user_head = (RoundImageView) convertView
						.findViewById(R.id.img_friend_user_head);
				holder.tv_friendid = (TextView) convertView
						.findViewById(R.id.tv_friendid);
				holder.tv_friend_personalwords = (TextView) convertView
						.findViewById(R.id.tv_friend_personalwords);
				convertView.setTag(holder);
			} else {
				holder = (FriendItemHolder) convertView.getTag();
			}
			holder.img_friend_user_head.setBackgroundColor(Color.TRANSPARENT);
			bitmapUtils.display(holder.img_friend_user_head, data.get(position).get("img").toString());
			
			holder.tv_friendid.setText(data.get(position).get("name").toString());
			holder.tv_friend_personalwords.setText(data.get(position).get("pinfo").toString());
			return convertView;
		}
	}
}
