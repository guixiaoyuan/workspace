package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.MySoldItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.LogisticsInfoListAdapter;
import com.deeal.exchange.adapter.MySoldListAdapter;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class MySoldActivity extends Activity {
    private ListView listView;
    private MySoldListAdapter mySoldListAdapter;

    private MySoldItemBean item;
    private ArrayList<MySoldItemBean> items = new ArrayList<MySoldItemBean>();
    private LoadingView loadingView;
    private TextView tvtitle;
    private Button btBack;
    // private BitmapUtils bitmapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bitmapUtils = BitmapHelper.getBitmapUtils(getApplicationContext());
        //bitmapUtils.display(Imgview,String);
        setContentView(R.layout.activity_my_sold);

        inittitle();
        //Slidr.attach(this);
        listView = (ListView) this.findViewById(R.id.lv_my_sold);
        loadingView = (LoadingView) findViewById(R.id.loadView);
        get_json_data();


    }

    private void inittitle() {
        tvtitle = (TextView) MySoldActivity.this.findViewById(R.id.tvTitle);
        btBack = (Button) MySoldActivity.this.findViewById(R.id.btBack);
        btBack.setVisibility(View.VISIBLE);
        tvtitle.setText("我卖出的");
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取并解析get_sold_list()接口里面的json信息,并把解析出来的数据放到数据数据适配器中
     */
    public void get_json_data() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.MySoldPath, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                loadingView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loadingView.setVisibility(View.GONE);
                item = new MySoldItemBean();
                Log.e("fsegsgsgs",responseInfo.result);
                items = item.parseSearchjson(responseInfo.result);
                if (items.size() == 0){
                    findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.nothing).setVisibility(View.GONE);
                }

                mySoldListAdapter = new MySoldListAdapter(MySoldActivity.this, items);

                listView.setAdapter(mySoldListAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingView.setVisibility(View.GONE);
                findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                Toast.makeText(MySoldActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == CourierServeActivity.REQUSET && resultCode == RESULT_OK) {
            initlist();
        }*/
        //填写单号回调后刷新界面
        if(requestCode == Constant.WRITENUMBER && resultCode == RESULT_OK){
            get_json_data();
        }
    }
}
