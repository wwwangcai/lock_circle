package com.terry.lock;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;

public class LockUser extends AVUser {
	
	public static final String UNIUE_ID =  "uniqueId";
	public static final String GENDER =  "gender";
	public static final String BIRTHDAY =  "birthday";
	public static final String INVATATION =  "invatation";
	
	//此列是pointer类型的 指向LockUser表
	public static final String BELONGS =  "belongs";
	//pointer类型 指向UserGame中一条数据
	public static final String CURRENT_GAME =  "curentGame";
	//pointer类型 指向Revenue中一条数据
	public static final String REVENUE =  "revenue";
	//该用户曾经玩过的所有游戏, relation类型，关联至UserGame表
	public static final String GAMES =  "games";
	//地址，关联至Address表。relation类型
	public static final String ADDRESS =  "address";
	//银行卡，包括支付宝账号。 关联至Cards表。relation类型
	public static final String CARD =  "card";
	//默认地址 pointer to Address
	public static final String DEFAULT_ADDR =  "defaultAddr";
	//默认银行卡 pointer to Cards
	public static final String DEFAULT_CARD =  "defaultCard";
	//该用户所有兑换记录
	public static final String EXCHANGE =  "exchange";
		
	//private AVRelation<UserGame> ugRelation = null;
	//private AVRelation<Address> addrRelation = null;
	//private AVRelation<Card> cardRelation = null;
	//private AVRelation<Exchange> exRelation = null;
	
	public LockUser() {
		super();
		//ugRelation = this.getRelation(GAMES);
		//addrRelation = this.getRelation(ADDRESS);
		//addrRelation = this.getRelation(CARD);
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

	/*
	public void getRevenue(GetCallback<AVObject> callback) {
		//this.getAVObject(REVENUE).fetchIfNeededInBackground(callback);
		//this.getAVObject(REVENUE, LockUser.class).fetchIfNeededInBackground(callback);
		//this.getAVObject(REVENUE, Revenue.class).fetchIfNeededInBackground(callback);
		try {
			this.getAVObject(REVENUE, Revenue.class).fetchIfNeededInBackground(callback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	public Revenue getRevenue(){
		return (Revenue)super.getAVObject(REVENUE);
	}

	public void setRevenue(Revenue revenue) {
		//revenue must be existed already
		//by default, each LockUser created must create a record in revenue table
		//this.put(REVENUE, AVObject.createWithoutData(Revenue.class, revenue.getObjectId()));
		this.put(REVENUE, revenue);
	}

	// below methods more complicated
	public LockUser getBelongs() {
		return (LockUser)super.getAVUser(BELONGS, LockUser.class);
	}

	public void setBelongs(LockUser invator) {
		//因为介绍者肯定是已存在用户,所以用createWithoutData
		//this.put(BELONGS, AVObject.createWithoutData(LockUser.class, invator.getObjectId()));
		this.put(BELONGS, invator);
	}

	public UserGame getCurrentGame() {
		return (UserGame)this.getAVObject(CURRENT_GAME);
	}

	public void setCurrentGame(UserGame game) {
		this.put(CURRENT_GAME, game);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserGame> getGames() {
		return (List<UserGame>)getList(GAMES);
	}

	public void addGames(UserGame game) {
		//this.ugRelation.add(game);
		addUnique(GAMES, game);
	}

	@SuppressWarnings("unchecked")
	public List<Address> getAddress() {
		return (List<Address>)getList(ADDRESS);
	}

	public void addAddress(Address address) {
		//this.ugRelation.add(game);
		addUnique(ADDRESS, address);
	}

	@SuppressWarnings("unchecked")
	public List<Card> getCard() {
		return (List<Card>)getList(CARD);
	}

	public void addCard(Card card) {
		//this.ugRelation.add(game);
		addUnique(CARD, card);
	}
	
	public Address getDeafultAddr() {
		return (Address)this.getAVObject(DEFAULT_ADDR);
	}

	public void setDefaultAddr(Address address) {
		this.put(DEFAULT_ADDR, address);
	}
	
	public Card getDeafultCard() {
		return (Card)this.getAVObject(CARD);
	}

	public void setDefaultCard(Card card) {
		this.put(DEFAULT_CARD, card);
	}
	
	@SuppressWarnings("unchecked")
	public List<Exchange> getExchange() {
		return (List<Exchange>)getList(EXCHANGE);
	}

	public void addExchange(Exchange exchange) {
		//this.ugRelation.add(game);
		addUnique(EXCHANGE, exchange);
	}

}

