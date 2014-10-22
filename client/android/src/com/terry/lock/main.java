package com.terry.lock;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.graphics.drawable.BitmapDrawable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
@SuppressWarnings("deprecation")


public class main extends Activity{
	
	public static final int MSG_LOCK_SUCESS = 0x123;
	protected static final int MSG_LOCK_CHANGE = 0x456;
	public static final int SCROLL_BUTTON_DRAW = 0x789;
	public static final int  MSG_SCORES_CHANGE = 0x111;
	public static final int  MSG_GID_CHANGE = 0x121;
	private static final boolean DEVELOPER_MODE = true;
	
	protected static final int MSG_GAME_BEGIN = 0x1000;
	protected static final int MSG_GAME_END = 0x1001;
	
	private SliderImage game_ball = null;
	//private TextView scores_intro = null;
	//private TextView rank_intro = null;
	//private TextView cash_intro = null;
	//手机唯一标示
	public String imei = null;
	
	//显示用
	private PopupWindow menuWindow;
	 
	LockScreenView mlockScreenView;
	RelativeLayout relativeLayout;
	private SlideLayout slide_layout;
	
	public static float density;
	public static int densityDPI;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	     if (DEVELOPER_MODE) {
	         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	                 .detectDiskReads()
	                 .detectDiskWrites()
	                 .detectNetwork()   // or .detectAll() for all detectable problems
	                 .penaltyLog()
	                 .build());
	         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
	                 .detectLeakedSqlLiteObjects()
	                 .detectLeakedClosableObjects()
	                 .penaltyLog()
	                 .penaltyDeath()
	                 .build());
	     }
		super.onCreate(savedInstanceState);
		imei = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		setContentView(R.layout.main);
		Log.e("main","ONCREATE");
		
		mlockScreenView = new LockScreenView(main.this);
		relativeLayout = (RelativeLayout) findViewById(R.id.mainlayout);		
		relativeLayout.addView(mlockScreenView);
				
		game_ball = (SliderImage) findViewById(R.id.slider_button);
		slide_layout = (SlideLayout) findViewById(R.id.slide_layout);
		
		//scores_intro = (TextView) findViewById(R.id.scores_intro);
		//rank_intro = (TextView) findViewById(R.id.rank_intro);
		//cash_intro = (TextView) findViewById(R.id.cash_intro);
		//scores_var.setText("1");
		//把game_ball的监听托管给了mlockscreenview
		//sb.setOnTouchListener(mlockScreenView);
		//sb.setOnTouchListener();
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		// 屏幕密度（像素比例：0.75/1.0/1.5/2.0） 
		density  = dm.density;	
		// 屏幕密度（像素比例：0.75/1.0/1.5/2.0） 
		densityDPI = dm.densityDpi;				
		Log.e("main" , "density=" + density + "; densityDPI=" + densityDPI);  
		/*
		Display mDisplay = getWindowManager().getDefaultDisplay();
		SCR_WIDTH = mDisplay.getWidth();
		SCR_HEIGHT = mDisplay.getHeight();
		Log.e("Main", "SCR_WIDTH = " + SCR_WIDTH);
		Log.e("Main", "SCR_HEIGHT = " + SCR_HEIGHT);
		*/
		
		MyApp mAPP = null; 
		
		//game_ball = (SliderImage) findViewById(R.id.game_ball);
		
        mAPP = (MyApp) getApplication(); 
        mAPP.setHandler(mHandler); 
        

        game_ball.setFocusable(true);  
        game_ball.setClickable(true);
        game_ball.setLongClickable(true);
        game_ball.setMainHandler(mHandler);
        
        mlockScreenView.setMainHandler(mHandler);
        slide_layout.setMainHandler(mHandler);
		
        
		
		Intent i = new Intent(main.this, OtherService.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(i);
		// what mean? 把当前的activity设置为锁屏type？
		main.this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		Intent intent = new Intent(main.this, LockService.class);
		startService(intent);
	}

	public void onStart(){
		super.onStart();
		mlockScreenView.bgImageView.setImageDrawable(mlockScreenView.bgDrawableArray[LockScreenView.bg_index]);
		Log.e("main:onStart--",LockScreenView.bg_index+"");
		
	}
	
	@SuppressLint("HandlerLeak") private Handler mHandler =new Handler (){
		
		public void handleMessage(Message msg){
			
			//Log.e(TAG, "handleMessage :  #### " );
			
			if(MSG_LOCK_SUCESS == msg.what)
			{
				Log.e("main","MSG_LOCK_SUCESS");
				//timer.cancel();
				finish(); // 锁屏成功时，结束我们的Activity界面
			}
			
			if(MSG_GAME_BEGIN == msg.what){
				Log.e("main","MSG_GAME_BEGIN");
				Toast.makeText(getApplicationContext(), "开始游戏", Toast.LENGTH_LONG).show();
				//显示游戏小球并初始化
				//game_ball.setVisibility(View.VISIBLE);
				
				final List <NameValuePair> start_params=new ArrayList<NameValuePair>();
				try {
					//requestByHttpGet("reg/");
					List <NameValuePair> params=null; 
			        params=new ArrayList<NameValuePair>(); 
			        params.add(new BasicNameValuePair("uid",imei)); 
					String res = requestByHttpPost("reg/",params);
					Log.e("lockscreen",res);
					JSONObject jsonObject = new JSONObject(res);
					//String jid = jsonObject.getString("result");
					JSONObject reg_result =jsonObject.getJSONObject("result");
					Log.e("gid",reg_result.getString("gid"));
					
					//每次为sliderBall分配一个gid，用于统计成绩
					Message.obtain(this, MSG_GID_CHANGE,reg_result.getString("gid") ).sendToTarget();
					
					//准备start的发送参数
					//start_params=new ArrayList<NameValuePair>(); 
					start_params.add(new BasicNameValuePair("uid",imei)); 
					start_params.add(new BasicNameValuePair("gid",reg_result.getString("gid"))); 
					requestByHttpPost("start/",start_params);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//延迟三秒允许开始游戏
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						try {		
							//requestByHttpPost("start/",start_params);
							//requestByHttpGet("start/");
							game_ball.setVisibility(View.VISIBLE);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, 100);
				
			}
			
			if(MSG_GAME_END == msg.what){
				Log.e("main","MSG_GAME_END");
				slide_layout.is_game_started = false;
				//game_ball.scores = 0;
				slide_layout.resetViewState();
				
			}
			
			if(MSG_LOCK_CHANGE == msg.what)
			{
				Log.e("main","MSG_LOCK_CHANGE");
				//view.setBackgroundResource(drawableIds[(int) ((Math.random()*10)%3)]);
				//view.setBackgroundResource(drawableIds[0]);
			}
			if (SCROLL_BUTTON_DRAW == msg.what)
			{
				game_ball.invalidate();
				//game_ball.UpdateWindow();
				//mlockScreenView.onDraw(canvas)
			}
			if(MSG_SCORES_CHANGE == msg.what)
			{
				Log.e("main","scores_change");
				Log.e("main",""+msg.obj+" "+msg.arg1 +" "+msg.arg2);
				
				LayoutInflater inflater = getLayoutInflater();
				   View layout = inflater.inflate(R.layout.score_borad,
				     (ViewGroup) findViewById(R.id.llToast));
				   ImageView image = (ImageView) layout
				     .findViewById(R.id.tvImageToast);
				   image.setImageResource(R.drawable.sliderball);
				   TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
				   title.setText("积分牌");
				   TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
				   text.setText("本次成绩如下：");
				   TextView sc_text = (TextView) layout.findViewById(R.id.tscores_intro);
				   sc_text.setText("scores  :"+msg.obj);
				   TextView pl_text = (TextView) layout.findViewById(R.id.tplayer_intro);
				   pl_text.setText("player  : 0");
				   TextView cash_text = (TextView) layout.findViewById(R.id.tcash_intro);
				   cash_text.setText("cash  :"+msg.arg1);
				   TextView rank_text = (TextView) layout.findViewById(R.id.trank_intro);
				   rank_text.setText("rank  :"+msg.arg2);
				   /*
				   menuWindow = new PopupWindow(layout,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,true);
				   menuWindow.showAsDropDown(layout); //设置弹出效果
				   menuWindow.setTouchable(true);
				   menuWindow.setOutsideTouchable(true);
				   menuWindow.update(); 
				   menuWindow.setBackgroundDrawable(new BitmapDrawable()); 
				   //menuWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
				   menuWindow.showAtLocation(relativeLayout, Gravity.TOP|Gravity.CENTER_HORIZONTAL, 600, 40); //设置layout在PopupWindow中显示的位置
				   */
				   
				   Toast toast = new Toast(getApplicationContext());
				   toast.setGravity(Gravity.TOP, 12, 40);
				   toast.setDuration(Toast.LENGTH_LONG);
				   //toast.setDuration(1000);
				   toast.setView(layout);
				   toast.show();
				   game_ball.scores = 1200;
				   game_ball.cash = 0;
				   game_ball.rank = 0;
				   
			}
			if (MSG_GID_CHANGE == msg.what)
			{
				game_ball.gid = (String) msg.obj;
			}
		}
		
	};
	
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
			
}


/*

	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub

		// android底层系统把HOME键屏蔽了，但如果发现它是TYPE_KEYGUARD类的窗体，则不会过滤。所以把Activity修改成TYPE_KEYGUARD

		// 类就好了
		// 按home键直接解锁了。。
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			Log.e("God", "丫的，看你住哪里跑!");
			break;
		case KeyEvent.KEYCODE_POWER:
			Log.e("God", "电源键按下");
		}
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		view = (ImageView) findViewById(R.id.image);
		Intent i = new Intent(main.this, OtherService.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(i);
		// what mean? 把当前的activity设置为锁屏type？
		main.this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		Intent intent = new Intent(main.this, LockService.class);
		startService(intent);
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				flag = true;
				return false;
			}
		});
	}
	*/
