package com.cc.client360;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cc.client360.fragement.LeftFragment;
import com.cc.client360.tools.PageRequest;
import com.cc.client360.tools.Tools;
import com.cc.client360.tools.sp;
import com.cc.slidingmenu.lib.SlidingMenu;
import com.cc.slidingmenu.lib.app.SlidingFragmentActivity;
import com.cc.client360.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
/**
 * 此activity为中间的主页面，左侧栏在LeftFragment中
 * 显示左侧栏请调用showContent()；
 * 关闭左侧栏请调用showMenu();
 * SlidingMenu有关闭监听事件：setOnOpenListener(listener)和关闭后的监听事件setOnOpenedListener(listener);
 * SlidingMenu有打开监听事件：setOnOpenListener(listener)和打开后的监听事件setOnOpenedListener(listener);
 * @author Administrator
 *
 */
public abstract class BaseActivity extends SlidingFragmentActivity implements OnClickListener{
   
	private static final String tag = "BaseActivity";	
	/** 侧边栏控件 */
	public SlidingMenu sm;
	public RelativeLayout topLayout;
	/** 左边按钮 */
	public TextView leftBtn;
	public TextView rightBtn;
	/** 中间的按钮 */
	private LinearLayout topMiddle;
	/** 中间的按钮 */
	public TextView topMiddleBtn;
	/** 中间显示的文字 */
	public TextView topMiddleTv;
	/** 标题 */
	public TextView titleLabel;
    /***/	
	private RelativeLayout contentLayout;
	
    private Fragment mLeftFrag;
	//private RelativeLayout state, location, order, wallet, notice;
	
    protected ProgressDialog progressDialog;
    /** 是否已退出 */
	private boolean isExit;
	protected static final ExecutorService executorService = Executors.newFixedThreadPool(5);
	
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		beforesetContentView();
		
		System.out.println("BaseActivity  super.onCreate    super.onCreate");
		//无标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//添加到list管理中
		getApplicationContext().add(this);
		//设置左侧栏的布局,此方法必须在setContentView（）之前调用，否则会报错
		
		
		setBehindContentView(R.layout.left_frame);
		//加载布局文件
		setContentView(R.layout.activity_main);
		//初始化顶部栏控件
		initBaseView();	
		//获取子类中的布局文件
		View view = getLayout();
		if (contentLayout == null){
					System.out.println("contentLayout为空....");
				} else {
					LayoutParams params =new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
					//子类中的布局文件添加到中间布局中
					contentLayout.addView(view,params);
				}
		//初始页面，实现fandview 点击事件的监听
		if (view != null) {
					initPage();
		}	
		//设置标题的padding
		if(onTopVisiable()){
				setTitleLabelPadding();
		 }
		//左边栏
//		if(!getApplicationContext().isfirst){
		
		if(!this.getClass().getSimpleName().equals("SelectActivity") && 
		   !this.getClass().getSimpleName().equals("WelcomeActivity")){
			initLeft();
		}
			
//		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("BaseActivity  当前的页面Activity： "+this.getClass().getSimpleName());
		showContent();
//		int res=selectSate.getSate(this);
//		System.out.println("res: "+res);
//		if(res==0){
//			((LeftFragment) mLeftFrag).changeSelected(state, 0);
//		}else if(res==1){
//			((LeftFragment) mLeftFrag).changeSelected(location, 1);
//		}else if(res==2){
//			((LeftFragment) mLeftFrag).changeSelected(order, 2);
//		}else if(res==3){
//			((LeftFragment) mLeftFrag).changeSelected(wallet, 3);
//		}else if(res==4){
//			((LeftFragment) mLeftFrag).changeSelected(notice, 4);
//		}else if(res==-1){
//			((LeftFragment) mLeftFrag).changeSelected(state, 0);
//		}
	}
	
	
	/**
	 * 初始化顶部栏控件
	 */
	private void initBaseView() {
		topLayout= (RelativeLayout) findViewById(R.id.topLayout);
		if(onTopVisiable()){
			topLayout.setVisibility(View.VISIBLE);
		}else{
			topLayout.setVisibility(View.GONE);
		}
		//顶部
		leftBtn = (TextView) findViewById(R.id.leftbtn);
		leftBtn.setOnClickListener(this);
		rightBtn = (TextView) findViewById(R.id.rightbtn);
		rightBtn.setOnClickListener(this);
		titleLabel= (TextView) findViewById(R.id.title);
		topMiddle= (LinearLayout) findViewById(R.id.topMiddle);
		if(onTopMidVisiable()){
			topMiddleBtn= (TextView) findViewById(R.id.topMiddleBtn);
			topMiddleTv= (TextView) findViewById(R.id.topMiddleTv);
			topMiddleBtn.setOnClickListener(this);
			topMiddleTv.setOnClickListener(this);
			topMiddle.setVisibility(View.VISIBLE);
			titleLabel.setVisibility(View.GONE);
		}else{
			titleLabel.setText(setTitle());
			topMiddle.setVisibility(View.GONE);
			titleLabel.setVisibility(View.VISIBLE);
		}
		//中间
		contentLayout = (RelativeLayout) findViewById(R.id.middle_frame);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leftbtn:
			showMenu();
			break;
//		case R.id.rightbtn:
//			break;
		default:
			onClickSubActivity(v);
			break;
		}
	}
	/**
	 * 页面点击事件在此方法中实现
	 */
	protected void onClickSubActivity(View view) {
	}
	
	/**
	 * 设置TitleLabel的padding
	 */
	private void setTitleLabelPadding() {
		//如果backBtn 和 enterBtn 都可见
	    if (rightBtn.getVisibility() == View.VISIBLE&& leftBtn.getVisibility() == View.VISIBLE) {
//			getViewSize(leftBtn);
		//如果backBtn可见	 和 enterBtn不可见	
		} else if (rightBtn.getVisibility() == View.VISIBLE&& leftBtn.getVisibility() == View.GONE) {
//			getViewSize(rightBtn);
		}
	    //如果backBtn不可见	 和 enterBtn不可见	
		else if (rightBtn.getVisibility() == View.GONE&& leftBtn.getVisibility() == View.GONE) {
		}
	}
	
	/**
	 * 得到本应用的ApplicationContext
	 */
	public ExpressApplication getApplicationContext(){
		return (ExpressApplication)super.getApplicationContext();
	}
	
	/**
	 * 在onCreat（）方法中获得view的尺寸
	 * @param view
	 * 
	 */
	private void getViewSize(final View view) {
		//这是个view事件的观察者。要注意的是它的初始化就是调用View.getViewTreeObserver()。
		ViewTreeObserver vto2 = view.getViewTreeObserver();
		                                   //当在一个视图树中的焦点状态发生改变时，所要调用的回调函数的接口类
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				//？？
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				//？？
				DisplayMetrics metrics = getResources().getDisplayMetrics();
				//？？
				float padding = view.getWidth() + 15 * metrics.density;
				titleLabel.setPadding((int) padding,
						               titleLabel.getPaddingTop(), 
						              (int) padding,
					                   titleLabel.getPaddingBottom());
			}
		});
	}
	/**
	 * 获取子类中的布局文件
	 * 
	 * @return
	 */
	private View getLayout() {
		View view = null;
		try {
			if (setLayout() > 0) {
				LayoutInflater inflater = LayoutInflater.from(this);
				view = inflater.inflate(setLayout(), null);
			} else {
				Log.e(tag, "布局资源文件不存在");
			}
		} catch (InflateException e) {
			e.printStackTrace();
			Log.e(tag, "布局资源文件不存在  异常");
		}
		return view;
	}
	/**
	 * 初始化页面（控件在此方法中初始化）
	 */
	protected abstract void initPage();
	/**
	 * 设置布局文件
	 */
	protected abstract int setLayout();
	/**
	 * 设置顶部标题栏
	 */
	protected abstract String setTitle();
	/**
	 * 顶部是否可见
	 */
	protected boolean onTopVisiable(){	
		return true;
	}
	/**
	 * 顶部是否可见
	 */
	protected boolean onTopMidVisiable(){	
		return true;
	}
	protected void beforesetContentView(){
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	/**
	 * 按键按了返回
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下返回键
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 判断是不是在首页
			if (this.getClass() != getApplicationContext().IndexActivity) {
//				if (getApplicationContext().isBottomActivity(this)) {
//					getApplicationContext().backIndex(this);
//				} else {
//					return super.onKeyDown(keyCode, event);
//				}
				Intent intent = null;
				getApplicationContext().delNotBottomActivity();
				intent = new Intent(this, IndexActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
				startActivity(intent);
			} else {
				showExitDialog();
			}
		}

		return false;
	}
	/**
	 * 退出对话框
	 */
	private void showExitDialog() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("温馨提示")
		 		.setMessage("您确定要退出?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						getApplicationContext().closeAllActivity();
						getApplicationContext().exit();
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		dialog.show();
	}
	/**
	 * 
	 * 设置左侧边栏属性
	 * 2013-9-17
	 * void
	 */
	private void setLeftMenuAttr(){
		sm = getSlidingMenu();
		//设置阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		//设置阴影
		sm.setShadowDrawable(R.drawable.shadow);
		//设置侧边栏显示时主页面显示的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.LEFT);//TOUCHMODE_FULLSCREEN
		sm.setBehindScrollScale(0.0f);
		//设置左侧栏的背景
		sm.setBackgroundResource(R.drawable.left_item_normal);
	}
	/**
	 * 初始化左侧栏
	 */
	private void initLeft(){
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		mLeftFrag = new LeftFragment();
		t.replace(R.id.left_menu, mLeftFrag);
		t.commit();
		setLeftMenuAttr();
	}
	
	
	
	
	
	
	
	/************************************************************
	 * 
	 * 以下代码是，服务器连接
	 * 
	 * 
	 */

	/**
	 * 显示加载对话框
	 * 
	 * @param msg
	 *            显示在对话框上的信息
	 */
	protected void showProgressDialog(String msg) {
		if (progressDialog != null) {
			progressDialog.cancel();
		}
		progressDialog = new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		if (msg == null) {
			progressDialog.setMessage("正在加载,请稍候...");
		} else {
			progressDialog.setMessage(msg);
		}
		if (!isExit) {
			progressDialog.show();
		}
	}
	
	public Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			cancelProgressDialog();
			dealWithMessage(msg);
		};
	};
	/**
	 * 取消加载对话框
	 */
	protected void cancelProgressDialog() {
		if (progressDialog != null) {
			progressDialog.cancel();
		}
	}
	protected void dealWithMessage(Message msg){
	}
	/**
	 * 请求服务器
	 */
	protected void requestServer() {
		requestServer(null, true, null);
	}

	/**
	 * 请求服务器
	 * 
	 * @param isShowDialog
	 *            是否显示加载进度对话框
	 */
	protected void requestServer(boolean isShowDialog) {
		requestServer(null, isShowDialog, null);
	}

	/**
	 * 请求服务器
	 * 
	 * @param pageRequest
	 *            页面请求接口
	 * @param msg
	 *            加载进度对话框上显示的信息
	 */
	protected void requestServer(PageRequest pageRequest, String msg) {
		requestServer(pageRequest, true, msg);
	}

	/**
	 * 请求服务器
	 * 
	 * @param pageRequest
	 *            页面请求接口
	 */
	protected void requestServer(PageRequest pageRequest) {
		requestServer(pageRequest, true, null);
	}

	/**
	 * 请求服务器
	 * 
	 * @param pageRequest
	 * @param isShowDialog
	 */
	protected void requestServer(PageRequest pageRequest, boolean isShowDialog) {
		requestServer(pageRequest, isShowDialog, null);
	}
	/**
	 * 请求服务器
	 * 
	 * @param pageRequest
	 *            请求页面的请求接口
	 * @param isShowDialog
	 *            是否显示加载进度对话框
	 * @param msg
	 *            加载进度对话框上显示的信息
	 */
	protected void requestServer(final com.cc.client360.tools.PageRequest pageRequest,
			boolean isShowDialog, String msg) {
		if (!Tools.isAccessNetwork(this)) {
			Toast.makeText(this, "没有网络", Toast.LENGTH_SHORT).show();
		} else {
			if (isShowDialog) {
				showProgressDialog(msg);
			}
			executorService.submit(new Runnable() {

				@Override
				public void run() {
					if (pageRequest != null) {
						pageRequest.requestServer();
					} else {
						setRequestParams();
					}
				}
			});
		}
	}
	protected void setRequestParams(){};
	
	
	
	
	
	
	
	
	
}
