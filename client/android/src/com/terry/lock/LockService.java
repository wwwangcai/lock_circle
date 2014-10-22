package com.terry.lock;



import java.util.Random;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LockService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCreate(){
		super.onCreate();
		registerIntentReceivers();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		//registerIntentReceivers();
		Log.w("服务启动", "启动");
		return super.onStartCommand(intent, flags, startId);
	}
	class AppIntentReceiver extends BroadcastReceiver { 
		@Override 
		
		public void onReceive(Context context, Intent intent) { 
	     Log.e("锁屏状态-------","已经锁屏");
	     Toast.makeText(getApplicationContext(),"锁屏!", Toast.LENGTH_SHORT).show();
	     System.out.println("进入锁屏界面");
	     
		//Random rand = new Random(System.currentTimeMillis());
		
		/*cal the ads to decide which to show*/
	    //LockScreenView.bg_index = Math.abs(rand.nextInt()%5 + 1);
	   // Log.e("Receiver:bg_index",LockScreenView.bg_index+"");
	    
	    // main.this.findViewById(R.id.lock_screen_bgimageView);
	    Intent intent1=new Intent(context,main.class);
	    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     context.startActivity(intent1);
	     System.out.println("进入锁屏界面2");
	     
	     // change LockScreenView by changeBGimg（）
		} 
		} 
	private void registerIntentReceivers() { 
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF); 
	 
	//	filter.addDataScheme("package"); 
		registerReceiver(new AppIntentReceiver(), filter); 
		} 
  

}
