package com.pinping.lockfun;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity {	

	protected static final String TAG = "MainActivity";
	
	public static final int MSG_GAME = 0x1000;
	protected static final int MSG_GAME_READY = 0x1001;
	public static final int MSG_GAME_BEGIN = 0x1002;
	protected static final int MSG_GAME_END = 0x1003;
	
	public static final int MSG_LOCK_SUCESS = 0x2000;


	private GameLayout mGameLayout;

	private RelativeLayout mainLayout;

	private LockScreenLayout mLockScrLayout;
	private SlideLayout mSlideLayout;

	private long gameStartTime;
	private long gameEndTime;
	private long gameScore;

	private GameReadyLayout mGameReady;
	private View gameScanView;

	private View scoreboardView;

	protected ImageView im_scan;

	protected ImageView im_dian;

	protected PopupWindow gameScanPopWin;

	protected PopupWindow scorePopWin;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainLayout = (RelativeLayout) findViewById(R.id.mainlayout);
       		
		mLockScrLayout = new LockScreenLayout(MainActivity.this);
			
		mainLayout.addView(mLockScrLayout);
		mSlideLayout = (SlideLayout)findViewById(R.id.slide_layout);
		
		mSlideLayout.setMainHandler(mHandler);
		
    }
    
	@SuppressWarnings("deprecation")
	private void popGameScanWin(View contentView,View parent) {

		gameScanPopWin = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		//ColorDrawable bg_color = new ColorDrawable(this.getResources().getColor(R.drawable.lightyellow));
		//popupWindow.setBackgroundDrawable(bg_color);
		gameScanPopWin.setAnimationStyle(R.style.PopupAnimation);
		gameScanPopWin.showAtLocation(parent, Gravity.CENTER
				| Gravity.CENTER, 0, 0);
		gameScanPopWin.update();
				
	}
	
	@SuppressWarnings("deprecation")
	private void popScoreWin(View contentView,View parent) {

		scorePopWin = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		ColorDrawable bg_color = new ColorDrawable(this.getResources().getColor(R.drawable.lightyellow));
		scorePopWin.setBackgroundDrawable(bg_color);
		scorePopWin.setAnimationStyle(R.style.PopupAnimation);
		//点击popupwindow外部无法跳过
		scorePopWin.setOutsideTouchable(false);
		scorePopWin.setFocusable(false);
		
		//设置位置
		scorePopWin.showAtLocation(parent, Gravity.CENTER
				| Gravity.CENTER, 0, 0);
		scorePopWin.update();
		
				
	}
	
    @SuppressLint("HandlerLeak")
    private Handler mHandler =new Handler (){
		



		public void handleMessage(Message msg){
			
			//Log.e(TAG, "handleMessage :  #### " );
			
			if(MSG_LOCK_SUCESS == msg.what)
			{
				Log.e(TAG,"MSG_LOCK_SUCESS");
				//timer.cancel();
				finish(); // 锁屏成功时，结束我们的Activity界面
			}
			
			if(MSG_GAME == msg.what){
				Log.e(TAG,"MSG_GAME");
				
				
				mGameReady = new GameReadyLayout(MainActivity.this);
				mGameReady.setMainHandler(mHandler);
				
				mainLayout.removeView(mLockScrLayout);
				mainLayout.addView(mGameReady);
				
				//mainLayout.addView(mGameLayout);
			}
			
			if(MSG_GAME_READY == msg.what){
				Log.e(TAG,"MSG_GAME_READY");
				mGameLayout = new GameLayout(MainActivity.this);
				mGameLayout.setMainHandler(mHandler);
				
				//inflate view_game_scoreboard
				LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				gameScanView =  mLayoutInflater.inflate(
						R.layout.view_game_readyscan, null, true);
				
				im_scan = (ImageView)(gameScanView.findViewById(R.id.im_scan));
				RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setDuration(1000);
				//animation.setRepeatCount(Animation.INFINITE);
				
				//3s 倒计时
				animation.setRepeatCount(3);
				
				animation.setAnimationListener(new AnimationListener(){

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						
						//start game
						gameScanPopWin.dismiss();
						mainLayout.removeView(mGameReady);
						
						mainLayout.addView(mGameLayout);
						
						// for test, to delete
						gameStartTime = System.nanoTime();
						
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
				});
				im_scan.startAnimation(animation);
				
				
				im_dian = (ImageView) (gameScanView.findViewById(R.id.im_dian));
				AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
				animation2.setDuration(1000);
				animation2.setRepeatCount(Animation.INFINITE);
				im_dian.startAnimation(animation2);
								
				View popParent = findViewById(R.id.mainlayout);
								
				//show game_score pop window
				popGameScanWin(gameScanView,popParent);	
				
				//1\2\3 coutdown

			}
			
			if(MSG_GAME_BEGIN == msg.what){
				Log.e(TAG,"MSG_GAME_BEGIN");
				//Toast.makeText(getApplicationContext(), "开始游戏", Toast.LENGTH_LONG).show();
				gameStartTime = System.nanoTime();
			}
			
							
			if(MSG_GAME_END == msg.what){
				Log.e(TAG,"MSG_GAME_END");
				gameEndTime = System.nanoTime();
				
				//gameScore单位是 ms
				gameScore = Math.round(((gameEndTime - gameStartTime)/Math.pow(10,6)));
				mainLayout.removeView(mGameLayout);
				//mainLayout.addView(mGameReady);
				
				//inflate view_game_scoreboard
				initGameScoreView();
				
				View popParent = findViewById(R.id.mainlayout);
				//show game_score pop window
				popScoreWin(scoreboardView,popParent);
				
				//fill time & coin in pop_window
				TextView tv = (TextView)(scoreboardView.findViewById(R.id.game_score));
				tv.setText(gameScore+" 毫秒");
           	 	//Toast.makeText(MainActivity.this, "SCORE:" + gameScore, Toast.LENGTH_SHORT).show(); 			
			}
			
		}
	};


	protected void initGameScoreView() {
		// TODO Auto-generated method stub
		LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		scoreboardView = mLayoutInflater.inflate(
				R.layout.view_game_scoreboard, null, true);
		
		//set listener
		Button againBtn = (Button) scoreboardView.findViewById(R.id.game_gain);
		Button backlockBtn = (Button) scoreboardView.findViewById(R.id.game_backlock);
		Button shareBtn = (Button) scoreboardView.findViewById(R.id.game_share);

		againBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				scorePopWin.dismiss();
				mainLayout.addView(mGameReady);
				return false;
			}
			
		});
		
		backlockBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				scorePopWin.dismiss();
				//mLockScrLayout.
				mainLayout.addView(mLockScrLayout);
				return false;
			}
			
		});
		
		shareBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
			
				return false;
			}
			
		});
		
	}

}
   





