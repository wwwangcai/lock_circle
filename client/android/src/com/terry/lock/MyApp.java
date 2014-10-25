package com.terry.lock;

import android.app.Application;
import android.os.Handler;
import com.avos.avoscloud.AVOSCloud;
//import com.avos.avoscloud.AVAnalytics;

/**
 * 自己实现Application，实现数据共享
 * 
 * @author ga
 * 
 */
public class MyApp extends Application {
	
	public void onCreate() {
	    AVOSCloud.initialize(this, "f8v0zoarc9jrk4sxom03wes6udntd4utpdhbaxohg5j4514f", "hqi4gfplb5vzya7k8wt91ksj6siy784o0vxr82kqf22a0m9r");
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
