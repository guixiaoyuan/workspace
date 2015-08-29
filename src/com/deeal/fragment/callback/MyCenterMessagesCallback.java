package com.deeal.fragment.callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.fragment.FragmentMycenterMessages.MessageHolder;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class MyCenterMessagesCallback extends RequestCallBack {
	private Activity activity;
	private LinkedList<HashMap<String, Object>> data;
	private MessageHolder holder;
	private MyCenterShowBaseAdapter adapter;
	private PullToRefreshListView lv_things;
	

	public MyCenterMessagesCallback(Activity activity, MessageHolder holder,
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
					Map<String, Object> messagesMap = new HashMap<String, Object>();
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
					// post_id 
					int post_id = dataObject.getInt("post_id");
					// content_part 部分文字
					String content_part = dataObject.getString("content_part");
					// time 时间 java.sql.Time 先用String类型测试
					String time = dataObject.getString("time");
					//标题
					String title=dataObject.getString("title");

					messagesMap.put("tv_id", post_id);
					messagesMap.put("item_time", date + time);
					messagesMap.put("tv_comment_content", content_part);
					messagesMap.put("tv_my_show_id", post_id);
					messagesMap.put("tv_my_show", title);
					data.add((HashMap<String, Object>) messagesMap);
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
		private LinkedList<HashMap<String, Object>> data;
		private MessageHolder holder;

		// 构造函数
		public MyCenterShowBaseAdapter(Activity activity,
				LinkedList<HashMap<String, Object>> data, MessageHolder holder) {
			super();
			this.activity = activity;
			this.data = data;
			this.holder = holder;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(
						R.layout.info_replay_item, null);
				holder.image_head=(ImageView) convertView.findViewById(R.id.image_head);
				holder.image_show=(ImageView) convertView.findViewById(R.id.image_show);
				holder.tv_my_id=(TextView) convertView.findViewById(R.id.tv_my_id);
				holder.tv_comment_content=(TextView) convertView.findViewById(R.id.tv_comment_content);
				holder.tv_my_show_id=(TextView) convertView.findViewById(R.id.tv_my_show_id);
				holder.tv_my_show=(TextView) convertView.findViewById(R.id.tv_my_show);

				convertView.setTag(holder);
			} else {
				holder = (MessageHolder) convertView.getTag();
			}

			BitmapUtils bitmapUtils = new BitmapUtils(activity);
			// 图片处理
			holder.image_head.setBackgroundColor(Color.TRANSPARENT);
			holder.image_show.setBackgroundColor(Color.TRANSPARENT);
			String imgUrl = "http://bbs.lidroid.com/static/image/common/logo.png";
			bitmapUtils.display(holder.image_head, imgUrl);
			bitmapUtils.display(holder.image_show, imgUrl);

			// 其他内容

			holder.tv_my_id.setText(data.get(position).get("tv_id").toString());
			holder.tv_my_show.setText(data.get(position).get("tv_my_show")
					.toString());
			holder.tv_my_show_id.setText(data.get(position)
					.get("tv_my_show_id").toString());
			holder.tv_comment_content.setText(data.get(position)
					.get("tv_comment_content").toString());

			return convertView;
		}

	}

}
