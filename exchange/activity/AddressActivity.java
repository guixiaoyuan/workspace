package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.deeal.exchange.Bean.AddressListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.AddressListAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.Address;
import com.deeal.exchange.tools.AddressJson;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.deeal.exchange.view.swipemenulistview.SwipeMenu;
import com.deeal.exchange.view.swipemenulistview.SwipeMenuCreator;
import com.deeal.exchange.view.swipemenulistview.SwipeMenuItem;
import com.deeal.exchange.view.swipemenulistview.SwipeMenuListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weixianbin on 2015/7/7.
 */

public class AddressActivity extends Activity {
    private List<ApplicationInfo> mAppList;
    private ArrayList<AddressListItemBean> mitem = new ArrayList<AddressListItemBean>();
    AddressListAdapter mAdapter;
    private String addressID;
    /*
    * 定义ListView
    * */
    private SwipeMenuListView mListView;
    private LoadingView loadingView;

    public static final int REQUSET = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        loadingView = (LoadingView) findViewById(R.id.loadView);
        setSimpleListView();
        setTitleBar();
        setMlistView();
        initlist();
    }


    /*设置titleBar*/
    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(AddressActivity.this);
        titlebar.setTitle(getString(R.string.DeliveryAddress));
        titlebar.getbtback();
        titlebar.getbtRight2().setBackgroundResource(R.drawable.release_demand);
        titlebar.getbtRight2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
                intent.putExtra("add", "1");
                startActivityForResult(intent, REQUSET);
            }
        });
    }

    /*
    * listview的设置
    *
    * */
    private void initlist() {

        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        uploadMethod(params, Path.searchaddresshost, AddressActivity.this);

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


    /*listvie响应方法*/
    protected void setMlistView() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = getIntent();
                Boolean isFromBuyActivity = i.getBooleanExtra("Buyactivity", false);
                if (isFromBuyActivity) {
                    Intent intent = new Intent();
                    intent.putExtra("addressList", mitem.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addressList", mitem.get(position));
                    intent.setClass(AddressActivity.this, EditAddressActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUSET);
                }

            }
        });
    }


    /*用xutils从服务器获得信息*/
    public void uploadMethod(RequestParams params, final String uploadHost, final Context context) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                loadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loadingView.setVisibility(View.GONE);
                mitem = AddressJson.parseSearchJson(responseInfo.result);
                if (mitem.size() == 0) {
                    ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                } else {
                    ((Activity) context).findViewById(R.id.nothing).setVisibility(View.GONE);
                }
                mAdapter = new AddressListAdapter(AddressActivity.this, mitem);  //得到一个mAdapter对象
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingView.setVisibility(View.GONE);
                ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                Toast.makeText(context, "对不起服务器开小差了", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }

    /*实现侧划删除*/
    public void setSimpleListView() {
        mAppList = getPackageManager().getInstalledApplications(0);//获取数据
        mListView = (SwipeMenuListView) findViewById(R.id.lvAddress);
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                    case 1:
                        // delete
                        addressID = mitem.get(position).getAddressID();
                        delete(item, position);
                        break;
                }
                return false;
            }
        });

        // set SwipeListener

    }


    private void delete(ApplicationInfo item, int position) {
        mAdapter.remove(position);
        // delete app
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("addressID", addressID);
        upMethod(params, Path.deleteaddress);
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    /*数据上传方法*/
    public void upMethod(RequestParams params, final String uploadHost) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    if (jsonObject.getString("status").equals("SUCCESS")) {
                        Toast.makeText(AddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddressActivity.this, "你的地址已在为结束的订单中使用，不能删除！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(AddressActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

    }

}
