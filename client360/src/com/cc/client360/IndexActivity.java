package com.cc.client360;


import java.util.ArrayList;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKStep;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.cc.client360.bean.DriverBean;
import com.cc.client360.fragement.LeftFragment;
import com.cc.client360.location.Location;
import com.cc.client360.location.Location.OnGetDataListener;
import com.cc.client360.maphelper.MapHelper;
import com.cc.client360.tools.sp;
import com.cc.slidingmenu.lib.SlidingMenu;
import com.cc.slidingmenu.lib.app.SlidingFragmentActivity;
import com.cc.client360.R;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

/**
 * 首页
 * @author Administrator
 *
 */

public class IndexActivity extends BaseActivity{

	
	private static final int LOCATION = 555;
	
	private Button pw_daijia,pw_peijia;
	private PopupWindow popupWindow = null; 
	private int screenWidth = 0; 
	private int screenHeight = 0; 
	
	
	//百度
	private MapView mapView;
	public BMapManager mBMapMan = null;
	public Location location;
	private MKSearch searchModel;//百度地图搜索

	
	//抽屉效果
	private SlidingDrawer sd;
	private RelativeLayout gv,tou;
	private ImageView iv;
	private TextView jisaunjiage,yuan,gongli;
	private TextView jgjsq,cankaojiage;//文字需要根据代驾和陪嫁改变的
	private EditText from,to;
	
	
	@Override
	protected void initPage(){
		
		//百度
		initMap();
		
		//定位
		ininLocation();
		
		//测试
		//test();
		
		//抽屉效果
	    SlidingDrawer();
	    
	    //百度地图先搜索后测距
		souSuo();
	}
	@Override
	protected int setLayout() {
		return R.layout.index_activity;
	}
	@Override
	protected String setTitle() {
		return "首页地图";
	}
	@Override
	protected boolean onTopMidVisiable() {
		return true;
	}
	
	
	/**
	 * 在加载布局文件之前
	 */
	@Override
	protected void beforesetContentView() {
		mBMapMan = new BMapManager(getApplication());
	    mBMapMan.init("E12cf28e261d30e9c9e307c70064516e", null);
	}
	
	
	@Override
	protected void onClickSubActivity(View view) {
		switch (view.getId()) {
		case R.id.topMiddleBtn:
			topMiddleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_middle_btn));
			topMiddleTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_middle_tv));
			break;
		case R.id.topMiddleTv:
			topMiddleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_middle_tv));
			topMiddleTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_middle_btn));
			break;
		case R.id.rightbtn:
				 getPopupWindow(); 
				 if(popupWindow.isShowing()){
					System.out.println("  if(popupWindow.isShowing()){ ");
					 popupWindow.dismiss();  
				 }else{
					 System.out.println("popupWindow.showAsDropDown(topLayout); ");
					popupWindow.showAsDropDown(topLayout); 
				 }
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		mapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	
	
	/**
	 * 初始化地图
	 */
	private void initMap() {
		mapView = (MapView) findViewById(R.id.bmapsView);
		mapView.setBuiltInZoomControls(true);
		mapView.getController().setZoom(14);
		
		showMap();
	}

	/**
	 * 显示地图
	 */
	public void showMap() {
		mapView.setVisibility(View.VISIBLE);
	}
	
	
	/**
	 * 定位的初始化
	 */
	public void	ininLocation(){
		location = new Location(this, handler, LOCATION);
		location.startLocation();
		location.setOnGetDataListener(new OnGetDataListener() {
			@Override
			public void onGetDataAddress(String address) {
                  if(!"".equals(address)){
                	  System.out.println("index_address: "+address);
                	  test();
                  }				
			}
		});
	}
	
	
	@Override
	protected void dealWithMessage(Message msg) {
		switch (msg.what) {
		case LOCATION:
			break;
		}
		
	}
	
	
	/**
	 * 测试使用
	 */
	private void test(){
	    testData();
 	    MapHelper.setMyLocation(IndexActivity.this, mapView,location.latitude, location.longitude,location.mlocation,0,0);
 	    MapHelper.setAroundDriverOverLay(IndexActivity.this, mapView, list);
 	    from.setText(location.mlocation.getAddrStr());
	}
	
	
	
	
	
	public void SlidingDrawer(){
	    //价格计算器
	    jgjsq= (TextView) findViewById(R.id.jgjsq);
	    cankaojiage= (TextView) findViewById(R.id.cankaojiage);
		jisaunjiage= (TextView) findViewById(R.id.jisaunjiage);
		from= (EditText) findViewById(R.id.from);
		to= (EditText) findViewById(R.id.to);
		sd = (SlidingDrawer) findViewById(R.id.drawer1);
		//代驾
	    if(sp.getDriverSate(this).equals("代驾")){
	    	sd.setVisibility(View.VISIBLE);	
	    }else{
	    	sd.setVisibility(View.GONE);	
	    }
		gv = (RelativeLayout) findViewById(R.id.mycontent1);
		tou= (RelativeLayout) findViewById(R.id.myImage);
        iv = (ImageView) findViewById(R.id.upanddown);
        //几元
        yuan= (TextView) findViewById(R.id.yuan);
        //几公里
        gongli= (TextView) findViewById(R.id.gongli);
      //代驾
      if(sp.getDriverSate(this).equals("代驾")){
    	  jgjsq.setText("代驾价格计算器");
    	  cankaojiage.setText("很高心为您预测代驾价格,供君参考!");
      }
	  //估算价格按钮
	  jisaunjiage.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeMethodManager();
				 //Toast.makeText(IndexActivity.this,"估算价格",Toast.LENGTH_LONG).show();
				progressDialog = ProgressDialog.show(IndexActivity.this, "",
						"请稍候 ...", true);
				progressDialog.show(); 
				String destination = to.getText().toString();  
	                //设置起始地（当前位置）  
	                MKPlanNode startNode = new MKPlanNode();  
	                startNode.pt = new GeoPoint((int)(location.mlocation.getLatitude()*1e6), (int)(location.mlocation.getLongitude()*1e6));  
	                //设置目的地  
	                MKPlanNode endNode = new MKPlanNode();   
	                endNode.name = destination;  
	                //展开搜索的城市  
	                String city = "上海";  
	                searchModel.drivingSearch(city, startNode, city, endNode);   
	            }  
			}
	  );
	
	 // 设定SlidingDrawer被打开的事件处理 
	 sd.setOnDrawerOpenListener(new OnDrawerOpenListener()
	 {
		public void onDrawerOpened()
		{
			iv.setImageResource(R.drawable.d_down);
		}
	 });
	 // 设定SlidingDrawer被关闭的事件处理 
	sd.setOnDrawerCloseListener(new OnDrawerCloseListener()
	{
		public void onDrawerClosed()
		{
			iv.setImageResource(R.drawable.d_up);
			//清空
			to.setText("");
			yuan.setText("");
			gongli.setText("");
		}
	});
}
	

	/**
	 * 计算多少公里多少钱
	 */
	public void calculate(String km){
		 // int k = Integer.parse(km); 
		float k  = (float)((int)(Float.parseFloat(km)*100) /100); 
		
		int hour = com.cc.client360.tools.Tools.getHour();
		int price=0;
		System.out.println("计算多少公里多少钱: k "+k);
		System.out.println("计算多少公里多少钱: hour "+hour);
		
		  //代驾
	      if(sp.getDriverSate(this).equals("代驾")){
	    	  if(7<=hour && hour<22){
	    		  if(k<=10){
	    			  price=39;
	    		  }else{
	    			  int yu = (int)((k-10)/10);
	    			  System.out.println("计算多少公里多少钱: yu "+yu);
	    			  price= (39+20*(yu+1));
	    		  }
	    	  }else if(22<=hour && hour<23){
	    		  if(k<=10){
	    			  price=59;
	    		  }else{
	    			  int yu = (int)((k-10)/10);
	    			  price= (59+20*(yu+1));
	    		  }
	    	  }else if(23<=hour && hour<24){
	    		  if(k<=10){
	    			  price=79;
	    		  }else{
	    			  int yu = (int)((k-10)/10);
	    			  price= (79+20*(yu+1));
	    		  }
	    	  }else if(24<=hour && hour<7){
	    		  if(k<=10){
	    			  price=99;
	    		  }else{
	    			  int yu = (int)((k-10)/10);
	    			  price= (99+20*(yu+1));
	    		  }
	    	  }
	      }
	      //设置数据
	      yuan.setText(""+price);
		  gongli.setText(""+k);
		
	}
	
	/**
	 * 关闭软键盘
	 */
	private void closeMethodManager(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(jisaunjiage.getWindowToken(), 0);
	}
	
	
	   /**
	    * 获取PopupWindow实例 
	    */ 
	   private void getPopupWindow(){ 
	       if(null == popupWindow){ 
	            initPopupWindow(); 
	        }else{ 
	        } 
	   } 
	
	   /** 
	    * 创建PopupWindow 
	    */ 
	   private void initPopupWindow(){ 
	        //得到屏幕的宽度和高度 
	        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth(); 
	        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
	        System.out.println("屏幕的宽度  : "+screenWidth);
	        System.out.println("屏幕的高度  : "+screenHeight);
	        //获取自定义布局文件popupwindow.xml 
	        View popupWindow_view = getLayoutInflater().inflate(R.layout.popupwindow, null, false);         
	        //设置其背景 
	        // popupWindow_view.setBackgroundResource(R.drawable.rounded_corners_view); 
	        //创建popupWindow实例 
	        
	        DisplayMetrics metrics=getResources().getDisplayMetrics();
            float density = metrics.density;
	        
	        popupWindow = new PopupWindow(popupWindow_view,screenWidth,(int) (100*density), true); 
	        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_pop)); 
	        
	        //获取视图中的按钮与编辑框 
	        pw_daijia = (Button) popupWindow_view.findViewById(R.id.pw_daijia); 
	        pw_peijia = (Button) popupWindow_view.findViewById(R.id.pw_peijia); 
	        pw_daijia.setOnClickListener(new OnClickListener() { 
	            @Override 
	            public void onClick(View v) { 
	                popupWindow.dismiss(); 
	                sd.setVisibility(View.VISIBLE);	
	                sp.saveDriverSate(IndexActivity.this,"代驾");
	            } 
	        }); 
	        pw_peijia.setOnClickListener(new OnClickListener(){ 
	            @Override 
	            public void onClick(View v) { 
	                popupWindow.dismiss(); 
	                sd.setVisibility(View.GONE);
	                sp.saveDriverSate(IndexActivity.this,"陪驾");
	            } 
	        }); 
	   } 
	
	
	
		/** 测试数据*/
		public ArrayList<DriverBean> list;
		private void testData(){
			list = new ArrayList<DriverBean>();
			DriverBean driverBean_1 = new DriverBean();
			driverBean_1.userName = "范长龙龙1";
			driverBean_1.status=1;
			driverBean_1.point = 3.0d;
			driverBean_1.latitude = 31.201753;
			driverBean_1.longitude = 121.596419;
			driverBean_1.together = 0;
			list.add(driverBean_1);
			DriverBean driverBean_2 = new DriverBean();
			driverBean_2.userName = "范长龙2";
			driverBean_2.point = 3.0d;
			driverBean_2.status=1;
			driverBean_2.latitude = 31.198972;
			driverBean_2.longitude = 121.604853;
			driverBean_2.together = 1;
			list.add(driverBean_2);
			DriverBean driverBean_3 = new DriverBean();
			driverBean_3.userName = "范长龙3";
			driverBean_3.point = 3.0d;
			driverBean_3.status=2;
			driverBean_3.latitude = 31.201698;
			driverBean_3.longitude = 121.591539;
			driverBean_3.together = 0;
			list.add(driverBean_3);
			DriverBean driverBean_4 = new DriverBean();
			driverBean_4.userName = "范长龙龙3";
			driverBean_4.status=2;
			driverBean_4.point = 3.0d;
			driverBean_4.latitude = 31.198;
			driverBean_4.longitude = 121.592859;
			driverBean_4.together = 1;
			list.add(driverBean_4);
		}
	
		/**
		 * 搜索地图，并且测距
		 */
		public void souSuo(){
			//初始化搜索模块  
	        searchModel = new MKSearch();  
	        //设置路线策略为最短距离  
	        searchModel.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);  
	        searchModel.init(mBMapMan, new MKSearchListener() {  
	        	//获取驾车路线回调方法  
	        	@Override
				public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
	        		progressDialog.dismiss(); 
	                if (error != 0 || res == null) {  
	                    Toast.makeText(IndexActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();  
	                    return;  
	                }  
	                // 此处仅展示一个方案作为示例  
	                MKRoute route = res.getPlan(0).getRoute(0);  
	                int distanceM = route.getDistance();  
	                String distanceKm = String.valueOf(distanceM / 1000) +"."+String.valueOf(distanceM % 1000);  
	                System.out.println("距离:"+distanceKm+"公里---节点数量:"+route.getNumSteps());  
	                for (int i = 0; i < route.getNumSteps(); i++) {  
	                    MKStep step = route.getStep(i);  
	                    System.out.println("节点信息："+step.getContent());  
	                }  
	                //计算
	                calculate(distanceKm);
				}
				@Override
				public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				}
				@Override
				public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
				}
				@Override
				public void onGetPoiDetailSearchResult(int arg0, int arg1) {
				}
				@Override
				public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
				}
				@Override
				public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
				}
				@Override
				public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
				}
				@Override
				public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
				}
				@Override
				public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
				}  
	        });  
		}
}
