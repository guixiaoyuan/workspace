package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.deeal.exchange.R;


/**
 * Created by Sunqiyong on 2015/7/10.
 */
public class GuideActivity extends Activity implements View.OnClickListener {

    private Button bt_login;
    private Button bt_register;
    private TextView tv_taste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        bt_login = (Button) this.findViewById(R.id.bt_guide_login);
        bt_register = (Button) this.findViewById(R.id.bt_guide_register);
        tv_taste = (TextView) this.findViewById(R.id.tv_guide_text_taste);

        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        tv_taste.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_guide_login:
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                intent.putExtra("guide", "guide_login");
                startActivity(intent);
                finish();
                break;
            case R.id.bt_guide_register:
                Intent intent1 = new Intent(GuideActivity.this, RegisterActivity.class);
                intent1.putExtra("guide", "guide_register");
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_guide_text_taste:
                Intent intent2 = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }


}
