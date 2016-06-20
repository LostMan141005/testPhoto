package com.yqq.test.testphoto.util;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);

}
