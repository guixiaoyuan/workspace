package com.deeal.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deeal.activity.MaterialActivity;
import com.example.deeal.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class HomePageManager {
	private ArrayList<String> alWomen;
	private ArrayList<String> alMen;
	private Activity activity;
	private List<ListView> listViews;

	private final String WOMEN = "women";
	private final String MEN = "men";
	private final String DATA = "data";
	private final String STATUS = "status";
	private final String TYPE = "type";

	public static int MALE_TYPE = 0;
	public static int FEMALE_TYPE = 1;

	public HomePageManager(List<ListView> listViews, Activity activity) {
		// TODO Auto-generated constructor stub
		// String[] types = { "T恤", "衬衫", "大衣", "外套", "卫衣", "毛衣", "羽绒服" };
		this.activity = activity;
		this.listViews = listViews;
	}

	public void updateData() {
		RequestParams params = new RequestParams();

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, URL.URL_CLOTHSE_TYPE, params,
				new RequestCallBack() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo result) {
						// TODO Auto-generated method stub
						String json = result.result.toString();
						System.out.println(json);
						ClothseParser parser = new ClothseParser(json);
						HashMap<String, ArrayList<String>> hm = parser
								.getClothseData();
						alMen = hm.get(MEN);
						alWomen = hm.get(WOMEN);
						ArrayList<HashMap<String, Object>> dataW = new ArrayList<HashMap<String, Object>>();
						ArrayList<HashMap<String, Object>> dataM = new ArrayList<HashMap<String, Object>>();

						for (int i = 0; i < alWomen.size(); i++) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put(TYPE, alWomen.get(i));
							dataW.add(map);
						}

						for (int i = 0; i < alMen.size(); i++) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put(TYPE, alMen.get(i));
							dataM.add(map);
						}

						String[] from = { TYPE };
						int[] to = { R.id.type };
						ListAdapter adapterW = new SimpleAdapter(activity,
								dataW, R.layout.main_list_item, from, to);
						listViews.get(0).setAdapter(adapterW);

						ListAdapter adapterM = new SimpleAdapter(activity,
								dataM, R.layout.main_list_item, from, to);
						listViews.get(1).setAdapter(adapterM);

						listViews.get(0).setOnItemClickListener(
								new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// TODO Auto-generated method stu

										startOrderActivity(MALE_TYPE);
									}
								});
						
						listViews.get(1).setOnItemClickListener(
								new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// TODO Auto-generated method stub
										startOrderActivity(FEMALE_TYPE);
									}
								}
								);
					}
				});

	}

	private void startOrderActivity(int type) {
		Intent intent = new Intent();
		intent.setClass(activity, MaterialActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("cloth_type", type);
		intent.putExtras(bundle);
		activity.startActivity(intent);
	}

	class ClothseParser {
		private String json;

		public ClothseParser(String json) {
			this.json = json;
		}

		public HashMap<String, ArrayList<String>> getClothseData() {
			HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();

			try {
				ArrayList<String> al = new ArrayList<String>();
				JSONObject object = new JSONObject(this.json);
				String status = object.getString(STATUS);

				JSONObject jdata = object.getJSONObject(DATA);
				JSONArray jarrayMen = jdata.getJSONArray(MEN);
				JSONArray jarrayWomen = jdata.getJSONArray(WOMEN);

				result.put(WOMEN, this.getArrayListFromJson(jarrayWomen));
				result.put(MEN, this.getArrayListFromJson(jarrayMen));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		private ArrayList<String> getArrayListFromJson(JSONArray jarray) {
			ArrayList<String> al = new ArrayList<String>();
			try {
				for (int i = 0; i < jarray.length(); i++) {
					al.add(jarray.getString(i));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return al;

		}
	}
}
