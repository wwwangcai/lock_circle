package com.terry.lock.test;

//import java.util.Iterator;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.terry.lock.AVService;
import com.terry.lock.Appstart;
import com.terry.lock.LockUser;
import com.terry.lock.Revenue;
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
	
	
	/*public void testAddLockUser(){
		LockUser user = new LockUser();
		user.setUserName("test1");
		user.setPassword("12345");
		try {
			user.signUp();
		} catch (AVException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	/*public void testAnymousUser(){
		String username = "asdf";
		AVService.createAnymousLockUser(username);
		LockUser user = null;
		user = AVUser.getCurrentUser(LockUser.class);
		if (user == null) {
			try {
				user = AVUser.logIn(username, AVService.DEFAULT_PASSWD, LockUser.class);
				
			} catch (AVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertEquals(username, user.getUserName());
		Log.e(TAG, user.getObjectId());
	}*/
	
	/*public void testFetchUserInfo() {
		String username = "asdf";
		try {
			LockUser user = AVUser.logIn(username, AVService.DEFAULT_PASSWD, LockUser.class);
			Revenue re = user.getRevenue();
			re.fetchIfNeeded();
			String day1 = re.getDay1();
			assertEquals("0", day1);
			
			Log.e(TAG, user.getRevenue().getDay1());
		} catch (AVException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*public void testUUID(){
		String s = AVService.df.getDeviceUuid().toString();
		Log.e(TAG, s);
	}*/
	
	public void testAnymousSignup(){
		AVService.signupOrLoginLockUser();
		LockUser user = AVUser.getCurrentUser(LockUser.class);
		assertNull(user);
	}
	

}
