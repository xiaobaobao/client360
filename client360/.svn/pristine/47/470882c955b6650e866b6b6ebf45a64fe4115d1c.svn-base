package com.cc.client360;


import java.util.HashMap;

import com.cc.client360.tools.sp;

//import cc.android.supu.WelcomeActivity;
//import cc.android.supu.tools.Update;

//import cc.android.supu.IndexActivity;

//import com.cc.client360.handler.UpdateHandler;
//import com.cc.dDriver360.tools.ConstantUrl;
//import com.cc.dDriver360.tools.Tools;
//import com.cc.dDriver360.tools.Update;
//import com.cc.dDriver360.tools.UserInfoTools;
//import com.cc.dDriver360.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 选择页面
 * @author Administrator
 *
 */

public class SelectActivity extends Activity implements OnClickListener{
     private Button serverdriver,driver;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
 		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
 				WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.select);		
    	driver=(Button) findViewById(R.id.driver);
		serverdriver=(Button) findViewById(R.id.serverdriver);
		driver.setOnClickListener(this);
		serverdriver.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
			case R.id.driver:
				 sp.saveDriverSate(this,"代驾");
				 startActivity(new Intent(SelectActivity.this, IndexActivity.class));
				 finish();
				break;
		    case R.id.serverdriver:
		    	 sp.saveDriverSate(this,"陪驾");
		    	 startActivity(new Intent(SelectActivity.this, IndexActivity.class));
				 finish();
				break;
			default:
				break;
			 }
	}
	
	
	
	
}
