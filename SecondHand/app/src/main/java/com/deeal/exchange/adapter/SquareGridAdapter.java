package com.deeal.exchange.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeal.exchange.Bean.CommodityListItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by dading on 2015/7/10.
 */
public class SquareGridAdapter extends BaseAdapter {

    private BitmapUtils bitmapUtils;
    private Context context;
    private ArrayList<CommodityListItemBean> items = new ArrayList<CommodityListItemBean>();
    private LayoutInflater inflater;

    public SquareGridAdapter(Context context, ArrayList<CommodityListItemBean> items) {
        this.context = context;
        this.items = items;
        bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
        inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.square_list_item,null);
            viewHolder.imgPic = (ImageView) convertView.findViewById(R.id.imgPic);
            viewHolder.tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);
            viewHolder.tvCost = (TextView) convertView.findViewById(R.id.tvCost);
            viewHolder.tvSchool = (TextView) convertView.findViewById(R.id.tvSchool);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommodityListItemBean item = items.get(position);
        bitmapUtils.display(viewHolder.imgPic, Path.MERCHANDISEIMGPATH+item.getMerchandiseID()+"/thumbnail/"+item.getPicurls().get(0));
        Log.i("SquareGridAdapter", viewHolder.toString());
        viewHolder.tvInfo.setText(item.getInfo().toString());
        viewHolder.tvSchool.setText(item.getCollegesTypes().toString());
        viewHolder.tvCost.setText(item.getCost());
        return convertView;
    }

    private static class  ViewHolder{
        ImageView imgPic;
        TextView tvInfo;
        TextView tvCost;
        TextView tvSchool;
    }

    public void addItems(ArrayList<CommodityListItemBean> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void refreshItems(ArrayList<CommodityListItemBean> items){
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
