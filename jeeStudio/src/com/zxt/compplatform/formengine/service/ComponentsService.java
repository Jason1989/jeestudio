package com.zxt.compplatform.formengine.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;

/**
 * 组件操作业务操作接口
 * 
 * @author 007
 */
public interface ComponentsService {
	/**
	 * 删除数据
	 * 
	 * @param keyTable
	 * @param IDkey
	 * @param dataID
	 * @return
	 */
	public int deleteData(ListPage listPage, HttpServletRequest request);

	/**
	 * 存储
	 * 
	 * @param request
	 * @return
	 */
	public int dynamicSave(HttpServletRequest request, EditPage editPage,
			WorkFlowDataStauts workFlowDataStauts);

	/**
	 * 加载编辑页数据
	 * 
	 * @param editPage
	 * @param parmerNameArray
	 *            参数数组
	 * @param parmerValueMap
	 *            参数值
	 * @return
	 */
	public EditPage loadEditPage(EditPage editPage, String[] parmerNameArray);

	/**
	 * 加载详情页
	 * 
	 * @param viewPage
	 * @param request
	 * @return
	 */
	public ViewPage loadViewPage(ViewPage viewPage, HttpServletRequest request);

	/**
	 * 传入数据字典ID 返回key value 字符串
	 * 
	 * @param dictionaryID
	 * @return
	 */
	public Map load_Dictionary(String dictionaryID);
	/**
	 * 传入数据字典ID 返回key value 字符串
	 * @author GUOWEIXIN 
	 * @param dictionaryID
	 * @return
	 */
	public Map load_Dictionary(String dictionaryID,HttpServletRequest request);
	/**
	 * 更新数据字典
	 * 
	 * @param dictionaryID
	 * @return
	 */
	public Map update_Dictionary(String dictionaryID);
	/**
	 * 更新数据字典
	 * @author GUOWEIXIN
	 * @param dictionaryID
	 * @return
	 */
	public Map update_Dictionary(String dictionaryID,HttpServletRequest request);

	/**
	 * 加载treejson
	 */
	public String[] loadTreeData(String dictionaryID, String defalutValue);

	/**
	 * 加载treejson
	 */
	public String[] loadTreeData(String dictionaryID, String defalutValue,
			String parentId);

	/**
	 * 加载treejson
	 */
	public String[] loadTreeOrgData(String dictionaryID, String defalutValue,
			String oid);

	/**
	 * 加载treejson
	 */
	public String[] loadTreeHumanData(String dictionaryID, String defalutValue,
			String oid);

	/**
	 * 查询数据库 配置信息ID
	 */
	public String[] load_XMLConfig();

	/**
	 * 查询验证名称
	 */
	public String load_validate(String id);

	/**
	 * 批量删除
	 * 
	 * @param listPage
	 * @param request
	 * @return
	 */
	public String bulkDelete(ListPage listPage, HttpServletRequest request);

	/**
	 * 菜单过滤数据
	 */
	public List findMenuFilter(String menuId, List initList);

	/**
	 * 导出
	 * 
	 * @param formId
	 * @param listPage
	 * @param request
	 * @param selectColumns
	 * @return
	 */
	public InputStream exportForListPage(String formId, ListPage listPage,
			HttpServletRequest request, String[] selectColumns);

	/**
	 * 指定数据源查询
	 * 
	 * @param conditions
	 */
	public List queryByDataSource(String dataSourceId, String querySql,
			Object[] conditions);

	/**
	 * 获取人员树控件列表数据
	 * 
	 * @param orgid
	 * @return
	 */
	public List queryForHumanList(String orgid, String dataSourceId,
			String state);
}
