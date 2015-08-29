package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.deeal.exchange.Bean.CourierListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.CourierListAdapter;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.CourierJson;
import com.deeal.exchange.tools.onPaySuccess;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class CourierServeActivity extends Activity {
    private ArrayList<CourierListItemBean> mitem = new ArrayList<CourierListItemBean>();

    /*
    * 定义listview
    * */
    private ListView mlistView;
    /*
    * 定义联系客服按钮、
    * */
    private Button btContact;
    /*
    * 定义支付按钮
    * */
    private Button btPay;
    /*
    * 定义删除按钮
    * */
    private Button btDelete;

    private LoadingView loadingView;
    private String servicenumber;
    private String expresscompany;
    private String expressnumber;
    private String issuer;
    public static final int REQUSET = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_serve);
        loadingView = (LoadingView) findViewById(R.id.loadView);
        setTitleBar();
        getID();
        initlist();
        setMlistView();
    }


    /*设置titleBar*/
    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.courierserve));
        titlebar.getbtback();
    }
    /*绑定控件ID*/
    private void getID() {
        mlistView = (ListView) findViewById(R.id.lvCourierServe);
    }

    /*listview的设置*/
    private void initlist() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        uploadMethod(params, Path.searchexpress, CourierServeActivity.this);
    }


    /*listview响应方法*/
    protected void setMlistView() {

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("tag", mitem.get(position).getTag());
                intent.putExtra("expressID", mitem.get(position).getExpressID());
                intent.setClass(CourierServeActivity.this, ServiceInformationActivity.class);
                startActivity(intent);
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
                mitem = CourierJson.parseCourierSearchJson(responseInfo.result);
                if (mitem.size() == 0){
                    ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                } else {
                    ((Activity) context).findViewById(R.id.nothing).setVisibility(View.GONE);
                }
                Log.e("GYGKUGUIG",responseInfo.result);
                CourierListAdapter mAdapter = new CourierListAdapter(CourierServeActivity.this, mitem, new onPaySuccess() {
                    @Override
                    public void initList() {
                        initlist();
                    }
                });  //得到一个mAdapter对象
                mlistView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingView.setVisibility(View.GONE);
                ((Activity) context).findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                Toast.makeText(CourierServeActivity.this, "无网络连接", Toast.LENGTH_LONG).show();
                e.printStackTrace();
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
        /*if (requestCode == CourierServeActivity.REQUSET && resultCode == RESULT_OK) {
            initlist();
        }*/
        //填写单号回调后刷新界面
        if(requestCode == Constant.WRITENUMBER && resultCode == RESULT_OK){
            initlist();
        }
    }



}
