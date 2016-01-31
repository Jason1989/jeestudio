package com.zxt.compplatform.acegi.util;

/**
 * 消息管理器具体实现
 * @author 007
 */
public class MessageServiceImpl implements MessageService {
    /* (non-Javadoc)
     * @see com.zxt.compplatform.acegi.util.MessageService#adminMessage()
     */
    public String adminMessage() {
        return "admin message";
    }

    /* (non-Javadoc)
     * @see com.zxt.compplatform.acegi.util.MessageService#adminDate()
     */
    public String adminDate() {
        return "admin " + System.currentTimeMillis();
    }

    /* (non-Javadoc)
     * @see com.zxt.compplatform.acegi.util.MessageService#userMessage()
     */
    public String userMessage() {
        return "user message";
    }

    /* (non-Javadoc)
     * @see com.zxt.compplatform.acegi.util.MessageService#userDate()
     */
    public String userDate() {
        return "user " + System.currentTimeMillis();
    }
}
