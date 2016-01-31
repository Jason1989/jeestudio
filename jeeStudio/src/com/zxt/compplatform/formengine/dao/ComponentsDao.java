package com.zxt.compplatform.formengine.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.PagerEntiy;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.ViewPage;

/**
 * 页面组件操作接口
 * 
 * @author 007
 */
public interface ComponentsDao {
	/**
	 * 删除数据
	 * 
	 * @param keyTable
	 * @param IDkey
	 * @param dataID
	 * @return
	 */
	public int deleteData(String sql, String[] parmers, ListPage listPage);

	/**
	 * 动态存储
	 * 
	 * @param sql
	 * @param field
	 *            存储提交数据的顺序
	 * @param valueMap
	 * @return
	 */
	public int dynamicSave(String sql, String[] endParmer, EditPage editPage,
			Map<String, EditColumn> editColumnMap, List<Param> list);

	/**
	 * 加载编辑页面
	 * 
	 * @param editPage
	 * @param parmers
	 * @param field
	 *            存放查询字段的顺序
	 * @return
	 */
	public EditPage loadEditPage(EditPage editPage, String[] parmers);

	/**
	 * 生成主鍵
	 */
	public String createIDkey(String tableName, String IDkey);

	/**
	 * 加载详情页
	 * 
	 * @param sql
	 * @param parmer
	 * @return
	 */
	public ViewPage loadViewPage(String sql, String[] parmer, ViewPage viewPage);

	/**
	 * 动态数据字典 取值
	 * 
	 * @param dictionaryID
	 * @return
	 */
	public Map loadDynamicDictionary(String sql, String dataSourceID);

	/**加载数据字典
	 * @GUOWEIXIN
	 *  数据字典中 设置动态SQL语句的解析和查询功能。
	 *  例如：[]在request范围中取。{}在session范围中取。
	 *  select * from TableName where first='[first]' and second='{second}'
	 */
	public Map loadDynamicDictionary(String sql, String dataSourceID,HttpServletRequest request);
	/**
	 * by formID 查询xml
	 * 
	 * @param sql
	 * @param formID
	 * @return
	 */
	public String findBlobXMLById(String sql, String formID);

	/**
	 * 加载列表页业务数据
	 * 
	 * @param sql
	 * @return
	 */
	public PagerEntiy queryFormData(String sql, String[] parmerValue,
			ListPage listPage, HttpServletRequest request);

	/**
	 * 查询数据库 配置信息ID
	 */
	public String[] load_XMLConfig();

	/**
	 * 查询验证名称
	 */
	public String serchValidate(String id);

	/**
	 * 根据formID 查询数据源Id
	 */
	public String findDataSourceId(String formId);

	/**
	 * 根据表单Id 查找 在缓存中对应的数据源连接池
	 */
	public ComboPooledDataSource findPoolByFormId(String formId);
	/**
	 * 根据所传数据源ID得到其数据源连接池
	 * param: dataSourceId(数据源ID) String
	 */
	public ComboPooledDataSource findPoolByDataSourceId(String dataSourceId);

	/**
	 * 数据列加载数据字典
	 */
	public Map findDictionary(String dictionaryID);

	/**
	 * 导出数据
	 * 
	 * @param sql
	 * @param sql2
	 * @param conditions
	 * @param listPage
	 * @param request
	 * @return
	 */
	public List queryForExport(String formId, String sql2, String[] conditions,
			ListPage listPage, HttpServletRequest request);

	/**
	 * 通过数据源ID查找dataSource 方法描述
	 * <p>
	 * 传入参数： username(*):(*表示必须参数)用户名 password(*):用户名密码
	 * 
	 * 传出参数（名称/类型）： 1. fieldname/String
	 * {"name":"hsl","id":""}或者"exist"[存在]、"unexist"[不存在](中括号解释返回字符串的意义)
	 * 
	 * action访问地址： (相对于当前项目的地址) system/login_login.action
	 * 
	 * 修改记录： 1. 2011-09-12 代号 修改说明
	 * </p>
	 */
	public DataSource queryForDataSource(String dataSourceId);

	/**
	 * 查询日志中的已办项
	 * 
	 * @param userId
	 * @return
	 */
	public List findAppidsInLog(String userId);

	/**
	 * 保存后SQL  执行 
	 * @param sqldic_dataSourceid 数据源ID
	 * @param sqldic_expression   SQL表达式
	 * @param strings             字段条件
	 * @param request             便于表达式取值
	 * @return
	 */
	public List queryForAfterSql(String sqldic_dataSourceid,String sqldic_expression, String[] strings,HttpServletRequest request);
}
