package com.terry.lock;

//import cn.buaa.myweixin.R;
import com.avos.avoscloud.LogUtil.log;


import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;



//import cn.buaa.myweixin.R.layout;

public class LockMainShop extends ActivityGroup  {


	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//LockUser TODO-- move to app
		
		//setContentView(R.layout.activity_shopping);
		setContentView(R.layout.activity_shopping_v2);
		log.d("lockMainshop","here");
	    TabHost tabHost = (TabHost) this.findViewById(R.id.tabhost);  
	    tabHost.setup(this.getLocalActivityManager());
	    TabWidget tabWidget = tabHost.getTabWidget();  
        //将要分页显示的View装入tabHost中
        //LayoutInflater mLi = LayoutInflater.from(this);
        //View view1 = mLi.inflate(R.layout.shop_item_fragment, null);
	    Intent v_intent = new Intent();
	    Bundle bundle = new Bundle();  
        bundle.putString("key", "hello world");  
        v_intent.putExtras(bundle);
        v_intent.setClass(this, ShopFragment.class); 
	      
	    tabHost.addTab(tabHost.newTabSpec("tab1")  
	            .setIndicator("虚拟商品")  
	            .setContent(v_intent));  
	      
	    tabHost.addTab(tabHost.newTabSpec("tab2")  
	            .setIndicator("实物商品")  
	            .setContent(R.id.view3));  
	      
	    tabHost.addTab(tabHost.newTabSpec("tab3")  
	            .setIndicator("已购商品")  
	            .setContent(R.id.view2));  
	    tabHost.setup();
	      
	    final int tabs = tabWidget.getChildCount();  
	    Log.d("xx", "***tabWidget.getChildCount() : "+ tabs);  
	    
		
   }

    
}