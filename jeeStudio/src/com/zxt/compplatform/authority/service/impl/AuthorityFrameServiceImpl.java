package com.zxt.compplatform.authority.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.util.AssertionHolder;

import com.zxt.compplatform.authority.dao.AuthorityFrameDao;
import com.zxt.compplatform.authority.service.AuthorityFrameService;
import com.zxt.compplatform.authority.service.RARelationShipService;
import com.zxt.compplatform.formengine.entity.view.SystemMenu;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.ThemesManager;

/**
 * 权限业务操作接口实现
 * 
 * @author 007
 */
public class AuthorityFrameServiceImpl implements AuthorityFrameService {

	/**
	 * 权限dao操作注入
	 */
	private AuthorityFrameDao authorityFrameDao;

	/**
	 * roles Merge
	 */
	public String initAuthorityFrameByAccount(String account) {
		// TODO Auto-generated method stub
		String roles = "";
		String temRoles = "";

		List list = authorityFrameDao.initAuthorityFrameByAccount(account);
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				if((String)list.get(i) instanceof String){
					temRoles=(String)list.get(i);
				}else{
				temRoles = ((Map) list.get(i)).get("rname").toString();
				}
				if (i == (list.size() - 1)) {
					roles += temRoles;
				} else {
					roles += temRoles + ",";
				}
			}
		} else {
			roles = "-1";
		}

		return roles;
	}
	//多余的代码。GUOWEIXIN
	/**
	 * rid Merge 黄姣 根据角色名称查询角色ID   
	 */
	public String initAuthorityRidFrameByAccount(String rname) {
		// TODO Auto-generated method stub
		String roles = "";
		String temRoles = "";

		List list = authorityFrameDao.initRolesFrameByAccount(rname);
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				try{
					temRoles = ((Map) list.get(i)).get("rid").toString();				
				}catch(Exception e){
					temRoles=(String)list.get(i);
				}
				if (i == (list.size() - 1)) {
					roles += temRoles;
				} else {
					roles += temRoles + ",";
				}
			}
		} else {
			roles = "-1";
		}

		return roles;
	}

	public AuthorityFrameDao getAuthorityFrameDao() {
		return authorityFrameDao;
	}

	public void setAuthorityFrameDao(AuthorityFrameDao authorityFrameDao) {
		this.authorityFrameDao = authorityFrameDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.authority.service.AuthorityFrameService#remeberSystem(javax.servlet.http.HttpServletRequest)
	 */
	public String remeberSystem(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		String idMark = request.getParameter("sysName");
		Object bak_url = session.getAttribute("bak_url");

		// bakup the first access url
		if (idMark == null || "".equals(idMark)) {
			if (bak_url != null || (!"".equals(bak_url))) {
				idMark = bak_url.toString();
			}
		} else {
			session.setAttribute("bak_url", idMark);
		}

		return idMark;
	}

	/**
	 * 权限检查的map
	 */
	public String portfolioAuthorityMap(List list, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map authority = new HashMap();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				authority.put(list.get(i).toString(), "AUTHORITY");
			}
		}
		request.getSession().setAttribute("authority", authority);

		return StringUtils.join(list, ',');
	}

	/**
	 * 初始化所有资源 系统资源 用户资源 权限资源。
	 */
	public List initResource(HttpServletRequest request,
			HttpServletResponse response,
			RARelationShipService relationShipService,
			SystemFrameService systemFrameService) {
		// TODO Auto-generated method stub

		String userName = AssertionHolder.getAssertion().getPrincipal()
				.getName();
		String userId = systemFrameService.findUserIdByUserName(userName);

		String roles = request.getParameter("setRole");
		if (roles == null || "".equals(roles)) {
			roles = initAuthorityFrameByAccount(userName);
		}

		String systemName = remeberSystem(request);
		String systemID = systemFrameService.treeId(systemName);

		List menuIds = relationShipService.getMenusByRoleNames(roles);
		String authorutyIDS = portfolioAuthorityMap(menuIds, request);

		request.getSession().setAttribute("userName", userName);
		request.getSession().setAttribute("userId", userId);

		List resourceList = new ArrayList();
		List systemMenuList = null;
		List tabMenuList = null;
		String theme = ThemesManager.getTheme(request, response, systemID);
		if (!StringUtils.equals(StringUtils.trim(authorutyIDS), ""))
			systemMenuList = systemFrameService.findSystemMenu(systemID,
					authorutyIDS);

		if (systemMenuList != null) {
			if (systemMenuList.size() != 0) {
				tabMenuList = ((SystemMenu) systemMenuList.get(0))
						.getTabMenuList();
			}
		}

		resourceList.add(systemMenuList);
		resourceList.add(tabMenuList);
		resourceList.add(theme);
		return resourceList;
	}
}