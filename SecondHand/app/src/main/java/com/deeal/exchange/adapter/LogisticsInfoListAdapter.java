package com.deeal.exchange.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeal.exchange.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sunqiyong on 2015/7/13.
 */
public class LogisticsInfoListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Map> items;

    public LogisticsInfoListAdapter(Context context,ArrayList<Map> items){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.items = items;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.logistics_info_item, null,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_adress = (TextView) convertView.findViewById(R.id.tv_logistics_detail_logistics_info_item);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_logistics_time_logistics_info_item);
            viewHolder.imv_sport = (ImageView) convertView.findViewById(R.id.imv_sport);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(position == 0 ){
            viewHolder.imv_sport.setImageResource(R.drawable.spot_logistics_blue);
            viewHolder.tv_adress.setTextColor(context.getResources().getColor(R.color.sport));
            viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.sport));
        }else {
            viewHolder.imv_sport.setImageResource(R.drawable.spot_logistics_gray);
            viewHolder.tv_adress.setTextColor(context.getResources().getColor(R.color.gray));
            viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.gray));
        }
        viewHolder.tv_adress.setText(items.get(position).get("adress").toString());
        viewHolder.tv_time.setText(items.get(position).get("time").toString());
        return convertView;
    }


    private static class ViewHolder{
        TextView tv_adress;
        TextView tv_time;
        ImageView imv_sport;
    }



}
