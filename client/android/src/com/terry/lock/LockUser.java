package com.terry.lock;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;

public class LockUser extends AVUser {
	
	private static final String UNIUE_ID =  "uniqueId";
	private static final String GENDER =  "gender";
	private static final String BIRTHDAY =  "birthday";
	private static final String INVATATION =  "invatation";
	
	//此列是pointer类型的 指向LockUser表
	private static final String BELONGS =  "belongs";
	//pointer类型 指向UserGame中一条数据
	private static final String CURRENT_GAME =  "curentGame";
	//pointer类型 指向Revenue中一条数据
	private static final String REVENUE =  "revenue";
	//该用户曾经玩过的所有游戏, relation类型，关联至UserGame表
	private static final String GAMES =  "games";
	//地址，关联至Address表。relation类型
	private static final String ADDRESS =  "address";
	//银行卡，包括支付宝账号。 关联至Cards表。relation类型
	private static final String CARD =  "card";
	//默认地址 pointer to Address
	private static final String DEFAULT_ADDR =  "defaultAddr";
	//默认银行卡 pointer to Cards
	private static final String DEFAULT_CARD =  "defaultCard";
		
	private AVRelation<UserGame> ugRelation = null;
	private AVRelation<Address> addrRelation = null;
	private AVRelation<Card> cardRelation = null;
	
	public LockUser() {
		super();
		ugRelation = this.getRelation(GAMES);
		addrRelation = this.getRelation(ADDRESS);
		addrRelation = this.getRelation(CARD);
	}
	
    public String getUserName() {
        return this.getUsername();
    }
    
    public void setUserName(String username) {
        this.setUsername(username);
    }
 
    public String getUniueId() {
		return this.getString(UNIUE_ID);
	}

	public void setUniueId(String uniueId) {
		this.put(UNIUE_ID, uniueId);
	}

	public String getGender() {
		return this.getString(GENDER);
	}

	public void setGender(String gender) {
		this.put(GENDER, gender);
	}

	public String getBirthday() {
		return this.getString(BIRTHDAY);
	}

	public void setBirthday(String birthday) {
		this.put(BIRTHDAY, birthday);
	}

	public int getInvatation() {
		return this.getInt(INVATATION);
	}

	public void setInvatation(String invatation) {
		this.put(INVATATION, invatation);
	}

	public void getRevenue(GetCallback<AVObject> callback) {
		this.getAVObject(REVENUE).fetchIfNeededInBackground(callback);
	}

	public void setRevenue(Revenue revenue) throws AVException {
		//revenue must be existed already
		//by default, each LockUser created must create a record in revenue table
		this.put(REVENUE, AVObject.createWithoutData(Revenue.class, revenue.getObjectId()));
	}

	// below methods more complicated
	public void getBelongs(GetCallback<AVObject> callback) {
		this.getAVObject(BELONGS).fetchIfNeededInBackground(callback);
	}

	public void setBelongs(LockUser invator) throws AVException {
		//因为介绍者肯定是已存在用户,所以用createWithoutData
		this.put(BELONGS, AVObject.createWithoutData(LockUser.class, invator.getObjectId()));
	}

	public void getCurrentGame(GetCallback<AVObject> callback) {
		this.getAVObject(CURRENT_GAME).fetchIfNeededInBackground(callback);
	}

	public void setCurrentGame(UserGame currentGame) throws AVException {
		this.put(CURRENT_GAME, AVObject.createWithoutData(UserGame.class, currentGame.getObjectId()));
	}
	
	public void getGames(FindCallback<UserGame> callback) {
		this.ugRelation.getQuery().findInBackground(callback);
	}

	public void setGames(UserGame game) {
		this.ugRelation.add(game);
	}

	public void getAddress(FindCallback<Address> callback) {
		this.addrRelation.getQuery().findInBackground(callback);
	}

	public void setAddress(Address address) {
		this.addrRelation.add(address);
	}

	public void getCard(FindCallback<Card> callback) {
		this.cardRelation.getQuery().findInBackground(callback);
	}

	public void setCard(Card card) {
		this.cardRelation.add(card);
	}
	
	public void getDefaultAddr(GetCallback<AVObject> callback) {
		this.getAVObject(DEFAULT_ADDR).fetchIfNeededInBackground(callback);
	}

	public void setDefaultAddr(Address address) throws AVException {
		this.put(DEFAULT_ADDR, AVObject.createWithoutData(Address.class, address.getObjectId()));
	}
	
	public void getDefaultCard(GetCallback<AVObject> callback) {
		this.getAVObject(DEFAULT_CARD).fetchIfNeededInBackground(callback);
	}

	public void setDefaultCard(Card card) throws AVException {
		this.put(DEFAULT_CARD, AVObject.createWithoutData(Card.class, card.getObjectId()));
	}

}

