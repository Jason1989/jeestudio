package ${package}.dao;

import java.util.List;
import java.io.InputStream;
import com.zxt.framework.jdbc.RandomGUID;

<#list imports as import>
	import ${import.name};
</#list>

/**
 * 持久层接口实现
 * @version ${version}
 * @date:  ${currentTime}
 */
public class ${class}DaoImpl  extends ZXTJDBCDaoSupport implements ${class}Dao{
    
	/**
	 * 如果Map中的key是Long类型
	 * ${session.getAttribute("cartMap").get(itemKey).getId}取值
	 */
    <#if list="true">
    public List find${class}s(Object[] params) {
		String sql = "select "  // 字段注释
				<#list sqlnote?keys as itemKey>   
		      	  <#assign item = sqlnote[itemKey]>
				  + " ${itemKey} " //${item}
				</#list>
		          + "${querySql}";
		           
		return this.get${queryDS}Template().findToMaps(sql<#if querySize != "0">,params</#if>);
	}
	
	
	public void deleteAll${object}(String ids){
	    String sql = "${deleteSql}"+" in ("+ids+")";
		this.get${queryDS}Template().delete(sql);
	}
	

	</#if>
	
	<#if edit="true">
	public ${object} find${object}ById(${editParams}){
	    String sql = "${findSql}";
	    Object[] para = new Object[]{${findPara}};
	    return (${object})this.get${queryDS}Template().findToObject(sql,para,${object}.class);
	}
	
	public void insert${object}(${editParams}) {
	    String sql = "${insertSql}";
	    Object[] para = new Object[]{${insertPara}};
		this.get${editDS}Template().create(sql,para);
	}
	

	public void update${object}(${editParams}) {
	    String sql = "${updateSql}";
	    Object[] para = new Object[]{${updatePara}};
		this.get${editDS}Template().update(sql,para);
	}
	</#if>

}
