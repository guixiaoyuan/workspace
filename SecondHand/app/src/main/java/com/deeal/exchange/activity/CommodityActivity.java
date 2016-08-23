package com.deeal.exchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.pullview.AbPullToRefreshView;
import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CommodityListAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.fragment.ComodityListFragment;
import com.deeal.exchange.fragment.MyNeedFragment;
import com.deeal.exchange.model.Colledge;
import com.deeal.exchange.model.MerchandiseSearchOption;
import com.deeal.exchange.model.MerchandiseType;
import com.deeal.exchange.model.Sort;
import com.deeal.exchange.tools.MerchandiseListUtils;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.activity_transition.fragment.FragmentTransition;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class CommodityActivity extends AbActivity {

    Fragment myNeedFragement,comodityListFragment;
    private Button btBack;
    private Button btCommodity;
    private Button btMyNeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);
        initFragment();
        initButton();
        getFragment(1);

    }
    private void initButton(){
        btBack = (Button) findViewById(R.id.btBack);
        btCommodity = (Button) findViewById(R.id.btCommoditity);
        btMyNeed = (Button) findViewById(R.id.btMyNeed);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(1);
            }
        });
        btMyNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(2);
            }
        });
    }

    private void initFragment(){
        myNeedFragement = new MyNeedFragment();
        comodityListFragment = new ComodityListFragment();
    }
    private void getFragment(int tab){
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransition = fragmentManager.beginTransaction();

        switch (tab){
            case 1:
                fragment = comodityListFragment;
                break;
            case 2:
                fragment = myNeedFragement;
                break;
            default:
                fragment = comodityListFragment;
        }
        fragmentTransition.replace(R.id.container,fragment).commit();
    }

}
