package com.deeal.exchange.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.MyPublishListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.PublishActivity;
import com.deeal.exchange.application.MyApplication;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.view.MyGirdView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我发布的商品 适配器
 * Created by gxy on 2015/7/13.
 */
public class MyPublishListAdapter extends BaseAdapter {

    private List<MyPublishListItemBean> myPublishItems = new ArrayList<MyPublishListItemBean>();
    private Context context = null;
    private LayoutInflater inflater = null;
    private BitmapUtils bitmapUtils = null;

    public MyPublishListAdapter(List<MyPublishListItemBean> myPublishItems, Context context) {
        this.myPublishItems = myPublishItems;
        this.context = context;
        inflater = LayoutInflater.from(context);
        bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        return myPublishItems.size();
    }

    @Override
    public Object getItem(int position) {
        return myPublishItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyPublishViewHold holder = null;
        if (convertView == null) {
            holder = new MyPublishViewHold();
            convertView = inflater.inflate(R.layout.my_publish_list_item, null);
            Log.d("gxy",convertView.toString());
            holder.tvGoodsName = (TextView) convertView.findViewById(R.id.tvGoodsName);
            holder.tvGoodsPrices = (TextView) convertView.findViewById(R.id.tvGoodsPrices);
            holder.btnMarket = (Button) convertView.findViewById(R.id.btn_market);
            holder.imgPublish = (MyGirdView) convertView.findViewById(R.id.glpublish);
            holder.tvMycollect = (TextView) convertView.findViewById(R.id.tv_mycollect);
            holder.tvShowDate = (TextView) convertView.findViewById(R.id.tv_showDate);
            holder.btnEdit = (Button) convertView.findViewById(R.id.btn_edit);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btn_delete);

            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setStroke(1, context.getResources().getColor(R.color.black));
            drawable.setColor(context.getResources().getColor(R.color.white));
            holder.btnMarket.setBackgroundDrawable(drawable);
            holder.btnEdit.setBackgroundDrawable(drawable);
            holder.btnDelete.setBackgroundDrawable(drawable);

            convertView.setTag(holder);
        } else {
            holder = (MyPublishViewHold) convertView.getTag();
        }
        holder.btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "推广成功", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnEdit.setVisibility(View.GONE);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("merchandiseId",myPublishItems.get(position).getMerchandiseId());
                intent.setClass(context, PublishActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
    });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                    RequestParams params  = new RequestParams();
                    params.addBodyParameter("tokenID", MyApplication.mUser.getTokenId());
                    params.addBodyParameter("merchandiseID", myPublishItems.get(position).getMerchandiseId());
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST, Path.deleteMyPublish, params, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                if (jsonObject.getString("status").equals("SUCCESS") && jsonObject.getBoolean("data") == true){
                                    removeItems(position);
                                }else {
                                    Log.i("MyPublishListAdapter",responseInfo.result);
                                    Toast.makeText(context, "对不起该商品已经被下单，无法删除", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(context, "网络异常，删除失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        holder.tvGoodsName.setText(myPublishItems.get(position).getTv_goods_name());
        holder.tvGoodsPrices.setText(myPublishItems.get(position).getTv_goods_price());
        holder.tvMycollect.setText(myPublishItems.get(position).getTv_mycollect());
        holder.tvShowDate.setText(myPublishItems.get(position).getTv_date());
        holder.imgPublish.setAdapter(new PicsGirdAdapter(context, myPublishItems.get(position).getImageurls(),9,myPublishItems.get(position).getMerchandiseId()));
        return convertView;
    }

    private static class MyPublishViewHold {
        public TextView tvGoodsName;
        public TextView tvGoodsPrices;
        public Button btnMarket;
        public MyGirdView imgPublish;
        public TextView tvMycollect;
        public TextView tvShowDate;
        public Button btnEdit;
        public Button btnDelete;
    }
    /**
     * 删除指定位置的条目
     *
     * @param position
     */
    public void removeItems(int position) {
        myPublishItems.remove(position);
        notifyDataSetChanged();
    }
}
