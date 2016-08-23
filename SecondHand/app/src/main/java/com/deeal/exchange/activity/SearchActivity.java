package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.deeal.exchange.R;
import com.deeal.exchange.adapter.SearchResultAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.MerchandiseInfo;
import com.deeal.exchange.tools.MerchandiseListUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private EditText etSearch;
    private Button btBack;
    private ListView lvSearchResult;
    private ArrayList<MerchandiseInfo> infos = new ArrayList<>();
    private SearchResultAdapter adapter;
    private TextView tvNothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initItems();
    }

    private void initItems(){
        etSearch = (EditText) this.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count != 0){
                    searchByName(s.toString(), MyApplication.mUser.getTokenId());
                }else{
                    lvSearchResult.setVisibility(View.GONE);
                    tvNothing.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btBack = (Button) this.findViewById(R.id.btBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvSearchResult = (ListView) this.findViewById(R.id.lvSearchResult);
        adapter = new SearchResultAdapter(SearchActivity.this,infos);
        lvSearchResult.setAdapter(adapter);
        lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this, CommodityDetailActivity.class);
                intent.putExtra("merchandiseID", infos.get(position).getMerchandiseID());
                startActivity(intent);
            }
        });
        tvNothing = (TextView) findViewById(R.id.tvNothing);
    }

    private void searchByName(String name , String TokenId){
        Log.e("searchresult",name);
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID",TokenId);
        params.addBodyParameter("merchandiseName",name);
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.search_info, params, new RequestCallBack<String>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("searchresult", responseInfo.result);
                ArrayList<MerchandiseInfo> infos = new ArrayList<MerchandiseInfo>();
                MerchandiseListUtils.parseMerchandiseList(infos, responseInfo.result);
                if(infos.size() == 0){
                    tvNothing.setVisibility(View.VISIBLE);
                    lvSearchResult.setVisibility(View.GONE);
                }else {
                    tvNothing.setVisibility(View.GONE);
                    lvSearchResult.setVisibility(View.VISIBLE);
                }
                adapter.addItems(infos);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("searchresult",e+s);
            }
        });

    }

}
