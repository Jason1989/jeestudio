package com.zxt.compplatform.authority.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zxt.compplatform.formengine.service.SystemFrameService;

/**
 * 权限业务操作接口
 * @author 007
 */
public interface AuthorityFrameService {
	/**
	 * 初始化指定数目的权限
	 * @param account
	 * @return
	 */
	public String initAuthorityFrameByAccount(String account);
	/**
	 * rid Merge 黄姣
	 */
	public String initAuthorityRidFrameByAccount(String account) ;
	/**
	 * 记住请求的子系统
	 * @return
	 */
	public  String remeberSystem(HttpServletRequest request);

	/**
	 * save  authorityMap
	 */
	public  String portfolioAuthorityMap(List list,HttpServletRequest request);
	/**
	 * 初始化所有资源 系统资源 用户资源 权限资源。
	 */
	public List initResource(HttpServletRequest request,HttpServletResponse response ,RARelationShipService relationShipService,SystemFrameService systemFrameService);
}
