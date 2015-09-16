package com.cc.client360.handler;

import org.json.JSONArray;
import org.json.JSONObject;

public class DefaultJSONData {
	
	public int state = 0;
	public String message = "";
	
	public void parse(JSONObject object){
		System.out.println("----------------");
		if(object == null){
			return;
		}
		
		state = object.optInt("status");
		
		System.out.println("object.optInt(\"status\")="+object.optInt("status"));
		message = object.optString("message", "");
		if(state == 1){
			parseJson(object);
		}
	}
	
	public void parse(JSONArray array){
		
	}
	
	protected void parseJson(JSONObject object){};

}
