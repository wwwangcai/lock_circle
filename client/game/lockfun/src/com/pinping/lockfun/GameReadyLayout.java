package com.pinping.lockfun;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameReadyLayout extends RelativeLayout {

	
	private Context mContext;
	private LayoutInflater mInflater;
	private ImageView ready_button;
	private Handler mainHandler = null;

	public GameReadyLayout(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
		initView();
	}
	
	

	public GameReadyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		mInflater = LayoutInflater.from(mContext);
		//load ganme layout
		mInflater.inflate(R.layout.view_game_ready,this);
		
		ready_button = (ImageView) findViewById(R.id.game_ready_button);
		ready_button.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				//send MSG_GAME_READY to mainHandler
				mainHandler.obtainMessage(MainActivity.MSG_GAME_READY).sendToTarget();
				return false;
			}
			
			
		});
	}
	
	public void setMainHandler(Handler handler) {
		// activity所在的Handler对象
		mainHandler  = handler;
	}

}
