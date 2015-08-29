package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deeal.exchange.Bean.MerchandiseTypeBean;
import com.deeal.exchange.R;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 */
public class MerchandiseTypeAdapter extends BaseAdapter{
    private Context mContext;
    private List<MerchandiseTypeBean> mList ;
    public MerchandiseTypeAdapter(Context context, List<MerchandiseTypeBean> list){
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
        convertView = inflater.inflate(R.layout.merchandise_type_item,null);
        if(convertView != null){
            TextView tvMerchandiseType = (TextView)convertView.findViewById(R.id.tvMerchandiseType);
            tvMerchandiseType.setText(mList.get(position).getMerchandiseTypeName());
        }
        return convertView;
    }
}
