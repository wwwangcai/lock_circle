package com.terry.lock;

import com.avos.avoscloud.AVClassName;
//import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.GetCallback;

@AVClassName("Card")
public class Card extends AVObject {
	
	//银行名称
	private static final String BANK =  "bank";
	//帐户号
	private static final String ACCOUNT =  "account";
	//账户名
	private static final String USER_NAME =  "userName";
	
	
	public String getBank() {
		return (this.getString(BANK));
		
	}
	public void setBank(String bank) {
		this.put(BANK, bank);
	}
	
	public String getAccount() {
		return (this.getString(ACCOUNT));
	}
	public void setAccount(String account) {
		this.put(ACCOUNT, account);
	}
	
	public String getUserName() {
		return (this.getString(USER_NAME));
		
	}
	public void setUserName(String userName) {
		this.put(USER_NAME, userName);
	}
	
}

