package com.deeal.callback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.fragment.Fragment_fashionshow.FashionShowHolder;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class FashionShowCallback extends RequestCallBack {
	private Activity activity;
	private FashionShowHolder holder;
	private PullToRefreshListView lvFashionShow;
	private LinkedList<HashMap<String, Object>> data;
	private FashionShowInfoAdapter adapter;
	private BitmapUtils bitmapUtils;

	public FashionShowCallback(Activity activity, FashionShowHolder holder,
			LinkedList<HashMap<String, Object>> data,
			PullToRefreshListView lvFashionShow) {
		this.activity = activity;
		this.holder = holder;
		this.data = data;
		this.lvFashionShow = lvFashionShow;
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
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < count; i++) {

					// 加载左边的item
					if (i % 2 == 0) {
						Map<String, Object> fashionShowMap1 = new HashMap<String, Object>();
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
						String text_part = dataObject.getString("text_part");

						fashionShowMap1.put("name1", name);
						fashionShowMap1.put("img1", imgPath);
						fashionShowMap1.put("date1", date);
						fashionShowMap1.put("text_part1", text_part);

						map.put("map1", fashionShowMap1);
					} else {
						Map<String, Object> fashionShowMap2 = new HashMap<String, Object>();
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
						String text_part = dataObject.getString("text_part");

						fashionShowMap2.put("name2", name);
						fashionShowMap2.put("img2", imgPath);
						fashionShowMap2.put("date2", date);
						fashionShowMap2.put("text_part2", text_part);

						map.put("map2", fashionShowMap2);
					}
					if (i % 2 != 0) {
						data.add((HashMap<String, Object>) map);
						map = new HashMap<String, Object>();
					}
				}
			}
		} catch (Exception e) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~~~~~~json err~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}

		adapter = new FashionShowInfoAdapter(activity, data, holder);
		lvFashionShow.setAdapter(adapter);

	}

	private void doGetDataFailure() {
		Toast.makeText(activity, "获取FashionShow消息出错", Toast.LENGTH_SHORT)
				.show();

	}

	public class FashionShowInfoAdapter extends BaseAdapter {
		private Activity activity;
		private LinkedList<HashMap<String, Object>> data;
		private FashionShowHolder holder;

		public FashionShowInfoAdapter(Activity activity,
				LinkedList<HashMap<String, Object>> data,
				FashionShowHolder holder) {
			this.activity = activity;
			this.data = data;
			this.holder = holder;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			//Log.e("sadffffffffffadsffffffff", data.size()+"");
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
						R.layout.bigfashionshow_item, null);
				holder.img_show1=(ImageView) convertView.findViewById(R.id.img_show1);
				holder.img_show2=(ImageView) convertView.findViewById(R.id.img_show2);
				holder.designer_name1 = (TextView) convertView
						.findViewById(R.id.designer_name1);
				holder.designer_name2 = (TextView) convertView
						.findViewById(R.id.designer_name2);
				holder.cloth_name1 = (TextView) convertView
						.findViewById(R.id.cloth_name1);
				holder.cloth_name2 = (TextView) convertView
						.findViewById(R.id.cloth_name2);
				holder.fashionshow_publish_time1 = (TextView) convertView
						.findViewById(R.id.fashionshow_publish_time1);
				holder.fashionshow_publish_time2 = (TextView) convertView
						.findViewById(R.id.fashionshow_publish_time2);

				convertView.setTag(holder);
			} else {
				holder = (FashionShowHolder) convertView.getTag();
			}
			
			holder.img_show1.setBackgroundColor(Color.TRANSPARENT);
			holder.img_show2.setBackgroundColor(Color.TRANSPARENT);

			bitmapUtils.display(holder.img_show1,
					((HashMap<String, Object>) data.get(position).get("map1"))
							.get("img1").toString());
			bitmapUtils.display(holder.img_show2,
					((HashMap<String, Object>) data.get(position).get("map2"))
							.get("img2").toString());

			holder.designer_name1.setText(((HashMap<String, Object>) data.get(
					position).get("map1")).get("name1").toString());
			holder.designer_name2.setText(((HashMap<String, Object>) data.get(
					position).get("map2")).get("name2").toString());
			holder.cloth_name1.setText(((HashMap<String, Object>) data.get(
					position).get("map1")).get("text_part1").toString());
			holder.cloth_name2.setText(((HashMap<String, Object>) data.get(
					position).get("map2")).get("text_part2").toString());
			holder.fashionshow_publish_time1
					.setText(((HashMap<String, Object>) data.get(position).get(
							"map1")).get("date1").toString());
			holder.fashionshow_publish_time2
					.setText(((HashMap<String, Object>) data.get(position).get(
							"map2")).get("date2").toString());
			return convertView;
		}
	}
}
