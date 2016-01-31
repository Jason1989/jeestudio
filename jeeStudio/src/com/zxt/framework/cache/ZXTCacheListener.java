package com.zxt.framework.cache;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zxt.framework.cache.service.EHCacheService;

public class ZXTCacheListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * 加载缓存控制器
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			org.springframework.web.context.WebApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils
					.getRequiredWebApplicationContext(arg0.getServletContext());
			EHCacheService t = (EHCacheService) ctx.getBean("cacheService");
			t.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
