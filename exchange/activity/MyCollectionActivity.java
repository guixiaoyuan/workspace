package com.deeal.exchange.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.MyCollectionActivityCallBack;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;


public class MyCollectionActivity extends Activity {
    private LoadingView loadingView;
    private ListView lvCollection;
    private Titlebar titlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        //Slidr.attach(this);
        inittitle();
        parseSearchJson();
    }

    private void inittitle() {
        titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.mycollection));
        titlebar.getbtback();
    }

    public void parseSearchJson() {
        lvCollection = (ListView) findViewById(R.id.lvCollection);
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.search_favorite, params, new MyCollectionActivityCallBack(MyCollectionActivity.this, lvCollection));
    }

}
