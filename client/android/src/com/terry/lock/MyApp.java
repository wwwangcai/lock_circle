package com.terry.lock;

import android.app.Application;
import android.os.Handler;

/**
 * 自己实现Application，实现数据共享
 * 
 * @author ga
 * 
 */
public class MyApp extends Application {
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
