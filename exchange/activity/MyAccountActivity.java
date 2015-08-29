package com.deeal.exchange.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MyAccountActivity extends Activity {

    /*
    * 充值按钮
    */
    private Button btRecharge;
    /*
    * 提现到支付宝按钮
    */
    private Button btWithDraw;
    /*
    * 余额
    */
    private TextView tvBalance;


    private final String ZERO_ACCOUNT = "￥ 0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        setTitleBar();
        getID();
        getAccount();
       // Slidr.attach(this);
    }

    private void getAccount() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.getAccountPATH, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject json = new JSONObject(responseInfo.result);
                    if (json.getString("status").equals("SUCCESS")) {
                        tvBalance.setText(json.getString("data"));
                    } else {
                        tvBalance.setText(ZERO_ACCOUNT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(MyAccountActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*设置titleBar*/
    private void setTitleBar() {
        Titlebar titlebar = new Titlebar(this);
        titlebar.setTitle(getString(R.string.MyAccount));
        titlebar.getbtback();
    }

    /*绑定控件ID*/

    private void getID() {
        btRecharge = (Button) findViewById(R.id.btRecharge);
        btWithDraw = (Button) findViewById(R.id.btWithDraw);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
    }


    /*
        实现充值按钮方法
        */
    protected void setBtRecharge() {

        btRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    /*
        实现提现按钮方法
        */
    protected void setBtWithDraw() {

        btWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}


