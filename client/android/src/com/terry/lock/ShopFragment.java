package com.terry.lock;

import java.util.ArrayList;
import java.util.HashMap;

import com.avos.avoscloud.LogUtil.log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;

import android.widget.ListView;
import android.widget.SimpleAdapter;


public class ShopFragment extends Activity{
	PullToRefreshListView mPullRefreshListView = null;
    SimpleAdapter listItemAdapter = null;
    ArrayList<HashMap<String, Object>> listItem = null;
	
	/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Context context = this.getActivity();

		TextView tv = new TextView(context);

		Bundle arc = this.getArguments();
		int tabs=arc.getInt("key");
		
		
		tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		tv.setText("hello actionbar "+tabs);

		return tv;

	}
	*/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();  
		String result=intent.getStringExtra("key");  
		log.d("shopFragment","key:"+result);
	
		setContentView(R.layout.shop_item_fragment);
		
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.vitemListView);

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

}
