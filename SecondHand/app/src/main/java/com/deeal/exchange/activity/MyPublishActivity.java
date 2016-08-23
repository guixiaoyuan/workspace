package com.deeal.exchange.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.deeal.exchange.R;
import com.deeal.exchange.adapter.MyPublishListAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.MyPublishActivityCallBack;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;


public class MyPublishActivity extends Activity {

    private ListView lvMyPublish;
    private Button btnBack;
    private TextView tvTitle;
    private LoadingView loadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish);
        initTitle();
        lvMyPublish = (ListView) this.findViewById(R.id.lv_mypublish);
        loadingView = (LoadingView) this.findViewById(R.id.loadView);
        lvMyPublish.setDividerHeight(0);
        parseMyPublishJson();
        //Slidr.attach(this);
    }

    private void parseMyPublishJson() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.MypublishPath, params, new MyPublishActivityCallBack(this, lvMyPublish,loadingView));
    }

    private void initTitle() {
        btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("我发布的");
    }

}
