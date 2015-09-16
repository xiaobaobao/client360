package com.cc.client360.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


/**
 * 司机详情
 * @author sheng
 *
 */
public class DriverDetailHandler extends DefaultJSONData{
	
	
	
	
	
	/** 姓名*/
	public  String username;
	/** 头像*/
	public String thumb;
	/** 评分等级*/
	public String point;
	/** */
	public String status;
	/** 驾龄 */
	public String drivingYears;
	/** 籍贯*/
	public String address;
	/** 代驾次数 */
	public String chasers;
	/** 驾照 */
	public String drivingLicense;
	
	/** 平常使用的手机号码*/
	public String Usually;
	
	
	@Override
	protected void parseJson(JSONObject object) {
		JSONObject data = object.optJSONObject("data");
		if(data != null){
			JSONObject detail = data.optJSONObject("detail");
					if(detail != null){
						username = detail.optString("userName", "");
						thumb = detail.optString("thumb", "");
						point = detail.optString("point", "");
						status = detail.optString("status", "");
						drivingYears = detail.optString("drivingYears", "");
						address = detail.optString("address", "");
						chasers = detail.optString("chasers", "");
						drivingLicense = detail.optString("drivingLicense", "");
						Usually=detail.optString("usually", "");
				}
			}
			
		}
}
