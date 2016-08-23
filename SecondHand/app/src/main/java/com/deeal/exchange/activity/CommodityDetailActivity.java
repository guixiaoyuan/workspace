package com.deeal.exchange.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.view.sample.AbInnerViewPager;
import com.deeal.exchange.R;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.User;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.tools.RongIMUtils;
import com.deeal.exchange.view.CircleImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommodityDetailActivity extends Activity {
    private Button btnBuy;
    private Button btBack;
    private TextView tvTitle;
    private LinearLayout linearLayout;
    private ImageView IBnselect;
    private ArrayList<ImageView> tips = new ArrayList<ImageView>();
    private Flag flag;

    private CircleImageView portrait;
    private TextView tvUserName;
    private TextView tvUserAddress;
    private TextView tvInfo;
    private TextView tvSchoolAddress;
    private TextView tvPublishTime;
    private TextView tvSalePrice;
    private TextView tvVisitedCount;
    private BitmapUtils bitmapUtils = null;
    private String id;
    private ArrayList<String> imgs = new ArrayList<String>();
    private ArrayList<String> picurls = new ArrayList<>();
    private JSONArray imglist = new JSONArray();
    private AbInnerViewPager mViewPager;
    private String userID;
    private User userInfo;
    private Boolean isFreeze;
    private Boolean isBought;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_detail);
        inittitle();
        init();
        id = httpSend();
    }


    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.size(); i++) {
            if (i == selectItems) {
                tips.get(i).setBackgroundResource(R.drawable.page_indicator_focused_show);
            } else {
                tips.get(i).setBackgroundResource(R.drawable.page_indicator_unfocused_show);
            }
        }
    }
    private void inittitle() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.commodityDetail));
        btBack = (Button) findViewById(R.id.btBack);
        btBack.setVisibility(View.VISIBLE);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void init() {
        bitmapUtils = BitmapHelper.getBitmapUtils(this);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        portrait = (CircleImageView) findViewById(R.id.imgUserHead);
        tvUserAddress = (TextView) findViewById(R.id.tvUserAdress);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvPublishTime = (TextView) findViewById(R.id.tvPublishTime);
        tvSchoolAddress = (TextView) findViewById(R.id.school_address);
        tvSalePrice = (TextView) findViewById(R.id.tv_salePrice);
        tvVisitedCount = (TextView) findViewById(R.id.tvVisitedCount);


        IBnselect = (ImageView) findViewById(R.id.img_myFavorite);

        Intent intent1 = getIntent();
        final String merchandiseId1 = intent1.getStringExtra("merchandiseID");
        IBnselect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.getFlag() == 0) {
                    addFavorite(merchandiseId1, flag, IBnselect);
                } else {
                    deleteFavorite(merchandiseId1, flag, IBnselect);
                }

            }
        });
    }

    private String stringOperation(String str) {
        if (null == str || str == "") {
            return "未设置";
        } else {
            return str;
        }
    }


    private String httpSend() {
        HttpUtils httpUtils = new HttpUtils();
        final Intent intent = getIntent();
        RequestParams params = new RequestParams();
        params.addBodyParameter("merchandiseID", intent.getStringExtra("merchandiseID"));
        final String merchandiseId = intent.getStringExtra("merchandiseID");
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        httpUtils.send(HttpRequest.HttpMethod.POST, Path.merchandiseDetail, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("CommodityDetailActivity", responseInfo.result);
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    if(jsonObject.getString("status").equals("SUCCESS")) {
                        JSONObject jsonData = new JSONObject(jsonObject.getString("data"));
                        flag = new Flag();
                        flag.setFlag(jsonData.getInt("favorite"));
                        if (flag.getFlag() == 1) {
                            IBnselect.setImageResource(R.mipmap.mycollection);
                        } else {
                            IBnselect.setImageResource(R.mipmap.uncollect);
                        }
                        String username = stringOperation(jsonData.getString("userName"));
                        tvUserName.setText(username);

                        String publishtime = stringOperation(jsonData.getString("publishedTime"));
                        tvPublishTime.setText(publishtime);

                        String schooladdress = stringOperation(jsonData.getString("college"));
                        tvSchoolAddress.setText(schooladdress);

                        String info = stringOperation(jsonData.getString("info"));
                        tvInfo.setText(info);

                        String useraddress = stringOperation(jsonData.getString("residence"));
                        tvUserAddress.setText(useraddress);

                        String visitedcount = stringOperation(jsonData.getString("visitedCount"));
                        tvVisitedCount.setText(visitedcount);

                        String currentprice = stringOperation(jsonData.getString("currentPrice"));
                        tvSalePrice.setText(currentprice);

                        //isFreeze如果为true则代表该商品已经被其他人下单
                        if (Integer.parseInt(jsonData.getString("isFreeze")) == 0) {
                            isFreeze = false;
                        } else {
                            isFreeze = true;
                        }
                        //isBought 如果为true 则代表该商品已经被本人所下单。那么就直接跳过前往订单界面
                        if (Integer.parseInt(jsonData.getString("isBought")) == 0) {
                            isBought = false;
                        } else {
                            isBought = true;
                        }
                        userID = jsonData.getString("userID");
                        userInfo = new User();
                        userInfo.setUserID(userID);
                        userInfo.setUserNickname(jsonData.getString("userName"));
                        userInfo.setUserHeadUrl(jsonData.getString("portraitPath"));
                        bitmapUtils.display(portrait, PathUtils.createPortriateUrl(userID,userInfo.getUserHeadUrl()));
                        imglist = jsonData.getJSONArray("imgList");
                        for (int i = 0; i < imglist.length(); i++) {
                            imgs.add(PathUtils.createMerchandiseImageUrl(merchandiseId,imglist.getString(i)));
                            picurls.add(imglist.getString(i));
                        }
                        initViewPager(imgs);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getConnectToSeller(responseInfo.result);//加载商品成功后初始化与卖家联系的接口
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CommodityDetailActivity.this,"服务器开小差了",Toast.LENGTH_SHORT).show();
            }
        });
        return merchandiseId;
    }

    /**
     * 根据获取的图片列表在viewpager中进行展示
     * @param imgs
     */
    private void initViewPager(ArrayList<String> imgs) {
        mViewPager = (AbInnerViewPager) findViewById(R.id.container);
        PagerAdapter mAdapter = new SimplePagerAdapter(bitmapUtils);
        mViewPager.setAdapter(mAdapter);


        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setRotationY(position * -30);
            }
        });


        linearLayout = (LinearLayout) findViewById(R.id.ll_image);
        for (int i = 0; i < imgs.size(); i++) {
            ImageView mImageView = new ImageView(this);
            tips.add(mImageView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
            layoutParams.rightMargin = 3;
            layoutParams.leftMargin = 3;
            mImageView
                    .setBackgroundResource(R.drawable.page_indicator_unfocused_show);
            linearLayout.addView(mImageView, layoutParams);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setImageBackground(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class SimplePagerAdapter extends PagerAdapter {
        private BitmapUtils bitmapUtils;

        public SimplePagerAdapter(BitmapUtils bitmapUtils){
            this.bitmapUtils = bitmapUtils;
        }

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            bitmapUtils.display(imageView, imgs.get(position));
            final int index = position;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("imgurls", picurls);
                    intent.putExtra("pagenum", index + 1);
                    intent.putExtra("merchandiseID",getIntent().getStringExtra("merchandiseID"));
                    intent.setClass(CommodityDetailActivity.this, ImageViewActivity.class);
                    startActivity(intent);
                }
            });
            container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return imageView;
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


    /**
     * 与卖家联系
     */
    private void getConnectToSeller(final String jsonData){
        btnBuy = (Button) findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBought){
                    Intent intent = getIntent();
                    intent.setClass(CommodityDetailActivity.this,OrderDetailActivity.class);
                    intent.putExtra("merchandiseID",intent.getStringExtra("merchandiseID"));
                    startActivity(intent);
                    finish();
                }else{
                    RongIMUtils.toChatForBuy(CommodityDetailActivity.this,userInfo.getUserID(),userInfo.getUserNickname(),
                            getIntent().getStringExtra("merchandiseID"),jsonData);
                }
            }
        });
    }


    /**
     * create by sunqiyong CommodityDetailActivity增加收藏
     * @param merchandiseID
     * @param img
     */
    private void addFavorite(final String merchandiseID, final Flag flag,final ImageView img){
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("merchandiseID", merchandiseID);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.add_favoritePath, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                flag.setFlag(1);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseInfo.result);
                    if(jsonObject.getString("status").equals("SUCCESS")){
                        img.setImageResource(R.mipmap.mycollection);
                        Toast.makeText(CommodityDetailActivity.this, "收藏成功" , Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CommodityDetailActivity.this, "收藏失败！" , Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * create by sunqiyong CommodityDetailActivity删除收藏
     * @param merchandiseID
     * @param img
     */
    private void deleteFavorite(String merchandiseID,final Flag flag,final ImageView img){
        RequestParams params = new RequestParams();
        params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
        params.addBodyParameter("merchandiseID", merchandiseID);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Path.delete_favoritePath, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                flag.setFlag(0);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseInfo.result);
                    if (jsonObject.getString("status").equals("SUCCESS")) {
                        Toast.makeText(CommodityDetailActivity.this, "取消收藏", Toast.LENGTH_LONG).show();
                        img.setImageResource(R.mipmap.uncollect);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(CommodityDetailActivity.this, "收藏成功" , Toast.LENGTH_LONG).show();
            }
        });
    }


    class Flag{
        private Integer flag;
        public Integer getFlag() {
            return flag;
        }
        public void setFlag(Integer flag) {
            this.flag = flag;
        }
    }

}