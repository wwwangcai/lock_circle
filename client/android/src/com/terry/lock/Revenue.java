package com.terry.lock;

import com.avos.avoscloud.AVClassName;
//import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.GetCallback;

@AVClassName("Revenue")
public class Revenue extends AVObject {
	
	public static final String TOTAL =  "total";
	public static final String TOTAL_CLD =  "totalCLD";
	public static final String TOTAL_COMP =  "totalComp";
	public static final String TOTAL_ACT =  "totalAct";
	public static final String MAX_COMP =  "maxComp";
	public static final String LASTDAY_CLD =  "lastDayCLD";
	public static final String LASTDAY_COMP =  "lastDayComp";
	public static final String LASTDAY_ACT =  "lastDayAct";
	public static final String DAY1 =  "day1";
	public static final String DAY2 =  "day2";
	public static final String DAY3 =  "day3";
	public static final String DAY4 =  "day4";
	public static final String DAY5 =  "day5";
	public static final String DAY6 =  "day6";
	public static final String DAY7 =  "day7";
	private static final String ZERO = "0";
	
	public Revenue(){
		super();
	}
	
	public void initAllField(){
		this.setDay1(ZERO);
		this.setDay2(ZERO);
		this.setDay3(ZERO);
		this.setDay4(ZERO);
		this.setDay5(ZERO);
		this.setDay6(ZERO);
		this.setDay7(ZERO);
		this.setLastdayAct(ZERO);
		this.setLastdayCld(ZERO);
		this.setLastdayComp(ZERO);
		this.setMaxComp(ZERO);
		this.setTotal(ZERO);
		this.setTotalAct(ZERO);
		this.setTotalCld(ZERO);
		this.setTotalComp(ZERO);
	}
	public String getTotal() {
		return (this.getString(TOTAL));
		
	}
	public void setTotal(String total) {
		this.put(TOTAL, total);
	}
	
	public String getTotalCld() {
		return (this.getString(TOTAL_CLD));
	}
	public void setTotalCld(String totalCld) {
		this.put(TOTAL_CLD, totalCld);
	}
	
	public String getTotalComp() {
		return (this.getString(TOTAL_COMP));
	}
	public void setTotalComp(String totalComp) {
		this.put(TOTAL_COMP, totalComp);
	}
	
	public String getTotalAct() {
		return (this.getString(TOTAL_ACT));
	}
	public void setTotalAct(String totalAct) {
		this.put(TOTAL_ACT, totalAct);
	}
	
	public String getMaxComp() {
		return (this.getString(MAX_COMP));
	}
	public void setMaxComp(String maxComp) {
		this.put(MAX_COMP, maxComp);
	}
	
	public String getLastdayCld() {
		return (this.getString(LASTDAY_CLD));
	}
	public void setLastdayCld(String lastdayCld) {
		this.put(LASTDAY_CLD, lastdayCld);
	}
	
	public String getLastdayComp() {
		return (this.getString(LASTDAY_COMP));
	}
	public void setLastdayComp(String lastdayComp) {
		this.put(LASTDAY_COMP, lastdayComp);
	}
	
	public String getLastdayAct() {
		return (this.getString(LASTDAY_ACT));
	}
	public void setLastdayAct(String lastdayAct) {
		this.put(LASTDAY_ACT, lastdayAct);
	}
	
	public String getDay1() {
		return this.getString(DAY1);
	}
	public void setDay1(String day1) {
		this.put(DAY1, day1);
	}
	public String getDay2() {
		return this.getString(DAY2);
	}
	public void setDay2(String day2) {
		this.put(DAY2, day2);
	}
	public String getDay3() {
		return this.getString(DAY3);
	}
	public void setDay3(String day3) {
		this.put(DAY3, day3);
	}
	public String getDay4() {
		return this.getString(DAY4);
	}
	public void setDay4(String day4) {
		this.put(DAY4, day4);
	}
	public String getDay5() {
		return this.getString(DAY5);
	}
	public void setDay5(String day5) {
		this.put(DAY5, day5);
	}
	public String getDay6() {
		return this.getString(DAY6);
	}
	public void setDay6(String day6) {
		this.put(DAY6, day6);
	}
	public String getDay7() {
		return this.getString(DAY7);
	}
	public void setDay7(String day7) {
		this.put(DAY7, day7);
	}
	
}

