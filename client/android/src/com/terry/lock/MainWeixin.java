package com.terry.lock;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.terry.lock.MagicScrollView;
import com.terry.lock.MagicTextView;
import com.terry.lock.R;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

@SuppressLint("HandlerLeak") public class MainWeixin extends Activity {
	
	public static MainWeixin instance = null;
    private LocalActivityManager mactivityManager = null; 
	 
	private ViewPager mTabPager;	
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1,mTab2,mTab3,mTab4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;//单个水平动画位移
	private int two;
	private int three;
	private LinearLayout mClose;
    private LinearLayout mCloseBtn;
    private View layout;	
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	//private Button mRightBtn;
	//用于显示支付宝效果的收益页面-------
    private MagicScrollView mScrollView;
    private MagicTextView mIncomeTxt;
    private MagicTextView mTotalMoneyTxt;
    private MagicTextView mIncTotalTxt;
    private MagicTextView mIncSevTxt;
    private MagicTextView mIncWeekTxt;
    private MagicTextView mIncMonTxt;
    private MagicTextView mOverPerTxt;
    private MagicTextView mOverCountTxt;
    private LinearLayout mContainer;
    public static int mWinheight;
    int[] location = new int[2];
    //----------------------------
    SimpleAdapter listItemAdapter = null;
    PullToRefreshListView mPullRefreshListView = null;
    ArrayList<HashMap<String, Object>> listItem = null;
    		
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int height = mContainer.getMeasuredHeight();
            Log.d("height  is ====>", "" + height);
            /*
            onMeasureTxt(mIncomeTxt);
            onMeasureTxt(mTotalMoneyTxt);
            onMeasureTxt(mIncTotalTxt);
            onMeasureTxt(mIncSevTxt);
            onMeasureTxt(mIncWeekTxt);
            onMeasureTxt(mIncMonTxt);
            onMeasureTxt(mOverPerTxt);
            onMeasureTxt(mOverCountTxt);
            */
            onMeasureTxt(mIncomeTxt);
            onMeasureTxt(mTotalMoneyTxt);
            onMeasureTxt(mIncTotalTxt);
            mScrollView.sendScroll(MagicScrollView.UP, 0);
        };
    };

    protected void initMagic(View father_view) {
        Rect fram = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(fram);
        mWinheight = fram.height();
        Log.d("winHeight is ====>", "" + mWinheight);
        /*
        mScrollView = (MagicScrollView) father_view.findViewById(R.id.magic_scroll);
        mIncomeTxt = (MagicTextView) father_view.findViewById(R.id.income_money);
        mTotalMoneyTxt = (MagicTextView) father_view.findViewById(R.id.total_money);
        mIncTotalTxt = (MagicTextView) father_view.findViewById(R.id.income_total_txt);
        mIncSevTxt = (MagicTextView) father_view.findViewById(R.id.income_sevdays_txt);
        mIncWeekTxt = (MagicTextView) father_view.findViewById(R.id.income_week_txt);
        mIncMonTxt = (MagicTextView) father_view.findViewById(R.id.income_month_txt);
        mOverPerTxt = (MagicTextView) father_view.findViewById(R.id.overperson_percent);
        mOverCountTxt = (MagicTextView) father_view.findViewById(R.id.overperson_count);
        mContainer = (LinearLayout) father_view.findViewById(R.id.container);

        mIncomeTxt.setValue(3.30);
        mTotalMoneyTxt.setValue(22800.56);
        mIncTotalTxt.setValue(58.56);
        mIncSevTxt.setValue(50.00);
        mIncWeekTxt.setValue(20.00);
        mIncMonTxt.setValue(40.20);
        mOverPerTxt.setValue(88.88);
        mOverCountTxt.setValue(300000000);
        */
        mContainer = (LinearLayout) father_view.findViewById(R.id.me_container);
        mScrollView = (MagicScrollView) father_view.findViewById(R.id.firstpage_magic_scroll);
        mIncomeTxt = (MagicTextView) father_view.findViewById(R.id.firstpage_crrent_rewards);
        mIncTotalTxt = (MagicTextView) father_view.findViewById(R.id.firstpage_yestoday_rewards);
        mTotalMoneyTxt = (MagicTextView) father_view.findViewById(R.id.firstpage_total_rewards);
        
        mIncomeTxt.setValue(100);
        mIncTotalTxt.setValue(100);
        mTotalMoneyTxt.setValue(10000);
        initListener();
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    private void initListener() {
    	/*
        mScrollView.AddListener(mIncomeTxt);
        mScrollView.AddListener(mTotalMoneyTxt);
        mScrollView.AddListener(mIncTotalTxt);
        mScrollView.AddListener(mIncSevTxt);
        mScrollView.AddListener(mIncWeekTxt);
        mScrollView.AddListener(mIncMonTxt);
        mScrollView.AddListener(mOverPerTxt);
        mScrollView.AddListener(mOverCountTxt);
        */
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
    //achartEngine
	protected void initAchart() {
		// TODO Auto-generated method stub
		// 1, 构造显示用渲染图
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		// 2,进行显示
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// 2.1, 构建数据
		Random r = new Random();
		for (int i = 0; i < 2; i++) {
			XYSeries series = new XYSeries("test" + (i + 1));
			// 填充数据
			for (int k = 0; k < 10; k++) {
				// 填x,y值
				series.add(k, 20 + r.nextInt() % 100);
			}
			// 需要绘制的点放进dataset中
			dataset.addSeries(series);
		}
		// 3, 对点的绘制进行设置
		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
		// 3.1设置颜色
		xyRenderer.setColor(Color.BLUE);
		// 3.2设置点的样式
		xyRenderer.setPointStyle(PointStyle.SQUARE);
		// 3.3, 将要绘制的点添加到坐标绘制中
		renderer.addSeriesRenderer(xyRenderer);
		// 3.4,重复 1~3的步骤绘制第二个系列点
		xyRenderer = new XYSeriesRenderer();
		xyRenderer.setColor(Color.RED);
		xyRenderer.setPointStyle(PointStyle.CIRCLE);
		renderer.addSeriesRenderer(xyRenderer);
		// Intent intent = new LinChart().execute(this);
		Intent intent = ChartFactory
				.getLineChartIntent(this, dataset, renderer);
		startActivity(intent);
		//return ChartFactory.getLineChartView(this, dataset, renderer);

	}
	//listView展示
	protected void initListView(View father_view) {
		//ListView list = (ListView) father_view.findViewById(R.id.gameListView);
		//PullToRefreshListView mPullRefreshListView = (PullToRefreshListView) father_view.findViewById(R.id.gameListView);
		mPullRefreshListView = (PullToRefreshListView) father_view.findViewById(R.id.gameListView);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});
		//生成数据用
		//ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		listItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<2;i++)  
        {  
            HashMap<String, Object> map = new HashMap<String, Object>();  
            map.put("ItemImage", R.drawable.item_view_networkcontrol);//图像资源的ID  
            map.put("ItemTitle", "Level "+i);  
            map.put("ItemText", "Finished in 1 Min 54 Secs, 70 Moves! ");  
            listItem.add(map);  
        }  
		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);

        //生成适配器的Item和动态数组对应的元素  
        //SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
		listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
            R.layout.activity_game_item,//ListItem的XML实现  
            //动态数组与ImageItem对应的子项          
            new String[] {"ItemImage","ItemTitle", "ItemText"},   
            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
            new int[] {R.id.mall_item_img,R.id.mall_item_title,R.id.mall_item_desc}  
        );  
        actualListView.setAdapter(listItemAdapter); 
         /*
        //添加并且显示  
        list.setAdapter(listItemAdapter); 
        //添加点击  
        list.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
            	Toast.makeText(getApplicationContext(), "点击第"+arg2+"个项目", Toast.LENGTH_LONG).show();
            }  
        });
        */ 
		
	}
	//pull-to-fresh list
	private class GetDataTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

		@Override
		protected HashMap<String, Object> doInBackground(Void... params) {
			// Simulates a background job.
			HashMap<String, Object> map = new HashMap<String, Object>(); 
			try {
				Thread.sleep(3000);
	            //HashMap<String, Object> map = new HashMap<String, Object>();  
	            map.put("ItemImage", R.drawable.item_view_networkcontrol);//图像资源的ID  
	            map.put("ItemTitle", "Level 4");  
	            map.put("ItemText", "Finished in 2 Min 54 Secs, 70 Moves! "); 
			} catch (InterruptedException e) {
			}
			return map;
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			listItem.add(result);
			listItemAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	
    /** 
     * 通过activity获取视图 
     *  
     * @param id 
     * @param intent 
     * @return 
     */  
    private View getView(String id, Intent intent) {  
        return mactivityManager.startActivity(id, intent).getDecorView();  
    } 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mactivityManager = new LocalActivityManager(this, true); 
        mactivityManager.dispatchCreate(savedInstanceState);  
        setContentView(R.layout.main_weixin);
         //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        instance = this;
        /*
        mRightBtn = (Button) findViewById(R.id.right_btn);
        mRightBtn.setOnClickListener(new Button.OnClickListener()
		{	@Override
			public void onClick(View v)
			{	showPopupWindow (MainWeixin.this,mRightBtn);
			}
		  });*/
        
        mTabPager = (ViewPager)findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        
        mTab1 = (ImageView) findViewById(R.id.img_weixin);
        mTab2 = (ImageView) findViewById(R.id.img_address);
        mTab3 = (ImageView) findViewById(R.id.img_friends);
        mTab4 = (ImageView) findViewById(R.id.img_settings);
        mTabImg = (ImageView) findViewById(R.id.img_tab_now);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));
        mTab4.setOnClickListener(new MyOnClickListener(3));
        Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
        int displayWidth = currDisplay.getWidth();
        int displayHeight = currDisplay.getHeight();
        one = displayWidth/4; //设置水平动画平移大小
        two = one*2;
        three = one*3;
        //Log.i("info", "获取的屏幕分辨率为" + one + two + three + "X" + displayHeight);
        
        
        //InitImageView();//使用动画
        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        
        //load view from activty
        Intent intent_revenue = new Intent(getApplicationContext(), LockMainRevenue.class); 
        Intent intent_shop = new Intent(getApplicationContext(), LockMainShop.class);  
        //View view1 = mLi.inflate(R.layout.main_tab_weixin, null);
        //View view1 = mLi.inflate(R.layout.first_page_layout, null);
        View view1 = getView("fake",intent_revenue );
        //LinearLayout view1 =  (LinearLayout)mLi.inflate(R.layout.first_page_layout, null);
        //View view2 = mLi.inflate(R.layout.main_tab_address, null);
        View view2 = mLi.inflate(R.layout.activity_game, null);
        //View view3 = mLi.inflate(R.layout.main_tab_friends, null);
        //View view3 = mLi.inflate(R.layout.activity_shopping, null);
        View view3 = getView("fake",intent_shop );
        //View view4 = mLi.inflate(R.layout.main_tab_settings, null);
        View view4 = mLi.inflate(R.layout.activity_setting, null);
        
        //用于初始化支付宝效果
        //initMagic(view1);
        //加入图表效果
        //view1.addView(initAchart());
        //初始化listview，后续加载物品在此栏目进行
        initListView(view2);


        //每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			
			//@Override
			//public CharSequence getPageTitle(int position) {
				//return titles.get(position);
			//}
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mTabPager.setAdapter(mPagerAdapter);
    }
    /**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};
    
	 /* 页卡切换监听(原作者:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
				}
				else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //获取 back键
    		
        	if(menu_display){         //如果 Menu已经打开 ，先关闭Menu
        		menuWindow.dismiss();
        		menu_display = false;
        		}
        	else {
        		Intent intent = new Intent();
            	intent.setClass(MainWeixin.this,Exit.class);
            	startActivity(intent);
        	}
    	}
    	/*
    	else if(keyCode == KeyEvent.KEYCODE_MENU){   //获取 Menu键			
			if(!menu_display){
				//获取LayoutInflater实例
				inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
				//这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
				//该方法返回的是一个View的对象，是布局中的根
				layout = inflater.inflate(R.layout.main_menu, null);
				
				//下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
				menuWindow = new PopupWindow(layout,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT); //后两个参数是width和height
				//menuWindow.showAsDropDown(layout); //设置弹出效果
				//menuWindow.showAsDropDown(null, 0, layout.getHeight());
				menuWindow.showAtLocation(this.findViewById(R.id.mainweixin), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
				//如何获取我们main中的控件呢？也很简单
				mClose = (LinearLayout)layout.findViewById(R.id.menu_close);
				mCloseBtn = (LinearLayout)layout.findViewById(R.id.menu_close_btn);
				
				
				//下面对每一个Layout进行单击事件的注册吧。。。
				//比如单击某个MenuItem的时候，他的背景色改变
				//事先准备好一些背景图片或者颜色
				mCloseBtn.setOnClickListener (new View.OnClickListener() {					
					@Override
					public void onClick(View arg0) {						
						//Toast.makeText(Main.this, "退出", Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
			        	intent.setClass(MainWeixin.this,Exit.class);
			        	startActivity(intent);
			        	menuWindow.dismiss(); //响应点击事件之后关闭Menu
					}
				});				
				menu_display = true;				
			}else{
				//如果当前已经为显示状态，则隐藏起来
				menuWindow.dismiss();
				menu_display = false;
				}
			
			return false;
		}
		*/
    	return false;
    }
	//设置标题栏右侧按钮的作用
	public void btnmainright(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  	
    /*
	public void exit_settings(View v) {                           //退出  伪“对话框”，其实是一个activity
		//Intent intent = new Intent (MainWeixin.this,ExitFromSettings.class);
		//Intent intent = new Intent (MainWeixin.this,Exit.class);
		//startActivity(intent);	
		initAchart();
	 }
	 */
    //activity_setting 所有按钮设置
	public void goLockSetting(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  
	
	public void goMyAccount(View v) {  
		Intent intent = new Intent (MainWeixin.this,MyAccount.class);			
		startActivity(intent);
		//Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  
	
	public void goMyDownload(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  
	
	public void goFlowControl(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  
	
	public void goFeedback(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  
	
	public void goCheckUpdate(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  
	
	public void goAboutMe(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  
	//以下为兑换各activty 入口
	public void goShopVitem(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "虚拟商品", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  
	public void goShopCitem(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "提现充值", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      } 
	public void goShopRitem(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);
		Toast.makeText(getApplicationContext(), "实物商品", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }
	
}
    
    

