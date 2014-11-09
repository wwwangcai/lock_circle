package com.terry.lock;

//import cn.buaa.myweixin.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
//import cn.buaa.myweixin.R.layout;

public class GenderSetting extends Activity{
	//false--> male   true--> female
	private  boolean gender = false;
	ImageView gender_setting_male = null;
	ImageView gender_setting_female = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//LockUser TODO-- move to app
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_gender_setting);
		
		gender_setting_male = (ImageView) findViewById(R.id.gender_setting_radio_male);
		gender_setting_female = (ImageView) findViewById(R.id.gender_setting_radio_female);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.navigation_bar); 
		TextView navi_bar_title = (TextView) findViewById(R.id.navi_bar_title);
		Button navi_bar_left_btn = (Button) findViewById(R.id.navi_bar_left_btn);
		ImageView navi_bar_back_img = (ImageView) findViewById(R.id.navi_bar_back_img);
		navi_bar_title.setText("性别设置");
		navi_bar_left_btn.setVisibility(android.view.View.VISIBLE);
		navi_bar_back_img.setVisibility(android.view.View.VISIBLE);
		//为返回按钮添加点击事件
		navi_bar_left_btn.setOnClickListener(new View.OnClickListener(){  
            public void onClick(View v) 
            { 
            	GenderSetting.this.finish();
            } 
        }); 
		
	
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);   //ȫ����ʾ
		//Toast.makeText(getApplicationContext(), "���ӣ��úñ��У�", Toast.LENGTH_LONG).show();
		//overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
   }
	public void checkGender(View v) {
		int id = v.getId();
		if (id == R.id.gender_setting_rl_female )
			gender = true;
		else
			gender = false;
		
		if(gender == false)
		{
			  gender_setting_male.setVisibility(android.view.View.VISIBLE);
			  gender_setting_female.setVisibility(android.view.View.GONE);						
		}
		else
		{
			  gender_setting_male.setVisibility(android.view.View.GONE);
			  gender_setting_female.setVisibility(android.view.View.VISIBLE);
		}
		Toast.makeText(getApplicationContext(), "改变性别", Toast.LENGTH_LONG).show();
		
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }
	
	public void confirmGender(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		LockUser user = AVUser.getCurrentUser(LockUser.class);
		if (user == null)
		{Toast.makeText(getApplicationContext(), "提交请求失败", Toast.LENGTH_LONG).show();}
		String gender_name = "undefined";
		gender_name = (gender == false) ? "male":"female";
		user.setGender(gender_name);
		/*
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
	     */
		/*try {
			user.save();
		} catch (AVException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	     SaveCallback callback = new SaveCallback(){

			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				if (arg0 == null){
					Toast.makeText(getApplicationContext(), "提交请求", Toast.LENGTH_LONG).show();
				}
			}
	    	 
	     };
	     user.saveInBackground(callback);
		//Toast.makeText(getApplicationContext(), "提交请求", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }
}