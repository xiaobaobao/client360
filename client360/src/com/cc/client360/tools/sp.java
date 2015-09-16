package com.cc.client360.tools;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences 保存少量的数据
 */
public final class sp{
	/**
	 * 保存左侧栏状态
	 * @param context
	 * @param value
	 */
	public static void saveSate(Context context, int  value){
		if(value == -1){
			return;
		}
		SharedPreferences sp = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("view", value);
		editor.commit();
	}
	/**
	 * 读取左侧栏状态
	 * @param context
	 * @return
	 */
	public static int getSate(Context context){
		int value=-1;
		SharedPreferences sp = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
		value = sp.getInt("view", -1);
		return value;
	}
	/**
	 * 保存代价和陪嫁的状态
	 * @param context
	 * @param value
	 */
	public static void saveDriverSate(Context context, String value){
		if("".equals(value)){
			return;
		}
		SharedPreferences sp = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("driversate", value);
		editor.commit();
	}
	/**
	 * 读取代价和陪嫁的状态
	 * @param context
	 * @return
	 */
	public static String getDriverSate(Context context){
		String value="";
		SharedPreferences sp = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
		value = sp.getString("driversate","");
		return value;
	}
	/**
	 * 清除数据
	 */
	public static void cleanData(Context context) {
		SharedPreferences sp = context.getSharedPreferences("INFO",Context.MODE_PRIVATE);
		Editor editor = sp.edit(); 
		editor.remove("view");
		editor.remove("driversate");
		editor.commit();
	}
	
	
	
	
	
	
	
	
//	public static String getUserPhone(Context context){
//		SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//
//		return sp.getString("userPhone", "");

		//return sp.getString("userPhone", "15221233762");
//		return sp.getString("userPhone", "18391639712");

//	}
	
//	public static boolean isLogin(Context context){
//		if(getUserPhone(context).equals("")){
//			return false;
//		}
//		return true;
//	}
	
	/**
	 * 清除用户数据
	 */
//	public static void cleanLogin(Context context) {
//		SharedPreferences sp = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
//		Editor editor = sp.edit(); 
//		editor.remove("userName");
//		editor.remove("userPhone");
//		editor.commit();
//	}
//	
	
	
	
	
//	public static void saveRefrushTime(Context context, String time){
//		SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//		Editor editor = sp.edit();
//		editor.putString("RefrushTime",time);
//		editor.commit();
//	}
//	public static String getRefrushTime(Context context){
//		SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//		return sp.getString("RefrushTime", "");
//	}
//	
	
	
	
}
