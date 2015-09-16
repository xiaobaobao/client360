package com.cc.client360;


import com.cc.client360.fragement.LeftFragment;
import com.cc.slidingmenu.lib.SlidingMenu;
import com.cc.slidingmenu.lib.app.SlidingFragmentActivity;
import com.cc.client360.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * 更多页面
 * @author Administrator
 *
 */
public class MoreActivity extends BaseActivity{

	
	//测试
	private TextView shuju;
	private Button but;
	private int c=0;
	
	
	@Override
	protected void initPage(){
		
		shuju=(TextView) findViewById(R.id.shuju222);
		but=(Button) findViewById(R.id.but);
		but.setOnClickListener(this);
	}

	@Override
	protected int setLayout() {
		return R.layout.more_activity;
	}

	@Override
	protected String setTitle() {
		return "更多";
	}
	@Override
	protected boolean onTopMidVisiable() {
		return false;
	}
	
	
	@Override
	protected void onClickSubActivity(View view) {
		switch (view.getId()) {
		case R.id.but:
			c++;
			shuju.setText(c+"");
			break;
		default:
			break;
		}
	}
	
	
	
}
