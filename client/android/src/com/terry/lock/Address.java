package com.terry.lock;

import com.avos.avoscloud.AVClassName;
//import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.GetCallback;

@AVClassName("Address")
public class Address extends AVObject {
	
	private static final String PROVINCE =  "province";
	private static final String CITY =  "city";
	private static final String DISTRICT =  "district";
	private static final String ADDR_LINE1 =  "addrLine1";
	
	public String getProvince() {
		return (this.getString(PROVINCE));
		
	}
	public void setProvince(String province) {
		this.put(PROVINCE, province);
	}
	
	public String getCity() {
		return (this.getString(CITY));
	}
	public void setCity(String city) {
		this.put(CITY, city);
	}
	
	public String getDistrict() {
		return (this.getString(DISTRICT));
		
	}
	public void setDistrict(String district) {
		this.put(DISTRICT, district);
	}
	
	public String getAddrLine1() {
		return (this.getString(ADDR_LINE1));
	}
	public void setAddrLine1(String addrLine1) {
		this.put(ADDR_LINE1, addrLine1);
	}	
}

