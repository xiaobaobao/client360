package com.cc.client360;

import java.util.LinkedList;

import com.cc.client360.tools.sp;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.BDNotifyListener;
//import com.baidu.location.LocationClient;
//import com.cc.client360.qinglv.tool.*;
//import com.cc.client360.qinglv.tool.Location.MyLocationListenner;
//import com.cc.client360.qinglv.tool.Location.NotifyLister;
//import com.cc.client360.qinglv.tool.Location.OnGetDataListener;

import android.app.Activity;
import android.app.Application;
import android.os.Process;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

/**
 * 360客户端
 * @author Administrator
 *
 */
public class ExpressApplication extends Application{
	
	
	public final static String tag="ExpressApplication";
	
	public int indexBg;
	
	public final static Class<?> IndexActivity = IndexActivity.class;
	
	public LinkedList<Activity> activities = new LinkedList<Activity>();
	
	public static Class<?>[] bottomsClazz = new Class<?>[] {
	IndexActivity.class,
	AppointmentActivity.class, InvitationActivity.class,
	MoreActivity.class,PriceActivity.class,
	};
	
	
	
	@Override
	public void onCreate() {
		System.out.println("ExpressApplication  onCreate...............");
		super.onCreate();
	}
	
	/**
	 * 添加新的activity
	 * @return
	 */
	public  void add(Activity activity) {		
		activities.add(activity);
	}
	
	
	/**
	 * 关闭所有的activity
	 */
	public void closeAllActivity() {
		for (Activity activity : activities) {
			if (activity != null) {
				activity.finish();
			}
		}
	}
	
	/**
	 * 弹出指定的Activity
	 */
//	public void pollActivity() {
//		for (Activity activity : activities) {
//			if (activity.getClass().getSimpleName().equals("IndexActivity")) {
//				activity.finish();
//			}
//		}
//	}
	
	/**
	 * 删除指定activity
	 * 
	 * @param activity
	 */
	public synchronized void popActivity(Activity activity) {

		if (activity != null) {
			activity.finish();
			activities.remove(activity);
		}

	}
	/**
	 * 退出系统
	 */
	public void exit(){
		System.out.println("退出系统");
		sp.cleanData(this);
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
	
	/**
	 * 删除非底部导航activity
	 */
	public void delNotBottomActivity() {
		
		Boolean flag=false;
		
		for (int i = 0; i < activities.size(); i++) {
			flag=false;
			for(int j=0;j<bottomsClazz.length;j++){
				
				if (activities.get(i).getClass().getSimpleName().equals(bottomsClazz[j].getSimpleName())) {					
					flag=true;
					break;
			    }	
		    }
			if(!flag){				
				popActivity(activities.get(i));
				if (i >= 1){
					i--;
				}
			}	
	 }
  }
	
	
	
	
	
	
	//**************************************************

	
	
//	@Override
//	public void onCreate() {
//		System.out.println("onCreate...............");
//		mLocationClient = new LocationClient( this );
////		locationClient = new LocationClient( this );
////		LocationClient = new LocationClient( this );
//		mLocationClient.registerLocationListener( myListener );
////		locationClient.registerLocationListener( listener );
////		LocationClient.registerLocationListener( locListener );
//		//位置提醒相关代码
////		mNotifyer = new NotifyLister();
////		mNotifyer.SetNotifyLocation(40.047883,116.312564,3000,"gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
////		mLocationClient.registerNotify(mNotifyer);
//		
//		super.onCreate(); 
//		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());
//	}
	
	/**
	 * 显示字符串
	 * @param str
	 */

	
	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
//	public class MyLocationListenner implements BDLocationListener {
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			System.out.println("2------------");
//			if (location == null)
//				return ;
//			String city=null;
//			String latitude=null;//纬度
//			String longitude=null;//经度
			
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("time : ");
//			sb.append(location.getTime());
//			sb.append("\nerror code : ");
//			sb.append(location.getLocType());
//			sb.append("\nlatitude : ");
//			sb.append(location.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(location.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(location.getRadius());
			//获取经度和纬度
//			latitude = String.valueOf(location.getLatitude());
//			longitude = String.valueOf(location.getLongitude());
//			if (location.getLocType() == BDLocation.TypeGpsLocation){
//				sb.append("\nspeed : ");
//				sb.append(location.getSpeed());
//				sb.append("\nsatellite : ");
//				sb.append(location.getSatelliteNumber());
//			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//				sb.append("\n省：");
//				sb.append(location.getProvince());
//				sb.append("\n市：");
//				sb.append(location.getCity());
//				sb.append("\n区/县：");
//				sb.append(location.getDistrict());
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//				
//				city=location.getCity();
//			}
//			sb.append("\nsdk version : ");
//			sb.append(mLocationClient.getVersion());
//			sb.append("\nisCellChangeFlag : ");
//			sb.append(location.isCellChangeFlag());
//			MyDebug.printf("ss2222s"," "+city+" "+latitude+"   "+longitude);
//			if(listener != null && latitude != null  && longitude != null){
//				//listener.onGetData(city);
//				MyDebug.printf("ssssssss"," "+city+" "+latitude+"   "+longitude);
//				listener.onGetDataLatAndLng(latitude, longitude);
//			}
			//logMsg(city);
//			Log.i(TAG, sb.toString());
//		}
		
//		public void onReceivePoi(BDLocation poiLocation) {
//			if (poiLocation == null){
//				return ; 
//			}
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("Poi time : ");
//			sb.append(poiLocation.getTime());
//			sb.append("\nerror code : "); 
//			sb.append(poiLocation.getLocType());
//			sb.append("\nlatitude : ");
//			sb.append(poiLocation.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(poiLocation.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(poiLocation.getRadius());
//			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
//				sb.append("\naddr : ");
//				sb.append(poiLocation.getAddrStr());
//			} 
//			if(poiLocation.hasPoi()){
//				sb.append("\nPoi:");
//				sb.append(poiLocation.getPoi());
//			}else{				
//				sb.append("noPoi information");
//			}
//			logMsg(sb.toString());
//		}
//	}
	
//	public class NotifyLister extends BDNotifyListener{
//		public void onNotify(BDLocation mlocation, float distance){
//			System.out.println("通知。。。。。。。。。。。。");
//			mVibrator01.vibrate(1000);
//		}
//	}
//	
//	
//	public void setOnGetDataListener(OnGetDataListener l){
//		listener = l;
//	}
//	public interface OnGetDataListener{
//		//public void onGetData(String city);
//		//纬度 //经度
//		public void onGetDataLatAndLng(String lat,String lng);
//	}
//	
	
	
	
	
	
}
