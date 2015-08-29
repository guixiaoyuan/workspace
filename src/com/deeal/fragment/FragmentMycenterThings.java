package com.deeal.fragment;

import java.util.HashMap;
import java.util.LinkedList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.deeal.activity.TalkActivity;
import com.deeal.fragment.callback.MyCenterThingsCallback;
import com.deeal.fragment.callback.MyCenterThingsCallback.MyCenterShowBaseAdapter;
import com.deeal.tools.URL;
import com.example.deeal.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

//个人中心下面的动态
public class FragmentMycenterThings extends Fragment {
	private PullToRefreshListView lv_things;
	private View item_view, fragment_view;
	private LinkedList<HashMap<String, Object>> data = new LinkedList<HashMap<String, Object>>();
	private BitmapUtils bitmapUtils;
	private MyCenterShowBaseAdapter adapter;
	// 图片的url
	private String imgUrl;
	//
	// private TextView
	// tv_id,item_time,item_username,tv_comment_content,tv_store;
	private ThingsHolder holder;
	private ImageView item_image_head, item_image_show;
	private TextView tv_id, item_time, tv_store_num, tv_prefer_num,
			tv_comment_num, item_username, tv_comment_content;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		item_view = inflater.inflate(
				R.layout.info_item, null);
		item_image_head = (ImageView) item_view.findViewById(R.id.image_head);
		tv_id = (TextView) item_view.findViewById(R.id.tv_id);
		item_image_show = (ImageView) item_view.findViewById(R.id.image_show);
		item_time = (TextView) item_view.findViewById(R.id.item_time);
		tv_store_num = (TextView) item_view.findViewById(R.id.tv_store_num);
		tv_prefer_num = (TextView) item_view.findViewById(R.id.tv_prefer_num);
		tv_comment_num = (TextView) item_view.findViewById(R.id.tv_comment_num);
		item_username = (TextView) item_view.findViewById(R.id.item_username);
		tv_comment_content = (TextView) item_view
				.findViewById(R.id.tv_comment_content);

		fragment_view = inflater.inflate(R.layout.fragment_center_things, null);
		lv_things = (PullToRefreshListView) fragment_view
				.findViewById(R.id.lv_mycenter_things);

		holder = new ThingsHolder(item_image_head, tv_id, item_image_show,
				item_time, tv_store_num, tv_prefer_num, tv_comment_num,
				item_username, tv_comment_content);
		// 实例化BitmapUtils this表示Fragment（不能用）

		//bitmapUtils = new BitmapUtils(getActivity());
		// 获取数据
		getData();

//		adapter = new MyCenterShowBaseAdapter(getActivity());
//		lv_things.setAdapter(adapter);

		lv_things.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				startTalkActivity();
			}
		});

		// 设定下拉监听函数
		this.lv_things.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				new GetDataTask().execute();
			}
		});

		return fragment_view;
	}

	// 获取数据---------这儿在本地获取的
	private void getData() {
//		LinkedList<HashMap<String, Object>> list = new LinkedList<HashMap<String, Object>>();

		this.imgUrl = "http://bbs.lidroid.com/static/image/common/logo.png";
		// 从服务器端加载数据
		/*
		 * HashMap<String, Object> map = new HashMap<String, Object>();
		 * 
		 * MyCenterThingsCallback callback=new
		 * MyCenterThingsCallback(getActivity(), map);
		 */

		// 自己添加的数据进行测试的
		// for (int i = 0; i < 10; i++) {
		//HashMap<String, Object> map = new HashMap<String, Object>();
		MyCenterThingsCallback callback = new MyCenterThingsCallback(
				getActivity(), holder, data,lv_things);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, URL.URL_GetShowList, callback);
		/*
		 * map.put("tv_id", i); map.put("item_time",
		 * System.currentTimeMillis()); map.put("tv_store", i + 1);
		 * map.put("tv_prefer", i + 2); map.put("tv_comment", i + 3);
		 * map.put("item_username", "things" + i); map.put("tv_comment_content",
		 * "ohohohohoh" + "~~~~");
		 */

	/*	map.put("tv_prefer", 22);
		map.put("tv_comment", 33);
		list.add(map);*/
		// }
		
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
			map.put("tv_id", "999");
			map.put("item_time", System.currentTimeMillis());
			map.put("tv_store", "111");
			map.put("tv_prefer", "222");
			map.put("tv_comment", "333");
			map.put("item_username", "anthow");
			map.put("tv_comment_content", "adadadadad");
			return map;
		}

		// 这里是对刷新的响应，可以利用addFirst()和addLast()函数将新加的内容加到ListView中
		// 根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			// 在头部增加新添内容

			data.addFirst(result);

			// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			adapter.notifyDataSetChanged();
			lv_things.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	// 跳转到到别的activity
	private void startTalkActivity() {
		Intent i = new Intent();
		i.setClass(getActivity(), TalkActivity.class);
		startActivity(i);
	}

/*	// 自定义的一个BaseAdapter
	private class MyCenterShowBaseAdapter extends BaseAdapter {
		private Context mContext;
		private final LayoutInflater mInflater;

		// 构造函数
		public MyCenterShowBaseAdapter(Context context) {
			super();
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);

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
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// ThingsHolder holder = null;
			if (convertView == null) {
				convertView = item_view;
				// convertView = mInflater.inflate(R.layout.info_item, null);
				// holder = new ThingsHolder();
				// ViewUtils.inject(holder, convertView);
				holder.image_head = item_image_head;
				holder.image_show = item_image_show;
				holder.item_time = item_time;
				holder.item_username = item_username;
				holder.tv_comment = tv_comment_num;
				holder.tv_comment_content = tv_comment_content;
				holder.tv_id = tv_id;
				holder.tv_prefer = tv_prefer_num;
				holder.tv_store = tv_store_num;
				convertView.setTag(holder);
			} else {
				holder = (ThingsHolder) convertView.getTag();
			}
			// 图片处理
			holder.image_head.setBackgroundColor(Color.TRANSPARENT);
			holder.image_show.setBackgroundColor(Color.TRANSPARENT);

			bitmapUtils.display(holder.image_head, imgUrl);
			bitmapUtils.display(holder.image_show, imgUrl);
			// 其他内容
			
			 * holder.tv_id.setText(data.get(position).get("tv_id").toString());
			 * holder.item_time.setText(data.get(position).get("item_time")
			 * .toString());
			 * holder.tv_store.setText(data.get(position).get("tv_store")
			 * .toString());
			 
			holder.tv_prefer.setText(data.get(position).get("tv_prefer")
					.toString());
			holder.tv_comment.setText(data.get(position).get("tv_comment")
					.toString());
			
			 * holder.item_username.setText(data.get(position)
			 * .get("item_username").toString());
			 * holder.tv_comment_content.setText(data.get(position)
			 * .get("tv_comment_content").toString());
			 
			return convertView;
		}
	}
*/
	// 定义一个holder
	public class ThingsHolder {
		public ThingsHolder(ImageView image_head, TextView tv_id,
				ImageView image_show, TextView item_time, TextView tv_store,
				TextView tv_prefer, TextView tv_comment,
				TextView item_username, TextView tv_comment_content
				) {
			super();
			this.image_head = image_head;
			this.tv_id = tv_id;
			this.image_show = image_show;
			this.item_time = item_time;
			this.tv_store = tv_store;
			this.tv_prefer = tv_prefer;
			this.tv_comment = tv_comment;
			this.item_username = item_username;
			this.tv_comment_content = tv_comment_content;
		}

		/*
		 * // 头像
		 * 
		 * @ViewInject(R.id.image_head)
		 */
		public ImageView image_head;
		/*
		 * // id
		 * 
		 * @ViewInject(R.id.tv_id)
		 */
		public TextView tv_id;
		/*
		 * // 显示动态的图片
		 * 
		 * @ViewInject(R.id.image_show)
		 */
		public ImageView image_show;
		/*
		 * // 发布的时间
		 * 
		 * @ViewInject(R.id.item_time)
		 */
		public TextView item_time;
		/*
		 * // 收藏的数量
		 * 
		 * @ViewInject(R.id.tv_store_num)
		 */
		public TextView tv_store;
		/*
		 * // 喜欢的数量
		 * 
		 * @ViewInject(R.id.tv_prefer_num)
		 */
		public TextView tv_prefer;
		/*
		 * // 评论的数量
		 * 
		 * @ViewInject(R.id.tv_comment_num)
		 */
		public TextView tv_comment;
		/*
		 * // 评论者的姓名
		 * 
		 * @ViewInject(R.id.item_username)
		 */
		public TextView item_username;
		/*
		 * // 评论的内容
		 * 
		 * @ViewInject(R.id.tv_comment_content)
		 */
		public TextView tv_comment_content;

		
	}
}
