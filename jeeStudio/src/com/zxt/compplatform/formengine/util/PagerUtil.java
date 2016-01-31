package com.zxt.compplatform.formengine.util;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.entity.view.PagerEntiy;

public class PagerUtil {
	/**
	 * 分页
	 * 
	 * @param request
	 * @param data
	 * @return
	 */
	public static PagerEntiy pagger(HttpServletRequest request) {
	
		PagerEntiy pagerEntiy=new PagerEntiy();//分页对象
		try {
			/**
			 * jquery方式
			 */
			int pageNum = Integer.parseInt(request.getParameter("page"));
			int size = Integer.parseInt(request.getParameter("rows"));
			int end = pageNum * size;
			int start = end - size;
			/**
			 * ExtJs方式
			 */
			// int size = Integer.parseInt(request.getParameter("limit"));
			// int start = Integer.parseInt(request.getParameter("start"));
			// int end = start + size;
			
			pagerEntiy.setSize(size);
			pagerEntiy.setEnd(end);
			pagerEntiy.setStart(start);
			pagerEntiy.setPageNum(pageNum);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return pagerEntiy;
	}
}
