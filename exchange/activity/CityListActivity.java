package com.deeal.exchange.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CityListAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.CityListJson;
import com.deeal.exchange.view.Titlebar;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/29.
 */
public class CityListActivity extends Activity {
    CityListAdapter mAdapter;
    private ListView mCityList;
    private ArrayList<HashMap<String,String>> cities = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city);
        mCityList = (ListView) findViewById(R.id.lvCity);

        setTitleBar();
        RequestParams params = new RequestParams();
        uploadMethod(params, Path.getcity);
    }

    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(CityListActivity.this);
        titlebar.setTitle(getString(R.string.citylist));
        titlebar.getbtback();
    }

    /*
    * 得到数据
    * */
    public void uploadMethod(RequestParams params, final String uploadHost) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                cities = CityListJson.parseJson(responseInfo.result);
                cities.remove(0);
                mAdapter = new CityListAdapter(CityListActivity.this, cities,"0");  //得到一个mAdapter对象
                mCityList.setAdapter(mAdapter);
                mCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyApplication.currentCity.setCityID(cities.get(position).get("cityID"));
                        MyApplication.currentCity.setCityName(cities.get(position).get("cityName"));
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CityListActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }

}
