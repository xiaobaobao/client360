package com.cc.client360.maphelper;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.cc.client360.bean.DriverBean;
//import com.cc.dDriver360.IndexActivity;
//import com.cc.client360.bean.DriverBean;
//import com.cc.dDriver360.fragment.DriverDetailFragment;


/**
 * 地图上的司机覆盖物
 * @author sheng
 *
 */
public class DriverOverlay extends ItemizedOverlay<OverlayItem>{
	private Context context;
	private ArrayList<DriverBean> list;
	public DriverOverlay(Context c, Drawable marker, MapView mapView) {
		super(marker, mapView);
		this.context = c;
		System.out.println("DriverOverlay");
		
	}
	@Override
	public boolean onTap(GeoPoint geoPoint, MapView mapView) {
		System.out.println("你点击了item...");
		return super.onTap(geoPoint, mapView);
	}
	
	@Override
	protected boolean onTap(int index) {
		super.onTap(index);
		System.out.println("你点击了item..."+index);
		if(list == null || list.size() == 0){
			return false;	
		}
		
		DriverBean driverBean = list.get(index);
		if(driverBean == null){
			return false;
		}
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("driverBean", driverBean);
//		indexActivity.switchFragment(new DriverDetailFragment(), bundle);
		
		Bundle bundle = new Bundle();
		bundle.putString("userPhone",driverBean.userPhone);
		bundle.putString("isFlag",driverBean.isFlag);
		//indexActivity.switchFragment(new DriverDetailFragment(), bundle);
		return true;
	}
	
	public void setData(ArrayList<DriverBean> list){
		this.list = list;
	}
}
