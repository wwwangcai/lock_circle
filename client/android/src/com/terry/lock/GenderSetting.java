package com.terry.lock;

//import cn.buaa.myweixin.R;
import com.terry.lock.Viewpager;
//import cn.buaa.myweixin.R.layout;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GenderSetting extends Activity{
	//false--> male   true--> female
	private  boolean gender = false;
	ImageView gender_setting_male = null;
	ImageView gender_setting_female = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		Toast.makeText(getApplicationContext(), "提交请求", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }
}