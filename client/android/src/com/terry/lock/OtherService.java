package com.terry.lock;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class OtherService extends Service {
	private KeyguardManager mKeyguard;
	private KeyguardLock mKeylock;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mKeyguard = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        mKeylock = mKeyguard.newKeyguardLock("Charge");
        mKeylock.disableKeyguard();
		Toast.makeText(OtherService.this, "重启服务成功", Toast.LENGTH_LONG).show();
		
		super.onCreate();
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				Log.d("H3c", "in Server");
//			}
//		}).start();
	}
}
