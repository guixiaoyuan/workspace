package com.deeal.exchange.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deeal.exchange.R;
import com.deeal.exchange.activity.MipcaActivityCapture;
import com.deeal.exchange.application.Constant;

/**
 * Created by Administrator on 2015/7/9.
 * 标题栏
 */
public class Titlebar  {
    private Context context;
    private TextView tvTitle;
    private Button btBack;
    private Button btRight;
    private Button btScan;
    private LayoutInflater inflater;
    private RelativeLayout rlTitle;
    public  Titlebar(final Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        rlTitle = (RelativeLayout) inflater.inflate(R.layout.title_bar, null);
        this.tvTitle = (TextView) ((Activity)context).findViewById(R.id.tvTitle);

    }

    /**
     * 显示标题
     * @param title
     */
    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    /**
     * 显示返回按钮
     */
    public Button setbtback() {
        this.btBack = (Button) ((Activity) context).findViewById(R.id.btBack);
        btBack.setVisibility(View.VISIBLE);
        return this.btBack;
    }
    public void getbtback(){
        this.btBack = (Button) ((Activity)context).findViewById(R.id.btBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
        btBack.setVisibility(View.VISIBLE);
    }


    public Button getbtRight() {
        this.btRight = (Button) ((Activity) context).findViewById(R.id.btRight);
        btRight.setVisibility(View.VISIBLE);
        return this.btRight;
    }
    public Button getbtRight2() {
        this.btRight = (Button) ((Activity) context).findViewById(R.id.btRight2);
        btRight.setVisibility(View.VISIBLE);
        return this.btRight;
    }

    /**
     * 扫描二维码
     */
    public void getScan() {
        this.btScan = (Button) ((Activity) context).findViewById(R.id.btScan);
        this.btScan.setVisibility(View.VISIBLE);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, MipcaActivityCapture.class);
                ((Activity) context).startActivityForResult(intent, Constant.SCAN);
            }
        });
    }
}
