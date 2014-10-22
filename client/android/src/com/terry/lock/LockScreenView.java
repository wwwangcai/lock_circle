package com.terry.lock;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
//import android.content.ClipData;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;

import android.widget.ImageView;
import android.widget.RelativeLayout;
//import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility")
public class LockScreenView extends RelativeLayout {
	
	private static final String TAG = "LockScreenView";
	Context context;
	// 界面的声明
	LayoutInflater mInflater;
	ImageView bgImageView;
	public static int bg_index = 0;
	private SliderImage si = null;
	private ImageView sl = null;
	
	SlideLayout slide_layout = null;
	private Handler mainHandler = null; //与主Activity通信的Handler对象
		
	// HttpGet方式请求
	public static void requestByHttpGet(String reqStr) throws Exception {
		String path = "http://besq.baidu.com/lock/";
		path += reqStr;
		Log.i("TAG_HTTPGET", path);
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
	//for change bg img
	public Drawable[] bgDrawableArray = new Drawable[]{
			this.getResources().getDrawable(R.drawable.a12),
			this.getResources().getDrawable(R.drawable.a2),
			this.getResources().getDrawable(R.drawable.a3),
			this.getResources().getDrawable(R.drawable.a4),
			this.getResources().getDrawable(R.drawable.a5)
	};
	
	
	public  LockScreenView(Context context) {
		super(context);
		Log.e(TAG,"LockScreenView construct 1!");
		this.context = context;		
		
		initView();
		
		this.si = (SliderImage) findViewById(R.id.slider_button);
		slide_layout = (SlideLayout)findViewById(R.id.slide_layout);
		slide_layout.slide_ring = (ImageView) findViewById(R.id.slide_ring);
		if ( slide_layout.slide_ring == null ){
			Log.e(TAG,"slide_ring is null!");
		}
		slide_layout.button_right = (ImageView) findViewById(R.id.buttonRight);

		slide_layout.button_left = (ImageView) findViewById(R.id.buttonLeft);


		//SliderImage.sl = this.sl;
					
		/*
		this.sl.setOnLongClickListener(new View.OnLongClickListener(){

			@Override
			public boolean onLongClick(View v){
				Log.e("lockscreen","onLongClick");
				//si.setVisibility(View.VISIBLE);
				//v.setVisibility(INVISIBLE);
				final List <NameValuePair> start_params=new ArrayList<NameValuePair>();
				try {
					//requestByHttpGet("reg/");
					List <NameValuePair> params=null; 
			        params=new ArrayList<NameValuePair>(); 
			        params.add(new BasicNameValuePair("uid","56789")); 
					String res = requestByHttpPost("reg/",params);
					Log.e("lockscreen",res);
					JSONObject jsonObject = new JSONObject(res);
					//String jid = jsonObject.getString("result");
					JSONObject reg_result =jsonObject.getJSONObject("result");
					Log.e("gid",reg_result.getString("gid"));
					
					//每次为sliderBall分配一个gid，用于统计成绩
					Message.obtain(mainHandler, 0x121,reg_result.getString("gid") ).sendToTarget();
					
					//准备start的发送参数
					//start_params=new ArrayList<NameValuePair>(); 
					start_params.add(new BasicNameValuePair("uid","56789")); 
					start_params.add(new BasicNameValuePair("gid",reg_result.getString("gid"))); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//延迟三秒允许开始游戏
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						try {		
							String res = requestByHttpPost("start/",start_params);
							//requestByHttpGet("start/");
							si.setVisibility(View.VISIBLE);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, 1000);
				
				//si.setVisibility(View.VISIBLE);
				v.setVisibility(INVISIBLE);
				//ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
				//ClipData dragData = new ClipData(v.getTag(),ClipData.MIMETYPE_TEXT_PLAIN,item);
				//DragShadowBuilder myShadow = new DragShadowBuilder(v);
				//startDrag(null, myShadow, v, 0);
				return false;
			}
		});
		*/
		
		
	}

	public LockScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		Log.e(TAG,"LockScreenView construct 2!");
		initView();
		
		Log.e("lockscreen:","获取slider_button VIEW");
		this.si = (SliderImage) findViewById(R.id.slider_button);
		
	}
	
	public LockScreenView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs);
		this.context = context;
		Log.e(TAG,"LockScreenView construct 3!");
		initView();
		
		Log.e("lockscreen:","获取slider_button VIEW");
		this.si = (SliderImage) findViewById(R.id.slider_button);
	}
	
	public void initView() {
		Log.e(TAG,"initView begin");
		this.mInflater = LayoutInflater.from(context);
		//load lock_screen_view
		this.mInflater.inflate(R.layout.lock_screen_view,this);
		
		Log.e(TAG,"layout inflated");
		bgImageView = (ImageView)this.findViewById(R.id.lock_screen_bgimageView);
		if (bgImageView == null){
			Log.e("initView","bgImageView is null!");
			System.exit(1);
		}
		bgImageView.setImageDrawable(bgDrawableArray[bg_index]);
	}
	
	public void changeBGimg(){
		bgImageView = (ImageView)findViewById(R.id.lock_screen_bgimageView);
		/*random bgDrawableArray index*/
		Random rand = new Random(122344);
		bg_index = rand.nextInt(bgDrawableArray.length);
		
		//bg_index = bg_index+1;
		bgImageView.setImageDrawable(bgDrawableArray[bg_index]);
	
	}

	public void setMainHandler(Handler handler){
		mainHandler = handler;//activity所在的Handler对象
	}	

}

