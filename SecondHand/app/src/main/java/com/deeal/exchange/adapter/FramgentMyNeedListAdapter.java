package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.view.MyGirdView;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.zip.Inflater;

import io.rong.imkit.utils.BitmapUtil;

/**
 * Created by gxy on 2015/8/24.
 */
public class FramgentMyNeedListAdapter extends BaseAdapter {

    private ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
    private Context context = null;
    private LayoutInflater inflater = null;
    private BitmapUtils bitmapUtil = null;

    public FramgentMyNeedListAdapter(ArrayList<CommodityListItemBean> items, Context context) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
        bitmapUtil = BitmapHelper.getBitmapUtils(context.getApplicationContext());
        bitmapUtil.configDefaultLoadingImage(R.mipmap.default_pic);
        bitmapUtil.configDefaultLoadFailedImage(R.mipmap.testuserhead);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyNeedViewHolder holder = null;
        if (convertView == null){
            holder = new MyNeedViewHolder();
            convertView = inflater.inflate(R.layout.myneed_item,null);
            holder.imgUserHead = (ImageView) convertView.findViewById(R.id.imgUserHead);
            holder.tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);
            holder.tvPublishTime = (TextView) convertView.findViewById(R.id.tvPublishTime);
            holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            convertView.setTag(holder);
        }else{
            holder = (MyNeedViewHolder) convertView.getTag();
        }
        holder.tvUserName.setText(items.get(position).getUsername());
        holder.tvPublishTime.setText(items.get(position).getPublishtime());
        holder.tvInfo.setText(items.get(position).getInfo());
        bitmapUtil.display(holder.imgUserHead, Path.picturePathUrl+items.get(position).getUserheadurl());
        return convertView;
    }

    private static class MyNeedViewHolder {
        public ImageView imgUserHead;
        public TextView tvUserName;
        public TextView tvInfo;
        public TextView tvPublishTime;
    }
    /**
     * 添加数据
     *
     * @param items
     */
    public void additems(ArrayList<CommodityListItemBean> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param items
     */
    public void refreshitems(ArrayList<CommodityListItemBean> items) {
        this.items.clear();
        this.items.addAll(items) ;
        notifyDataSetChanged();
    }
}
