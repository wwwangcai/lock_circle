package com.terry.lock;


import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class LockMainRevenue extends Activity{
	//用于显示支付宝效果的收益页面-------
    private MagicScrollView mScrollView;
    private MagicTextView mIncomeTxt;
    private MagicTextView mTotalMoneyTxt;
    private MagicTextView mIncTotalTxt;

    private LinearLayout mContainer;
    public static int mWinheight;
    int[] location = new int[2];

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//LockUser TODO-- move to app
		
		setContentView(R.layout.first_page_layout);
		initMagic();
		
   }
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int height = mContainer.getMeasuredHeight();
            Log.d("height  is ====>", "" + height);

            onMeasureTxt(mIncomeTxt);
            onMeasureTxt(mTotalMoneyTxt);
            onMeasureTxt(mIncTotalTxt);
            mScrollView.sendScroll(MagicScrollView.UP, 0);
        };
    };

    protected void initMagic() {
        Rect fram = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(fram);
        mWinheight = fram.height();
       
        Log.d("winHeight is ====>", "" + mWinheight);

        mContainer = (LinearLayout) this.findViewById(R.id.me_container);
        mScrollView = (MagicScrollView) this.findViewById(R.id.firstpage_magic_scroll);
        mIncomeTxt = (MagicTextView) this.findViewById(R.id.firstpage_crrent_rewards);
        mIncTotalTxt = (MagicTextView) this.findViewById(R.id.firstpage_yestoday_rewards);
        mTotalMoneyTxt = (MagicTextView) this.findViewById(R.id.firstpage_total_rewards);
        
        mIncomeTxt.setValue(100);
        mIncTotalTxt.setValue(100);
        mTotalMoneyTxt.setValue(1000);
        
        initListener();
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    private void initListener() {

        mScrollView.AddListener(mIncomeTxt);
        mScrollView.AddListener(mTotalMoneyTxt);
        mScrollView.AddListener(mIncTotalTxt);
    }

    private void onMeasureTxt(MagicTextView view) {
        // 用来获取view在距离屏幕顶端的距离(屏幕顶端也是scrollView的顶端)
        view.getLocationInWindow(location);
        view.setLocHeight(location[1]);
        Log.d("window y is ====>", "" + location[1]);
    }
    
	public void exit_settings(View v) {                          
		//Intent intent = new Intent (MainWeixin.this,Exit.class);
		//startActivity(intent);	
		Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
	 }

}