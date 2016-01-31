/**
* Copyright© 2009 kzh Co. Ltd.
* All right reserved.
* 
* */
package com.zxt.framework.common.message;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class MessageHandler extends HibernateDaoSupport implements IMessageHandler {
	
	private HashMap messages;
	protected void initDao() {
		List listMessage = getHibernateTemplate().find("from Message");

		this.messages = new HashMap();
		for (int i = 0; i < listMessage.size(); ++i) {
			Message message = (Message) listMessage.get(i);
			this.messages.put(message.getCode(), message);
		}
	}

	public Message getMessage(String code) {
		if (this.messages == null) {
			initDao();
		}

		Object message = this.messages.get(code);
		return (message == null) ? null : (Message) message;
	}
}