package com.deeal.exchange.activity;

import android.app.Activity;
import android.os.Bundle;

import com.deeal.exchange.R;
import com.deeal.exchange.view.Titlebar;

public class AbortActivity extends Activity {

    private Titlebar mTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abort);
        initTitle();
    }

    private void initTitle() {
        mTitlebar = new Titlebar(this);
        mTitlebar.setTitle("关于");
        mTitlebar.getbtback();
    }

}
