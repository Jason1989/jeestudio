package com.zxt.compplatform.acegi.util;

/**
 * 消息管理器
 * @author 007
 */
public interface MessageService {
	
    /**
     * 管理员信息资源串
     * @return
     */
    String adminMessage();

    /**
     * 管理员操作时间
     * @return
     */
    String adminDate();

    /**
     * 普通用户字符串
     * @return
     */
    String userMessage();

    /**
     * 普通用户操作时间
     * @return
     */
    String userDate();
}
