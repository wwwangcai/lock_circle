package com.terry.lock;

//import cn.buaa.myweixin.R;
import com.avos.avoscloud.AVUser;
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

public class MyAccount extends Activity{
	private String current_nickname = null;
	private String gender = null;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_my_account);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.navigation_bar); 
		TextView navi_bar_title = (TextView) findViewById(R.id.navi_bar_title);
		Button navi_bar_left_btn = (Button) findViewById(R.id.navi_bar_left_btn);
		ImageView navi_bar_back_img = (ImageView) findViewById(R.id.navi_bar_back_img);
		navi_bar_title.setText("我的账户");
		navi_bar_left_btn.setVisibility(android.view.View.VISIBLE);
		navi_bar_back_img.setVisibility(android.view.View.VISIBLE);
		//为返回按钮添加点击事件
		navi_bar_left_btn.setOnClickListener(new View.OnClickListener(){  
            public void onClick(View v) 
            { 
            	MyAccount.this.finish();
            } 
        }); 
		
		//获取本用户设置的属性，用于显示
		LockUser user = AVUser.getCurrentUser(LockUser.class);
		if (user == null)
		{Toast.makeText(getApplicationContext(), "获取用户失败", Toast.LENGTH_LONG).show();}
		current_nickname = user.getUserName();
		gender = user.getGender();
		
		//获取布局中需要显示的TextView
		TextView my_account_current_nickname = (TextView) findViewById(R.id.my_account_current_nickname);
		TextView my_account_gender = (TextView) findViewById(R.id.my_account_gender);
		
		//将TextView修改为设置的值
		if (current_nickname != null) my_account_current_nickname.setText(current_nickname);
		if (gender != null) my_account_gender.setText(gender);

		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);   //ȫ����ʾ
		//Toast.makeText(getApplicationContext(), "���ӣ��úñ��У�", Toast.LENGTH_LONG).show();
		//overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
   }
	public void goGenderSetting(View v) {  
		Intent intent = new Intent (MyAccount.this,GenderSetting.class);			
		startActivity(intent);
		//Toast.makeText(getApplicationContext(), "实物商品", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }
	
	public void goNicknameSetting(View v) {  
		Intent intent = new Intent (MyAccount.this,NicknameSetting.class);			
		startActivity(intent);
		//Toast.makeText(getApplicationContext(), "实物商品", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }
}