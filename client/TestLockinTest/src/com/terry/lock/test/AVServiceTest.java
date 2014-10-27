package com.terry.lock.test;

//import java.util.Iterator;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.terry.lock.AVService;
import com.terry.lock.Appstart;
import com.terry.lock.Good;
//import com.terry.lock.main;

public class AVServiceTest extends ActivityInstrumentationTestCase2<Appstart> {
	
	private static final String TAG = "TEST";
	Appstart testapp;

	//@SuppressWarnings("deprecation")
	public AVServiceTest() {
		//super(name);
		super("com.terry.lock.Appstart",Appstart.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		testapp = this.getActivity();
		AVService.AVInit(testapp);
	}
	
	public void testGoodSaveAndFetch(){
		//boolean flag = false;
		//assertTrue("demo flag", flag);
		//assertEquals("test", testapp.test);
		Good good = new Good();
		good.setName("good1");
		good.setPrice("28");
		good.setType("real");
		
		
		SaveCallback callback = new SaveCallback(){

			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				if (arg0 == null) {
					Log.e(TAG, "good save success");
					FindCallback<Good> fcallback = new FindCallback<Good>(){

						@Override
						public void done(List<Good> arg0, AVException arg1) {
							// TODO Auto-generated method stub
							for (Good g : arg0){
								Log.e(TAG, g.getName());
							}
							assertEquals(1, arg0.size());
						}
						
					};
					AVService.fetchGoodByType("real", fcallback);
				} else {
					Log.e(TAG, "good save failed:"+ arg0.getMessage());
				}
				//assertNull(arg0);
			}
		};
		good.saveInBackground(callback);
		
	}

}
