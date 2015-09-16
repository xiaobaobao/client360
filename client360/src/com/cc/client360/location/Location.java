package com.cc.client360.location;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 百度定位类
 * @author sheng
 *
 */
public class Location {
	public LocationClient mLocationClient = null;
	/** 经纬度*/
	public double latitude, longitude;
	/** 地址*/
	public String address;
	
	public BDLocation mlocation;
	
	private Handler handler;
	private int msgCode;
	
	private OnGetDataListener listener; 
	
	
	/**
	 * @param context
	 * @param handler  
	 * @param msgCode  
	 */
	public Location(Context context, Handler handler, int msgCode){
		this.handler = handler;
		this.msgCode = msgCode;
		mLocationClient = new LocationClient(context.getApplicationContext());     //声明LocationClient类
		setOption();
//	    mLocationClient.start();
	    mLocationClient.registerLocationListener(locationListener);
	    
//	    listener  = 
	}
	
	/** 
	 * 发起定位
	 */
	public void startLocation(){
		if(mLocationClient != null && !mLocationClient.isStarted()){
			mLocationClient.start();
		}

	    if(mLocationClient.isStarted()){
	    	mLocationClient.requestLocation();
	    }
	    Log.d("test","发起定位！");
	    System.out.println("发起定位！ 发起定位！发起定位！发起定位！");
	}
	
	private BDLocationListener locationListener = new BDLocationListener() {
		
		@Override
		public void onReceivePoi(BDLocation location) {
			
		}
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			if(location == null){
				mLocationClient.requestLocation();
				System.out.println("sddgdfddddddddddddd");
				return;
			}
			mlocation = location;
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			address = location.getStreet();
			if(handler != null){
				System.out.println("---handler");
				//刷新
				handler.sendEmptyMessage(msgCode);
			}
			System.out.println("location_address="+address);
			if(!"".equals(address) && listener!=null){
				listener.onGetDataAddress(address);
			}
		}
	};
	
	private void setOption(){
		LocationClientOption option = new LocationClientOption();
	    option.setOpenGps(true);
	    option.setAddrType("all");//返回的定位结果包含地址信息
	    option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
	    option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
	    option.disableCache(true);//禁止启用缓存定位
	    option.setPoiNumber(5);	//最多返回POI个数	
	    option.setPoiDistance(1000); //poi查询距离		
	    option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息		
	    mLocationClient.setLocOption(option);
	}
	
	
	
	public void setOnGetDataListener(OnGetDataListener l){
		listener = l;
	}
	public interface OnGetDataListener{
		public void onGetDataAddress(String address);
	}
	
	
	
	
	
	
	
}
