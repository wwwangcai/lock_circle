package com.terry.lock;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by liulinlin 2014-10-25.
 */
public class AVService {
 
  public static DeviceUuidFactory df = null;
  private static final String AV_TAG = "AVService";
  public static final String DEFAULT_PASSWD = "lockin1234";

  public static void AVInit(Context ctx) {
    
    AVOSCloud.initialize(ctx, "f8v0zoarc9jrk4sxom03wes6udntd4utpdhbaxohg5j4514f",
        "hqi4gfplb5vzya7k8wt91ksj6siy784o0vxr82kqf22a0m9r");
    
    //AVAnalytics.enableCrashReport(ctx, true);
    
    AVObject.registerSubclass(Address.class);
    AVObject.registerSubclass(Card.class);
    AVObject.registerSubclass(UserGame.class);
    AVObject.registerSubclass(Games.class);
    AVObject.registerSubclass(Revenue.class);
    AVObject.registerSubclass(Exchange.class);
    AVObject.registerSubclass(Good.class);
    df = new DeviceUuidFactory(ctx);
  }

   /**
   *每一个终端安装及首次启动后都会默认的注册一个用户, 用户名，密码和uniqueID都是跟手机设备绑定的。
   *当用户真正注册的时候会填入手机号和密码，这时候其实是相当于更新原来已存在用户的mobilephone number
   *当用户登陆时，这时候相当于调用loginByMobilePhoneNumberInBackground
   * @param callback
   * @throws AVException
   */
  public static void signupOrLoginLockUser(LogInCallback<LockUser> callback) throws AVException {
	  String uniqueId = df.getDeviceUuid().toString();
	  long invatation = df.getDeviceUuid().timestamp();
	  //check if there is already recode existed
	  AVQuery<LockUser> query = AVQuery.getQuery(LockUser.class);
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
			  if (createAnymousLockUser(uniqueId, invatation)){
				  LockUser.logInInBackground(uniqueId, DEFAULT_PASSWD, callback, LockUser.class);
			  } else {
				  Log.e(AV_TAG, "create user failed");
			  }
		  }
	  } else {
		  //no record, so create it
		  if (createAnymousLockUser(uniqueId, invatation)){
			  LockUser.logInInBackground(uniqueId, DEFAULT_PASSWD, callback, LockUser.class);
		  } else {
			  Log.e(AV_TAG, "create user failed");
		  }
	  }
  }
  
  public static void signupOrLoginLockUser() {
	  String uniqueId = df.getDeviceUuid().toString();
	  long invatation = df.getDeviceUuid().timestamp();
	  //check if there is already recode existed
	  AVQuery<LockUser> query = AVQuery.getQuery(LockUser.class);
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
				LockUser.logIn(name, passwd, LockUser.class);
				
			}
		  } catch (AVException e) {
			// 
			  Log.e(AV_TAG, "some thing wrong when query LockUser, so create it");
			  if (createAnymousLockUser(uniqueId, invatation)){
				  try {
					LockUser.logIn(uniqueId, DEFAULT_PASSWD, LockUser.class);
				} catch (AVException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  } else {
				  Log.e(AV_TAG, "create user failed");
			  }
		  }
	  } else {
		  //no record, so create it
		  if (createAnymousLockUser(uniqueId, invatation)){
			  try {
				LockUser.logIn(uniqueId, DEFAULT_PASSWD, LockUser.class);
			} catch (AVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  } else {
			  Log.e(AV_TAG, "create user failed");
		  }
	  }
  }
  
  
  
  public static boolean createAnymousLockUser(String name, long invatation){
	  LockUser lu = new LockUser();
	  lu.setUserName(name);
	  lu.setPassword(DEFAULT_PASSWD);
	  lu.setUniueId(name);
	  lu.setInvatation(String.valueOf(invatation));
	  //add default relationship or init table here
	  Revenue revenue = new Revenue();
	  revenue.initAllField();
	 /* try {
		revenue.save();
	} catch (AVException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}*/
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
  
  /**
   * 通过手机号和密码登录
   * @param phoneNumber
   * @param passwd
   * @param callback
   */
  public static void loginByMobile(String phoneNumber, String passwd, LogInCallback<LockUser> callback) {
	  if (LockUser.getCurrentUser(LockUser.class) ==  null) {
		  LockUser.loginByMobilePhoneNumberInBackground(phoneNumber, passwd, callback, LockUser.class);
	  }
  }
  /**
   * 注册新用户
   * @param phoneNumber
   * @param password
   * @param callback
   */
  public static void signupByMobile(String phoneNumber, String password, SaveCallback callback){
	  LockUser user = LockUser.getCurrentUser(LockUser.class);
	  if (user != null){
		  user.setPassword(password);
		  user.setMobilePhoneNumber(phoneNumber);
		  user.saveInBackground(callback);
	  }
  }
  
  /**
   * 获取特定类型的商品, type为real/virtual/cash中的一种
   * @param type
   * @param callback
   */
  public static void fetchGoodByType(String type, FindCallback<Good> callback){
	  AVQuery<Good> query = AVQuery.getQuery(Good.class);
	  query.whereEqualTo(Good.TYPE, type);
	  query.findInBackground(callback);
  }
  
  /**
   * 获取所有游戏
   * @param callback
   */
  public static void fetchAllGame(FindCallback<Games> callback){
	  AVQuery<Games> query = new AVQuery<Games>();
	  query.findInBackground(callback);
  }
  
  /**
   * 获取当前用户的收益。函数不负责校验当前用户是否为空，由函数调用者校验
   * @param callback
   */
  public static void fetchUserRevenue(GetCallback<AVObject> callback) {
	  LockUser user = LockUser.getCurrentUser(LockUser.class);
	  Revenue re = user.getRevenue();
	  re.fetchIfNeededInBackground(callback);
  }

 }
