package com.terry.lock;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.GetCallback;
//import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.GetCallback;

@AVClassName("UserGame")
public class UserGame extends AVObject {
	
	//pointer类型列，指向Games表
	private static final String GAME =  "game";
	//某用户在该游戏上的最好成绩最好成绩
	private static final String MAX_SCORE =  "maxScore";
	//某用户在该游戏上的总收益
	private static final String TOTAL_REVENUE =  "totalRevenue";
	
	public UserGame(){
		super();
	}
	
	public String getMaxScore() {
		return (this.getString(MAX_SCORE));
		
	}
	public void setMaxScore(String score) {
		this.put(MAX_SCORE, score);
	}
	
	public String getTotalRevenue() {
		return (this.getString(TOTAL_REVENUE));
	}
	public void setTotalRevenue(String revenue) {
		this.put(TOTAL_REVENUE, revenue);
	}
	
	public Games getGame() {
		return (Games)this.getAVObject(GAME);
	}

	public void setGame(Games game) {
		this.put(GAME, game);
	}
	
		
}

