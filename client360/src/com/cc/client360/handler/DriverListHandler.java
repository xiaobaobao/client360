package com.cc.client360.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cc.client360.bean.DriverBean;

import android.content.Context;
import android.util.Log;


/**
 * 司机列表
 * @author
 *
 */
public class DriverListHandler extends DefaultJSONData{
	
	public ArrayList<DriverBean> list;
	
	public DriverBean driverBean;
	
	private Context context;
	/** 每页多少条信息*/
	public int pageSize;
	/** 总页数*/
	public int pageNum;
	/** 当前页数*/
	public int pageNow;
	
	
	@Override
	protected void parseJson(JSONObject object) {
		JSONObject data = object.optJSONObject("data");
		if(data != null){
			JSONArray orderList = data.optJSONArray("orderList");
			if(orderList != null){
				System.out.println("(driverList != null) : ");
				list = new ArrayList<DriverBean>();
				for(int i=0;i<orderList.length();i++){
					System.out.println("driverList.length() 真数据: "+orderList.length());
					JSONObject driver = orderList.optJSONObject(i);
					if(driver != null){
						driverBean = new DriverBean();
						driverBean.userName = driver.optString("userName", "");
						driverBean.point = driver.optDouble("point", 0);
						driverBean.status = driver.optInt("status", 3);
						driverBean.userPhone=driver.optString("userPhone","");
						
							driverBean.address = driver.optString("address", "");
							driverBean.drivingYears = driver.optString("drivingYears", "");
							driverBean.longitude = driver.optDouble("lng", 0);
							driverBean.latitude = driver.optDouble("lat", 0);
							driverBean.count = driver.optString("chasers", "");
							driverBean.thumb= driver.optString("thumb", "");
							driverBean.isFlag = "1";
							list.add(driverBean);
						}
					}
				}
			}
			
			JSONArray driverList = data.optJSONArray("driverList");
			if(driverList != null){
				for(int i=0;i<driverList.length();i++){
					System.out.println("driverList.length() 假数据: "+driverList.length());
					JSONObject driver = driverList.optJSONObject(i);
					if(driver != null){
						driverBean = new DriverBean();
						driverBean.userName = driver.optString("userName", "");
						driverBean.point = driver.optDouble("point", 0);
						driverBean.status = driver.optInt("status", 3);
						driverBean.userPhone=driver.optString("userPhone","");
						driverBean.address = driver.optString("address", "");
						driverBean.drivingYears = driver.optString("drivingYears", "");
						driverBean.longitude = driver.optDouble("lng", 0);
						driverBean.latitude = driver.optDouble("lat", 0);
						driverBean.count = driver.optString("chasers", "");
						driverBean.isFlag = "2";//假
						list.add(driverBean);
					}
				}
			}
			
			
			JSONObject page = data.optJSONObject("page");
			if(page != null){
				pageSize = page.optInt("pageSize", 0);
				pageNum = page.optInt("pageNum", 0);
				pageNow = page.optInt("pageNow", 0);
			}
		}
}
