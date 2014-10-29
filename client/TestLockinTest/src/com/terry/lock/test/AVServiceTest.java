package com.terry.lock.test;

//import java.util.Iterator;
import android.test.ActivityInstrumentationTestCase2;

import com.avos.avoscloud.AVException;
import com.terry.lock.AVService;
import com.terry.lock.Appstart;
import com.terry.lock.LockUser;
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
	
	
	public void testAddLockUser(){
		LockUser user = new LockUser();
		user.setUserName("test1");
		user.setPassword("12345");
		try {
			user.signUp();
		} catch (AVException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
