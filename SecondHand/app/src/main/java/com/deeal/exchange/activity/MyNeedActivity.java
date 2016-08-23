package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.deeal.exchange.R;
import com.deeal.exchange.adapter.MyNeedListAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MyNeedActivity extends Activity implements Serializable {
    private ListView listView;
    ArrayList<HashMap<String, String>> mitem = new ArrayList<HashMap<String, String>>();
    public static final int REQUSET = 1;
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_need);
        loadingView = (LoadingView) findViewById(R.id.loadView);
        listView = (ListView) this.findViewById(R.id.lv_my_need);
        setTitleBar();
        initlist();
        ListItem();
       // Slidr.attach(this);
    }


    /*设置listview显示*/
    private void initlist() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        uploadNeed(params, Path.searchRequirement, MyNeedActivity.this);
    }

    /*设置titleBar*/
    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.MyNeed));
        titlebar.getbtback();
        titlebar.getbtRight2().setBackgroundResource(R.drawable.release_demand);
        titlebar.getbtRight2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNeedActivity.this, ReleaseDemandActivity.class);
                startActivityForResult(intent, REQUSET);
            }
        });
    }


    /*获取服务器信息*/
    private void uploadNeed(RequestParams params, final String uploadHost,final Context context) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                loadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loadingView.setVisibility(View.GONE);
                mitem = parseMyNeedJson(responseInfo.result);
                if (mitem.size() == 0) {
                    ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                }else {
                    ((Activity) context).findViewById(R.id.nothing).setVisibility(View.GONE);
                }
                MyNeedListAdapter myNeedListAdapter = new MyNeedListAdapter(MyNeedActivity.this, mitem);
                listView.setAdapter(myNeedListAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingView.setVisibility(View.GONE);
                ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                Toast.makeText(MyNeedActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }


    /*解析JSON数据*/
    protected ArrayList<HashMap<String, String>> parseMyNeedJson(String jsonData) {
        ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObj = new JSONObject(jsonData);
            JSONArray data = new JSONArray(jsonObj.getString("data"));
            for (int i = 0; i < data.length(); i++) {
                HashMap<String, String> item = new HashMap<String, String>();
                JSONObject json = data.getJSONObject(i);
                item.put("info", json.getString("info"));
                item.put("userID", json.getString("userID"));
                item.put("requirementID", json.getString("requirementID"));
                item.put("merchandiseType", json.getString("merchandiseType"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }


    /*设置listview的单个item的点击事件*/
    private void ListItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("NeedItem", mitem.get(position));
                intent.setClass(MyNeedActivity.this, ReleaseDemandActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUSET);
            }
        });
    }


    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddressActivity.REQUSET && resultCode == RESULT_OK) {
            initlist();
        }
    }


}
