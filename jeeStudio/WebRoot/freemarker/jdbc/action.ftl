package ${package}.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ${package}.service.${class}Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
<#list imports as import>
import ${import.name};
</#list>

/**
 * 控制器
 * @version ${version}
 * @date:  ${currentTime}
 */
public class ${class}Action extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private ${class}Service ${classProp}Service;

	public void set${class}Service(${class}Service ${classProp}Service) {
		this.${classProp}Service = ${classProp}Service;
	}

	public String execute() {
		return SUCCESS;
	}
	
    <#if list="true">
	public String list() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			Map map = new HashMap();
			Object[] params = new Object[${querySize}];
			int i = 0;
			<#list queryParams as query>
			if(null != request.getParameter("${query.key}")){
				params[i] = ${query.type}.valueOf(request.getParameter("${query.key}"));
			}else{
			    params[i] = "";  
			}
			i++;
			</#list>
			List data = ${classProp}Service.find${class}s(params);
			map.put("rows", data);
			map.put("total", new Integer(5));
			String json = JSONObject.fromObject(map).toString();
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String doDelete() {
	    HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			String ids = request.getParameter("ids");
			${classProp}Service.deleteAll${object}(ids);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(1);
		}catch(Exception e){
		   e.printStackTrace();
		}
		return null;
	}
	</#if>
	
	<#if edit="true">
	
	public String goEdit() {
	    HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		${object} ${objectParam} = new ${object}();
		<#list findFields as field>
		${field.type}  ${field.name} = <#if field.type!="String">${field.parse} (</#if>request.getParameter("${field.name}")<#if field.type!="String">)</#if>;
		${objectParam}.${field.setName}(${field.name});
		</#list>
		if("update".equalsIgnoreCase(type)){
		   request.setAttribute("field",${classProp}Service.find${object}ById(${objectParam}));
		}
		request.setAttribute("typeMethod", type);
		return "goEdit";
	}
	
	public String goView() {
	    HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		${object} ${objectParam} = new ${object}();
		<#list findFields as field>
		${field.type}  ${field.name} = <#if field.type!="String">${field.parse} (</#if>request.getParameter("${field.name}")<#if field.type!="String">)</#if>;
		${objectParam}.${field.setName}(${field.name});
		</#list>
		if("update".equalsIgnoreCase(type)){
		   request.setAttribute("field",${classProp}Service.find${object}ById(${objectParam}));
		}
		return "goView";
	}
	
	public String doSave() {
	    HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
		    String type = request.getParameter("typeMethod");
			${object} ${objectParam} = new ${object}();
			<#list fields as field>
			${field.type}  ${field.name} = <#if field.type!="String">${field.parse} (</#if>request.getParameter("${field.name}")<#if field.type!="String">)</#if>;
			${objectParam}.${field.setName}(${field.name});
			</#list>
			if("add".equals(type)){
			  ${classProp}Service.insert${object}(${objectParam});
			}else{
			  ${classProp}Service.update${object}(${objectParam});
			}
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(1);
		}catch(Exception e){
		   e.printStackTrace();
		}
		return null;
	}
	</#if>
	

}
