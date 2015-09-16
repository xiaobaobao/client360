package com.cc.client360;


import java.util.HashMap;

//import cc.android.supu.WelcomeActivity;
//import cc.android.supu.tools.Update;

//import cc.android.supu.IndexActivity;

//import com.cc.client360.handler.UpdateHandler;
//import com.cc.dDriver360.tools.ConstantUrl;
//import com.cc.dDriver360.tools.Tools;
//import com.cc.dDriver360.tools.Update;
//import com.cc.dDriver360.tools.UserInfoTools;
//import com.cc.dDriver360.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 欢迎页面
 * @author Administrator
 *
 */

public class WelcomeActivity extends Activity {
     
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.welcome);	
	    	handler.sendEmptyMessageDelayed(7, 2000);
	  }
	 
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 5:
				break;
			case 6:
				break;	
			case 7:
				startActivity(new Intent(WelcomeActivity.this, SelectActivity.class));
				finish();
				break;	
		 };
	   };
	};
	
	
	
	/**
	 * 
	 * 获取版本信息
	 * 
	 */
//	private void getVersionInfo(){
//		try {
//			 PackageManager pm = getPackageManager();
//			 PackageInfo pinfo = pm.getPackageInfo(getPackageName(),PackageManager.GET_CONFIGURATIONS);
//			 String versionName = pinfo.versionName;
//			 int versionCode = pinfo.versionCode;
//			 version=versionName;
//			 System.out.println("获取版本信息");
//			 System.out.println("versionName:"+versionName);
//			 System.out.println("versionCode:"+versionCode);
//			 } catch (NameNotFoundException e) {
//				 System.out.println("获取版本信息异常*********");
//			 }
//	}
//	
	
//	
//	private boolean checkUpdateFlag=false;
//	private String version="";
//	private String type="";
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		setContentView(R.layout.welcome);
//		getVersionInfo();
//		
//		 type=getApplicationContext().client_type+"";
//		//检查是否要更新
//	    requestUpdateInfo();
//	   
//	}
//	public DDApplication getApplicationContext() {
//		return (DDApplication) super.getApplicationContext();
//	}
//	
	
	
	
	/**
	 * 升级信息请求
	 */
//	private void requestUpdateInfo() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				updateHandler = new UpdateHandler();
//				HashMap<String, String> params = new HashMap<String, String>();
//				params.put("versions",version);
//				params.put("type",type);
//				int responseValue = Tools.reqPost(WelcomeActivity.this,ConstantUrl.UPDATE,params,updateHandler, false,null);
//				checkUpdateFlag=true;
//				System.out.println("responseValue "+responseValue);
//				System.out.println("updateHandler.result_code "+updateHandler.result_code);
//				
//				if (responseValue == 1 && updateHandler.result_code == 1) {
//					if(updateHandler.newVersion.equals("1")){
//					   //需要更新
//						System.out.println(" //需要更新 //需要更新");
//					  handler.sendEmptyMessage(5);
//					}else{
//					  handler.sendEmptyMessage(6);
//					  //handler.sendEmptyMessageDelayed(6, 3000);
//					}
//				} else {
//					handler.sendEmptyMessage(7);
//				}
//			}
//		}).start();
//	}
	
	
}
