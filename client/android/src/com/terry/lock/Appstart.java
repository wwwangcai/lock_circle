package com.terry.lock;

//import cn.buaa.myweixin.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
//import cn.buaa.myweixin.R.layout;
import com.avos.avoscloud.AVUser;

public class Appstart extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	     if (true) {
	         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	                 .detectDiskReads()
	                 .detectDiskWrites()
	                 .detectNetwork()   // or .detectAll() for all detectable problems
	                 .penaltyLog()
	                 .build());
	         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
	                 .detectLeakedSqlLiteObjects()
	                 .detectLeakedClosableObjects()
	                 .penaltyLog()
	                 .penaltyDeath()
	                 .build());
	     }
	    try {
			LockUser user = AVUser.logIn("asdf", "lockin1234", LockUser.class);
		} catch (AVException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.appstart);
		
	new Handler().postDelayed(new Runnable(){
		@Override
		public void run(){
			Intent intent = new Intent (Appstart.this,Viewpager.class);			
			startActivity(intent);			
			Appstart.this.finish();
		}
	}, 1000);
   }
}