package com.cc.client360;


import com.cc.client360.fragement.LeftFragment;
import com.cc.slidingmenu.lib.SlidingMenu;
import com.cc.slidingmenu.lib.app.SlidingFragmentActivity;
import com.cc.client360.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
/**
 * 价格明细
 * @author Administrator
 *
 */
public class PriceActivity extends BaseActivity{

	@Override
	protected void initPage(){
	}

	@Override
	protected int setLayout() {
		return R.layout.price_activity;
	}

	@Override
	protected String setTitle() {
		return "城市费用查询";
	}
	@Override
	protected boolean onTopMidVisiable() {
		return false;
	}
	
	
}
