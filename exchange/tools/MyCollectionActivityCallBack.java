package com.deeal.exchange.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.CommodityDetailActivity;
import com.deeal.exchange.adapter.CollectionListAdapter;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sunqiyong on 2015/7/18.
 */
public class MyCollectionActivityCallBack extends RequestCallBack<String> {
    private LoadingView loadingView;
    private Activity activity;
    private ListView listView;
    private ArrayList<CommodityListItemBean> items;
    private CommodityListItemBean item;
    private CollectionListAdapter adapter;


    public MyCollectionActivityCallBack(Activity activity, ListView listView) {
        this.activity = activity;
        this.listView = listView;
        this.loadingView =  (LoadingView)activity.findViewById(R.id.loadView);

    }
    @Override
    public void onStart() {
        loadingView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        loadingView.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(responseInfo.result);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                items = new ArrayList<CommodityListItemBean>();
                JSONArray dataArray = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < dataArray.length(); i++) {
                    item = new CommodityListItemBean();
                    JSONObject data = dataArray.getJSONObject(i);
                    item.setUsername(data.getString("userName"));
                    item.setUserheadurl(data.getString("portraitPath"));
                    item.setCost(data.getString("currentPrice"));
                    item.setInfo(data.getString("info"));
                    item.setUserid(data.getString("userID"));
                    item.setFavoriteID(data.getString("favoriteID"));
                    item.setMerchandiseID(data.getString("merchandiseID"));
                    JSONArray imvArray = new JSONArray(data.getString("path"));
                    ArrayList<String> picurls = new ArrayList<String>();
                    for (int j = 0; j < imvArray.length(); j++) {
                        picurls.add(imvArray.getString(j));
                    }
                    item.setPicurls(picurls);
                    items.add(item);
                }
                if (items.size() ==0){
                    activity.findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                }else {
                    activity.findViewById(R.id.nothing).setVisibility(View.GONE);
                }
                adapter = new CollectionListAdapter(activity, items);
                listView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CollectionListAdapter adapter = new CollectionListAdapter(activity, items);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("merchandiseID",items.get(position).getMerchandiseID());
                intent.setClass(activity, CommodityDetailActivity.class);
                activity.startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }
    @Override
    public void onFailure(HttpException e, String s) {
        loadingView.setVisibility(View.GONE);
        activity.findViewById(R.id.nothing).setVisibility(View.VISIBLE);
        Toast.makeText(activity, "网络连接失败！", Toast.LENGTH_SHORT).show();
    }
}
