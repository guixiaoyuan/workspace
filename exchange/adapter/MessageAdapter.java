package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deeal.exchange.Bean.MessageItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.tools.BitmapHelper;
import com.deeal.exchange.view.CircleImageView;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/7/13.
 */
public class MessageAdapter extends BaseAdapter {

    private List<MessageItemBean> items;
    private Context context;
    private LayoutInflater inflater;
    private BitmapUtils bitmapUtils;

    public MessageAdapter(Context context, List<MessageItemBean> items) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
        bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            viewHolder.imgHead = (CircleImageView) convertView.findViewById(R.id.imgHead);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvPublishTime = (TextView) convertView.findViewById(R.id.tvPublishTime);
            viewHolder.tvMessage = (TextView) convertView.findViewById(R.id.tvGoMessage);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(viewHolder.imgHead,items.get(position).getSender().getUserHeadUrl());
        viewHolder.tvMessage.setText(items.get(position).getMessage());
        viewHolder.tvPublishTime.setText(items.get(position).getTime());
        viewHolder.tvUserName.setText(items.get(position).getSender().getUserNickname());
        return convertView;
    }

    private class ViewHolder{
        CircleImageView imgHead;
        TextView tvUserName;
        TextView tvMessage;
        TextView tvPublishTime;
    }
}
