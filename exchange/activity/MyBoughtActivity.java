package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.MyBoughtItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.MyBoughtListAdapter;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.onPaySuccess;
import com.deeal.exchange.view.loadingview.LoadingView;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class MyBoughtActivity extends Activity {

    private ListView listView;
    private MyBoughtListAdapter myBoughtListAdapter;
    private ArrayList<MyBoughtItemBean> myBoughtItemBeans;
    private LoadingView loadingView;
    private TextView tvTitle;
    private Button btBack;

    private ArrayList<MyBoughtItemBean> beans = new ArrayList<MyBoughtItemBean>();
    private MyBoughtItemBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bought);
        //Slidr.attach(this);
        inittitle();

        //得到ListView
        listView = (ListView) this.findViewById(R.id.lv_my_bought);
        loadingView = (LoadingView) findViewById(R.id.loadView);
        //从服务器获取我买的商品列表的json文件
        get_jsons_data();


    }

    private void inittitle() {
        tvTitle = (TextView) MyBoughtActivity.this.findViewById(R.id.tvTitle);
        btBack = (Button) MyBoughtActivity.this.findViewById(R.id.btBack);
        tvTitle.setText("我买到的");
        btBack.setVisibility(View.VISIBLE);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    /**
     * 获取并解析get_bought_list()接口里面的json信息,并把解析出来的数据放到数据数据适配器中
     */
    public void get_jsons_data() {

        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.MyBoughtPath, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                loadingView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loadingView.setVisibility(View.GONE);
                /**
                 * 定义一个MyBoughtItemBean类对象bean
                 */
                bean = new MyBoughtItemBean();
                /**
                 * bean里面的parseSearchjson方法返回的是一个MyBoughtItemBean对象集合，
                 * 参数1：服务器返回的json数据
                 * 参数2：当前的上下文
                 */
                beans = bean.parseSearchjson(responseInfo.result);

                if (beans.size() == 0){
                    findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.nothing).setVisibility(View.GONE);
                }
                /**
                 * 创建一个数据适配器，把MyBoughtItemBean集合放进去
                 */
                Log.e("=======98634567",responseInfo.result);
                myBoughtListAdapter = new MyBoughtListAdapter(MyBoughtActivity.this, beans,new onPaySuccess() {
                    @Override
                    public void initList() {
                        get_jsons_data();
                    }
                });

                listView.setAdapter(myBoughtListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        MyBoughtItemBean itemBean = beans.get(position);
                        String merchandiseID = itemBean.getMerchandiseID();
                        intent.putExtra("merchandiseID", merchandiseID);
                        intent.setClass(MyBoughtActivity.this,OrderDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingView.setVisibility(View.GONE);
                findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                Toast.makeText(MyBoughtActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
            }
        });
    }

}
