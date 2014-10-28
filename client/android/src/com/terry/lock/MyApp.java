package com.terry.lock;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

import android.app.Application;
import android.os.Handler;
//import com.avos.avoscloud.AVAnalytics;
import android.os.StrictMode;

/**
 * 自己实现Application，实现数据共享
 * 
 * @author ga
 * 
 */
public class MyApp extends Application {
	
	@Override
	public void onCreate() {
	    AVService.AVInit(this);
		//user = new LockUser();
		//user.setUserName("test1");
		//user.setPassword("12345");
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
			LockUser user = AVUser.logIn("test1", "12345", LockUser.class);
		} catch (AVException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //LockUser user = AVUser.log
	}
	
	// 共享变量
	private Handler myhandler = null;
	//private LockUser user = null;

	// set方法
	public void setHandler(Handler handler) {
		this.myhandler = handler;
	}

	// get方法
	public Handler getHandler() {
		return myhandler;
	}
}
