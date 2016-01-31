/**
* Copyright© 2010 zxt Co. Ltd.
* All right reserved.
* 
*/
package com.zxt.framework.common.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.zxt.framework.common.message.IMessageHandler;
import com.zxt.framework.common.message.Message;
import com.zxt.framework.common.util.DBUtil;
/**
 * Title: GeneralException
 * Description:  
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 * 
 */
public class GeneralException extends Exception {
	private static final long serialVersionUID = 4997121561818000009L;
	private String code;
	private String message;
	private String systemMessage;
	private String errortype;

	public GeneralException() {
	}

	public GeneralException(String code) {
		this.code = code;
		try {
			if (!code.equals("0")) {
				Message msg = ((IMessageHandler) DBUtil.getBeanByClass(IMessageHandler.class)).getMessage(code);
				if (msg == null) {
					this.message = "Not define error code,please defineû���������������ݣ�����ƽ̨����ӡ�";
				} else {
					this.message = msg.getMessage();
					this.errortype = msg.getType();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.message = "û���������������ݣ�����ƽ̨����ӡ�";
		}
	}

	public GeneralException(String code, String[] messages, String[] systemMessages) {
		this(code);
		for (int i = 0; i < messages.length; ++i) {
			this.message = this.message.replaceAll("\\{" + i + "\\}", "[" + messages[i] + "]");
		}

		for (int i = 0; i < systemMessages.length; ++i) {
			this.systemMessage = this.systemMessage.replaceAll("\\{" + i + "\\}", "[" + systemMessages[i] + "]");
		}
	}

	public GeneralException(String code, String message, String systemMessage) {
		this.code = code;
		this.message = message;
		this.systemMessage = systemMessage;
	}

	public GeneralException(String code, String message, String systemMessage, String errorType) {
		this.code = code;
		this.message = message;
		this.systemMessage = systemMessage;
		this.errortype = errorType;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return getErrortype() + this.code + "-" + this.message;
	}

	public String getMessageNoCode() {
		return this.message;
	}

	public String getSystemMessage() {
		return this.systemMessage;
	}

	public String toString() {
		return "GeneralException [Code:" + this.code + ", Message:" + this.message + ", SystemMessage:"
				+ this.systemMessage + "]";
	}

	public void setSystemMessage(String systemMessage) {
		this.systemMessage = systemMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrortype() {
		if ((this.errortype == null) || (this.errortype.length() == 0))
			return "E";
		return this.errortype.toUpperCase();
	}

	public void setErrortype(String errortype) {
		this.errortype = errortype;
	}

	static {
		Logger.getLogger("com.caucho.hessian.server.HessianSkeleton").setLevel(Level.OFF);
	}
}
