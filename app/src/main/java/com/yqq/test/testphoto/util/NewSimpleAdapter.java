package com.yqq.test.testphoto.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.yqq.test.testphoto.R;


import java.util.List;
import java.util.Map;

/**
 * Created by sdgl on 2016/6/10.
 */
public class NewSimpleAdapter extends SimpleAdapter {


    private LayoutInflater layoutInflater;
    private Context context;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public NewSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(position ==0 && convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_view_layout,null);
            convertView.findViewById(R.id.rl_item1).setBackgroundColor(context.getResources().getColor(R.color.orange));
            convertView.findViewById(R.id.rl_item2).setBackgroundColor(context.getResources().getColor(R.color.orange));
            return super.getView(position, convertView, parent);
        }
        return super.getView(position, convertView, parent);
    }
}
