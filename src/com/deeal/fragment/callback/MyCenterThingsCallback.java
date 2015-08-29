package com.deeal.fragment.callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.fragment.FragmentMycenterThings.ThingsHolder;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class MyCenterThingsCallback extends RequestCallBack {
	private Activity activity;
	private LinkedList<HashMap<String, Object>> data;
	private ThingsHolder holder;
	private MyCenterShowBaseAdapter adapter;
	private PullToRefreshListView lv_things;
	private List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();

	public MyCenterThingsCallback(Activity activity, ThingsHolder holder,
			LinkedList<HashMap<String, Object>> data,
			PullToRefreshListView lv_things) {
		this.activity = activity;
		this.holder = holder;
		this.data = data;
		this.lv_things = lv_things;
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
			// 将返回的结果解析
			JSONObject allJson = new JSONObject(string);
			// 得到状态
			String result = allJson.getString("status");
			// 成功就接着解析
			if (result.equals("success")) {
				// 获取data 是一个json数组
				JSONArray dataArray = allJson.getJSONArray("data");
				int count = dataArray.length();
				for (int i = 0; i < count; i++) {
					Map<String, Object> thingsMap = new HashMap<String, Object>();
					JSONObject dataObject = dataArray.getJSONObject(i);
					// 解析这个对象
					// author 也是一个json对象
					JSONObject authorObject = dataObject
							.getJSONObject("author");
					// author:id, img, name,pinfo(个人简介)
					int id = authorObject.getInt("id");
					String img = authorObject.getString("img");
					String name = authorObject.getString("name");
					String pinfo = authorObject.getString("pinfo");

					// cover封面
					String cover = dataObject.getString("cover");
					// java.sql.Date 先用String类型测试
					String date = dataObject.getString("date");
					// is_fine加精
					boolean is_fine = dataObject.getBoolean("is_fine");
					// is_top置顶
					boolean is_top = dataObject.getBoolean("is_top");
					// show_id Fashion Show ID
					int show_id = dataObject.getInt("show_id");
					// text_part 部分文字
					String text_part = dataObject.getString("text_part");
					// time 时间 java.sql.Time 先用String类型测试
					String time = dataObject.getString("time");
					// vote点赞数
					int vote = dataObject.getInt("vote");

					// holder.tv_id.setText(show_id + "");
					// holder.item_time.setText(date + " " + time);
					// holder.item_username.setText(name);
					// holder.tv_comment_content.setText(text_part);
					// holder.tv_store.setText(vote + "");

					thingsMap.put("tv_id", id);
					thingsMap.put("item_time", date + time);
					thingsMap.put("item_username", name);
					thingsMap.put("tv_comment_content", text_part);
					thingsMap.put("tv_store", vote);
					thingsMap.put("tv_prefer", 22);
					thingsMap.put("tv_comment", 33);

					data.add((HashMap<String, Object>) thingsMap);
					/*
					 * System.out.println("tv_id:" + show_id + "  item_time:" +
					 * date + " " + time + "  item_username:" + name +
					 * "  tv_comment_content" + text_part + "  tv_store" +
					 * vote);
					 */
				}

			} else {
				Toast.makeText(activity, "获取MyCenterThingsCallback信息出错",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~~~~~~json err~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}
		adapter = new MyCenterShowBaseAdapter(activity, data, holder);
		lv_things.setAdapter(adapter);
	}

	private void doGetDataFailure() {
		Toast.makeText(this.activity, "getInfo err", Toast.LENGTH_SHORT).show();
	}

	// 自定义的一个BaseAdapter
	public class MyCenterShowBaseAdapter extends BaseAdapter {
		private Activity activity;
		private final LayoutInflater mInflater;
		private LinkedList<HashMap<String, Object>> data;
		private ThingsHolder holder;

		// 构造函数
		public MyCenterShowBaseAdapter(Activity activity,
				LinkedList<HashMap<String, Object>> data, ThingsHolder holder) {
			super();
			this.activity = activity;
			this.mInflater = LayoutInflater.from(activity);
			this.data = data;
			this.holder = holder;
		}

		@Override
		public int getCount() {
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
			// ThingsHolder holder = null;
			Log.i("1111111111111111111111111", "aaaaaaaaaaaaaaaaaaa");
			if (convertView == null) {
				// convertView=item_view;
				convertView = LayoutInflater.from(activity).inflate(
						R.layout.info_item, null);
				// holder = new ThingsHolder();
				// ViewUtils.inject(holder, convertView);
				holder.image_head = (ImageView) convertView
						.findViewById(R.id.image_head);
				holder.image_show = (ImageView) convertView
						.findViewById(R.id.image_show);
				holder.item_time = (TextView) convertView
						.findViewById(R.id.item_time);
				holder.item_username = (TextView) convertView
						.findViewById(R.id.item_username);
				holder.tv_comment = (TextView) convertView
						.findViewById(R.id.tv_comment_num);
				holder.tv_comment_content = (TextView) convertView
						.findViewById(R.id.tv_comment_content);
				holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
				holder.tv_prefer = (TextView) convertView
						.findViewById(R.id.tv_prefer_num);
				holder.tv_store = (TextView) convertView
						.findViewById(R.id.tv_store_num);

				convertView.setTag(holder);
			} else {
				holder = (ThingsHolder) convertView.getTag();
			}

			Log.i("22222222222222222222222", "bbbbbbbbbbbbbbbbbbb");
			BitmapUtils bitmapUtils = new BitmapUtils(activity);
			// 图片处理
			holder.image_head.setBackgroundColor(Color.TRANSPARENT);
			holder.image_show.setBackgroundColor(Color.TRANSPARENT);
			String imgUrl = "http://bbs.lidroid.com/static/image/common/logo.png";
			bitmapUtils.display(holder.image_head, imgUrl);
			bitmapUtils.display(holder.image_show, imgUrl);

			// 其他内容

			holder.tv_id.setText(data.get(position).get("tv_id").toString());
			holder.item_time.setText(data.get(position).get("item_time")
					.toString());
			holder.tv_store.setText(data.get(position).get("tv_store")
					.toString());
			holder.tv_prefer.setText(data.get(position).get("tv_prefer")
					.toString());
			holder.tv_comment.setText(data.get(position).get("tv_comment")
					.toString());
			holder.item_username.setText(data.get(position)
					.get("item_username").toString());
			holder.tv_comment_content.setText(data.get(position)
					.get("tv_comment_content").toString());

			return convertView;
		}

	}

}
