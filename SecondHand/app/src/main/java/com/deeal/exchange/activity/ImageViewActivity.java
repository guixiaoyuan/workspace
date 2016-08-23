package com.deeal.exchange.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.sample.AbInnerViewPager;
import com.deeal.exchange.R;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.view.activity_transition.ActivityTransition;
import com.deeal.exchange.view.activity_transition.ExitActivityTransition;
import com.deeal.exchange.view.photoview.PhotoView;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片浏览器
 */
public class ImageViewActivity extends AbActivity {
    private BitmapUtils bitmapUtils;
    private AbInnerViewPager contaniner;
    private List<String> imgurls = new ArrayList<String>();
    private TextView tvPageNum;
    private String merchandiseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        tvPageNum = (TextView) findViewById(R.id.tvPageNum);
        bitmapUtils = BitmapHelper.getBitmapUtils(getApplicationContext());
        contaniner = (AbInnerViewPager) findViewById(R.id.container);
        Intent intent = getIntent();
        int pagenum = intent.getIntExtra("pagenum", 1);
        imgurls =  intent.getStringArrayListExtra("imgurls");
        merchandiseID = intent.getStringExtra("merchandiseID");
        tvPageNum.setText(pagenum + "/" + imgurls.size());
        SamplePagerAdapter adapter = new SamplePagerAdapter(bitmapUtils);
        contaniner.setAdapter(adapter);
        //单击退出
        contaniner.setCurrentItem(pagenum - 1);
        contaniner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvPageNum.setText(position+1+"/"+imgurls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class SamplePagerAdapter extends PagerAdapter {
        private BitmapUtils bitmapUtils;

        public SamplePagerAdapter(BitmapUtils bitmapUtils){
            this.bitmapUtils = bitmapUtils;
        }

        @Override
        public int getCount() {
            return imgurls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            bitmapUtils.display(photoView, Path.MERCHANDISEIMGPATH+merchandiseID+"/origin/"+imgurls.get(position));
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
