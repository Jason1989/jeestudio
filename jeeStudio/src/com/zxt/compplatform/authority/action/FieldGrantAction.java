package com.zxt.compplatform.authority.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.authority.entity.FieldGrant;
import com.zxt.compplatform.authority.service.FieldGrantService;

public class FieldGrantAction extends ActionSupport{
	/**
	 * 字段授权 操作业务逻辑实体 SET注入 GUOWEIXIN
	 */
	private FieldGrantService fieldGrantService;
	public void setFieldGrantService(FieldGrantService fieldGrantService) {
		this.fieldGrantService = fieldGrantService;
	}
	
	
	
	/**
	 * 执行添加操作
	 *
	 */
	public String addFieldGrant() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String fieldString = req.getParameter("fieldString");
		String tableNameStr=req.getParameter("tableName");
		try {
			//得到角色ID。如果没有，不操作：
			String roleId=req.getParameter("roleId");
			if(roleId!=null && !"".equals(tableNameStr)){
				//得到ID后，先将该角色ID的值删除后，再进行保存.该删除方法 根据角色ID
				fieldGrantService.deleteFieldGrant(new Long(roleId));
				String[] arrayTableName=tableNameStr.split(",");//得到表的名称
				String subStrFieldStr="";//截取字符串了
				
				for(int i=0;i<arrayTableName.length;i++){
					if(i==0)	
						subStrFieldStr=fieldString.substring(0,fieldString.lastIndexOf(arrayTableName[i]));//截取字符串，从最后的截取过来。
					else{
						int first=fieldString.lastIndexOf(arrayTableName[i-1])+arrayTableName[i-1].length();
						int index=fieldString.indexOf(arrayTableName[i])-first;
						int result=fieldString.indexOf(arrayTableName[i])-index;
						subStrFieldStr=fieldString.substring(result,fieldString.lastIndexOf(arrayTableName[i]));//截取字符串，从最后的截取过来。
					}
					 String[] arrayFieldStr= subStrFieldStr.split(arrayTableName[i]);
					//System.out.println(arrayFieldStr.length);//测试数据 输出 该表字段个数
					int flagLength=0;
					for(int j=0;j<arrayFieldStr.length;j++){
						//System.out.println(arrayFieldStr[j]+"~~~"+arrayTableName[i]);//测试数据  输出字段名+表名
						FieldGrant fieldGrant=new FieldGrant();
						Long l=System.currentTimeMillis();
						fieldGrant.setId(l.toString());
						fieldGrant.setRid(new Long(roleId));//角色ID
						fieldGrant.setTablename(arrayTableName[i]);
						fieldGrant.setFieldname(arrayFieldStr[j]);//字段名称
						//执行添加操作
						fieldGrantService.addFieldGrant(fieldGrant);
						//执行更新操作
						try{
							fieldGrantService.update_service(new Long(roleId));
						}catch(Exception e1){
							e1.printStackTrace();
						}	
					}
				}
			}
				//res.getWriter().write("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  字段授权 LIST页面
	 */
	public String list(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		/*
		 * 根据传过来的角色ID。进行查询 
		 */
		String roleId=req.getParameter("roleId");
		req.setAttribute("roleId",roleId);
		if(roleId==null){
			return "list";
		}
		/**
		 * 得到存储的数据ID列表
		 * 得到其角色所属的 字段名和表名 
		 */
		List listRoleId=fieldGrantService.getAllByRid(new Long(roleId));
		if(listRoleId!=null && listRoleId.size()>0){
			Map map1 = new HashMap();
			if (listRoleId == null) {
				listRoleId = new ArrayList();
			}
			map1.put("resultListColumnJson",listRoleId);
			String dataJson1 = JSONObject.fromObject(map1).toString();
			req.setAttribute("formJson",dataJson1);	
		}else{
			req.setAttribute("resultListColumnJson","");	 
		}
		/**
		    * 根据角色ID，得到其所属表名称
		    *  select tableName from T_FIELD_GRANT where rid=2 group by tableName 
		    *  如果flag==0时，则让其查所有表名称。rid不起作用。如果是其它，flag不起作用
		    */	
		List listTableName=fieldGrantService.getTableNameByRid(new Long(roleId),1);
		String tableNameSplit="";
		if(listTableName!=null && listTableName.size()>0){
			for(int i=0;i<listTableName.size();i++){
				FieldGrant fieldGrant=(FieldGrant)listTableName.get(i);
				tableNameSplit+=fieldGrant.getTablename()+",";
			}
			req.setAttribute("tableNameSplit", tableNameSplit);
		}
		return "list";
	}
	/**
	* 根据 角色RID，删除该角色所有操作
	* @param id
	*/
	public String deleteFieldGrant(){
		fieldGrantService.deleteFieldGrant(new Long(1));	
		return "toList";
	}
}
