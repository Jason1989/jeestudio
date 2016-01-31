package com.zxt.framework.dictionary.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.jsqlparser.JSQLParserException;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.zxt.framework.dictionary.entity.DictionaryCache;
import com.zxt.framework.dictionary.service.DictionaryCacheService;

public class DictionaryCacheAction implements ParameterAware,ServletRequestAware,ServletResponseAware {

	private static final long serialVersionUID = 3639139443778255139L;
	
	HttpServletRequest request;
	HttpServletResponse response;
	
	/**
	 * 请求参数
	 */
	private Map params;
	
	/**
	 * ajax请求返回参数封装
	 */
	private Map resState =  new HashMap();
	
	private DictionaryCacheService cacheService;
	/** 
	 * 执行数据字典的缓存相关的操作
	 * 访问地址：/dictionary/dictionaryCache.action
	 * @throws IOException 
	 */
	public String execute() throws IOException{
		
		resState.put("flag", "fail");//默认系统失败
		
		try{
			cacheService.insertCache(params);
			resState.put("flag", "success");
		} catch (NullPointerException e) {
			resState.put("flag", "systemError");//参数异常
		} catch (JSQLParserException e) {
		} catch (SQLException e) {
			resState.put("flag", "sqlError");//执行异常
		}
		
		response.getWriter().write(new JSONObject().fromObject(resState).toString());
		
		return null;
	}
	
	/**
	 * 跳转到数据字典编辑页
	 * 访问地址：/dictionary/dictionaryCache_toDictionarySettingPage.action
	 * @return
	 * @throws JSQLParserException 
	 */
	public String toDictionarySettingPage() throws JSQLParserException{
		
		
		String[] sqlExpression = (String[])params.get("sql");
		String[] dataSourceId = (String[])params.get("dataSourceId");
		
		//获取相应的值，放到值栈中
		List triggers = null;
		try {
			triggers = cacheService.getTriggers(dataSourceId[0], sqlExpression[0]);
		} catch (Exception e) {
			throw new JSQLParserException();
		}
		List cacheRecords = cacheService.getDictionaryCacheRecord(sqlExpression[0]);
		
		//查看是不是有相应的trigger已经配置了，如果没有配置则把相应的triiger配置出来
		String triggerName = "";
		if(triggers.size() <= 0){
			request.setAttribute("triggersScript",cacheService.getTriggerScript(dataSourceId[0], sqlExpression[0]));
		}else{
			request.setAttribute("triggersScript",((Map)triggers.get(0)).get("text"));
		}
		
		
		request.setAttribute("triggers",triggers.size() + "");
		request.setAttribute("cacheRecord",cacheRecords.size() + "");

		request.setAttribute("triggerName",triggers);
		request.setAttribute("cacheRecordKey",cacheRecords);
		
		return "toDictionarySettingPage";
	}
	
	/**
	 * 清除缓存的相关的记录
	 * 访问记录：/dictionary/dictionaryCache_clearCacheAndTriiger.action
	 * @return
	 * @throws IOException 
	 */
	public String clearCacheAndTriiger() throws IOException{
		resState.put("flag", "fail");//默认系统失败
		
		try{
			cacheService.deleteCache(params);
			resState.put("flag", "success");//默认系统失败
		}catch (NullPointerException e) {
			resState.put("flag", "systemError");//参数异常
		} catch (JSQLParserException e) {
			resState.put("flag", "sqlParseError");//解析异常
		}
		
		response.getWriter().write(new JSONObject().fromObject(resState).toString());
		
		return null;
	}
	
	
	/**
	 * 查看页面是不是已经配置了cache和trigger
	 * 访问地址：/dictionary/dictionaryCache_getCacheSettingFlag.action
	 * @return
	 * @throws IOException 
	 */
	public String getCacheSettingFlag() throws IOException{
		//设置返回值
		resState.put("flag", "no");
		
		String[] sqlExpression = (String[])params.get("sql");
		String[] dataSourceId = (String[])params.get("dataSourceId");
		
		//获取相应的值，放到值栈中
		List triggers = null;
		List cacheRecords = null;
		try {
			triggers = cacheService.getTriggers(dataSourceId[0], sqlExpression[0]);
			cacheRecords = cacheService.getDictionaryCacheRecord(sqlExpression[0]);
			
			if(triggers.size() > 0 && cacheRecords.size() > 0){
				resState.put("flag", "yes");
				resState.put("triggerName", ((Map)triggers.get(0)).get("name"));
				resState.put("cacheKey", ((DictionaryCache)cacheRecords.get(0)).getCacheKey());
			}
			
		} catch (JSQLParserException e) {
			resState.put("flag", "error");
		}
		/**
		 * 发送json数据
		 */
		response.getWriter().write(new JSONObject().fromObject(resState).toString());
		return null;
	}
	
	
	public DictionaryCacheService getCacheService() {
		return cacheService;
	}
	public void setCacheService(DictionaryCacheService cacheService) {
		this.cacheService = cacheService;
	}
	
	public void setParameters(Map params) {
		this.params = params;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}
