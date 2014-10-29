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

public class InviterSetting extends Activity{
	private EditText inviter = null;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//LockUser TODO-- move to app
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_edit_inviter);
		
		inviter = (EditText) findViewById(R.id.send_invite_name);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.navigation_bar); 
		TextView navi_bar_title = (TextView) findViewById(R.id.navi_bar_title);
		Button navi_bar_left_btn = (Button) findViewById(R.id.navi_bar_left_btn);
		ImageView navi_bar_back_img = (ImageView) findViewById(R.id.navi_bar_back_img);
		navi_bar_title.setText("邀请码设置");
		navi_bar_left_btn.setVisibility(android.view.View.VISIBLE);
		navi_bar_back_img.setVisibility(android.view.View.VISIBLE);
		//为返回按钮添加点击事件
		navi_bar_left_btn.setOnClickListener(new View.OnClickListener(){  
            public void onClick(View v) 
            { 
            	InviterSetting.this.finish();
            } 
        }); 
		
	
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);   //ȫ����ʾ
		//Toast.makeText(getApplicationContext(), "���ӣ��úñ��У�", Toast.LENGTH_LONG).show();
		//overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
   }
	//邀请码仅可以输入整数
	/*
	public static boolean isNumeric(String str){
		   for(int i=str.length();--i>=0;){
		      int chr=str.charAt(i);
		      if(chr<48 || chr>57)
		         return false;
		   }
		   return true;
    }
	*/
	public void confirmInviter(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		LockUser user = AVUser.getCurrentUser(LockUser.class);
		if (user == null)
		{Toast.makeText(getApplicationContext(), "获取用户失败", Toast.LENGTH_LONG).show();}
		String invatation = inviter.getText().toString();
		/*
		if (isNumeric(invatation) == false)
		{
			Toast.makeText(getApplicationContext(), "请输入数字", Toast.LENGTH_LONG).show();
			return ;
		}
		*/
		user.setInvatation(invatation);
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