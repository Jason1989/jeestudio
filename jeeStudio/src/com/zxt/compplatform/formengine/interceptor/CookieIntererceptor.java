package com.zxt.compplatform.formengine.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * cookie过滤器
 * @author 007
 */
public class CookieIntererceptor extends AbstractInterceptor {
	private static final Logger log = Logger
			.getLogger(CookieIntererceptor.class);

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		// TODO Auto-generated method stub
		// log.info("intercept...");

		/** 定义 request response session对象* */
		ActionContext actionContext = actionInvocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) actionContext
				.get(StrutsStatics.HTTP_RESPONSE);

		String userLoginIp = request.getRemoteAddr();// 用户请求的Ip
		String userRequestUrl = request.getRequestURI().toString();// 用户请求的URL

		/** 当session存在该对象 */
		String host = request.getServerName();// 获得用户的主机名
		String theme = request.getParameter("theme");

		if ((theme != null) && (!"".equals(theme))) {
			Cookie cookie2 = new Cookie("themes", theme);
		    cookie2.setPath("/compplatform/");
		    //  cookie2.setDomain(host);
			cookie2.setMaxAge(33333333);
			response.addCookie(cookie2);
		}

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if ("themes".equals(cookies[i].getName())) {
					log.info("cookies[i].getValue():  "
									+ cookies[i].getValue());
				}
			}
		}
		
		return actionInvocation.invoke();
	}
}
