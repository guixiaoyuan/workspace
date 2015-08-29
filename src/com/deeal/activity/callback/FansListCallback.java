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

import com.deeal.activity.FansListActivity.FansItemHolder;
import com.deeal.activity.FriendsListActivity.FriendItemHolder;
import com.deeal.view.RoundImageView;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class FansListCallback extends RequestCallBack {
	private Activity activity;
	private FansItemHolder holder;
	private PullToRefreshListView lvFans;
	private LinkedList<HashMap<String, Object>> data;
	private FansInfoAdapter adapter;
	private BitmapUtils bitmapUtils;
	
	public FansListCallback(Activity activity, FansItemHolder holder,
			LinkedList<HashMap<String, Object>> data,
			PullToRefreshListView lvFans) {
		this.activity = activity;
		this.holder = holder;
		this.lvFans = lvFans;
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
					Map<String, Object> fansMap = new HashMap<String, Object>();
					JSONObject dataObject = dataArray.getJSONObject(i);
					JSONObject userObject=dataObject.getJSONObject("user");
					String imgUrl=userObject.getString("img");
					String name=userObject.getString("name");
					String pinfo=userObject.getString("pinfo");
					
					fansMap.put("img", imgUrl);
					fansMap.put("name", name);
					fansMap.put("pinfo", pinfo);
					data.add((HashMap<String, Object>) fansMap);
				}
			}else {
				Toast.makeText(activity, "获取FansListCallback信息出错",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~~~~~~json err~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}
		adapter=new FansInfoAdapter(activity, data, holder);
		lvFans.setAdapter(adapter);

	}

	private void doGetDataFailure() {
		Toast.makeText(activity, "获取粉丝信息出错", Toast.LENGTH_SHORT).show();

	}

	public class FansInfoAdapter extends BaseAdapter {
		private Activity activity;
		private LinkedList<HashMap<String, Object>> data;
		private FansItemHolder holder;

		public FansInfoAdapter(Activity activity,
				LinkedList<HashMap<String, Object>> data, FansItemHolder holder) {
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
						R.layout.fans_list_item, null);
				holder.img_fans_user_head = (RoundImageView) convertView
						.findViewById(R.id.img_fans_user_head);
				holder.tv_fansid = (TextView) convertView
						.findViewById(R.id.tv_fansid);
				holder.tv_fans_personalwords = (TextView) convertView
						.findViewById(R.id.tv_fans_personalwords);
				convertView.setTag(holder);
			} else {
				holder = (FansItemHolder) convertView.getTag();
			}
			
			holder.img_fans_user_head.setBackgroundColor(Color.TRANSPARENT);
			bitmapUtils.display(holder.img_fans_user_head, data.get(position).get("img").toString());
			holder.tv_fansid.setText(data.get(position).get("name").toString());
			holder.tv_fans_personalwords.setText(data.get(position).get("pinfo").toString());
			
			return convertView;
		}
	}
}
