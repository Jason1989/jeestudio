package com.zxt.compplatform.formengine.service;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.output.XMLOutputter;

import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.compplatform.formengine.dao.IQueryXmlData;
import com.zxt.compplatform.formengine.engine.ConditionFactory;
import com.zxt.compplatform.formengine.engine.IPageTransFer;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formula.service.FurmulaService;
import com.zxt.compplatform.validationrule.service.IValidationRuleService;
import com.zxt.framework.common.util.XMLParse;
import com.zxt.framework.dictionary.service.IDataDictionaryService;

/**
 * 页面业务操作接口
 * 
 * @author 007
 */
public class PageService {

	private static final Logger log = Logger.getLogger(PageService.class);
	/**
	 * doc解析接口
	 */
	private ResolveObjectDefService resolveObjectDefService;
	/**
	 * 页面解析成展示界面
	 */
	private IPageTransFer IPageTransFer;
	// private dataBaseService dbservice;
	/**
	 * xml查询
	 */
	private IQueryXmlData QueryXmlData;
	/**
	 * 数据列操作接口
	 */
	private IDataColumnService dataColumnService;
	/**
	 * xml查询操作接口
	 */
	private IQueryXmlDataService queryXmlDataService;
	/**
	 * 数据对象操作接口
	 */
	private IDataTableService dataTableService;
	/**
	 * 数据源操作接口
	 */
	private IDataSourceService dataSourceService;
	/**
	 * 公式操作接口
	 */
	private FurmulaService furmulaService;
	/**
	 * 验证规则操作接口
	 */
	private IValidationRuleService validationRuleService;
	/**
	 * 数据字典操作接口
	 */
	private IDataDictionaryService dataDictionaryService;
	/**
	 * 列表页
	 */
	private ListPage listPage;
	/**
	 * xml对应的dom节点
	 */
	private org.jdom.Document doc;
	/**
	 * url
	 */
	private String url;
	/**
	 * 数据库名称
	 */
	private String dbname;
	/**
	 * 数据集合
	 */
	private List dataSets;
	/**
	 * 布局
	 */
	private List layouts;
	/**
	 * 按钮
	 */
	private List buttons;
	/**
	 * 数据列
	 */
	private List columns;
	/**
	 * 数据列
	 */
	private List listColumns;
	/**
	 * 是否可读写
	 */
	private List editMode;
	/**
	 * 查询区域
	 */
	private List queryZone;

	public PageService() {

	}

	public PageService(IPageTransFer IPageTransFer)
			throws CodeGenerateException {
		// this.IPageTransFer = IPageTransFer;
		// this.dbservice = dbservice;
	}

	/**
	 * 查询表单配置并解析成对应的界面
	 * 
	 * @param url
	 * @return
	 */
	public Map load_service(String url) {
		try {
			readXML(url);
			parse();
			Map map = transToPage();
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 更新xml
	 * 
	 * @param url
	 * @return
	 */
	public Map update_service(String url) {
		try {
			readXML(url);
			parse();
			Map map = transToPage();
			return map;
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * 读取表单配置xml
	 * 
	 * @param url
	 * @return
	 */
	private boolean readXML(String url) {
		// 实例化一个数据库操作的servie
		// read xml from database
		boolean r = true;
		try {
			String xml = queryXmlDataService.findBolbById(url);
			if(xml!=null){
				doc = XMLParse.jdomXmlToDoc(xml);
				/**
				 * 输出xml 格式 调试的时候可以打开，但不要这个提交了。影响程序效率。
				 */
				///**
				XMLOutputter outputter = new XMLOutputter();
				 // outputter.setFormat(Format.getPrettyFormat());// 格式化 xml
				 try {
				 outputter.output(doc.getRootElement(), new PrintWriter(
				 System.out));// 输出到控制台
				 } catch (Exception e) {
				 e.printStackTrace();
				 }//**/
			}
			
		} catch (Exception e) {
			r = false;
		}
		return r;
	}

	/**
	 * 获取解析xml;
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean parse() throws Exception {
		// 获取解析xml的Service;
		// 从xml取得不同表单类型，实例化不同IPageTransFer实例
		boolean b = true;
		try {
			// this.IPageTransFer = IPageTransFer;
			listPage = null;
			listPage = (ListPage) resolveObjectDefService.resolveObject(doc);
			IPageTransFer = ConditionFactory.creator(listPage.getFlag());

		} catch (Exception e) {
			// e.printStackTrace();
			b = false;
		}
		return b;
	}

	/**
	 * transfer to Page html
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map transToPage() throws Exception {
		return this.IPageTransFer.transToPage(listPage, doc, dataColumnService,
				dataTableService, dataSourceService, furmulaService,
				validationRuleService, dataDictionaryService);
	}

	public IPageTransFer getIPageTransFer() {
		return IPageTransFer;
	}

	public List getDataSets() {
		return dataSets;
	}

	public List getLayouts() {
		return layouts;
	}

	public List getButtons() {
		return buttons;
	}

	public List getColumns() {
		return columns;
	}

	public void setIPageTransFer(IPageTransFer pageTransFer) {
		IPageTransFer = pageTransFer;
	}

	public void setDataSets(List dataSets) {
		this.dataSets = dataSets;
	}

	public void setButtons(List buttons) {
		this.buttons = buttons;
	}

	public void setColumns(List columns) {
		this.columns = columns;
	}

	public IQueryXmlData getQueryXmlData() {
		return QueryXmlData;
	}

	public void setQueryXmlData(IQueryXmlData queryXmlData) {
		QueryXmlData = queryXmlData;
	}

	public org.jdom.Document getDoc() {
		return doc;
	}

	public void setDoc(org.jdom.Document doc) {
		this.doc = doc;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public List getListColumns() {
		return listColumns;
	}

	public void setListColumns(List listColumns) {
		this.listColumns = listColumns;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ResolveObjectDefService getResolveObjectDefService() {
		return resolveObjectDefService;
	}

	public void setResolveObjectDefService(
			ResolveObjectDefService resolveObjectDefService) {
		this.resolveObjectDefService = resolveObjectDefService;
	}

	public List getEditMode() {
		return editMode;
	}

	public void setEditMode(List editMode) {
		this.editMode = editMode;
	}

	public List getQueryZone() {
		return queryZone;
	}

	public void setQueryZone(List queryZone) {
		this.queryZone = queryZone;
	}

	public IQueryXmlDataService getQueryXmlDataService() {
		return queryXmlDataService;
	}

	public void setQueryXmlDataService(IQueryXmlDataService queryXmlDataService) {
		this.queryXmlDataService = queryXmlDataService;
	}

	public static void main(String[] args) {
		System.out.println(750 * 0.75);
	}
}
