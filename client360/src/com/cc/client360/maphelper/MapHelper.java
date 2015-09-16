package com.cc.client360.maphelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.cc.client360.R;
import com.cc.client360.bean.DriverBean;
//import com.cc.dDriver360.IndexActivity;
//import com.cc.dDriver360.R;
//import com.cc.dDriver360.fragment.DriverDetailFragment;

/**
 * 地图帮助类
 * 
 * @author sheng
 * 
 */
public final class MapHelper {

	
	public static View viewCache;
	
	
	/**
	 * 设置我的位置
	 */
	public static void setMyLocation(Context context, MapView mapView,
			double latitude, double longitude,BDLocation location,int r,int o) {
		
		
		viewCache = LayoutInflater.from(context).inflate(R.layout.pop_layout, null);
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mapView);
		LocationData locData = new LocationData();
		// 手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要使用百度经纬度坐标（bd09ll）
		locData.latitude = latitude;
		locData.longitude = longitude;
		locData.direction = 2.0f;
		myLocationOverlay.setData(locData);
		
		
		TextView popText = ((TextView)viewCache.findViewById(R.id.location_tips));
		RelativeLayout pop_lay= ((RelativeLayout) viewCache.findViewById(R.id.pop_lay));
		if(location!=null){
			if("".equals(location.getStreet()) &&
			   "".equals(location.getStreetNumber())){
				popText.setText("");
			}else{
				popText.setText(location.getStreet()+location.getStreetNumber());
			}
			
		}
		
//		System.out.println(".................."+pop_lay);
		
				 
		Bitmap bm=viewCovertBitmap(pop_lay); //xxx根据你的情况获取  
		BitmapDrawable bd= new BitmapDrawable(context.getResources(), bm);   
		// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。   
		 //Drawable myMarker = null;
//				context.getResources().getDrawable(
//				R.drawable.overlay_mylocation);
		myLocationOverlay.setMarker(bd);
		mapView.getOverlays().add(myLocationOverlay);
		mapView.refresh();
		mapView.getController().animateTo(
				new GeoPoint((int) (latitude * 1e6), (int) (longitude * 1e6)));
		
//		mapView.getController().setRotation(r);
//		mapView.getController().setOverlooking(o);
		
		
//		PopupOverlay pop = new PopupOverlay(mapView,new PopupClickListener() {                  
//	        @Override  
//	        public void onClickedPopup(int index) {  
//	                //在此处理pop点击事件，index为点击区域索引,点击区域最多可有三个  
//	        }  
//	      });  
//		showPopupOverlay(location,pop);

		
	}

	public static void setAroundDriverOverLay(Context context,
			MapView mapView, ArrayList<DriverBean> list) {
		if (list == null || list.size() == 0) {
			return;
		}

		int max = 10;
		if (list.size() < 10) {
			max = list.size();
		}
		DriverOverlay driverOverlay = new DriverOverlay(context, null, mapView);
		driverOverlay.setData(list);
		for (int i = 0; i < max; i++) {
			DriverBean driveBean = list.get(i);
			if (driveBean != null) {
				GeoPoint geoPoint = new GeoPoint(
						(int) (driveBean.latitude * 1E6),
						(int) (driveBean.longitude * 1E6));
//				System.out.println("View view = getItemView(context, driveBean);");
				
				View view = getItemView(context, driveBean);
//				System.out.println("Bitmap bitmap = viewCovertBitmap(view);");
				Bitmap bitmap = viewCovertBitmap(view);
				if(bitmap != null){
					bitmap = scaleBitmap(bitmap, 0.7f);
//					System.out.println("bitmap不为空....");
					BitmapDrawable marker = new BitmapDrawable(bitmap);
					OverlayItem item = new OverlayItem(geoPoint, "", "");
					item.setMarker(marker);
					item.setAnchor(0.2f, 0.9f);
					driverOverlay.addItem(item);
				}
			}
		}
		mapView.getOverlays().add(driverOverlay);
		mapView.refresh();
	}
	
	/**
	 * view转为bitmap
	 * @param view
	 */
	private static Bitmap viewCovertBitmap(View view){
		view.setDrawingCacheEnabled(true);
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		return view.getDrawingCache();
	}
	/**
	 * 
	 * @param context
	 * @param name
	 * @param point
	 * @return
	 */
	private static View getItemView(final Context context,
			final DriverBean driveBean) {
		int layout = 0;
		if (driveBean.status == 2) {
			layout = R.layout.overlayitem;
		} else if(driveBean.status == 1){
			layout = R.layout.overlayitem_together;
		}
//		System.out.println("111");
		View view = LayoutInflater.from(context).inflate(layout, null);
//		System.out.println("222");
		TextView nameTv = (TextView) view.findViewById(R.id.nameTv);
		ImageView star = (ImageView) view.findViewById(R.id.star);
		int count = (int) driveBean.point;
		nameTv.setText(driveBean.userName);
		Bitmap starBitmap = scaleBitmap(drawStars(context, count, 5), 0.5f);
		star.setImageBitmap(starBitmap);
		return view;
	}

	
	/**
	 * 画星星
	 * 
	 * @param num
	 *            评分
	 * @param total
	 *            总数
	 */
	private static Bitmap drawStars(Context context, int num, int total) {
		Bitmap normal = ((BitmapDrawable) context.getResources().getDrawable(
				R.drawable.star_gray)).getBitmap();
		Bitmap select = ((BitmapDrawable) context.getResources().getDrawable(
				R.drawable.star_yellow)).getBitmap();
		int width = normal.getWidth() + 3;
		int height = normal.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(total * width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		int x = 0;
		for (int i = 0; i < total; i++) {
			if (i < num) {
				canvas.drawBitmap(select, x, 0, null);
			} else {
				canvas.drawBitmap(normal, x, 0, null);
			}
			x += width;
		}
		
		return bitmap;
	}
	
	/**
	 * 缩放bitmap
	 * @param bitmap
	 * @param scale
	 * @return
	 */
	private static Bitmap scaleBitmap(Bitmap bitmap, float scale){
		if(bitmap == null){
			return null;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
	}
	
	
	
	
	public static String getFromAssets(Context context,String fileName){ 
		String line="";
        String Result="";
		try { 
             InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) ); 
            BufferedReader bufReader = new BufferedReader(inputReader);
            
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
		return Result;
     } 
	
	
	
	public static void showPopupOverlay(BDLocation location,PopupOverlay popupOverlay){
		 TextView popText = ((TextView)viewCache.findViewById(R.id.location_tips));
		 RelativeLayout pop_lay= ((RelativeLayout) viewCache.findViewById(R.id.pop_lay));
		 popText.setText(location.getAddrStr());
//		 System.out.println(".................."+pop_lay);
		 popupOverlay.showPopup(viewCovertBitmap(pop_lay),
					new GeoPoint((int)(location.getLatitude()*1e6), (int)(location.getLongitude()*1e6)),
					10);
	 }
	/**
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
	}
	
	
	/**
	 * 画星星
	 * @param num
	 *          评分
	 * @param total
	 *          总数
	 */
	public static Bitmap drawStarsWithHalf(Context context,double num, int total){
		int intNum = (int)num;
		//System.out.println("intNum="+intNum);
		double halfNum = num - intNum;
		//System.out.println("halfNum="+halfNum);
		Bitmap normal = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.star_gray)).getBitmap();
		Bitmap select = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.star_yellow)).getBitmap();
		Bitmap half = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.star_gray)).getBitmap();
		int width = normal.getWidth() + 6;
		int height = normal.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(total * width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		int x = 0;
		for (int i = 0; i < total; i++) {
			if(i < intNum){
				canvas.drawBitmap(select, x, 0, null);
			}else if(intNum == i && halfNum <= 0.5){
				canvas.drawBitmap(half, x, 0, null);
			}else if(intNum == i && halfNum > 0.5){
				canvas.drawBitmap(select, x, 0, null);
			}else{
				canvas.drawBitmap(normal, x, 0, null);
			}
			x += width;
		}
		return bitmap;
	}
	
} 
	
	
	
	
	
	
	
	
	
	
	
	

