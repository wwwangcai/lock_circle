package com.terry.lock;

//import cn.buaa.myweixin.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
//import cn.buaa.myweixin.R.layout;

public class NicknameSetting extends Activity{
	private EditText nick_name = null;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//LockUser TODO-- move to app
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_modify_nickname);
		
		nick_name = (EditText) findViewById(R.id.edit_nick_name);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.navigation_bar); 
		TextView navi_bar_title = (TextView) findViewById(R.id.navi_bar_title);
		Button navi_bar_left_btn = (Button) findViewById(R.id.navi_bar_left_btn);
		ImageView navi_bar_back_img = (ImageView) findViewById(R.id.navi_bar_back_img);
		navi_bar_title.setText("昵称设置");
		navi_bar_left_btn.setVisibility(android.view.View.VISIBLE);
		navi_bar_back_img.setVisibility(android.view.View.VISIBLE);
		//为返回按钮添加点击事件
		navi_bar_left_btn.setOnClickListener(new View.OnClickListener(){  
            public void onClick(View v) 
            { 
            	NicknameSetting.this.finish();
            } 
        }); 
		
	
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);   //ȫ����ʾ
		//Toast.makeText(getApplicationContext(), "���ӣ��úñ��У�", Toast.LENGTH_LONG).show();
		//overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
   }
	
	public void confirmNickname(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		LockUser user = AVUser.getCurrentUser(LockUser.class);
		if (user == null)
		{Toast.makeText(getApplicationContext(), "获取用户失败", Toast.LENGTH_LONG).show();}
		String input_nick_name = nick_name.getText().toString();
		user.setNickName(input_nick_name);
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
					Toast.makeText(getApplicationContext(), "提交请求成功", Toast.LENGTH_LONG).show();
				}
			}
	    	 
	     };
	     user.saveInBackground(callback);
		//Toast.makeText(getApplicationContext(), "提交请求", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }
}