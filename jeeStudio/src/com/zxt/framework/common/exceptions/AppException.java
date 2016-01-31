/**
* Copyright© 2010 zxt Co. Ltd.
* All right reserved.
* 
*/
package com.zxt.framework.common.exceptions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
/**
 * Title: AppException
 * Description:  
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 * 
 */
public class AppException extends GeneralException {
	private static final long serialVersionUID = -2122941609576536542L;

	public AppException() {
	}

	public AppException(String code, String message, String systemMessage) {
		super(code, message, systemMessage);
	}

	public AppException(String code) {
		super(code);
	}

	public AppException(String code, String message, String systemMessage, String errorType) {
		super(code, message, systemMessage, errorType);
	}

	public AppException(String code, String message) {
		super(code, message, null);
	}

	public AppException(String code, Exception ex, String errorType) {
		this(code);
		if (ex != null) {
			ExceptionFormatter formatter = new ExceptionFormatter(ex);
			setSystemMessage(formatter.getStackMessage());
			setMessage(ex.getMessage());
		}
	}

	public AppException(String code, HashMap paraInfo, String errorType) {
		this(code, paraInfo);
	}

	public AppException(String code, HashMap paraInfo) {
		this(code);
		if (paraInfo != null) {
			Set setInfo = paraInfo.keySet();
			Iterator iteInfo = setInfo.iterator();
			while (iteInfo.hasNext()) {
				String key = (String) iteInfo.next();
				String value = (String) paraInfo.get(key);
				setMessage(getMessageNoCode().replaceAll(key, value));
			}
		}
	}

	public AppException(String code, HashMap paraInfo, Exception ex, String errorType) {
		this(code, paraInfo, ex);
	}

	public AppException(String code, HashMap paraInfo, Exception ex) {
		this(code);
		if (paraInfo != null) {
			Set setInfo = paraInfo.keySet();
			Iterator iteInfo = setInfo.iterator();
			while (iteInfo.hasNext()) {
				String key = (String) iteInfo.next();
				String value = (String) paraInfo.get(key);
				setMessage(getMessageNoCode().replaceAll(key, value));
			}
		}
		if (ex == null)
			return;
		ExceptionFormatter formatter = new ExceptionFormatter(ex);
		setSystemMessage(formatter.getStackMessage());
	}

	public AppException(String code, String[] messages, String[] systemMessages) {
		this(code);
		if (getMessageNoCode() != null) {
			for (int i = 0; i < messages.length; ++i) {
				setMessage(getMessageNoCode().replaceAll("\\{" + i + "\\}", "[" + messages[i] + "]"));
			}
		}

		if (getSystemMessage() == null)
			return;
		for (int i = 0; i < systemMessages.length; ++i) {
			setSystemMessage(getSystemMessage().replaceAll("\\{" + i + "\\}", "[" + systemMessages[i] + "]"));
		}
	}

	public AppException(String code, String message, String systemMessage, Exception ex) {
		super(code, message, systemMessage + ex.getMessage());
	}
}