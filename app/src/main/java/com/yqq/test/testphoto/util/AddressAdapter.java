package com.yqq.test.testphoto.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yqq.test.testphoto.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sdgl on 2016/6/6.
 */
public class AddressAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String,Object>> arrayList;
    public AddressAdapter adapter = this;

    public AddressAdapter(Context context,ArrayList<HashMap<String,Object>> arrayList){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.address_adapter_layout,null);
            holder = new ViewHolder();

            holder.tv_receiveName = (TextView) convertView.findViewById(R.id.tv_receiveName);
            holder.tv_receivePhone = (TextView) convertView.findViewById(R.id.tv_receivePhone);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.ib_edit = (ImageButton) convertView.findViewById(R.id.ib_edit);
            holder.ib_delete = (ImageButton) convertView.findViewById(R.id.ib_delete);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_receiveName.setText(arrayList.get(position).get("tv_receiveName").toString());
        holder.tv_receivePhone.setText(arrayList.get(position).get("receive_Phone").toString());
        holder.tv_address.setText(arrayList.get(position).get("tv_address").toString());
        holder.ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ib_edit","onClick");
            }
        });
        holder.ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ib_delete","onClick");
            }
        });
        return convertView;
    }

    public final class ViewHolder{
        public TextView tv_receiveName;
        public TextView tv_receivePhone;
        public TextView tv_address;
        public ImageButton ib_edit;
        public ImageButton ib_delete;
    }
}
