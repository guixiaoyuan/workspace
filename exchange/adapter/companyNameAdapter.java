package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deeal.exchange.Bean.companyNameBean;
import com.deeal.exchange.R;

import java.util.List;

/**
 * Created by Administrator on 2015/8/1.
 */
public class companyNameAdapter extends BaseAdapter {
    private List<companyNameBean> mList;
    private Context mContext;
    public companyNameAdapter(Context context, List<companyNameBean> list){
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.companyname_item,null);
        if(convertView != null){
            TextView tvCompanyName = (TextView)convertView.findViewById(R.id.tvCompanyName);
            tvCompanyName.setText(mList.get(position).getCompanyName());
        }
        return convertView;
    }
}
