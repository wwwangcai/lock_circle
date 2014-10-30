package com.terry.lock;


import com.terry.wheelview.NumericWheelAdapter;
import com.terry.wheelview.WheelView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BirthdaySetting extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	public static WheelView years;
	public static WheelView months;
	public static WheelView days;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_birthday);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.dialog_container);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
						Toast.LENGTH_SHORT).show();	
			}
		});
		years = (WheelView) findViewById(R.id.birthday_dialog_year);
		months = (WheelView) findViewById(R.id.birthday_dialog_month);
		days = (WheelView) findViewById(R.id.birthday_dialog_day);
		//birthday_dialog_day

		years.setAdapter(new NumericWheelAdapter(1970, 2020));
		//years.setLabel("年");
		years.TEXT_SIZE = 30;

		months.setAdapter(new NumericWheelAdapter(1, 12));
		//months.setLabel("月");
		months.TEXT_SIZE = 30;
		
		days.setAdapter(new NumericWheelAdapter(1, 31));
		//months.setLabel("日");
		days.TEXT_SIZE = 30;
		/*
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				txt_show_time.setText(days.getAdapter().getItem(
						days.getCurrentItem())
						+ hour.getAdapter().getItem(hour.getCurrentItem())
						+ hour.getLabel()
						+ mins.getAdapter().getItem(mins.getCurrentItem())
						+ mins.getLabel());
			}
		})
		*/;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void exitbutton1(View v) {  
    	this.finish();    	
      }  
	public void exitbutton0(View v) {  
    	this.finish();
		//Intent intent = new Intent(BirthdaySetting.this, LockService.class);
		//startService(intent);
    	//MainWeixin.instance.finish();//关闭Main 这个Activity
      }  
	
}
