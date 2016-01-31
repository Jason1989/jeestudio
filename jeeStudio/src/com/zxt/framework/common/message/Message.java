package com.zxt.framework.common.message;

public class Message {
	private String code;
	private String message;
	private String type;

	public String getCode() {
		return this.code;
	}

	public void setCode(String fcode) {
		this.code = fcode;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String fmessage) {
		this.message = fmessage;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}