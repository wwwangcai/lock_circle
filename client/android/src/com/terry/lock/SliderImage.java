package com.terry.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

//import android.widget.RelativeLayout;

@SuppressLint("ClickableViewAccessibility")
public class SliderImage extends ImageView implements OnTouchListener,
		OnGestureListener {

	private static final float K_ratio = (float)0.008;

	private static final String TAG = "SliderImage";

	private Context mContext = null; // 初始化图片拖拽时的Bitmap对象

	private GestureDetector gestureDetector;// 注册监听事件用于监听

	private Handler mainHandler = null; // 与主Activity通信的Handler对象

	private Bitmap dragBitmap = null; // 拖拽图片
	
	public String imei = null;
	
	//public String imei = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
	
	
	public String gid = "0";//游戏id，用于记录每次游戏的数据
	public float scores = 0;
	public int cash = 0;
	public int rank = 0;
	
	//初始化slider在image view中的位置偏移这里需要考虑屏幕密度问题
	public float drawXCor = 0;
	public float drawYCor = 0;
	
	public int dragBitmap_h;
	public int dragBitmap_w;
	
	//初始化速度，速度由lockscreen view中的onfliding方法进行赋值
	float speedY = (float) 0.0;
	float speedX = (float) 0.0;
	
	
	public boolean is_canvas_scaled = false;
	public boolean is_fling = false;
	RelativeLayout.LayoutParams lp ;
	
	Resources r = getResources();
	//获取ation_area高度，用于在ondraw函数中动态改变小球滚动区域
	//public float action_area_h = r.getDimension(R.dimen.action_area_h);
	float scale_view_Y= 0;

	Timer timer = null;

	private boolean is_draw_end = false;

	private float reset_drawXCor;

	private float reset_drawYCor;

	private float y_offset = 0;

	private float speedY_delta = 0;
	public static void requestByHttpGet(String reqStr) throws Exception {
		String path = "http://besq.baidu.com/lock/";
		path += path + reqStr;
		// 新建HttpGet对象
		HttpGet httpGet = new HttpGet(path);
		// 获取HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
		// 获取HttpResponse实例
		HttpResponse httpResp = httpClient.execute(httpGet);
		// 判断是够请求成功
		if (httpResp.getStatusLine().getStatusCode() == 200) {
			// 获取返回的数据
			String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
			Log.i("TAG_HTTPGET", "HttpGet方式请求成功，返回数据如下：");
			Log.i("TAG_HTTPGET", result);
		} else {
			Log.i("TAG_HTTPGET", "HttpGet方式请求失败");
		}
	}
	// HttpPost方式请求
	public static String requestByHttpPost(String reqStr,List <NameValuePair> params) throws Exception {
		//String path = "http://cq01-testing-ecom611.vm.baidu.com:8095/lock/";
		String path = "http://besq.baidu.com/lock/";
		path += reqStr;
		//List <NameValuePair> params=null; 
		Log.i("TAG_HTTPPost", path);
		// 新建HttpGet对象
		HttpPost httpPost = new HttpPost(path);
        /*Post运作传送变数必须用NameValuePair[]阵列储存*/ 
        //params=new ArrayList<NameValuePair>(); 
        //params.add(new BasicNameValuePair("uid","56789")); 
        httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(params,HTTP.UTF_8));
		// 获取HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
		// 获取HttpResponse实例
		HttpResponse httpResp = httpClient.execute(httpPost);
		// 判断是够请求成功
		if (httpResp.getStatusLine().getStatusCode() == 200) {
			// 获取返回的数据
			String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
			Log.i("TAG_HTTPPost", "HttpPost方式请求成功，返回数据如下：");
			Log.i("TAG_HTTPPost", result);
			return result;
		} else {
			Log.i("TAG_HTTPPost", "HttpPost方式请求失败");
			return "";
		}
	}

	public SliderImage(Context context) {
		super(context);
		mContext = context;
		// 设置监听对象
		gestureDetector = new GestureDetector(getContext(), this);
		setOnTouchListener(this);
		initDragBitmap();
		this.setLongClickable(true);
		gestureDetector.setIsLongpressEnabled(true);
		//slide_ring = (ImageView)findViewById(R.id.slide_ring);
		
	}

	public SliderImage(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		mContext = context;
		gestureDetector = new GestureDetector(getContext(), this);
		setOnTouchListener(this);
		initDragBitmap();
		this.setLongClickable(true);
		gestureDetector.setIsLongpressEnabled(true);
		//sl = (ImageView)findViewById(R.id.slider);
	}

	public SliderImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		gestureDetector = new GestureDetector(getContext(), this);
		setOnTouchListener(this);
		initDragBitmap();
		this.setLongClickable(true);
		gestureDetector.setIsLongpressEnabled(true);
		//sl = (ImageView)findViewById(R.id.slider);
	}

	private void initDragBitmap() {
		
		if (dragBitmap == null){
			dragBitmap = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.sliderball);
			dragBitmap_h = dragBitmap.getHeight();
			dragBitmap_w = dragBitmap.getWidth();
			//canvas.drawBitmap(dragBitmap,  100 , 200 , null);
			imei = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
			
			Log.e(TAG,"初始化小球");
			Log.e("initDragBitmap","dragBitmap_h:"+dragBitmap_h+";dragBitmap_w:"+dragBitmap_w);
		}
		else
			Log.e(TAG,"小球失败！");
		
	}

	public void onDraw(Canvas canvas) {
		/*程序初始化时，会调用ShowWindow以及UpdateWindow，而UpdateWindow会发送一个WM_PAINT消息给系统
		 * 从而触发onDraw函数，后续AppWizard生成的程序框架中包含许多涉及发送WM_PAINT消息的函数，比如，当你调整窗口大小
		 * 使得客户区中的显示内容改变时，窗口的OnSize函数会发送WM_PAINT消息，此时OnDraw会被调用。
		 * 所以，在客户区的显示内容改变，客户区的大小改变，客户区由隐藏到显示，以及诸如此类使客户区内容改变的操作，
		 * 都会发送WM_PAINT消息，从而调用OnDraw。
		 */
		//Log.e(TAG,"onDraw");
		invalidateDragImg(canvas);
	}

	
	// 图片更随手势移动
	private void invalidateDragImg(Canvas canvas) {
		//以合适的坐标值绘制该图片
		//Log.e("canvas:","width:"+canvas.getWidth()+";height:"+canvas.getHeight());
	
		//Log.e("invalidateDragImg","login");		
		
		if (is_draw_end){
			//Log.e("canvasmonitor","is_draw_end");
			//先复位修正			
			drawXCor = reset_drawXCor;
			//drawYCor = reset_drawYCor;
			//5px的步长下沉
			drawYCor += 10;
			if (drawYCor <= this.getHeight() + dragBitmap_h){
				
			    canvas.drawBitmap(dragBitmap,drawXCor, drawYCor, null);
			}
			else{
				timer.cancel();				
				//复位小球
				setVisibility(INVISIBLE);				
				//Log.e("RESETDRAG",reset_drawXCor+":"+(reset_drawYCor-1));
				
				speedX = 0;
				speedY = 0;
				drawYCor = 0;
				
				//初始化位置足够大让其不可见
				drawXCor = 1000;
				
				
				//设置显示位置
				lp = new RelativeLayout.LayoutParams(
						(int)(r.getDimension(R.dimen.SliderImage_w)), (int)(r.getDimension(R.dimen.SliderImage_h)));
				lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
				//lp.setMargins(0, 0, 0, (int) (dragBitmap_h));
				setLayoutParams(lp);
				
				
				//有秒闪的bug
				canvas.drawBitmap(dragBitmap,drawXCor, drawYCor, null);

				is_canvas_scaled = false;			
				setOnTouchListener(this);				
				is_draw_end = false;
				
				mainHandler.obtainMessage(main.MSG_GAME_END).sendToTarget();
				Log.e("invalidateDragImg","scores:"+scores);

				//延迟3秒允许开始游戏
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						try {	
							List <NameValuePair> start_params=null; 
							start_params=new ArrayList<NameValuePair>(); 
							start_params.add(new BasicNameValuePair("gid",gid)); 
							start_params.add(new BasicNameValuePair("uid",imei)); 
							String res = requestByHttpPost("score/",start_params);
							Log.e("scores:",res);
							JSONObject jsonObject = new JSONObject(res);
							JSONObject reg_result =jsonObject.getJSONObject("result");
							cash =reg_result.getInt("cash");
							rank =reg_result.getInt("rank");
							Message.obtain(mainHandler, 0x111, cash, rank, scores).sendToTarget();
							//Message.obtain(mainHandler, 0x111, reg_result.getInt("cash"), reg_result.getInt("rank"), scores).sendToTarget();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							Message.obtain(mainHandler, 0x111, cash, rank, scores).sendToTarget();
							//Message.obtain(mainHandler, 0x111, 0, 0, scores).sendToTarget();
							e.printStackTrace();
						}
					}
				}, 2000);
				
				//canvas.drawBitmap(dragBitmap,drawXCor, drawYCor, null);
				//findViewById 是找child view，所以找不到
				//sl.setVisibility(VISIBLE);
				return;
			}
			return;	
		    //canvas.drawBitmap(dragBitmap,drawXCor, drawYCor, null);
		    //写个动画文件，搞个小球渐渐消失效果？
		}

		if (drawYCor < 0 ){
			is_draw_end = true;
			List <NameValuePair> params=null; 
	        params=new ArrayList<NameValuePair>(); 
	        params.add(new BasicNameValuePair("gid",gid)); 
	        params.add(new BasicNameValuePair("uid",imei)); 
	        params.add(new BasicNameValuePair("score","10000")); 
	        try{
	        	
			    String res = requestByHttpPost("end/",params);
	           }catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//sl.setVisibility(VISIBLE);
			Log.e("invalidateDragImg","is_draw_end|drawYCor:"+drawYCor);
			return;
		}
		
		if (drawXCor <= 0 || drawXCor >= this.getWidth()-dragBitmap_w)
		{
			//Log.e("invalidateDragImg","drawX:"+drawXCor);

			//修复speed过小时导致的小球无限循环，增大同方向速度
			if (Math.abs(speedX) < 1 && speedX != 0){
				if (speedX < 0)
					speedX -= 1;
				else
					speedX += 1;
			}
			speedX = -speedX;
		}
		//注意这里的等号 区别
		if ( drawYCor > this.getHeight()-dragBitmap_h)
		{

			if (Math.abs(speedX) < 1 && speedX != 0){
				if (speedX < 0)
					speedX -= 1;
				else
					speedX += 1;
			}
			speedY = -speedY;
		}
		
		//开始计算即时速度，根据弹性力算出实时加速度，继而算出speedY
		
		//弹性力 F=-Kx，假设起始位置为平衡位置，K为弹性系数（可调），x为相对位移
		y_offset = drawYCor - this.getHeight();
		
		//质量默认为1,弹性力等于加速度
		speedY_delta = - K_ratio * y_offset;
		float speed_y = speedY + speedY_delta ;
		
		if ( speed_y * speedY < 0){
			List <NameValuePair> params=null; 
	        params=new ArrayList<NameValuePair>(); 
	        params.add(new BasicNameValuePair("gid",gid)); 
	        params.add(new BasicNameValuePair("uid",imei)); 
	        params.add(new BasicNameValuePair("score",""+drawYCor)); 
	        try{
	        	
			    String res = requestByHttpPost("end/",params);
	           }catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//mainHandler.sendEmptyMessage(0x111);
			//Message.obtain(mainHandler, 0x111, drawYCor).sendToTarget();
			//mainHandler.sendMessage(msg);
			scores = drawYCor;
			Toast.makeText(mContext.getApplicationContext(),drawYCor+"", Toast.LENGTH_SHORT).show();
		}
		
		speedY = speed_y;
		drawXCor += speedX;
		drawYCor += speedY;
		
		
		//为视觉效果，放大5px的深度
		drawXCor = drawXCor > this.getWidth()-dragBitmap_w ? this.getWidth()-dragBitmap_w:drawXCor;
		//drawYCor = drawYCor > this.getHeight()-dragBitmap_h ?  this.getHeight()-dragBitmap_h+5:drawYCor;
		
		
		//落地结束
		if (drawYCor > this.getHeight()-dragBitmap_h ){
			drawYCor = this.getHeight()-dragBitmap_h;
			//复位横轴速度
			//speedX = 50;
		}
		
		if (drawYCor == this.getHeight()-dragBitmap_h &&
				Math.abs(drawXCor-this.getWidth()/2) <= Math.abs(speedX)){
			//timer.cancel();
			//Log.e("canvasmonitor","cancle timer!");
			is_draw_end  = true;
			//Log.e("canvasmonitor:",this.getWidth()+":"+dragBitmap_w);
			drawXCor = (this.getWidth()-dragBitmap_w)/2;

			//Log.e("canvasmonitor","end at:"+drawXCor+":"+drawYCor);
			
		    canvas.drawBitmap(dragBitmap,drawXCor, drawYCor, null);
		    
		    //这里需要优化，没必要每一次都来算一次
			reset_drawXCor = (this.getWidth() - dragBitmap_w)/2;
			reset_drawYCor = this.getHeight()-dragBitmap_h;
			
			//is_once = false;
			//Message.obtain(mainHandler, 0x111, 0, 0, "无成绩").sendToTarget();
			
		    return;
		}
		//Log.e("canvasmonitor",drawXCor+":"+drawYCor+":"+speedX+":"+speedY);
		//为视觉效果，放大5px的深度
	    canvas.drawBitmap(dragBitmap,  drawXCor < 0 ? -5 : drawXCor , drawYCor < 0 ? -5 : drawYCor , null);



	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		Log.e("MyGesture", "Fling");
				
		/*
		 * 初始化起始点，让球在画布变化时候位置不会跳变
		 * 获取手的点击区域中心，会印象体验，touch的区域中心和球的实际位置是有偏移的，即使用也应该用rect的左上角
		 * 
		 */
		Rect rect = new Rect();
		getHitRect(rect);	
		
		drawXCor = 	rect.left;
		drawYCor = 	rect.top;
		//drawXCor = rect.centerX();
		//drawYCor = rect.centerY();
		
		speedX=velocityX/100;
		speedY=velocityY/100;
		
		//限速，保证在10ms的时钟频率下，渲染效果在速度过快时候降低，而加快时钟频率消耗更多CPU
		if( speedX >= 50)
			speedX = 50;
		if( speedY >= 50)
			speedY = 50;
		
		if ( speedY >= 0 ){
			speedY = 0 ;
			speedX = 0;
		}else{

			setLayoutParams(new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			is_canvas_scaled = true;
		
			startTimer();
			is_fling = true;
			//滑动结束后取消监听，以免中途干预小球
			setOnTouchListener(null);
		}
		return false;
		
	}
	
	private void startTimer(){
		Log.e("slider:startTimer","login");
		timer = new Timer();
	    timer.schedule(new TimerTask()
	    {
		     @Override
		     public void run()
		     {
                //Log.e("timer","+++");
		    	
		    	 //SliderImage.UpdateWindow();
			    mainHandler.sendEmptyMessage(0x789);
		     }
	     }, 0, 10);
	}
	
	// 震动一下下咯
	private void virbate() {
		Vibrator vibrator = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
	}

	public void setMainHandler(Handler handler) {
		mainHandler = handler;// activity所在的Handler对象
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		// Log.e("MyGesture", "onDown");
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		// Log.e("MyGesture", "Tapup");
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		// Log.e("MyGesture", "scroll");
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// Log.e("MyGesture", "onTouch");
		Log.e("SliderImage","touch");
		return gestureDetector.onTouchEvent(event);
	}
	

}