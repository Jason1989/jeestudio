package ${package}.dao;

import java.util.List;
<#list imports as import>
import ${import.name};
</#list>

/**
 * 持久层接口
 * @version ${version}
 * @date:  ${currentTime}
 */
public interface ${class}Dao {


    <#if list="true">
	public List find${class}s(Object[] params);
	
	public void deleteAll${object}(String ids);

     </#if>
     
    <#if edit="true">
    public ${object} find${object}ById(${editParams});
    
	public void insert${object}(${editParams});
	
	public void update${object}(${editParams});
	</#if>
}
