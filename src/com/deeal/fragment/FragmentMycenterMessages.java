package com.deeal.fragment;

import java.util.HashMap;
import java.util.LinkedList;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.deeal.fragment.callback.MyCenterMessagesCallback;
import com.deeal.fragment.callback.MyCenterThingsCallback.MyCenterShowBaseAdapter;
import com.deeal.tools.URL;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

//个人中心下面的消息
public class FragmentMycenterMessages extends Fragment {
	private PullToRefreshListView lv_messages;
	private View item_view, fragment_view;
	private LinkedList<HashMap<String, Object>> data = new LinkedList<HashMap<String, Object>>();
	private MessageHolder holder;
	private ImageView image_head,image_show;
	private TextView tv_my_id,tv_comment_content,tv_my_show_id,tv_my_show;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		item_view=inflater.inflate(R.layout.info_replay_item, null);
		image_head=(ImageView) item_view.findViewById(R.id.image_head);
		image_show=(ImageView) item_view.findViewById(R.id.image_show);
		tv_my_id=(TextView) item_view.findViewById(R.id.tv_my_id);
		tv_comment_content=(TextView) item_view.findViewById(R.id.tv_comment_content);
		tv_my_show_id=(TextView) item_view.findViewById(R.id.tv_my_show_id);
		tv_my_show=(TextView) item_view.findViewById(R.id.tv_my_show);
		

		fragment_view=inflater.inflate(R.layout.fragment_center_messages, null);
		lv_messages = (PullToRefreshListView) fragment_view
				.findViewById(R.id.lv_mycenter_message);
		
		holder=new MessageHolder(image_head, tv_my_id, tv_comment_content, image_show, tv_my_show_id, tv_my_show);
		
		getData();

		// 设定下拉监听函数
		this.lv_messages
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						new GetDataTask().execute();
					}
				});

		return fragment_view;
	}

	// 获取数据---------这儿在本地获取的
	private void getData() {
		MyCenterMessagesCallback callback=new MyCenterMessagesCallback(getActivity(), holder, data, lv_messages);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, URL.URL_GetTalkList, callback);
	}

	// 下拉刷新数据
	private class GetDataTask extends
			AsyncTask<Void, Void, HashMap<String, Object>> {

		// 后台处理部分
		@Override
		protected HashMap<String, Object> doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = new HashMap<String, Object>();
			map.put("tv_my_id", "999");
			map.put("tv_comment_content", "aaaaaaa" + "~~~~");
			map.put("tv_my_show_id", "111");
			map.put("tv_my_show", "shenmegui");
			return map;
		}

		// 这里是对刷新的响应，可以利用addFirst()和addLast()函数将新加的内容加到ListView中
		// 根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			// 在头部增加新添内容

			data.addFirst(result);

			// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			//adapter.notifyDataSetChanged();
			lv_messages.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	// 定义一个holder
	public class MessageHolder {
		public MessageHolder(ImageView image_head, TextView tv_my_id,
				TextView tv_comment_content, ImageView image_show,
				TextView tv_my_show_id, TextView tv_my_show) {
			this.image_head=image_head;
			this.tv_my_id=tv_my_id;
			this.tv_comment_content=tv_comment_content;
			this.image_show=image_show;
			this.tv_my_show_id=tv_my_show_id;
			this.tv_my_show=tv_my_show;
		}
		/*
		 * // 回复人的头像
		 * 
		 * @ViewInject(R.id.image_head)
		 */
		public ImageView image_head;
		/*
		 * // 回复人的id
		 * 
		 * @ViewInject(R.id.tv_my_id)
		 */
		public TextView tv_my_id;
		/*
		 * // 评论的内容
		 * 
		 * @ViewInject(R.id.tv_comment_content)
		 */
		public TextView tv_comment_content;
		/*
		 * // 评论的图片
		 * 
		 * @ViewInject(R.id.image_show)
		 */
		public ImageView image_show;
		/*
		 * // 自己的id
		 * 
		 * @ViewInject(R.id.tv_my_show_id)
		 */
		public TextView tv_my_show_id;
		/*
		 * // 衣服的名称
		 * 
		 * @ViewInject(R.id.tv_my_show)
		 */
		public TextView tv_my_show;
	}

}
