package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deeal.exchange.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/7/29.
 */
public class CityListAdapter extends BaseAdapter {
    /*构造数据*/
    private ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
    private Context context = null;
    private LayoutInflater mInflater = null;
    private String tag;

    /*构造函数*/
    public CityListAdapter(Context context, ArrayList<HashMap<String, String>> items) {
        this.context = context;
        this.items = items;
        this.mInflater = LayoutInflater.from(context);
    }
    public CityListAdapter(Context context, ArrayList<HashMap<String, String>> items,String tag) {
        this.context = context;
        this.items = items;
        this.mInflater = LayoutInflater.from(context);
        this.tag = tag;
    }

    /*返回数组长度*/
    @Override
    public int getCount() {
        return items.size();
    }

    /**/
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    /**/
    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            if (tag.equals("0")) {
                convertView = mInflater.inflate(R.layout.item_cities, null, false);
            }else {
                convertView = mInflater.inflate(R.layout.item_city, null, false);
            }
            holder = new ViewHolder();
                /*得到各个控件对象*/
            holder.tvCities = (TextView) convertView.findViewById(R.id.tvCities);
            convertView.setTag(holder);   //绑定ViewHplder对象
        } else {
            holder = (ViewHolder) convertView.getTag();   //取出Holder对象

        }
        HashMap<String, String> item = (HashMap<String, String>) getItem(position);
             /*设置TextView显示的内容，即是存在动态数组的数据*/
        holder.tvCities.setText(item.get("cityName"));
        return convertView;
    }

    /**
      * 存放控件
      **/
    public final class ViewHolder {
        public TextView tvCities;
    }
}
