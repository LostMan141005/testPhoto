package com.yqq.test.testphoto;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yqq.test.testphoto.util.AddressAdapter;
import com.yqq.test.testphoto.util.HttpCallbackListener;
import com.yqq.test.testphoto.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {

    private ListView lv_test = null;
    private ArrayList<HashMap<String,Object>> listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_personmessage_adress_layout);

        lv_test = (ListView) findViewById(R.id.lv_test);

        listItem =  new ArrayList<HashMap<String, Object>>();

        Map<String, String> map = new Hashtable<String, String>();
        map.put("userName", "xh");
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
                Log.d("onError",e.toString());
                handler.sendEmptyMessage(0x124);
            }
        });
        Log.d("AddressAdapter", "start");
        AddressAdapter addressAdapter = new AddressAdapter(this,listItem);
        lv_test.setAdapter(addressAdapter);


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
                map.put("tv_address",jsonObject.get("area").toString() + jsonObject.get("detailed_area").toString());
                listItem.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("initArray","stop");
    }
//    private List<String> getData() {
//        List<String> data = new ArrayList<String>();
//        data.add("测试数据1");
//        data.add("测试数据2");
//        data.add("测试数据3");
//        data.add("测试数据4");
//        data.add("测试数据5");
//        data.add("测试数据6");
//        data.add("测试数据7");
//        data.add("测试数据8");
//        data.add("测试数据9");
//        data.add("测试数据10");
//        data.add("测试数据11");
//        data.add("测试数据12");
//        data.add("测试数据13");
//        data.add("测试数据14");
//        return data;
//    }

}
