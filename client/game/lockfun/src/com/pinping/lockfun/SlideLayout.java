package com.pinping.lockfun;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


@SuppressLint("ClickableViewAccessibility")
public class SlideLayout extends RelativeLayout {
	
	private static final String TAG = "SlideLayout";
	// ��ǰbitmapӦ�û��Ƶĵط� �� ��ʼֵΪ�㹻�󣬿�����Ϊ������
	private int mLastMoveX = 10000;
	private int slide_Y = 0;
	private Context mContext = null;
	// ��ק��
	private Bitmap dragBitmap = null;
	private Handler mainHandler = null;

	ImageView slide_ring = null;
	ImageView button_right = null;
	ImageView button_left = null;

	RelativeLayout buttonRight = null;
	RelativeLayout buttonLeft = null;
	
	private ImageView buttonRight_circle;
	private ImageView buttonLeft_circle;

	public static int slide_ring_w = 0;
	public static int slide_ring_h = 0;

	// private LayoutInflater mInflater;

	// ���˶���ʱ����ֵ
	private static int BACK_DURATION = 20; // 20ms
	// ˮƽ����ǰ������
	private static float VE_HORIZONTAL = 0.7f; // 0.1dip/ms

	boolean is_game_started = false;
	private boolean isUnlock = false;
	private boolean isGame = false;
	

	public SlideLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		initDragBitmap();

		// ��ʼ��slide_ring �ߴ�
		Resources r = getResources();
		slide_ring_h = (int) r.getDimension(R.dimen.slide_ring_h);
		slide_ring_w = (int) r.getDimension(R.dimen.slide_ring_w);
		
		Log.e(TAG, "SlideLayout construct 1!");
	}
	
	
	public SlideLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		initDragBitmap();

		// ��ʼ��slide_ring �ߴ�
		Resources r = getResources();
		slide_ring_h = (int) r.getDimension(R.dimen.slide_ring_h);
		slide_ring_w = (int) r.getDimension(R.dimen.slide_ring_w);
		Log.e(TAG, "SlideLayout construct 2!");
	}
	
	
	// ��ʼ��ͼƬ��קʱ��Bitmap����
	private void initDragBitmap() {
		if (dragBitmap == null)
			dragBitmap = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.drag_ring);
	}
	
	// ͨ����ʱ���Ƶ�ǰ����bitmap��λ������
		private Runnable BackDragImgTask = new Runnable() {

			public void run() {
				// һ�´�BitmapӦ�õ��������ֵ
				mLastMoveX = mLastMoveX - (int) (BACK_DURATION * VE_HORIZONTAL);

				// Log.e(TAG, "BackDragImgTask ############# mLastMoveX " +
				// mLastMoveX);

				invalidate();// �ػ�
				// �Ƿ���Ҫ��һ�ζ��� �� �����˳�ʼλ�ã�������Ҫ����
				boolean shouldEnd = Math.abs(mLastMoveX - slide_ring.getRight()) <= 8;
				if (!shouldEnd)
					mainHandler.postDelayed(BackDragImgTask, BACK_DURATION);
				else { // ��ԭ��ʼ����
					resetViewState();
				}
			}
		};
		

	// ���������ǣ��Ƿ������ͼƬ�����Ƿ���Ҫ��ʼ�ƶ�
		private boolean handleActionDownEvenet(MotionEvent event) {
			Log.e("handleActionDownEvenet", "login");

			Rect rect = new Rect();
			slide_ring.getHitRect(rect);
			Log.e("handleActionDownEvenet",
					"rect:" + rect + ";getX():" + event.getX() + ";getY():"
							+ event.getY());

			boolean isHit = rect.contains((int) event.getX(), (int) event.getY());

			//button_right.getHitRect(button_r_rect);


			//button_left.getHitRect(button_l_rect);

			if (isHit) { // ��ʼ��ק �����ظ�ͼƬ
				Log.e("handleActionDownEvenet", "isHit");
				slide_Y = slide_ring.getTop();
				slide_ring.setVisibility(View.INVISIBLE);
			}
			Log.e("handleActionDownEvenet", "isHit:" + isHit);

			return isHit;
			// Log.e(TAG, "handleActionDownEvenet : isHit" + isHit);

		}

		// �ж��ɿ���ָʱ���Ƿ�ﵽĩβ�����Կ����� , �ǣ�����������ͨ��һ�����㷨ʹ����ˡ�
		private void handleActionUpEvent(MotionEvent event) {
			int x = (int) event.getX();

			// Log.e(TAG, "handleActionUpEvent : x -->" + x + "   getRight() " +
			// getRight() );
			// �������ұ߻�15dip���ڴ�������ɹ���
			
			if (isUnlock) {
				Log.e(TAG, "Unlock");
				
				Toast.makeText(mContext, "�����ɹ�", 1000).show();
				
				resetViewState();
				// virbate(); //��һ��
				// �������ǵ���Activity����
				mainHandler.obtainMessage(MainActivity.MSG_LOCK_SUCESS).sendToTarget();
			} else if (isGame) {			
					Log.e(TAG, "BEGIN GAME");
					//Toast.makeText(mContext, "��ʼ��Ϸ", Toast.LENGTH_LONG).show();
					
					resetViewState();
					mainHandler.obtainMessage(MainActivity.MSG_GAME).sendToTarget();
					//is_game_started = true;					
				
			} else {// û�гɹ���������һ�����㷨ʹ�����
					// ÿ��20ms , ����Ϊ0.6dip/ms , ʹ��ǰ��ͼƬ�������һ�ξ��룬ֱ�����������
				mLastMoveX = x; // ��¼�����ɿ�ʱ����ǰ������λ�á�
				int distance = x - slide_ring.getRight();
				// ֻ���ƶ����㹻����Ż���
				// Log.e(TAG, "handleActionUpEvent : mLastMoveX -->" + mLastMoveX +
				// " distance -->" + distance );
				if (distance >= 0)
					mainHandler.postDelayed(BackDragImgTask, BACK_DURATION);
				else { // ��ԭ��ʼ����
					resetViewState();
				}
			}
		}

		// ���ó�ʼ��״̬����ʾtv_slider_iconͼ��ʹbitmap���ɼ�
		void resetViewState() {
			mLastMoveX = 10000;
			slide_ring.setVisibility(View.VISIBLE);
			
			buttonRight_circle = (ImageView) findViewById(R.id.buttonright_circle);
			buttonLeft_circle = (ImageView) findViewById(R.id.buttonLeft_circle);

			
			buttonRight_circle.setImageDrawable(getResources().getDrawable(R.drawable.side_ring));
			buttonLeft_circle.setImageDrawable(getResources().getDrawable(R.drawable.side_ring));

			invalidate(); // �ػ����һ��
		}
		
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.e("onTouchEvent","ontouch");
		int x = (int) event.getX();
		int y = (int) event.getY();
		// Log.e(TAG, "onTouchEvent" + " X is " + x + " Y is " + y);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Log.e(TAG,"ACTION_DOWN");
			mLastMoveX = (int) event.getX();
			// ����Action_Down�¼��� �ж��Ƿ����˻�������
			return handleActionDownEvenet(event);
		case MotionEvent.ACTION_MOVE:
			// Log.e(TAG,"ACTION_MOVE");
			mLastMoveX = x; // ������X�᷽��
			
			buttonRight = (RelativeLayout) findViewById(R.id.buttonRight_comb);
			buttonLeft = (RelativeLayout) findViewById(R.id.buttonLeft_comb);
			
			// ������߼����Ż�����������15������
			isUnlock = x >= buttonRight.getLeft() - 15;
			isGame = x <= buttonLeft.getRight() + 15;

			//Log.e(TAG, "isUnlock:isGame" + isUnlock + ":" + isGame);
			
			//������ı䱳��
			if( isUnlock ){
				buttonRight_circle = (ImageView) findViewById(R.id.buttonright_circle);
				buttonRight_circle.setImageDrawable(getResources().getDrawable(R.drawable.side_touched_ring));
			}
			if( isGame ){
				buttonLeft_circle = (ImageView) findViewById(R.id.buttonLeft_circle);
				buttonLeft_circle.setImageDrawable(getResources().getDrawable(R.drawable.side_touched_ring));
			}
			
			
			
			invalidate(); // ���»���
			return true;

		case MotionEvent.ACTION_UP:
			// Log.e(TAG,"ACTION_UP");
			// ����Action_Up�¼��� �ж��Ƿ�����ɹ����ɹ���������ǵ�Activity ������ ���������˸�ͼƬ��
			handleActionUpEvent(event);
			return true;
		}
		return super.onTouchEvent(event);
	}

	// �����϶�ʱ��ͼƬ
		public void onDraw(Canvas canvas) {
			// super.onDraw(canvas);
			// Log.e(TAG, "onDraw ######" );

			// ͼƬ���������ƶ�
			invalidateDragImg(canvas);
		}

		// ͼƬ���������ƶ�
		private void invalidateDragImg(Canvas canvas) {
			Log.e(TAG, "invalidateDragImg");
			// �Ժ��ʵ�����ֵ���Ƹ�ͼƬ
			int drawXCor = mLastMoveX - slide_ring_w / 2;
			int drawYCor = slide_Y;
			
			canvas.drawBitmap(dragBitmap, drawXCor < 0 ? 5 : drawXCor, drawYCor,
					null);
		}

		public void setMainHandler(Handler mHandler) {
			// TODO Auto-generated method stub
			mainHandler = mHandler;
			
		}

}