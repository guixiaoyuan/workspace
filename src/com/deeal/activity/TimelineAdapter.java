package com.deeal.activity;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.deeal.model.maleRes;
import com.example.deeal.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;
	private LayoutInflater inflater;
	private final int TYPE_COUNT=2;  
    private final int FIRST_TYPE=0;  
    private final int OTHERS_TYPE=1;
    private int currentType; 

	public TimelineAdapter(Context context, List<Map<String, Object>> list) {
		super();
		this.context = context;
		this.list = list;
		inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);  
    
	}

	@Override
	public int getCount() {

		if (list==null) {  
            return 0;  
        } else {  
            return (list.size()+1);  
        }  
	}

	@Override
	public Object getItem(int position) {
		if (list==null) {  
            return null;  
        } else {  
            if (position>0) {  
                return list.get(position-1);  
            } else {  
                return list.get(position+1);  
            }  
        }  
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View firstItemView = null;  
        View otherItemView=null; 
		currentType= getItemViewType(position);  
		System.out.println("type="+currentType); 
		if(currentType==FIRST_TYPE){
			firstItemView = convertView;  
            FirstItemViewHolder firstItemViewHolder=null;
		
		if (firstItemView == null) {
			inflater = LayoutInflater.from(parent.getContext());
			firstItemView = inflater.inflate(R.layout.timeline_item1, null);
			firstItemViewHolder = new FirstItemViewHolder();

			firstItemViewHolder.title = (TextView) firstItemView.findViewById(R.id.title);
			firstItemViewHolder.time = (TextView) firstItemView
					.findViewById(R.id.show_time);
			firstItemView.setTag(firstItemViewHolder);
		} else {
			firstItemViewHolder = (FirstItemViewHolder) firstItemView.getTag();
		}
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		String titleStr = list.get(position).get("title").toString();

		firstItemViewHolder.title.setText(titleStr);
		firstItemViewHolder.time.setText(""+year+"/"+month+"/"+day);
		convertView=firstItemView; 
		}else{
			otherItemView = convertView;  
            OtherItemViewHolder otherItemViewHolder=null;
		
		if (otherItemView == null) {
			inflater = LayoutInflater.from(parent.getContext());
			otherItemView = inflater.inflate(R.layout.timeline_item2, null);
			otherItemViewHolder = new OtherItemViewHolder();

			
			otherItemViewHolder.time2 = (TextView) otherItemView
					.findViewById(R.id.show_time2);
			otherItemViewHolder.timeline_edit=(ImageView) otherItemView.findViewById(R.id.timeline_item2_edit);
			
			otherItemView.setTag(otherItemViewHolder);
		} else {
			otherItemViewHolder = (OtherItemViewHolder) otherItemView.getTag();
		}
		otherItemViewHolder.timeline_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				System.out.print("```````````````````````````````````````````````````````````````````````````````````````````````");
				
			}
		});
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
	
		otherItemViewHolder.time2.setText(""+year+"/"+month+"/"+day);
		convertView=otherItemView;
			
		}

		return convertView;
	}
	
	

		
		
	
	

	@Override
	public int getItemViewType(int position) {
		 if (position==0) {  
	            return FIRST_TYPE;  
	        } else {  
	            return OTHERS_TYPE;  
	        }  
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}
	
	static class FirstItemViewHolder {
		private TextView time;
		private TextView year;
		private TextView month;
		private TextView title;
	}
	
	static class OtherItemViewHolder {
		
		private TextView time2;
		private ImageView timeline_edit;
	}
}


