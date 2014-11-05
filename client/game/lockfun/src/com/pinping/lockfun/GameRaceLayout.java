package com.pinping.lockfun;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author wangjun02
 *
 */
public class GameRaceLayout extends RelativeLayout {

	private LayoutInflater mInflater;
	private Context mContext;
	private Button left_foot;
	private ImageView game_fairy;
	private Animation stepAnimation;
	private int game_race_h;
	private RelativeLayout game_race;
	private Button right_foot;
	
	//标识上一次是哪个脚，0:左脚 1右脚
	protected int cur_foot = 0;
	private Handler mainHandler;
	protected int cur_bottom;
	protected int cur_top;

	

	public GameRaceLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mInflater = LayoutInflater.from(mContext);
		//load ganme layout
		mInflater.inflate(R.layout.view_game_race,this);
		
		game_race = (RelativeLayout)findViewById(R.id.game_race);
			
		left_foot = (Button)findViewById(R.id.left_foot);
		right_foot = (Button)findViewById(R.id.right_foot);
		game_fairy = (ImageView)findViewById(R.id.game_fairy);

		
		left_foot.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//判断是否轮到左脚
				if(cur_foot  == 1){
					//爬上一步
					stepForward();
					cur_foot = 0;
				}
			}
			
		});
		
		right_foot.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//判断是否轮到左脚
				if(cur_foot == 0){
					//爬上一步
					stepForward();
					Log.e("cur_bottom",cur_bottom+"");
					
					cur_foot = 1;
				}	
			}
			
		});
		
		initAnim();
	}

	private void initAnim() {
		// TODO Auto-generated method stub	
		stepAnimation = AnimationUtils.loadAnimation(mContext,R.anim.step_forward);		
	}


	
	//前进一步，根据不同手机屏幕算出每一步的步长
	protected void stepForward() {
		// TODO Auto-generated method stub
		game_race_h = game_race.getHeight();
		Log.e("stepForward","login");

		stepAnimation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub	

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				int left = game_fairy.getLeft();
				int top = game_fairy.getTop();
				int right = game_fairy.getRight();
				int bottom = game_fairy.getBottom();			
				Log.e("onAnimationEnd",left+";"+top+";"+right+";"+bottom);
				
				int Y_delta = (int) (game_race_h*0.01);
								
				game_fairy.clearAnimation();
				
				cur_bottom = bottom - Y_delta;
				cur_top = top-Y_delta;
				game_fairy.layout(left,cur_top,right,cur_bottom);
				
				if(judgeGameState()==1){
					endGame();
				}
				
			
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		game_fairy.startAnimation(stepAnimation);
		Log.e("stepForward","startAnimation");
	}
	
	private void endGame() {
		// TODO Auto-generated method stub
		mainHandler.obtainMessage(MainActivity.MSG_GAME_END).sendToTarget();
	}
	
	
	//判断game状态，0：未结束  1：结束  2：超时？
	private int judgeGameState() {
		// TODO Auto-generated method stub
		
		if(cur_bottom <= game_race_h *0.1){
			Log.e("cur_bottom:game_race_h",cur_bottom+":"+game_race_h);
			return 1;
		}
		return 0;
	}
    
	
	public void setMainHandler(Handler mHandler) {
		// TODO Auto-generated method stub
		mainHandler = mHandler;
		
	}

	
	
}
