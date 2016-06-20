package com.yqq.test.testphoto.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
	public static HttpClient httpClient = new DefaultHttpClient();

	/**
	 * 路由器
	 */
	public static final String BASE_URL = "http://192.168.14.150:8080/quickframe/android/";
	public static void sendHttpRequestGetSubmit(final String address,
												final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						// 回调onFinish()方法
						listener.onFinish(response.toString());
						Log.d("luolin", response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						// 回调onError()方法
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	public static void sendHttpRequestPostSubmit(final String url,
												 final Map<String, String> map, final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					// 创建HttpPost对象。
					HttpPost post = new HttpPost(url);
					// 如果传递参数个数比较多的话可以对传递的参数进行封装
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					for (String key : map.keySet()) {
						// 封装请求参数
						params.add(new BasicNameValuePair(key, map.get(key)));
					}
					// 设置请求参数
					post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
					// 发送POST请求
					HttpResponse httpResponse = httpClient.execute(post);
					// 如果服务器成功地返回响应
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						// 获取服务器响应字符串
						String result = EntityUtils.toString(httpResponse
								.getEntity());

						Log.d("luolin", "服务器返回内容： " + result);

						if (listener != null) {
							// 回调onFinish()方法
							listener.onFinish(result);
						}
					}
				} catch (Exception e) {
					if (listener != null) {
						// 回调onError()方法
						listener.onError(e);
					}
				}
			}
		}).start();
	}

	public static void uploadFile(final String actionUrl,final String path) {
		new Thread(new Runnable() {
			@Override
			public void run() {

//				String UrlPath = HttpUtil.BASE_URL + "addHeadPhoto.do";
				File file = new File(path);
				try {
					URL url = new URL(actionUrl);
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					conn.setRequestMethod("POST");
					conn.setDoOutput(true);
					conn.setRequestProperty("connection", "Keep-Alive");
					conn.setRequestProperty("Charset", "UTF-8");

//					conn.setRequestProperty("Content-Type", "application/x-javascript; charset=" + encoding);
//					conn.setRequestProperty("Content-Length", String.valueOf(data.length));
					conn.setConnectTimeout(5 * 1000);
//					OutputStream outStream = conn.getOutputStream();
//					outStream.write(data);
//					outStream.flush();
//					outStream.close();

					DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

					dos.writeUTF(file.getName());
					FileInputStream fis = new FileInputStream(file);
					dos.writeInt(fis.available());

//					File file1 = new File("/sdcard/test.jpg");
//					FileOutputStream fos = new FileOutputStream(file1);
//					fos.write(bt);
//					fos.close();

					byte[] buf = new byte[1024];
					int len = -1;

					while ((len = fis.read(buf))!=-1){
//						fos.write(buf,0,len);
						dos.write(buf,0,len);
					}
//					fos.close();

					fis.close();
					dos.flush();
					dos.close();
					Log.d("0001", "######################");
					if(conn.getResponseCode() == 200) {
						Log.d("test", "*********************");
//						InputStream inStream = conn.getInputStream();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
	public static void upLoadFile(){
		new Thread(new Runnable() {
			@Override
			public void run() {

				String message = "test upLoadFile *************";
				String encoding="UTF-8";
				String path = HttpUtil.BASE_URL + "addHeadPhoto.do";

				File file = new File("/mnt/sdcard/IMG_20160531_201035.jpg");

				try {
					byte[] data = message.getBytes(encoding);
					URL url = new URL(path);

					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					conn.setRequestMethod("POST");
					conn.setDoOutput(true);
					conn.setRequestProperty("connection", "Keep-Alive");
					conn.setRequestProperty("Charset", "UTF-8");

//					conn.setRequestProperty("Content-Type", "application/x-javascript; charset=" + encoding);
//					conn.setRequestProperty("Content-Length", String.valueOf(data.length));
					conn.setConnectTimeout(5 * 1000);
//					OutputStream outStream = conn.getOutputStream();
//					outStream.write(data);
//					outStream.flush();
//					outStream.close();
					DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

					dos.writeUTF(file.getName());

					FileInputStream fis = new FileInputStream(file);
					dos.writeInt(fis.available());

//					byte[] bt = new byte[fis.available()];
//					fis.read(bt);
//					dos.write(bt);
					File file1 = new File("/sdcard/test.jpg");
					FileOutputStream fos = new FileOutputStream(file1);
//					fos.write(bt);
//					fos.close();

					byte[] buf = new byte[1024];
					int len = -1;

					while ((len = fis.read(buf))!=-1){
						fos.write(buf,0,len);
						dos.write(buf,0,len);

						Log.d("0002", String.valueOf(len));
					}
					fos.close();

					fis.close();
					dos.flush();
					dos.close();


					Log.d("0001", "######################");
					if(conn.getResponseCode() == 200) {
						Log.d("test", "*********************");

						InputStream inStream = conn.getInputStream();

					}

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public static void downLoadFile(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String path = HttpUtil.BASE_URL + "selectHeadPhoto.do";

				try {
					URL url = new URL(path);

					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					conn.setRequestMethod("POST");
					conn.setDoOutput(true);
					conn.setRequestProperty("connection", "Keep-Alive");
					conn.setRequestProperty("Charset", "UTF-8");
					conn.setConnectTimeout(5 * 1000);



					if(conn.getResponseCode() == 200) {
						Log.d("test", "*********************");
						DataInputStream dis = new DataInputStream(conn.getInputStream());
						String fileName = dis.readUTF();
						File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" +fileName);
						byte[] buff = new byte[1024];
						FileOutputStream fos = new FileOutputStream(file);
						int len =-1;
						while ((len = dis.read(buff))!=-1){
							fos.write(buff,0,len);
						}
						dis.close();
						fos.close();
						Log.d("downLoadFile","stop");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}