package com.terry.lock;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by liulinlin 2014-10-25.
 */
public class AVService {
 
  protected static DeviceUuidFactory df = null;
  private static final String AV_TAG = "AVService";
  private static final String DEFAULT_PASSWD = "lockin1234";

  public static void AVInit(Context ctx) {
    
    AVOSCloud.initialize(ctx, "f8v0zoarc9jrk4sxom03wes6udntd4utpdhbaxohg5j4514f",
        "hqi4gfplb5vzya7k8wt91ksj6siy784o0vxr82kqf22a0m9r");
    
    //AVAnalytics.enableCrashReport(ctx, true);
    
    AVObject.registerSubclass(Address.class);
    AVObject.registerSubclass(Card.class);
    AVObject.registerSubclass(UserGame.class);
    AVObject.registerSubclass(Games.class);
    AVObject.registerSubclass(Revenue.class);
    df = new DeviceUuidFactory(ctx);
  }

  //每一个终端安装及首次启动后都会默认的注册一个用户, 用户名，密码和uniqueID都是跟手机设备绑定的。
  //当用户真正注册的时候会填入手机号和密码，这时候其实是相当于更新原来已存在用户的mobilephone number
  //当用户登陆时，这时候相当于调用loginByMobilePhoneNumberInBackground
  public static void signupOrLoginLockUser(LogInCallback<LockUser> callback) throws AVException {
	  String uniqueId = df.getDeviceUuid().toString();
	  //check if there is already recode existed
	  AVQuery<LockUser> query = new AVQuery<LockUser>();
	  query.whereEqualTo(LockUser.UNIUE_ID, uniqueId);
	  int user_count = 0;
	  try {
		user_count = query.count();
	  } catch (AVException e) {
		Log.i(AV_TAG, "more then 1000 user existed");
		user_count = 1000;
	  }
	  if (user_count > 0 ) {
		  //already existed
		  try {
			List<LockUser> lockUserList = query.find();
			if (lockUserList.size() == 1) {
				LockUser currentUser = lockUserList.get(0);
				String name = currentUser.getUserName();
				String passwd = DEFAULT_PASSWD;
				LockUser.logInInBackground(name, passwd, callback, LockUser.class);
			}
		  } catch (AVException e) {
			// 
			  Log.e(AV_TAG, "some thing wrong when query LockUser, so create it");
			  if (createAnymousLockUser(uniqueId)){
				  LockUser.logInInBackground(uniqueId, DEFAULT_PASSWD, callback, LockUser.class);
			  } else {
				  Log.e(AV_TAG, "create user failed");
			  }
		  }
	  } else {
		  //no record, so create it
		  if (createAnymousLockUser(uniqueId)){
			  LockUser.logInInBackground(uniqueId, DEFAULT_PASSWD, callback, LockUser.class);
		  } else {
			  Log.e(AV_TAG, "create user failed");
		  }
	  }
  }
  
  private static boolean createAnymousLockUser(String name) throws AVException{
	  LockUser lu = new LockUser();
	  lu.setUserName(name);
	  lu.setPassword(DEFAULT_PASSWD);
	  lu.setUniueId(name);
	  //add default relationship or init table here
	  Revenue revenue = new Revenue();
	  revenue.save();
	  lu.setRevenue(revenue);
	  
	  try {
		lu.signUp();
		return true;
	} catch (AVException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
  }
  
  public static void loginByMobile(String phoneNumber, String passwd, LogInCallback<LockUser> callback) {
	  if (LockUser.getCurrentUser(LockUser.class) ==  null) {
		  LockUser.loginByMobilePhoneNumberInBackground(phoneNumber, passwd, callback, LockUser.class);
	  }
  }
  
  public static void signupByMobile(String phoneNumber, String password, SaveCallback callback){
	  LockUser user = LockUser.getCurrentUser(LockUser.class);
	  if (user != null){
		  user.setPassword(password);
		  user.setMobilePhoneNumber(phoneNumber);
		  user.saveInBackground(callback);
	  }
  }
  
  //type should be GOOD.TYPE_VIRTUAL/GOOD.TYPE_REAL/GOOD.TYPE_CASH
  public static void fetchGoodByType(String type, FindCallback<Good> callback){
	  AVQuery<Good> query = new AVQuery<Good>();
	  query.whereEqualTo(Good.TYPE, type);
	  query.findInBackground(callback);
  }
  
  //fetch all games
  public static void fetchAllGame(FindCallback<Games> callback){
	  AVQuery<Games> query = new AVQuery<Games>();
	  query.findInBackground(callback);
  }

 }
