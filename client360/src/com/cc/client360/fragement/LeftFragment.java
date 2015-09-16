package com.cc.client360.fragement;


import java.util.ArrayList;

import com.cc.client360.AppointmentActivity;
import com.cc.client360.ExpressApplication;
import com.cc.client360.IndexActivity;
import com.cc.client360.InvitationActivity;
import com.cc.client360.MoreActivity;
import com.cc.client360.PriceActivity;
import com.cc.client360.tools.sp;
import com.cc.slidingmenu.lib.app.SlidingFragmentActivity;
import com.cc.client360.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 左侧边栏
 * @author 
 *
 */

//@SuppressLint("ValidFragment")
public class LeftFragment extends Fragment implements OnClickListener{
	
	public RelativeLayout state, location, order, wallet, notice;
	public TextView left_title;
    
	private ArrayList<RelativeLayout> layouts;
	
	private int selected = -1;
//	private Context context;
	private Intent intent;
	
//	public LeftFragment(Context c){
//		context=c;
//	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//保持Fragment的实例，在Activity重新创建时可以不完全销毁Fragment
		setRetainInstance(true);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//获取左侧栏的contentview
		View contentview = inflater.inflate(R.layout.leftmenu, null);
		initView(contentview);
		System.out.println("initView(contentview);...");
		System.out.println("当前的页面Activity： "+getBaseActivity().getClass().getSimpleName());
		return contentview;
	}
	
	/**
	 * 此处实例化左侧栏中的view控件
	 * @param view
	 */
	private void initView(View view){
        left_title =(TextView) view.findViewById(R.id.left_title);
		
		layouts = new ArrayList<RelativeLayout>();
		state = (RelativeLayout) view.findViewById(R.id.state);
		state.setOnClickListener(this);
		layouts.add(state);
		
		location = (RelativeLayout) view.findViewById(R.id.location);
		location.setOnClickListener(this);
		layouts.add(location);
		
		order = (RelativeLayout) view.findViewById(R.id.order);
		order.setOnClickListener(this);
		layouts.add(order);
		
		wallet = (RelativeLayout) view.findViewById(R.id.wallet);
		wallet.setOnClickListener(this);
		layouts.add(wallet);
		
		notice = (RelativeLayout) view.findViewById(R.id.notice);
		notice.setOnClickListener(this);
		layouts.add(notice);
		
		int res=sp.getSate(getBaseActivity());
		System.out.println("res: "+res);
		if(res==0){
			changeSelected(state, 0);
		}else if(res==1){
			changeSelected(location, 1);
		}else if(res==2){
			changeSelected(order, 2);
		}else if(res==3){
			changeSelected(wallet, 3);
		}else if(res==4){
			changeSelected(notice, 4);
		}else if(res==-1){
			changeSelected(state, 0);
		}
	}

	public com.cc.client360.BaseActivity getBaseActivity(){
		return (com.cc.client360.BaseActivity)super.getActivity();
	}
	
	/**
	 * 得到本应用的ApplicationContext
	 */
//	public ExpressApplication getApplicationContext(){
//		return (ExpressApplication)super.getApplicationContext();
//	}
	
	@Override
	public void onClick(View view) {
		System.out.println("onClick  当前的页面Activity： "+getBaseActivity().getClass().getSimpleName());
		switch (view.getId()) {
		case R.id.state:
			  sp.saveSate(getBaseActivity(), 0);
//			  changeSelected(view, 0);
				if(getBaseActivity().getClass().getSimpleName().equals("IndexActivity")){
					getBaseActivity().showContent();
					System.out.println("IndexActivity.........");
				}else{
					System.out.println("IndexActivity.........else");
					((SlidingFragmentActivity) getActivity()).showContent();
					/*startActivity(new Intent(getActivity(), IndexActivity.class));*/
					
					intent=new Intent(getActivity(),IndexActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(intent);
					
				}
				
				
//				getBaseActivity().showContent();
//				intent=new Intent(getActivity(),IndexActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//				startActivity(intent);
				
			break;
			
		case R.id.location:
			sp.saveSate(getBaseActivity(), 1);
//			changeSelected(view, 1);
			if(getBaseActivity().getClass().getSimpleName().equals("AppointmentActivity")){
				getBaseActivity().showContent();
				System.out.println("AppointmentActivity.........");
			}else{
				System.out.println("AppointmentActivity.........else");
				((SlidingFragmentActivity) getActivity()).showContent();
				/*startActivity(new Intent(getActivity(), AppointmentActivity.class));*/
				
				intent=new Intent(getActivity(),AppointmentActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			
			
//			getBaseActivity().showContent();
//			intent=new Intent(getActivity(),AppointmentActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//			startActivity(intent);
			
			break;
					
		case R.id.order:
			  sp.saveSate(getBaseActivity(), 2);
//			changeSelected(view, 2);
			if(getBaseActivity().getClass().getSimpleName().equals("PriceActivity")){
				getBaseActivity().showContent();
				System.out.println("PriceActivity.........");
			}else{
				System.out.println("PriceActivity.........else");
//				((SlidingFragmentActivity) getActivity()).showContent();
				/*startActivity(new Intent(getActivity(), PriceActivity.class));*/
				
				intent=new Intent(getActivity(),PriceActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				
			}
			
//			getBaseActivity().showContent();
//			intent=new Intent(getActivity(),PriceActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//			startActivity(intent);
			
			break;
			
		case R.id.wallet:
			  sp.saveSate(getBaseActivity(), 3);
//			changeSelected(view, 3);
			if(getBaseActivity().getClass().getSimpleName().equals("InvitationActivity")){
				getBaseActivity().showContent();
				System.out.println("InvitationActivity.........");
			}else{
				System.out.println("InvitationActivity.........else");
				((SlidingFragmentActivity) getActivity()).showContent();
				/*startActivity(new Intent(getActivity(), InvitationActivity.class));*/
				
				intent=new Intent(getActivity(),InvitationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				
			}
			
			
//			getBaseActivity().showContent();
//			intent=new Intent(getActivity(),InvitationActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//			startActivity(intent);
			
			break;
			
		case R.id.notice:
		    sp.saveSate(getBaseActivity(), 4);
//			changeSelected(view, 4);
			if(getBaseActivity().getClass().getSimpleName().equals("MoreActivity")){
				getBaseActivity().showContent();
				System.out.println("MoreActivity.........");
			}else{
				System.out.println("MoreActivity.........else");
				((SlidingFragmentActivity) getActivity()).showContent();
				/*startActivity(new Intent(getActivity(), MoreActivity.class));*/
				
				intent=new Intent(getActivity(),MoreActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				
			}
			
			
//			getBaseActivity().showContent();
//			intent=new Intent(getActivity(),MoreActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//			startActivity(intent);
			
			break;
		}
	}
	
	
	public void changeSelected(View view, int index){
		RelativeLayout layout_1 = (RelativeLayout)view;
		if(index != selected){
			changeState(layout_1, true);
			if(selected != -1){
				RelativeLayout layout_2 = layouts.get(selected);
				changeState(layout_2, false);
			}
			selected = index;
		}
	}
	
	private void changeState(RelativeLayout layout, boolean isSelected){
		layout.setBackgroundResource(isSelected ? R.drawable.left_item_selected : R.drawable.left_item_normal);
		View child_2 = layout.getChildAt(1);
		if(child_2 instanceof TextView){
			((TextView) child_2).setTextColor(Color.parseColor(getResources().getString(isSelected ? R.color.colorYellow : R.color.colorWhite)));
		}
		View child_3 = layout.getChildAt(2);
		if(child_3 instanceof ImageView){
			((ImageView) child_3).setImageResource(isSelected ? R.drawable.arrow_selected : R.drawable.arrow_normal);
		}
	}
}


//if(selected != 4){
//	System.out.println("if(selected != 4)");
//	changeSelected(view, 4);
//	intent=new Intent(context,MoreActivity.class);
//	intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//	startActivity(intent);
//}
//((SlidingFragmentActivity) context).showContent();
