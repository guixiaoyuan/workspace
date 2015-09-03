package gxy.shanbaytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import gxy.shanbaytest.R;
import gxy.shanbaytest.tools.BitmapHelper;
import gxy.shanbaytest.tools.EnglishText;


/**
 * Created by dading on 2015/7/10.
 */
public class SquareGridAdapter extends BaseAdapter {

    private BitmapUtils bitmapUtils;
    private Context context;
    private ArrayList<EnglishText> items = new ArrayList<>();
    private LayoutInflater inflater;

    public SquareGridAdapter(Context context, ArrayList<EnglishText> items) {
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
        EnglishText item = items.get(position);
        //bitmapUtils.display(viewHolder.imgPic, Path.MERCHANDISEIMGPATH+item.getMerchandiseID()+"/thumbnail/"+item.getPicurls().get(0));
        viewHolder.imgPic.setImageResource(item.getPicture());
        viewHolder.tvInfo.setText(item.getInfo());
        viewHolder.tvSchool.setText(item.getSchool());
        viewHolder.tvCost.setText(item.getCost());
        return convertView;
    }

    private static class  ViewHolder{
        ImageView imgPic;
        TextView tvInfo;
        TextView tvCost;
        TextView tvSchool;
    }
}
