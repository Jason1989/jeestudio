package com.zxt.compplatform.validationrule.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.validationrule.entity.ValidationRule;
import com.zxt.compplatform.validationrule.service.IValidationRuleService;
import com.zxt.framework.common.util.RandomGUID;

public class ValidationRuleAction {
	private IValidationRuleService validationRuleService;
	private ValidationRule validationRule;
	/**
	 * 列表入口
	 * @return
	 */
	public String toList(){
		return "list";		
	}
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		int totalRows = validationRuleService.findTotalRows();
		List validationRuleList = validationRuleService.findAllByPage(page,rows);
		Map map = new HashMap();
		if(validationRuleList != null){
			map.put("rows", validationRuleList);
			map.put("total", new Integer(totalRows));
		}
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	public String toAdd(){
		return "toadd";
	}
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		HttpServletResponse res = ServletActionContext.getResponse();
		if(validationRule.getId() == null || validationRule.getId().equals("")){
			validationRule.setId(RandomGUID.geneGuid());
		}
		try {
			if(validationRuleService.isExist(validationRule.getId(),validationRule.getName())){				
				res.getWriter().write("exist");
			}else{
				validationRuleService.insert(validationRule);
				res.getWriter().write("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String toUpdate(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String validationRuleId = req.getParameter("validationRuleId");
		if(validationRuleId!= null && !validationRuleId.equals("")){
			validationRule = validationRuleService.findById(validationRuleId);
		}
		return "toupdate";
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		HttpServletResponse res = ServletActionContext.getResponse();
		try {
			if(validationRuleService.isExistUpdate(validationRule.getId(),validationRule.getName())){				
				res.getWriter().write("exist");
			}else{
				validationRuleService.update(validationRule);
				res.getWriter().write("success"); 
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String validationRuleId = req.getParameter("validationRuleId");
		String validationRuleIds = req.getParameter("validationRuleIds");
		try {
			if(validationRuleId!= null && !validationRuleId.equals("")){
				validationRuleService.deleteById(validationRuleId);
				res.getWriter().write("success");
			}
			if(validationRuleIds!= null && !validationRuleIds.equals("")){
				validationRuleService.deleteAll(validationRuleService.findAllByIds(validationRuleIds));
				res.getWriter().write("success");
			}
		} catch (IOException e) {				
			e.printStackTrace();
		}
		return null;
	}
	public void setValidationRuleService(IValidationRuleService validationRuleService) {
		this.validationRuleService = validationRuleService;
	}
	public ValidationRule getValidationRule() {
		return validationRule;
	}
	public void setValidationRule(ValidationRule validationRule) {
		this.validationRule = validationRule;
	}
}
