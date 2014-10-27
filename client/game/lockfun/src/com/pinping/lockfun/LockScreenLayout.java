package com.pinping.lockfun;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LockScreenLayout extends RelativeLayout {	

	private static final String TAG = "LockScreenLayout";
	private LayoutInflater mInflater;
	private Context mContext;
	private com.pinping.lockfun.SlideLayout mSlideLayout;

	public LockScreenLayout(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
		initView();
	}

	public LockScreenLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		mInflater = LayoutInflater.from(mContext);
		//load view_lockscreen
		mInflater.inflate(R.layout.view_lockscreen,this);
		
		mSlideLayout = (SlideLayout)findViewById(R.id.slide_layout);
		mSlideLayout.slide_ring = (ImageView) findViewById(R.id.slide_ring);
		if ( mSlideLayout.slide_ring == null ){
			Log.e(TAG,"slide_ring is null!");
		}
		mSlideLayout.button_right = (ImageView) findViewById(R.id.buttonRight);

		mSlideLayout.button_left = (ImageView) findViewById(R.id.buttonLeft);
	}
	
	
}
