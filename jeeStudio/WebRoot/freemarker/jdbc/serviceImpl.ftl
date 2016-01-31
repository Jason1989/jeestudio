package ${package}.service;

import java.util.List;
<#list imports as import>
import ${import.name};
</#list>
import ${package}.dao.${class}Dao;

/**
 * 业务接口实现
 * @version ${version}
 * @date:  ${currentTime}
 */
public class ${class}ServiceImpl implements ${class}Service{
	
	private ${class}Dao ${classProp}Dao;

    
	public void set${class}Dao(${class}Dao ${classProp}Dao) {
		this.${classProp}Dao = ${classProp}Dao;
	}

    
   <#if list="true">
	public List find${class}s(Object[] params) {
		return ${classProp}Dao.find${class}s(params);
	}
	
	
	public void deleteAll${object}(String ids) {
	    ${classProp}Dao.deleteAll${object}(ids);
	}
	</#if>
	
	<#if edit="true">
	public ${object} find${object}ById(${editParams}){
	    return ${classProp}Dao.find${object}ById(${editPara});
	}
	
	public void insert${object}(${editParams}) {
		${classProp}Dao.insert${object}(${editPara});
	}

	public void update${object}(${editParams}) {
	    ${classProp}Dao.update${object}(${editPara});
	}
	</#if>



}
