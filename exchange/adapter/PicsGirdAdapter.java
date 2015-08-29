package com.deeal.exchange.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.deeal.exchange.activity.ImageViewActivity;
import com.deeal.exchange.R;
import com.deeal.exchange.application.Path;
import com.deeal.exchange.tools.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/9.
 * 九宫格图片适配器
 */
public class PicsGirdAdapter extends BaseAdapter {

    private ArrayList<String> picurls = new ArrayList<>();
    private Context context = null;
    private LayoutInflater inflater;
    private BitmapUtils bitmapUtils;
    private int limitcount;
    private String merchandiseID;

    /**
     *
     * @param context 上下文
     * @param imgurls 图片的获取地址
     * @param limitcount  显示图片的最大限制
     */
    public PicsGirdAdapter(Context context,ArrayList<String> imgurls,int limitcount,String merchandiseID){
        this.context = context;
        this.picurls.addAll(imgurls);
        this.inflater = LayoutInflater.from(context);
        this.bitmapUtils = BitmapHelper.getBitmapUtils(context.getApplicationContext());
        this.limitcount = limitcount;
        this.merchandiseID = merchandiseID;
    }

    @Override
    public int getCount() {
        int size = picurls.size();
        if(size>limitcount){
            size = limitcount;
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        return picurls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.img_gird_item,null);
            viewHolder.imgPic = (ImageView) convertView.findViewById(R.id.imgPic);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*int width = viewHolder.imgPic.getWidth();
        int height = width;
        viewHolder.imgPic.setLayoutParams(new RelativeLayout.LayoutParams(width,height));*/
        bitmapUtils.display(viewHolder.imgPic,Path.MERCHANDISEIMGPATH+merchandiseID+"/thumbnail/"+picurls.get(position));
        viewHolder.imgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("imgurls", picurls);
                intent.putExtra("pagenum", position + 1);
                intent.putExtra("merchandiseID",merchandiseID);
                intent.setClass(context, ImageViewActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    private static class ViewHolder {
        public ImageView imgPic;
    }
}
