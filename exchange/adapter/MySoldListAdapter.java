package com.deeal.exchange.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.exchange.Bean.MySoldItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.activity.LogisticsInfoActivity;
import com.deeal.exchange.activity.WriteNumberActivity;
import com.deeal.exchange.application.Constant;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.model.MerchandiseInfo;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.tools.HttpSuccess;
import com.deeal.exchange.tools.MySoldJson;
import com.deeal.exchange.tools.PathUtils;
import com.deeal.exchange.tools.RongIMUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import io.rong.imkit.RongIM;

/**
 * Created by Sunqiyong on 2015/7/13.
 */
public class MySoldListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private BitmapUtils bitmapUtils;

    ArrayList<MySoldItemBean> items = new ArrayList<MySoldItemBean>();

    public MySoldListAdapter(Context context, ArrayList<MySoldItemBean> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
        bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MySoldViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_sold_item, null);
            holder = new MySoldViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.imv_my_sold_item);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_goods_name_my_sold_item);
            holder.tv_cureent_price = (TextView) convertView.findViewById(R.id.tv_goods_money_my_sold_item);
            holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price_goods);
            holder.tv_condiction = (TextView) convertView.findViewById(R.id.tv_goods_condition_my_sold_item);
            holder.bt_1 = (Button) convertView.findViewById(R.id.bt_my_bought1_my_sold_item);
            holder.bt_2 = (Button) convertView.findViewById(R.id.bt_my_bought2_my_sold_item);

            holder.connect1 = (ImageView) convertView.findViewById(R.id.imv_contace_customer_my_sold_item);
            holder.connect2 = (TextView) convertView.findViewById(R.id.tv_contace_customer_my_sold_item);

            convertView.setTag(holder);
        } else {
            holder = (MySoldViewHolder) convertView.getTag();
        }
        final MySoldItemBean itemBean = items.get(position);
        bitmapUtils.display(holder.img, PathUtils.createMerchandiseImageUrl(itemBean.getMerchandiseID(),itemBean.getImv_goods()));
        holder.tv_title.setText(items.get(position).getName_goods());
        holder.tv_cureent_price.setText("￥" + itemBean.getPrice_goods());
        holder.tv_old_price.setText("原价:￥" + itemBean.getOld_price());
        holder.connect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(context, itemBean.getBuyerID(), "买家");
            }
        });
        holder.connect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(context, itemBean.getBuyerID(), "买家");
            }
        });
        Log.e("condition:","---"+items.get(position).getCondition_goods());
        if (items.get(position).getCondition_goods().equals("1")) {
            holder.tv_condiction.setText("买家未付款");
            holder.bt_1.setVisibility(View.INVISIBLE);
            holder.bt_2.setVisibility(View.INVISIBLE);
        } else if (items.get(position).getCondition_goods().equals("2")) {
            holder.tv_condiction.setText("待发货");
            holder.bt_1.setVisibility(View.INVISIBLE);
            holder.bt_2.setText("填写单号");
            holder.bt_2.setVisibility(View.VISIBLE);
            holder.bt_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent writeNum = new Intent();
                    writeNum.putExtra("from","");
                    writeNum.putExtra("orderID",items.get(position).getOrderID());
                    writeNum.setClass(context, WriteNumberActivity.class);
                    ((Activity)context).startActivityForResult(writeNum, Constant.WRITENUMBER);
                }
            });
        } else if (items.get(position).getCondition_goods().equals("3")) {
            holder.tv_condiction.setText("已发货");
            holder.bt_1.setVisibility(View.INVISIBLE);
            holder.bt_2.setText("查看物流");
            holder.bt_2.setVisibility(View.VISIBLE);
            holder.bt_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logistics = new Intent();
                    logistics.putExtra("orderID",items.get(position).getOrderID());
                    logistics.setClass(context, LogisticsInfoActivity.class);
                    ((Activity)context).startActivity(logistics);
                }
            });
        } else if (items.get(position).getCondition_goods().equals("4")) {
            holder.tv_condiction.setText("交易成功");
            holder.bt_1.setVisibility(View.INVISIBLE);
            holder.bt_2.setText("删除");
            holder.bt_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MySoldJson deleteOrder = new MySoldJson();
                    deleteOrder.deleteOrder(items.get(position).getOrderID(), new HttpSuccess() {
                        @Override
                        public void onfail() {
                            Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onsuccess(MerchandiseInfo info) {
                            items.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }



        return convertView;
    }



    class MySoldViewHolder {
        ImageView img;
        TextView tv_title;
        TextView tv_cureent_price;
        TextView tv_old_price;
        TextView tv_condiction;
        Button bt_1;
        Button bt_2;

        ImageView connect1;
        TextView connect2;

    }
}
