package com.zxt.framework.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;

public class ActionHelpper {
	
	public static final int PARAM_PAGE = NumberUtils.INTEGER_ONE.intValue();
	public static final int PARAM_SIZE = 10;
	
	public static final String PARAM_ID = "id";
	
	public static final String RETURN_SUCCESS = "success";
	public static final String RETURN_FAILED = "failed";
	public static final String RETURN_EXIST = "exists";

	public static int getPage(HttpServletRequest req){
		int page = PARAM_PAGE;
		if( NumberUtils.isNumber(req.getParameter("page")) ){
			page = Integer.parseInt(req.getParameter("page"));
		}
		return page;
	}
	
	public static int getSize(HttpServletRequest req){
		int size = PARAM_SIZE;
		if( NumberUtils.isNumber(req.getParameter("rows")) ){
			size = Integer.parseInt(req.getParameter("rows"));
		}
		return size;
	}
}
