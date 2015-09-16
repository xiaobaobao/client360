package com.cc.client360.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cc.client360.handler.DefaultJSONData;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Time;
import android.util.Log;

/**
 * 网络请求类
 * 
 * @author sheng
 * 
 */
public final class Tools {
	private static final String TAG = "RequestNet";

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求的url地址
	 * @param params
	 *            上传的参数
	 * @param jsonData
	 *            json解析器
	 * @param saveCache
	 *            true,设置缓存； false,不设置缓存
	 * @return 请求结果状态码, -1协议错误
	 * 
	 */
	public static int reqPost(Context context, String url,
			HashMap<String, String> params, DefaultJSONData jsonData,
			boolean saveCache, String fileName) {

		if (url == null
				|| !(url.startsWith("http://") || url.startsWith("https://"))) {
			Log.e(TAG, "协议错误！url应以\"http://\"开头，或以\"https://\"开头");
			return -1;
		}
		int requestValue = 2;
		Log.e("post请求url", url);
		if (params == null) {
			params = new HashMap<String, String>();
		}
		String sign = md5(PublicParams.getToken());
		params.put("sign", sign);
		ArrayList<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> m : params.entrySet()) {
			Log.e("m.getKey()", m.getKey() + " " + m.getValue());
			postData.add(new BasicNameValuePair(m.getKey(), m.getValue()));
		}

		HttpPost httpPost = new HttpPost(url);
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
		HttpConnectionParams.setSoTimeout(httpParams, 0);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData,
					HTTP.UTF_8);
			httpPost.setEntity(entity);
			Log.i("post请求start", "connect");
			response = httpClient.execute(httpPost);
			Log.i("post请求end", "end connect");
			Log.e("post请求statu", response.getStatusLine().getStatusCode() + "");

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				requestValue = 1;
			} else {
				requestValue = 2;
			}
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			requestValue = 3;
		} catch (Exception e) {
			e.printStackTrace();
			requestValue = 2;
		}

		if (requestValue == 1) {// 请求成功
			try {
				HttpEntity entity = response.getEntity();
				String buffData = EntityUtils.toString(entity, HTTP.UTF_8)
						.trim();
				if (saveCache) {
					saveJsonToSDCard(buffData, fileName);
				}
				Log.e("buffData", buffData);
				if (jsonData != null) {
					if (buffData.startsWith("{")) {
						JSONObject object = new JSONObject(buffData.toString());
						jsonData.parse(object);
					} else if (buffData.startsWith("[")) {
						JSONArray object = new JSONArray(buffData.toString());
						jsonData.parse(object);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("return requestValue:"+requestValue);
		return requestValue;
	}

	/**
	 * GET请求并解析返回的JSON
	 */
	public static DefaultJSONData requestToParse(Context context, String url,
			DefaultJSONData jsonData) {
		int responseValue = 0;
		// settings = context.getSharedPreferences(Constant.USER, 0);
		HttpGet httpGet = new HttpGet(url);

		HttpClient httpClient = new DefaultHttpClient(/* httpParams */);
		HttpResponse response = null;

		try {
			// Log.i("99", "connect");
			response = httpClient.execute(httpGet);
			// Log.i("99", "end connect");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseValue = 1;
			} else {
				responseValue = 2;
				httpGet.abort();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			responseValue = 2;
		} catch (ClientProtocolException e){
			e.printStackTrace();
			responseValue = 2;
		} catch (IOException e) {
			e.printStackTrace();
			responseValue = 2;
		}

		// 响应正常
		if (responseValue != 2) {
			try {
				HttpEntity httpEntity = response.getEntity();
				InputStream inputStream = httpEntity.getContent();
				StringBuffer buff = new StringBuffer();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				String temp = null;
				while ((temp = reader.readLine()) != null) {
					buff.append(temp);
				}
				System.out.println("Get请求返回数据：：" + buff.toString());
				Log.i("Get请求返回数据：：", buff.toString());
				// System.out.println("Get请求返回数据：：" + buff.toString());
				String data = buff.toString();
				if (data.startsWith("{")) {
					JSONObject object = new JSONObject(buff.toString().trim());
					jsonData.parse(object);
				} else if (data.startsWith("[")) {
					JSONArray object = new JSONArray(buff.toString());
					jsonData.parse(object);
				}

				responseValue = 1;// 解析成功
			} catch (Exception e) {
				e.printStackTrace();
				responseValue = 3;
			}
		}
		return jsonData;
	}

	/**
	 * 将json保存到sd卡
	 */
	public static void saveJsonToSDCard(String data, String fileName) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String sdPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/jdker/";
			File file = new File(sdPath);
			if (!file.exists())
				file.mkdirs();
			File jsonFlie = new File(file, fileName);
			try {
				jsonFlie.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				FileOutputStream outStream = new FileOutputStream(jsonFlie);
				outStream.write(data.getBytes());
				outStream.flush();
				outStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 没有网络对话框
	 * 
	 * @param con
	 */
	public static void netError(Context con) {
		AlertDialog.Builder adb = new AlertDialog.Builder(con);
		adb.setTitle("温馨提示");
		adb.setMessage("没有网络");
		adb.setPositiveButton("确定", null);
		adb.show();
	}

	/**
	 * MD5加密字符
	 */
	public static String md5(String source) {

		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			digest.update(source.getBytes());
			byte[] mess = digest.digest();
			return toHexString(mess);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return source;
		// }

	}

	public static String toHexString(byte[] b) { // byte to String
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		Log.d("test", "md 5 加密的信息是：sb.toString()=" + sb.toString());
		return sb.toString();
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAccessNetwork(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity.getActiveNetworkInfo() != null
				&& connectivity.getActiveNetworkInfo().isAvailable()) {
			return true;
		}
		return false;
	}
	/**
	 * 获取SD卡剩余空间，无卡则返�?1
	 */
	public static long getSDSize() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			String path = Environment.getExternalStorageDirectory().getPath();
			StatFs statFs = new StatFs(path);
			long l = statFs.getBlockSize();
			return statFs.getAvailableBlocks() * l;
		}

		return -1;
	}
	
	
	/**
	 * 获取系统时间
	 * @return
	 */
	public static int getHour(){
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
		t.setToNow(); // 取得系统时间。
		int year = t.year;
		int month = t.month;
		int date = t.monthDay;
		int hour = t.hour;   // 0-23
		return hour;
	}
	
	
	
	
	
	
	
}
