package com.yqq.test.testphoto.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yqq.test.testphoto.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sdgl on 2016/6/5.
 */
public class MineAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String,Object>> arrayList;
    public MineAdapter adapter = this;

    public MineAdapter(Context context,ArrayList<HashMap<String,Object>> arrayList){
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.simple_layout,null);
            holder = new ViewHolder();

            holder.btn_item = (ImageButton) convertView.findViewById(R.id.btn_item);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(arrayList.get(position).get("tv_title").toString());
        holder.tv_text.setText(arrayList.get(position).get("tv_text").toString());
        holder.btn_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "你点击了按钮" + position);
                arrayList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public final class ViewHolder{
        public TextView tv_title;
        public TextView tv_text;
        public ImageButton btn_item;
    };
}
