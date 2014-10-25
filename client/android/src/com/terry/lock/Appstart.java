package com.terry.lock;

//import cn.buaa.myweixin.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.avos.avoscloud.AVObject;
//import cn.buaa.myweixin.R.layout;

public class Appstart extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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