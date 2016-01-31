package com.zxt.compplatform.formengine.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.action.SystemFrameAction;
import com.zxt.compplatform.formengine.constant.Constant;

/**
 * 子系统主题工具管理类
 * 
 * @author 007
 */
public class ThemesManager {
	private static final Logger log = Logger.getLogger(SystemFrameAction.class);

	/**
	 * 获取当前的样式
	 * 
	 * @param request
	 * @return
	 */
	public static String getTheme(HttpServletRequest request,
			HttpServletResponse response, String sysKey) {
		// 皮肤

		String changeSkipTheme = request.getParameter("theme");// 换肤请求
		String cookieTheme = getCookieTheme(request, response, sysKey);// cookie皮肤
		String systemDefaultSkip = "";// 系统设置默认皮肤
		if (request.getAttribute("theme") != null) {
			systemDefaultSkip = request.getAttribute("theme").toString();
		}

		if (cookieTheme == null || "".equals(cookieTheme)) {
			cookieTheme = Constant.PLAT_INIT_SKIP;
		}

		// 判断该系统是否允许换肤
		String isAbleChangeSkip = "";
		if (request.getAttribute("selectskinenable") != null) {
			isAbleChangeSkip = request.getAttribute("selectskinenable")
					.toString();
		}

		// 系统禁用换肤
		if ("0".equals(isAbleChangeSkip)) {
			if (!"".equals(systemDefaultSkip)) {
				return systemDefaultSkip;
			} else {
				return Constant.PLAT_INIT_SKIP;
			}
		} else {// 系统允许换肤

			if (!"".equals(changeSkipTheme) && changeSkipTheme != null) {// 有换肤
				return changeSkipTheme;
			} else {
				return cookieTheme;
			}
		}

	}

	/**
	 * 获取客户端宽度
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getClientWidth(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取样式主题

		String width = request.getParameter("cwidth");
		String clientWidth = null;
		if (width != null && !"".equals(width)) {
			return width;
		} else {
			clientWidth = getCookieClientWidth(request, response);
			return clientWidth;
		}

	}

	/**
	 * 获取cookie中的皮肤样式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getCookieTheme(HttpServletRequest request,
			HttpServletResponse response, String sysKey) {

		String themes = null;
		/**
		 * 获取cookie themes的值
		 */
		Cookie[] cookies = request.getCookies();
		// log.info(request.getRequestURI());
		// log.info(request.getRequestURL());
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (("themes_" + sysKey).equals(cookies[i].getName())) {
					themes = cookies[i].getValue(); // 得到cookie的用户名
				}
			}
		}
		/**
		 * 设置默认值
		 */
		if ((themes == null) || ("".equals(themes))) {
			themes = Constant.PLAT_INIT_SKIP;
		}
		return themes;
	}

	/**
	 * 获取客户端宽度
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getCookieClientWidth(HttpServletRequest request,
			HttpServletResponse response) {

		String clientWidth = null;
		/**
		 * 获取cookie themes的值
		 */
		Cookie[] cookies = request.getCookies();
		// log.info(request.getRequestURI());
		// log.info(request.getRequestURL());
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if ("clientWidth".equals(cookies[i].getName())) {
					clientWidth = cookies[i].getValue(); // 得到cookie的用户名
				}
			}
		}
		/**
		 * 设置默认值
		 */
		if ((clientWidth == null) || ("".equals(clientWidth))) {
			clientWidth = Constant.PLAT_INIT_CLIENTWIDTH;
		}
		return clientWidth;
	}

	/**
	 * 修改cookie值
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static void changeTheme(HttpServletRequest request,
			HttpServletResponse response, String theme) {
		String host = request.getServerName();
		Cookie cookie = new Cookie("themes", theme); // 保存用户名到Cookie
		cookie.setPath("/");
		cookie.setDomain("localhost");
		cookie.setMaxAge(30 * 24 * 60 * 60);
		response.addCookie(cookie);
	}
}
