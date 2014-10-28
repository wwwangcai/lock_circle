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
	
	public void getGame(GetCallback<AVObject> callback) {
		this.getAVObject(GAME).fetchIfNeededInBackground(callback);
	}

	public void setGame(Games game) throws AVException {
		//revenue must be existed already
		//by default, each LockUser created must create a record in revenue table
		this.put(GAME, AVObject.createWithoutData(Games.class, game.getObjectId()));
	}
	
		
}

