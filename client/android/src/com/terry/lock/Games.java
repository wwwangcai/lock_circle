package com.terry.lock;

import com.avos.avoscloud.AVClassName;
//import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.GetCallback;

@AVClassName("Games")
public class Games extends AVObject {
	
	private static final String NAME =  "name";
	private static final String MVP =  "mvp";
	//最好成绩
	private static final String SCORE =  "score";
	
	public String getName() {
		return (this.getString(NAME));
		
	}
	public void setName(String name) {
		this.put(NAME, name);
	}
	
	public String getScore() {
		return (this.getString(SCORE));
	}
	public void setScore(String score) {
		this.put(SCORE, score);
	}
	
		
}

