package com.terry.lock;

import com.avos.avoscloud.AVClassName;
//import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.GetCallback;

@AVClassName("Good")
public class Good extends AVObject {
	
	public static final String NAME =  "name";
	public static final String TYPE =  "type";
	public static final String PRICE =  "price";
	public static final String URL =  "url";
	public static final String FILE_PATH =  "filePath";
	public static final String INTRODUCTION =  "introduction";
	public static final String INSTRUCTION =  "instruction";
	public static final String STOCK =  "stock";
	public static final String SELLER =  "seller";
	public static final String TYPE_VIRTUAL = "virtual";
	public static final String TYPE_REAL = "real";
	public static final String TYPE_CASH = "cash";
	
	public String getName() {
		return (this.getString(NAME));
		
	}
	public void setName(String name) {
		this.put(NAME, name);
	}
	
	public String getType() {
		return (this.getString(TYPE));
	}
	public void setType(String type) {
		this.put(TYPE, type);
	}
	
	public String getPrice() {
		return (this.getString(PRICE));
	}
	public void setPrice(String price) {
		this.put(PRICE, price);
	}
	public String getUrl() {
		return (this.getString(URL));
	}
	public void setUrl(String url) {
		this.put(URL, url);
	}
	
	public String getFilePath() {
		return (this.getString(FILE_PATH));
	}
	public void setFilePath(String filePath) {
		this.put(FILE_PATH, filePath);
	}
	
	public String getIntroduction() {
		return (this.getString(INTRODUCTION));
	}
	public void setIntroduction(String introduction) {
		this.put(INTRODUCTION, introduction);
	}
	public String getInstruction() {
		return (this.getString(INSTRUCTION));
	}
	public void setInstruction(String instruction) {
		this.put(INSTRUCTION, instruction);
	}
	public int getStock() {
		return (this.getInt(STOCK));
	}
	public void setStock(int stock) {
		this.put(STOCK, stock);
	}
	
}
