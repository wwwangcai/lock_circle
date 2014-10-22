package com.terry.lock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility") 

public class SlideLayout extends RelativeLayout {

	private static final String TAG = "SlideLayout";
	// 当前bitmap应该绘制的地方 ， 初始值为足够大，可以认为看不见
	private int mLastMoveX = 1500;
	private int slide_Y = 0;
	private Context mContext = null;
	// 拖拽环
	private Bitmap dragBitmap = null;
	private Handler mainHandler = null;
	
	
	ImageView slide_ring = null;
	ImageView button_right= null;
	ImageView button_left = null;
	
	Rect button_r_rect = new Rect();
	Rect button_l_rect = new Rect();
	
	boolean isHit_right = false;
	boolean isHit_left = false;
	
	public static int slide_ring_w = 0;
	public static int slide_ring_h = 0;
	
	//private LayoutInflater mInflater;
	
	//回退动画时间间隔值 
	private static int BACK_DURATION = 20 ;   // 20ms
    //水平方向前进速率
	private static float VE_HORIZONTAL = 0.7f ;  //0.1dip/ms

	boolean is_game_started = false;
	
	public SlideLayout(Context context) {
		super(context);
		mContext = context;
		initDragBitmap();

		//初始化slide_ring 尺寸
		Resources r = getResources();
		slide_ring_h = (int) r.getDimension(R.dimen.slide_ring_h);
		slide_ring_w = (int) r.getDimension(R.dimen.slide_ring_w);
		
		Log.e(TAG,"SlideLayout construct 1!");

	}

	public SlideLayout(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		mContext = context;
		initDragBitmap();

		//初始化slide_ring 尺寸
		Resources r = getResources();
		slide_ring_h = (int) r.getDimension(R.dimen.slide_ring_h);
		slide_ring_w = (int) r.getDimension(R.dimen.slide_ring_w);
		
		Log.e(TAG,"slide_ring_h:"+slide_ring_h+";slide_ring_w:"+slide_ring_w);
		
		
		Log.e(TAG,"SlideLayout construct 2!");

	}
	

	public SlideLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initDragBitmap();
		
		//初始化slide_ring 尺寸
		Resources r = getResources();
		slide_ring_h = (int) r.getDimension(R.dimen.slide_ring_h);
		slide_ring_w = (int) r.getDimension(R.dimen.slide_ring_w);
		
		Log.e(TAG,"SlideLayout construct 3!");
		
	}

	

	//通过延时控制当前绘制bitmap的位置坐标
	private Runnable BackDragImgTask = new Runnable(){
		
		public void run(){
			//一下次Bitmap应该到达的坐标值
			mLastMoveX = mLastMoveX - (int)(BACK_DURATION * VE_HORIZONTAL);
			
			//Log.e(TAG, "BackDragImgTask ############# mLastMoveX " + mLastMoveX);
			
			invalidate();//重绘		
			//是否需要下一次动画 ？ 到达了初始位置，不在需要绘制
			boolean shouldEnd = Math.abs(mLastMoveX - slide_ring.getRight()) <= 8 ;			
			if(!shouldEnd)
				mainHandler.postDelayed(BackDragImgTask, BACK_DURATION);
			else { //复原初始场景
				resetViewState();	
			}				
		}
	};
	


	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.e("onTouchEvent","ontouch");
		int x = (int) event.getX();
		int y = (int) event.getY();
		//Log.e(TAG, "onTouchEvent" + " X is " + x + " Y is " + y);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//Log.e(TAG,"ACTION_DOWN");
			mLastMoveX = (int) event.getX();
			// 处理Action_Down事件： 判断是否点击了滑动区域
			return handleActionDownEvenet(event);
		case MotionEvent.ACTION_MOVE:
			//Log.e(TAG,"ACTION_MOVE");
			mLastMoveX = x; // 保存了X轴方向
			isHit_right = button_r_rect.contains((int) event.getX(), (int) event.getY());
			isHit_left = button_l_rect.contains((int) event.getX(), (int) event.getY());
			//Log.e("handleMoveEvenet",
			//		"rect:" + button_r_rect + ";getX():" + event.getX() + ";getY():"
			//				+ event.getY());
			invalidate(); // 重新绘制
			return true;
			
		case MotionEvent.ACTION_UP:
			//Log.e(TAG,"ACTION_UP");
			// 处理Action_Up事件： 判断是否解锁成功，成功则结束我们的Activity ；否则 ，缓慢回退该图片。
			handleActionUpEvent(event);
			return true;
		}
		return super.onTouchEvent(event);
	}

	
	
	// 手势落下是，是否点中了图片，即是否需要开始移动
	private boolean handleActionDownEvenet(MotionEvent event) {
		Log.e("handleActionDownEvenet", "login");

		Rect rect = new Rect();
		slide_ring.getHitRect(rect);
		Log.e("handleActionDownEvenet",
				"rect:" + rect + ";getX():" + event.getX() + ";getY():"
						+ event.getY());

		boolean isHit = rect.contains((int) event.getX(), (int) event.getY());
		
	    button_right.getHitRect(button_r_rect);
	    Log.e("handleMoveEvenet",
				"rect:" + button_r_rect + ";getX():" + event.getX() + ";getY():"
						+ event.getY());

	    button_left.getHitRect(button_l_rect);
	    Log.e("handleMoveEvenet",
				"rect:" + button_l_rect + ";getX():" + event.getX() + ";getY():"
						+ event.getY());

		if (isHit) { // 开始拖拽 ，隐藏该图片
			Log.e("handleActionDownEvenet", "isHit");
			slide_Y = slide_ring.getTop();
			slide_ring.setVisibility(View.INVISIBLE);
		}
		Log.e("handleActionDownEvenet", "isHit:" + isHit);

		return isHit;
		// Log.e(TAG, "handleActionDownEvenet : isHit" + isHit);

	}

	// 判断松开手指时，是否达到末尾即可以开锁了 , 是，则开锁，否则，通过一定的算法使其回退。
	private void handleActionUpEvent(MotionEvent event) {
		int x = (int) event.getX();
		RelativeLayout buttonRight = null ;
		RelativeLayout buttonLeft = null ;
		// Log.e(TAG, "handleActionUpEvent : x -->" + x + "   getRight() " +
		// getRight() );
		// 距离在右边环15dip以内代表解锁成功。
		
		buttonRight = (RelativeLayout) findViewById(R.id.buttonRight_comb);
		buttonLeft = (RelativeLayout) findViewById(R.id.buttonLeft_comb);
		
		//这里的逻辑得优化，不能是在15像素内
		boolean isUnlock = x >= buttonRight.getLeft() - 15;
		
		boolean	isGame = x <= buttonLeft.getRight() + 15;

		Log.e(TAG,"isUnlock:isGame"+isUnlock+":"+isGame);	
		if (isUnlock) {
			Toast.makeText(mContext, "解锁成功", 1000).show();
			Log.e(TAG,"Unlock");
			//resetViewState();
			// virbate(); //震动一下
			// 结束我们的主Activity界面
			mainHandler.obtainMessage(main.MSG_LOCK_SUCESS).sendToTarget();
		} else if(isGame){
			if (is_game_started){
				//游戏已经开始，不能重复启动
				resetViewState();
				Toast.makeText(mContext, "游戏已启动", 1000).show();
			}else{
				Log.e(TAG,"BEGIN GAME");
				
				//Toast.makeText(mContext, "开始游戏", Toast.LENGTH_LONG).show();
				//resetViewState();
				mainHandler.obtainMessage(main.MSG_GAME_BEGIN).sendToTarget();
				is_game_started = true;
			}
		}else {// 没有成功解锁，以一定的算法使其回退
				// 每隔20ms , 速率为0.6dip/ms , 使当前的图片往后回退一段距离，直到到达最左端
			mLastMoveX = x; // 记录手势松开时，当前的坐标位置。
			int distance = x - slide_ring.getRight();
			// 只有移动了足够距离才回退
			// Log.e(TAG, "handleActionUpEvent : mLastMoveX -->" + mLastMoveX +
			// " distance -->" + distance );
			if (distance >= 0)
				mainHandler.postDelayed(BackDragImgTask, BACK_DURATION);
			else { // 复原初始场景
				resetViewState();
			}
		}
	}

	
	//重置初始的状态，显示tv_slider_icon图像，使bitmap不可见
	void resetViewState(){
		mLastMoveX = 1500 ;
		slide_ring.setVisibility(View.VISIBLE);
		invalidate();        //重绘最后一次
	}
	
	
	// 初始化图片拖拽时的Bitmap对象
	private void initDragBitmap() {
		if (dragBitmap == null)
			dragBitmap = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.drag_ring);
	}
	
	// 绘制拖动时的图片
	public void onDraw(Canvas canvas) {
		//super.onDraw(canvas);		
		//Log.e(TAG, "onDraw ######" );
		
		// 图片更随手势移动
		invalidateDragImg(canvas);
	}

	// 图片更随手势移动
	private void invalidateDragImg(Canvas canvas) {
		//Log.e(TAG, "invalidateDragImg" );
		//以合适的坐标值绘制该图片
		int drawXCor = mLastMoveX -  slide_ring_w/2;
		//int drawYCor = slide_ring.getTop();
		int drawYCor = slide_Y;
		//canvas.drawBitmap(dragBitmap,  drawXCor < 0 ? 5 : drawXCor , drawYCor , null);
		/*
		if (isHit_right)
		{
			//Log.e(TAG,"isHit_right"+ button_r_rect.exactCenterX());
			canvas.drawBitmap(dragBitmap, button_r_rect.top , button_r_rect.right , null);
		}
		else if (isHit_left)
		{
			//Log.e(TAG,"isHit_left"+ button_l_rect.exactCenterX());
			canvas.drawBitmap(dragBitmap, button_l_rect.exactCenterX()  , button_l_rect.exactCenterY() , null);
		}
		*/
	
		//Log.e(TAG, "invalidateDragImg" + " drawXCor "+ drawXCor + " and drawYCor" + drawYCor);
	    canvas.drawBitmap(dragBitmap,  drawXCor < 0 ? 5 : drawXCor , drawYCor , null);
		
	}
	
	public void setMainHandler(Handler handler){
		mainHandler  = handler;//activity所在的Handler对象
	}

}
