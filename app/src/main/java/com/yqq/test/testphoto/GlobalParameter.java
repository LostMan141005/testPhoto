package com.yqq.test.testphoto;

import android.os.Environment;

/**
 * 全局参数
 * Created by Carter.Gao on 2015-12-21
 */
public class GlobalParameter {
    //真假数据接口 1.demo数据。2.web端后台获取数据
    public static final int DATA_FLAG=2;
    /**
     * 写点评
     */
    public static final String SD_CARD = Environment.getExternalStorageDirectory().getPath();
    public static final String COMMENTS_PHOTO = SD_CARD + "/swanApp/Shop_Photo/";
    //拍照、上传照片标志位
    public static final int COMMENTS_LOCAL_PHOTO=1;
    public static final int COMMENTS_TAKE_PHOTO=2;
    //订单状态标志位
    public static final String ORDER_WAITING_PAY="1";//待付款
    public static final String ORDER_PAY_SUCCESS="2";//付款成功
    public static final String ORDER_PAY_FAILURE="3";//付款失败
    public static final String ORDER_REFUND="4";//退款成功
    public static final String ORDER_REFUND_FAILURE="5";//退款失败

    public static final String LOCATION_ATT = "location-att";
    public static final String LOCATION_LAT = "location-lat";
    public static final String LOCATION_ACTION = "locationAction";

    public static final int LOCATION_REQUEST_CODE = 1001;
    public static final int LOCATION_RESULT_CODE = 1002;

    public static final int INTEGRAL_REQUEST_CODE = 1011;
    public static final int INTEGRAL_RESULT_CODE = 102;

    public static String DEFAULT_CITY = "242";
}
