package com.deeal.exchange.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.model.MerchandiseInfo;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.HttpSuccess;
import com.deeal.exchange.tools.MerchandiseListUtils;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.view.Titlebar;
import com.deeal.exchange.view.slidr.Slidr;
import com.lidroid.xutils.BitmapUtils;

import io.rong.imkit.fragment.ConversationFragment;

public class ChatActivity extends FragmentActivity {

    private CardView cvMerchandise;
    private Titlebar mTitlebar;
    private String title;
    private String userID;
    private String merchandiseID;
    private Button btGoPay;
    private ImageView img_merchandise;
    private TextView tv_trade_name;
    private TextView tv_goods_fee;
    private TextView tv_seller_adress;
    private TextView tv_transport_fee;
    private TextView tv_flag;
    private Boolean flag = true;
    private RelativeLayout rl_merchandiseInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getChatInfo();
        inittitle();
    }


    private void initMerchandiseView(final MerchandiseInfo info) {
        rl_merchandiseInfo = (RelativeLayout) findViewById(R.id.rl_merchandiseInfo);
        cvMerchandise = (CardView) findViewById(R.id.cvMerchandise);
        cvMerchandise.setVisibility(View.VISIBLE);
        img_merchandise = (ImageView) findViewById(R.id.img_merchandise);
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils(getApplicationContext());
        bitmapUtils.display(img_merchandise, PathUtils.createMerchandiseImageUrl(merchandiseID,info.getImgList().get(0)));
        tv_goods_fee = (TextView) findViewById(R.id.tv_goods_fee);
        tv_goods_fee.setText(info.getPrice() + "元");
        tv_trade_name = (TextView) findViewById(R.id.tv_trade_name);
        tv_trade_name.setText(info.getTitle());
        tv_seller_adress = (TextView) findViewById(R.id.tv_seller_adress);
        tv_seller_adress.setText(info.getCollege() + "");
        tv_transport_fee = (TextView) findViewById(R.id.tv_transport_fee);
        tv_transport_fee.setText(info.getCarriage() + "元");
        tv_flag = (TextView) findViewById(R.id.tvFlag);
        tv_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    flag = false;
                    tv_flag.setText("展示商品");
                    rl_merchandiseInfo.setVisibility(View.GONE);
                } else {
                    flag = true;
                    tv_flag.setText("隐藏商品");
                    rl_merchandiseInfo.setVisibility(View.VISIBLE);
                }
            }
        });
        btGoPay = (Button) findViewById(R.id.btGoPay);
        btGoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2buy = new Intent();
                intent2buy.setClass(ChatActivity.this, BuyActivity.class);
                intent2buy.putExtra("jsonData", getIntent().getExtras().getString("jsonData"));
                startActivity(intent2buy);
                finish();
            }
        });
    }

    private void inittitle() {
        mTitlebar = new Titlebar(this);
        if (title == null) {
            mTitlebar.setTitle("会话");
        } else {
            mTitlebar.setTitle(title);
        }
        mTitlebar.getbtback();
    }

    /**
     * 如果是从商品详情过来的，就需要加载商品信息，如果从聊天界面过来则不加载任何信息
     */
    private void getChatInfo() {
        Intent intent = getIntent();
        if (intent.getExtras() == null) {
            return;
        }
        title = intent.getExtras().getString("title");
        userID = intent.getExtras().getString("userID");
        merchandiseID = intent.getExtras().getString("merchandiseId");
        MerchandiseListUtils.getMerchandiseDetailFromServe(merchandiseID, MyApplication.mUser.getTokenId(),
                new HttpSuccess() {
                    @Override
                    public void onfail() {

                    }

                    @Override
                    public void onsuccess(MerchandiseInfo info) {
                        initMerchandiseView(info);
                    }
                });
        toChat();
    }

    private void toChat() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(io.rong.imlib.model.Conversation.ConversationType.PRIVATE.getName().toLowerCase())
                .appendQueryParameter("targetId", userID).appendQueryParameter("title", title).build();
        fragment.setUri(uri);
    }
}
