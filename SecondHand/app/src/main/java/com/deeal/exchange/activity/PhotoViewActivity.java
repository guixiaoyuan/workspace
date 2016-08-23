package com.deeal.exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.deeal.exchange.R;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.view.photoview.PhotoView;
import com.lidroid.xutils.BitmapUtils;

public class PhotoViewActivity extends Activity {

    private PhotoView photoView;
    private String imgurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        getImageUrl();
        initItems();
    }

    private void getImageUrl(){
        Intent intent = getIntent();
        imgurl = intent.getStringExtra("imgName");
    }
    private void initItems(){
        photoView = (PhotoView) findViewById(R.id.imgPhoto);
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils(getApplicationContext());
        bitmapUtils.display(photoView, Path.PORTRAIT_HD+imgurl);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
