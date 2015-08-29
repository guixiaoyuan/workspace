package com.deeal.callback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.color;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.deeal.fragment.Fragment_fashionshow.FashionShowHolder;
import com.deeal.fragment.Fragment_fashionshuo.FashionTalkHolder;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class FashionTalkCallback extends RequestCallBack {
	private Activity activity;
	private FashionTalkHolder holder;
	private PullToRefreshListView lvFashionTalk;
	private LinkedList<HashMap<String, Object>> data;
	private FashionTalkInfoAdapter adapter;
	private BitmapUtils bitmapUtils;

	public FashionTalkCallback(Activity activity, FashionTalkHolder holder,
			LinkedList<HashMap<String, Object>> data,
			PullToRefreshListView lvFashionTalk) {
		this.activity = activity;
		this.holder = holder;
		this.data = data;
		this.lvFashionTalk = lvFashionTalk;
		bitmapUtils = new BitmapUtils(activity);
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
		try {
			JSONObject allJson = new JSONObject(string);
			// 状态
			String status = allJson.getString("status");
			if (status.equals("success")) {
				// data是个数组
				JSONArray dataArray = allJson.getJSONArray("data");
				int count = dataArray.length();
				for (int i = 0; i < count; i++) {
					Map<String, Object> fashionTalkMap = new HashMap<String, Object>();
					JSONObject dataObject = dataArray.getJSONObject(i);
					JSONObject authorObject = dataObject
							.getJSONObject("author");
					// 作者名字
					String name = authorObject.getString("name");
					// 照片路径
					String imgPath = authorObject.getString("img");
					// 时间
					String date = dataObject.getString("date");
					// 内容
					String content_part = dataObject.getString("content_part");
					// 标题
					String title = dataObject.getString("title");

					fashionTalkMap.put("fashionshuo_editor", name);
					fashionTalkMap.put("img", imgPath);
					fashionTalkMap.put("fashionshuo_publish_date", date);
					fashionTalkMap.put("tv_fashionshuo_content", content_part);
					fashionTalkMap.put("fashionshuo_title", title);

					data.add((HashMap<String, Object>) fashionTalkMap);
				}
			}
		} catch (Exception e) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~~~~~~json err~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}

		adapter = new FashionTalkInfoAdapter(activity, data, holder);
		lvFashionTalk.setAdapter(adapter);

	}

	private void doGetDataFailure() {
		Toast.makeText(activity, "获取FashionShow消息出错", Toast.LENGTH_SHORT)
				.show();

	}

	public class FashionTalkInfoAdapter extends BaseAdapter {
		private Activity activity;
		private LinkedList<HashMap<String, Object>> data;
		private FashionTalkHolder holder;

		public FashionTalkInfoAdapter(Activity activity,
				LinkedList<HashMap<String, Object>> data,
				FashionTalkHolder holder) {
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
						R.layout.bigfashionshuo_item, null);
				holder.img_head = (ImageView) convertView
						.findViewById(R.id.img_head);
				holder.fashionshuo_editor = (TextView) convertView
						.findViewById(R.id.fashionshuo_editor);
				holder.fashionshuo_publish_date = (TextView) convertView
						.findViewById(R.id.fashionshuo_publish_date);
				holder.tv_fashionshuo_content = (TextView) convertView
						.findViewById(R.id.tv_fashionshuo_content);
				holder.fashionshuo_title = (TextView) convertView
						.findViewById(R.id.fashionshuo_title);

				convertView.setTag(holder);
			} else {
				holder = (FashionTalkHolder) convertView.getTag();
			}
			holder.img_head.setBackgroundColor(color.transparent);
			bitmapUtils.display(holder.img_head, data.get(position).get("img")
					.toString());

			holder.fashionshuo_editor.setText(data.get(position)
					.get("fashionshuo_editor").toString());
			holder.fashionshuo_publish_date.setText(data.get(position)
					.get("fashionshuo_publish_date").toString());
			holder.tv_fashionshuo_content.setText(data.get(position)
					.get("tv_fashionshuo_content").toString());
			holder.fashionshuo_title.setText(data.get(position)
					.get("fashionshuo_title").toString());

			return convertView;
		}
	}
}
