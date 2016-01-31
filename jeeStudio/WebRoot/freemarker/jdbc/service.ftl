package ${package}.service;

import java.util.List;
<#list imports as import>
import ${import.name};
</#list>
/**
 * 业务接口
 * @version ${version}
 * @date:  ${currentTime}
 */
public interface ${class}Service {

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
