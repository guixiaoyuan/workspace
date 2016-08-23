package com.deeal.exchange.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/8.
 */
public class MyPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<View> views;

    public MyPagerAdapter(Context context, ArrayList<View> views){
        this.context = context;
        this.views = views;
    }

    //viewpager中的组件数量
    @Override
    public int getCount() {
        return views.size();
    }

    //每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public void addItem( View view){
        views.add(view);
        notifyDataSetChanged();
    }

    public void addItems(int position,ArrayList<View> views){
        this.views.addAll(position,views);
        notifyDataSetChanged();
    }
}
