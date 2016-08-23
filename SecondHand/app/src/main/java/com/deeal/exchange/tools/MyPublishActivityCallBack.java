package com.deeal.exchange.tools;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.MyPublishListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.MyPublishListAdapter;
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
 * Created by Sunqiyong on 2015/7/24.
 */
public class MyPublishActivityCallBack extends RequestCallBack<String> {

    private Activity activity;
    private ListView listView;
    private ArrayList<MyPublishListItemBean> items;
    private MyPublishListAdapter adapter;
    private LoadingView loadingView;

    public MyPublishActivityCallBack(Activity activity, ListView listView,LoadingView loadingView) {
        this.activity = activity;
        this.listView = listView;
        this.loadingView = loadingView;
    }

    private String stringOperation(String str){
        if(null == str || "" == str){
            return "商品未描述";
        }else
            return str;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        loadingView.setVisibility(View.GONE);
        Log.i("publishCallBack",responseInfo.result);
        try {
            items = new ArrayList<MyPublishListItemBean>();
            JSONObject jsonObject = new JSONObject(responseInfo.result);
            if (jsonObject.getString("status").equals("SUCCESS")) {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < jsonArray.length() - 1; i++) {
                    MyPublishListItemBean item = new MyPublishListItemBean();
                    JSONObject data = jsonArray.getJSONObject(i);
                    item.setTv_goods_name(stringOperation(data.getString("info")));
                    String Price = "￥"+data.getString("currentPrice");
                    item.setTv_goods_price(Price);
                    String time = data.getString("publishedTime").substring(0,10);
                    item.setTv_date(time);
                    String visitcount = data.getString("visitedCount")+" 次浏览";
                    item.setTv_mycollect(visitcount);
                    item.setMerchandiseId(data.getString("merchandiseID"));
                    JSONArray dataarray = data.getJSONArray("imgList");
                    ArrayList<String> imapath = new ArrayList<String>();
                    for (int j = 0; j < dataarray.length(); j++) {
                        imapath.add(dataarray.getString(j));
                    }
                    item.setImageurls(imapath);
                    items.add(item);
                }
                if (items.size() ==0){
                    activity.findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                }else{
                    activity.findViewById(R.id.nothing).setVisibility(View.GONE);
                }
                adapter = new MyPublishListAdapter(items,activity);
                listView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(HttpException e, String s) {
        loadingView.setVisibility(View.GONE);
        activity.findViewById(R.id.nothing).setVisibility(View.VISIBLE);
    }
}
