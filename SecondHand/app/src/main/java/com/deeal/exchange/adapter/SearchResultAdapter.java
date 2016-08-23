package com.deeal.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deeal.exchange.R;
import com.deeal.exchange.model.MerchandiseInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/3.
 */
public class SearchResultAdapter extends BaseAdapter {

    private ArrayList<MerchandiseInfo> results;
    private Context mContext;
    private LayoutInflater mInflater;

    public SearchResultAdapter(Context context,ArrayList<MerchandiseInfo> results ){
        this.mContext =context;
        this.results =results;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItems(ArrayList<MerchandiseInfo> infos){
        this.results.clear();
        this.results.addAll(infos);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.search_result_item,null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvCost = (TextView) convertView.findViewById(R.id.tvCost);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(results.get(position).getTitle());
        viewHolder.tvCost.setText(results.get(position).getPrice()+"元");
        return convertView;
    }

    private class  ViewHolder{
         TextView tvTitle;
        TextView tvCost;

    }
}
