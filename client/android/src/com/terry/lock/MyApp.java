package com.terry.lock;

import android.app.Application;
import android.os.Handler;
//import com.avos.avoscloud.AVAnalytics;

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
	}
	
	// 共享变量
	private Handler myhandler = null;

	// set方法
	public void setHandler(Handler handler) {
		this.myhandler = handler;
	}

	// get方法
	public Handler getHandler() {
		return myhandler;
	}
}
