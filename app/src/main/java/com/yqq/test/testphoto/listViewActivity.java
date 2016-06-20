package com.yqq.test.testphoto;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.yqq.test.testphoto.util.HttpCallbackListener;
import com.yqq.test.testphoto.util.HttpUtil;
import com.yqq.test.testphoto.util.MineAdapter;
import com.yqq.test.testphoto.util.NewSimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class listViewActivity extends Activity {

    private LinearLayout ll_item = null;
    private RelativeLayout rl_item1 = null;
    private RelativeLayout rl_item2 = null;
    public String path = null;

    private ListView lv_test = null;
    ArrayList<HashMap<String,Object>> listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_personmessage_adress_layout);


        lv_test = (ListView) findViewById(R.id.lv_test);

        ll_item = (LinearLayout) findViewById(R.id.ll_item);

        listItem = new ArrayList<HashMap<String, Object>>();

        Map<String, String> map = new Hashtable<String, String>();
        map.put("user_id", "79212ea3-5e87-4b3a-b09b-776dd239f8f3");
        String url = HttpUtil.BASE_URL + "selectReceiveAddress.do";

        HttpUtil.sendHttpRequestPostSubmit(url, map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("onFinish", "success");
                Message message = Message.obtain();
                message.what = 0x123;
                message.obj = response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {
                Log.d("onError", e.toString());
                handler.sendEmptyMessage(0x124);
            }
        });
//        NewSimpleAdapter simpleAdapter = new NewSimpleAdapter(this,listItem,R.layout.list_view_layout,new String[] {"tv_receiveName"
//                ,"receive_Phone", "tv_address"},new int[]{R.id.tv_receiveName,R.id.tv_receivePhone,R.id.tv_address});
//        lv_test.setAdapter(simpleAdapter);

//        View view = simpleAdapter.getView(0, null, null);
//        rl_item1 = (RelativeLayout) view.findViewById(R.id.rl_item1);
//        rl_item2 = (RelativeLayout) view.findViewById(R.id.rl_item2);
//        rl_item1.setBackgroundColor(getResources().getColor(R.color.orange));
//        rl_item2.setBackgroundColor(getResources().getColor(R.color.orange));

//        lv_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("onItemClick","start");
//
//            }
//        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    Log.d("0x123",msg.obj.toString());
                    initArray(msg.obj.toString());
                    break;
                case 0x124:
                    Log.d("0x124","error");
                    break;
            }

            super.handleMessage(msg);
        }
    };
    public void initArray(String str){
        Log.d("initArray","start");
        try {
            JSONArray jsonArray = new JSONArray(str);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String,Object> map = new HashMap<String, Object>();
                map.put("tv_receiveName",jsonObject.get("receive_name").toString());
                map.put("receive_Phone",jsonObject.get("mobile_phone").toString());
                map.put("tv_address",jsonObject.get("area").toString() + jsonObject.get("detailed_address").toString());
                listItem.add(map);
            }
            NewSimpleAdapter simpleAdapter = new NewSimpleAdapter(this,listItem,R.layout.list_view_layout,new String[] {"tv_receiveName"
                    ,"receive_Phone", "tv_address"},new int[]{R.id.tv_receiveName,R.id.tv_receivePhone,R.id.tv_address});
            lv_test.setAdapter(simpleAdapter);
            lv_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("onItemClick", "start");

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("initArray","stop");
    }
}
