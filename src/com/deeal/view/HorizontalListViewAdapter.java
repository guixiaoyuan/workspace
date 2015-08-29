package com.deeal.view;



  
import java.util.List;

import com.example.deeal.R;

import android.annotation.SuppressLint;
import android.content.Context;  
import android.graphics.drawable.Drawable;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView;  
  
@SuppressLint("NewApi")
public class HorizontalListViewAdapter extends BaseAdapter{  
    private int[] mIconIDs;  
    private List<Drawable> mImages;  
    private Context mContext;  
    private LayoutInflater mInflater;  
    
    private int selectIndex = -1;  
  
    public HorizontalListViewAdapter(Context context, List<Drawable> imgs, int[] ids){  
        this.mContext = context;  
        this.mIconIDs = ids;  
        this.mImages =imgs; 
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);  
    }  
    @Override  
    public int getCount() {  
        return mIconIDs.length;  
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
  
        ViewHolder holder;  
        if(convertView==null){  
            holder = new ViewHolder();  
            convertView = mInflater.inflate(R.layout.cloth_type_item, null);  
            holder.mImage=(ImageView)convertView.findViewById(R.id.cloth_details);  
            
            convertView.setTag(holder);  
        }else{  
            holder=(ViewHolder)convertView.getTag();  
        }  
        if(position == selectIndex){  
            convertView.setSelected(true);  
        }else{  
            convertView.setSelected(false);  
        }  
        holder.mImage.setBackground(mImages.get(position));
       
        
  
        return convertView;  
    }  
  
    private static class ViewHolder {  
        
        private ImageView mImage;  
    }  
   
    public void setSelectIndex(int i){  
        selectIndex = i;  
    }  
}