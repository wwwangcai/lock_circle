package com.terry.lock;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.GetCallback;
//import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.GetCallback;

@AVClassName("Exchange")
public class Exchange extends AVObject {
	
	private static final String GOOD =  "good";
	private static final String STATUS =  "status";
	private static final String COUNT =  "count";
	
	public Exchange(){
		super();
	}
	
	public String getStatus() {
		return (this.getString(STATUS));
		
	}
	public void setStatus(String status) {
		this.put(STATUS, status);
	}
	
	public int getCount() {
		return (this.getInt(COUNT));
	}
	public void setScore(int count) {
		this.put(COUNT, count);
	}
	
	public Good getGood() {
		return (Good)this.getAVObject(GOOD);
	}

	public void setGood(Good good) {
		this.put(GOOD, good);
	}
		
}

