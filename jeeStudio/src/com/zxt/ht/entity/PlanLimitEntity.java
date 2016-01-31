package com.zxt.ht.entity;

public class PlanLimitEntity {

	private String sParmerKey;// 参数名
	private String sParmerValue;// 参数值
	private String amountAllowed;// 允许超过额度
	public String getsParmerKey() {
		return sParmerKey;
	}
	public void setsParmerKey(String sParmerKey) {
		this.sParmerKey = sParmerKey;
	}
	public String getsParmerValue() {
		return sParmerValue;
	}
	public void setsParmerValue(String sParmerValue) {
		this.sParmerValue = sParmerValue;
	}
	public String getAmountAllowed() {
		return amountAllowed;
	}
	public void setAmountAllowed(String amountAllowed) {
		this.amountAllowed = amountAllowed;
	}

	

}
