package com.zxt.compplatform.authority.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.authority.service.AuthorityFrameService;
import com.zxt.compplatform.authority.service.RARelationShipService;
import com.zxt.compplatform.formengine.action.SystemFrameAction;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.skip.entity.UserDeskSetVo;

/**
 * 单点登操作
 * @author 007
 */
public class SsoLoginAction extends ActionSupport {
	/**
	 * 系统操作业务逻辑实体
	 */
	private SystemFrameService systemFrameService;
	/**
	 * 关系操作业务逻辑实体
	 */
	private RARelationShipService relationShipService;
	/**
	 * 权限操作业务逻辑实体
	 */
	private AuthorityFrameService authorityFrameService;
	
	private static final Logger log = Logger.getLogger(SystemFrameAction.class);

	/**
	 * 系统菜单
	 */
	private List systemMenuList = null;
	/**
	 * 选项卡菜单 and 选项卡下 树菜单json
	 */
	private List tabMenuList;
	/**
	 * 桌面版用户实体Vo
	 */
	private UserDeskSetVo userDeskSetVo;
	/**
	 * 存储菜单json数据
	 */
	private String initMenuJson;
	/**
	 * 主题
	 */
	private String themes;

	/**
	 * 跳转到frame页面
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		// TODO Auto-generated method stub

		List resourceList = authorityFrameService.initResource(findReq(),findResp(),
				relationShipService, systemFrameService);

		systemMenuList = (List) resourceList.get(0);
		tabMenuList = (List) resourceList.get(1);
		themes = (String) resourceList.get(2);;

		return "frame";
	}

	/**
	 * 获取request对象
	 * @return
	 */
	public HttpServletRequest findReq() {

		return ServletActionContext.getRequest();
	}

	/**
	 * 获取response对象
	 * @return
	 */
	public HttpServletResponse findResp() {

		return ServletActionContext.getResponse();
	}

	/**
	 * 获取session对象
	 * @return
	 */
	public HttpSession findSession() {

		return ServletActionContext.getRequest().getSession();
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

	public RARelationShipService getRelationShipService() {
		return relationShipService;
	}

	public void setRelationShipService(RARelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	public List getSystemMenuList() {
		return systemMenuList;
	}

	public void setSystemMenuList(List systemMenuList) {
		this.systemMenuList = systemMenuList;
	}

	public List getTabMenuList() {
		return tabMenuList;
	}

	public void setTabMenuList(List tabMenuList) {
		this.tabMenuList = tabMenuList;
	}

	public UserDeskSetVo getUserDeskSetVo() {
		return userDeskSetVo;
	}

	public void setUserDeskSetVo(UserDeskSetVo userDeskSetVo) {
		this.userDeskSetVo = userDeskSetVo;
	}

	public String getInitMenuJson() {
		return initMenuJson;
	}

	public void setInitMenuJson(String initMenuJson) {
		this.initMenuJson = initMenuJson;
	}

	public String getThemes() {
		return themes;
	}

	public void setThemes(String themes) {
		this.themes = themes;
	}

	public AuthorityFrameService getAuthorityFrameService() {
		return authorityFrameService;
	}

	public void setAuthorityFrameService(AuthorityFrameService authorityFrameService) {
		this.authorityFrameService = authorityFrameService;
	}

}
