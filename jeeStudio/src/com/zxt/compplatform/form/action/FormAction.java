package com.zxt.compplatform.form.action;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springmodules.cache.regex.Regex;

import com.googlecode.jsonplugin.JSONUtil;
import com.zxt.compplatform.datacolumn.entity.DataColumn;
import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.datatable.entity.DataObjectMenu;
import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.compplatform.form.entity.Form;
import com.zxt.compplatform.form.service.IFormService;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.ListColumn;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;
import com.zxt.compplatform.formengine.service.IQueryXmlDataService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.service.ResolveObjectDefService;
import com.zxt.compplatform.formengine.util.StringFormat;
import com.zxt.compplatform.formengine.util.TreeUtil;
import com.zxt.compplatform.organization.service.OrganizationService;
import com.zxt.compplatform.validationrule.entity.ValidationRule;
import com.zxt.compplatform.validationrule.service.IValidationRuleService;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.common.util.SQLBlobUtil;
import com.zxt.framework.common.util.XMLParse;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.DictionaryGroup;
import com.zxt.framework.dictionary.service.IDataDictionaryService;
public class FormAction {
	private static final Logger log = Logger.getLogger(FormAction.class);
	private IFormService formService;
	private IDataTableService dataTableService;
	private IDataColumnService dataColumnService;
	private IValidationRuleService validationRuleService;
	private IDataDictionaryService dataDictionaryService;
	private OrganizationService organizationService;
	private PageService pageService;
	private Form form;
	private String dataObjectGroupId;

	private IQueryXmlDataService queryXmlDataService;
	private ResolveObjectDefService resolveObjectDefService;
	/**
	 * 列表入口
	 * @return
	 */
	public String toList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataObjectId = req.getParameter("dataObjectId");
		req.setAttribute("dataObjectId", dataObjectId);
		List validationRuleList = validationRuleService.findAll();
	 // List dataDictionaryList = dataDictionaryService.findAll();
		List validationRuleTemp = new ArrayList();
		Map defaultMap = new HashMap();
		defaultMap.put("id", "-1");
		defaultMap.put("text", "无");
		validationRuleTemp.add(defaultMap);
		if(validationRuleList != null)
			for(int i=0;i<validationRuleList.size();i++){
				Map map = new HashMap();
				ValidationRule validationRule = (ValidationRule)validationRuleList.get(i);
				map.put("id", validationRule.getId());
				map.put("text", validationRule.getName());				
				validationRuleTemp.add(map);
			}

		String validationRuleJson = JSONArray.fromObject(validationRuleTemp).toString();
		req.getSession().setAttribute("validationRuleJson", validationRuleJson);
		dictionaryTreeJson(req);
		orgTreeJson(req);
		
		//判断是否用于codeFlag 代码生成的页面操作。
		String codeFlag=req.getParameter("codeFlag");
		if("codeFlag".equals(codeFlag)){
			return "listCode";
		}
		return "list";		
	}
	public String refreshSession(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		List validationRuleList = validationRuleService.findAll();
		List validationRuleTemp = new ArrayList();
		Map defaultMap = new HashMap();
		defaultMap.put("id", "-1");
		defaultMap.put("text", "无");
		validationRuleTemp.add(defaultMap);
		if(validationRuleList != null)
			for(int i=0;i<validationRuleList.size();i++){
				Map map = new HashMap();
				ValidationRule validationRule = (ValidationRule)validationRuleList.get(i);
				map.put("id", validationRule.getId());
				map.put("text", validationRule.getName());				
				validationRuleTemp.add(map);
			}

		String validationRuleJson = JSONArray.fromObject(validationRuleTemp).toString();
		req.getSession().setAttribute("validationRuleJson", validationRuleJson);
		try {
			res.getWriter().write(validationRuleJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 数据字典信息转成tree json
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String dictionaryTreeJson(HttpServletRequest req){
		//HttpServletRequest req = ServletActionContext.getRequest();
		List dictGroupList = dataDictionaryService.findAllDictGroup();
		List resultList = new ArrayList();
		Map defaultMap = new HashMap();
		defaultMap.put("id", "-1");
		defaultMap.put("text", "无");
		defaultMap.put("children", new ArrayList());
		resultList.add(defaultMap);
		if(dictGroupList != null)
			for(int i=0;i<dictGroupList.size();i++){
				Map dictGroupMap = new HashMap();
				DictionaryGroup dictGroup = (DictionaryGroup) dictGroupList.get(i);
				dictGroupMap.put("id", dictGroup.getId());
				dictGroupMap.put("text", dictGroup.getName());
				List dictList = dataDictionaryService.findByDictGroupId(dictGroup.getId());
				List dictTempList = new ArrayList();
				if(dictList != null)
					for(int j=0;j<dictList.size();j++){
						DataDictionary dict = (DataDictionary) dictList.get(j);
						Map dictMap = new HashMap();
						dictMap.put("id", dict.getId());
						dictMap.put("text", dict.getName());
						dictTempList.add(dictMap);
					}
				dictGroupMap.put("children", dictTempList);
				resultList.add(dictGroupMap);
			}
		String dictTreeJson = JSONArray.fromObject(resultList).toString();		
		req.getSession().setAttribute("dictTreeJson", dictTreeJson);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String dictionaryTreeJson(){
		//HttpServletRequest req = ServletActionContext.getRequest();
		List dictGroupList = dataDictionaryService.findAllDictGroup();
		List resultList = new ArrayList();
		Map defaultMap = new HashMap();
		defaultMap.put("id", "-1");
		defaultMap.put("text", "无");
		defaultMap.put("children", new ArrayList());
		resultList.add(defaultMap);
		if(dictGroupList != null)
			for(int i=0;i<dictGroupList.size();i++){
				Map dictGroupMap = new HashMap();
				DictionaryGroup dictGroup = (DictionaryGroup) dictGroupList.get(i);
				dictGroupMap.put("id", dictGroup.getId());
				dictGroupMap.put("text", dictGroup.getName());
				List dictList = dataDictionaryService.findByDictGroupId(dictGroup.getId());
				List dictTempList = new ArrayList();
				if(dictList != null)
					for(int j=0;j<dictList.size();j++){
						DataDictionary dict = (DataDictionary) dictList.get(j);
						Map dictMap = new HashMap();
						dictMap.put("id", dict.getId());
						dictMap.put("text", dict.getName());
						dictTempList.add(dictMap);
					}
				dictGroupMap.put("children", dictTempList);
				resultList.add(dictGroupMap);
			}
		String dictTreeJson = JSONArray.fromObject(resultList).toString();		
	
		try {
			ServletActionContext.getResponse().setContentType("text/plain;charset=UTF-8 ");
			ServletActionContext.getResponse().getWriter().write(dictTreeJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 组织机构信息转成tree json
	 * @param req
	 * @return
	 */
	public String orgTreeJson(HttpServletRequest req){
		//HttpServletRequest req = ServletActionContext.getRequest();
		//GUOWEIXIN 掉用此方法。不用下边的 原因：当configSQL.properties使用时，会请求这个
		List list=organizationService.getAllJsonListByBack();
		//List list = organizationService.get_jsonList();
		String root = Constant.TREE_ROOT;
		/**s
		 * 加载时选中节点
		 */
		list = TreeUtil.treeAlgorithm(list, root);
		String orgTreeJson = JSONArray.fromObject(list).toString();		
		req.getSession().setAttribute("orgTreeJson", orgTreeJson);
		return null;
	}
	/**
	 * 按名称查找数据字典信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDictionaryLikeName(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		List resultList = new ArrayList();
		Map defaultMap = new HashMap();
		defaultMap.put("id", "-1");
		defaultMap.put("text", "无");
		defaultMap.put("children", new ArrayList());
		resultList.add(defaultMap);
		String dictName = "";
		if(req.getParameter("dictName") != null){
			dictName = req.getParameter("dictName");
		}
		List dictList = dataDictionaryService.findDictLikeName(dictName);
		if(dictList != null){
			for(int i=0;i<dictList.size();i++){
				DataDictionary dict = (DataDictionary) dictList.get(i);
				boolean flag = false;
				for(int j=0;j<resultList.size();j++){
					Map tempMap = (Map) resultList.get(j);
					if(tempMap.get("id").equals(dict.getDictionaryGroup().getId())){
						List childList = (List) tempMap.get("children");
						Map dictMap = new HashMap();
						dictMap.put("id", dict.getId());
						dictMap.put("text", dict.getName());
						childList.add(dictMap);
						flag = true;
						break;
					}
				}
				if(!flag){
					Map dictGroupMap = new HashMap();
					dictGroupMap.put("id", dict.getDictionaryGroup().getId());
					dictGroupMap.put("text", dict.getDictionaryGroup().getName());
					List tempDictList = new ArrayList();
					Map dictMap = new HashMap();
					dictMap.put("id", dict.getId());
					dictMap.put("text", dict.getName());
					tempDictList.add(dictMap);
					dictGroupMap.put("children", tempDictList);
					resultList.add(dictGroupMap);
				}
			}
		}
		String dictTreeJson = JSONArray.fromObject(resultList).toString();
		try {
			res.getWriter().write(dictTreeJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataObjectId = req.getParameter("dataObjectId");
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		try {
			int totalRows = formService.findTotalRowsByDataObjectId(dataObjectId);
			List formList = formService.findAllByPageAndDataObjectId(page,rows,dataObjectId);
			Map map = new HashMap();
			if(formList != null){
				for(int i=0;i<formList.size();i++){
					form = (Form) formList.get(i);
					
				  //String formSettings = SQLBlobUtil.blobToString(form.getSettings());
					byte[] byteArray = form.getSettings().getBytes(1, (int) form.getSettings().length());
					String formSettings = new String(byteArray);
					
					form.setSettings(null);
					Document doc = XMLParse.jdomXmlToDoc(formSettings);
					if(doc != null){		
						try {
							Element root = doc.getRootElement();
							Element columnsEle = root.getChild("Columns");
							String columnsIsConfig = columnsEle.getAttributeValue("isConfig");
							form.setConfDatacolumn(columnsIsConfig);
							Element buttonsEle = root.getChild("Buttons");
							String basicIsConfig = buttonsEle.getAttributeValue("isConfig");
							form.setConfBasic(basicIsConfig);
							Element paramsEle = root.getChild("Params");
							String paramsIsConfig = paramsEle.getAttributeValue("isConfig");
							form.setConfParam(paramsIsConfig);
							Element tabsEle = root.getChild("Tabs");
							String tabsIsConfig = tabsEle.getAttributeValue("isConfig");
							form.setConfTabs(tabsIsConfig);
							Element queryZoneEle = root.getChild("QueryZone");
							String queryZoneIsConfig = queryZoneEle.getAttributeValue("isConfig");
							form.setConfQuery(queryZoneIsConfig);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}else{
				formList = new ArrayList();
			}
			map.put("rows", formList);
			map.put("total", new Integer(totalRows));
			String formJson = JSONObject.fromObject(map).toString();
			res.getWriter().write(formJson);
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *GUOWEIXIN  选择表和列操作  第一个表
	 */
	public String dataRowsList(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataObjectId = req.getParameter("dataObjectId");
		String mainObjectId=req.getParameter("mainObjectId");//该主表名称所属ID，此处作用：通过其ID 用于得到其所属的数据源 
		 List<String> listStrId=new ArrayList<String>();
		if(dataObjectId!=null && !dataObjectId.equals("")){
			String[] str=dataObjectId.split(",");
			for(int i=0;i<str.length;i++)
			listStrId.add(str[i]);
		}
		Map map = new HashMap();
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		try {
			/**
			 * 1表单A，not in的值。
			 * flag=0,则listStrId的值 不查。否则只得到listStrId中的值
			 */
		    int totalRows=dataTableService.findTotalRowsByIsNotId(listStrId,0,mainObjectId);
			List formList=dataTableService.findAllByPageByIsNotId(page, rows,listStrId,0,mainObjectId);
			map.put("rows", formList);
			map.put("total", new Integer(totalRows));
			String formJson = JSONObject.fromObject(map).toString();
			res.getWriter().write(formJson);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *GUOWEIXIN  选择表和列操作  第二个表
	 */
	public String dataRowsListOnlyCheck(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataObjectId = req.getParameter("dataObjectId");
		String mainObjectId=req.getParameter("mainObjectId");//该主表名称所属ID，此处作用：通过其ID 用于得到其所属的数据源 
		List<String> listStrId=new ArrayList<String>();
			if(dataObjectId!=null && !dataObjectId.equals("")){
				String[] str=dataObjectId.split(",");
				for(int i=0;i<str.length;i++)
				listStrId.add(str[i]);
			}
			Map map = new HashMap();
			int page = 1;
			if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
				page = Integer.parseInt(req.getParameter("page"));
			}
			int rows = 0;
			if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
				rows = Integer.parseInt(req.getParameter("rows"));
			}	
		try {
			/**
			 * 1表单A，not in的值。
			 * flag=1,则listStrId的值 不查。否则只得到listStrId中的值
			 */
		    int totalRows=dataTableService.findTotalRowsByIsNotId(listStrId,1,mainObjectId);
			List formList=dataTableService.findAllByPageByIsNotId(page, rows,listStrId,1,mainObjectId);
			map.put("rows", formList);
			map.put("total", new Integer(totalRows));
			String formJson = JSONObject.fromObject(map).toString();
			res.getWriter().write(formJson);
		}catch(Exception e){
			e.printStackTrace();
		}
      return null;
	}
	/**
	 * 跳转添加页面 
	 * @return
	 */
	public String toAdd(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataObjectId = req.getParameter("dataObjectId");
		req.setAttribute("dataObjectId", dataObjectId);
		return "toadd";
	}
	
	/**
	 * 跳转添加页面 快速创建表单
	 * @return
	 */
	public String toAddForALL(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataObjectId = req.getParameter("dataObjectId");
		req.setAttribute("dataObjectId", dataObjectId);
		String pageType=req.getParameter("pageType");
		/**
		 * 查询数据主对象 和 主对象字段列
		 */
		DataTable mainTable=dataTableService.findById(dataObjectId);
		List dataColumnList= dataColumnService.findAllByTableIdAndStatus(dataObjectId,"0");//0表示可用的数据字段
		/**
		 * 设置快速创建的表单名称
		 */
		String objectName=mainTable.getName();
		//req.setAttribute("dataObjectName", objectName+"_"+RandomGUID.geneGuid());
		String dataObjectName="";
		if ("editPage".equals(pageType)) {
			dataObjectName=objectName+"_编辑页_"+new Random().nextInt(999999);
		}else if ("viewPage".equals(pageType)) {
			dataObjectName=objectName+"_详情页_"+new Random().nextInt(999999);
		}else {
			dataObjectName=objectName+"_列表页_"+new Random().nextInt(999999);
		}
		req.setAttribute("dataObjectName", dataObjectName);
		/**
		 * 构建 数据主对象和 主对象数据列的json对象。
		 */
		String jsonforDataCloumn=JSONArray.fromObject(dataColumnList).toString();
		String jsonForDataObj=JSONArray.fromObject(mainTable).toString();
		
		req.setAttribute("jsonForDataObj", jsonForDataObj);
		req.setAttribute("jsonforDataCloumn", jsonforDataCloumn);
		
		req.setAttribute("pageType", pageType);
		
		if ("viewPage".equals(pageType)) {
			String editPageId=req.getParameter("editPageId");
			req.setAttribute("editPageId", editPageId);
		}else if ("listPage".equals(pageType)) {
			String editPageId=req.getParameter("editPageId");
			String viewPageId=req.getParameter("viewPageId");
			req.setAttribute("editPageId", editPageId);
			req.setAttribute("viewPageId", viewPageId);
		}
		
		return "toadd_forAll";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataObjectId = req.getParameter("dataObjectId");
		HttpServletResponse res = ServletActionContext.getResponse();
		DataTable dataTable = dataTableService.findById(dataObjectId);
		if(dataTable != null){
			if(form.getId() == null || form.getId().equals("")){
				form.setId(RandomGUID.geneGuid());
			}
			form.setDataTable(dataTable);
			try {
				if(formService.findById(form.getId()) != null){				
					res.getWriter().write("exist");
				}else{
					String temp = req.getParameter("formSettings");
					temp = temp.replaceAll("\\r", "");
					temp = temp.replaceAll("\\n", "");
			
					byte[] buffer = temp.getBytes();   
			
					form.setSettings(Hibernate.createBlob(buffer));
					form.setState("暂存");		
					formService.insert(form);
					res.getWriter().write("success");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
/**
 * 添加 update by hgw
 * @return
 */
	
public String addForAll(){
	HttpServletRequest req = ServletActionContext.getRequest();
	String dataObjectId = req.getParameter("dataObjectId");
	HttpServletResponse res = ServletActionContext.getResponse();
	DataTable dataTable = dataTableService.findById(dataObjectId);
	if(dataTable != null){
		if(form.getId() == null || form.getId().equals("")){
			form.setId(RandomGUID.geneGuid());
		}
		form.setDataTable(dataTable);
		try {
			if(formService.findById(form.getId()) != null){				
				res.getWriter().write("exist");
			}else{
				String temp = req.getParameter("formSettings");
				temp = temp.replaceAll("\\r", "");
				temp = temp.replaceAll("\\n", "");
				StringFormat.replaceBlank(temp);
				byte[] buffer = temp.getBytes();
				form.setSettings(Hibernate.createBlob(buffer));
				form.setState("暂存");		
				formService.insert(form);
				Map resultMap=new HashMap();
				resultMap.put("status", "success");
				
				if ("editPage".equals(form.getType())) {
					String editPageId= form.getId();
					resultMap.put("editPageId",editPageId);
					
				}else if ("viewPage".equals(form.getType())) {
					
					String editPageId=req.getParameter("editPageId");
					String viewPageId=form.getId();
					resultMap.put("editPageId", editPageId);
					resultMap.put("viewPageId", viewPageId);
				}
				
				res.getWriter().write(JSONUtil.serialize(resultMap));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return null;
}
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String toUpdate(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String formId = req.getParameter("formId");
		if(formId!= null && !formId.equals("")){
			form = formService.findById(formId);
			byte[] byteArray = null;
			try {
				byteArray = form.getSettings().getBytes(1, (int) form.getSettings().length());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String	formSettings = new String(byteArray);
			formSettings=StringFormat.replaceBlank(formSettings);
		
			Document doc = XMLParse.jdomXmlToDoc(formSettings);
			if(doc != null){			
				Element e = doc.getRootElement();
				if(e != null){
					String rowsNumber = e.getAttributeValue("rows");
					String colsNumber = e.getAttributeValue("cols");
					req.setAttribute("formSettings", formSettings);
					req.setAttribute("rowsNumber", rowsNumber);
					req.setAttribute("colsNumber", colsNumber);
				}
			}
		}
		return "toupdate";
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		try {
			String temp = req.getParameter("formSettings");
			
			temp=StringFormat.replaceBlank(temp);//替换换行符
			//temp = temp.replaceAll("\\r", "");
			//temp = temp.replaceAll("\\n", "");
			byte[] buffer = temp.getBytes();
			form.setSettings(Hibernate.createBlob(buffer));
			form.setDataTable(dataTableService.findById(form.getDataTable().getId()));
			formService.update(form);
			try{
				pageService.update_service(form.getId());
			}catch(Exception e1){
				e1.printStackTrace();
			}
			res.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String formId = req.getParameter("formId");
		String formIds = req.getParameter("formIds");
		try {
			if(formId!= null && !formId.equals("")){
				formService.deleteById(formId);
				res.getWriter().write("success");
			}
			if(formIds!= null && !formIds.equals("")){
				formService.deleteAll(formService.findAllByIds(formIds));
				res.getWriter().write("success");
			}
		} catch (IOException e) {				
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 基础数据管理--数据对象管理 --（表）执行删除时，级联此操作
	 * 根据表名ID，删除其ID所属下方的页面信息
	 * @param dataTableId  表名ID     author:guo
	 */
	public String deleteAllByDataTableId(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dataTableId = req.getParameter("dataTableId");
		try{
			formService.deleteAll(formService.findAllByObjectId(dataTableId));
			res.getWriter().write("success");
		}catch(Exception e){
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * 设计
	 * @return
	 */
	public String toDesign(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String formId = req.getParameter("formId");
		if(formId!= null && !formId.equals("")){
			form = formService.findById(formId);
			//String formSettings = SQLBlobUtil.blobToString(form.getSettings());
			
			
			byte[] byteArray = null;
			try {
				byteArray = form.getSettings().getBytes(1, (int) form.getSettings().length());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String	formSettings = new String(byteArray);
			/**
			 * 替换换行符  
			 */
			formSettings=StringFormat.replaceBlank(formSettings);
			Document doc = XMLParse.jdomXmlToDoc(formSettings);
			/*Element group=doc.getRootElement().getChild("Groups");
			List list=group.getChildren();
			int len=list.size();
			StringBuffer buffer=new StringBuffer();
			for (int i = 0 ; i < len ; i++) {
				Element element=(Element)list.get(i);
				buffer.append(element.getAttributeValue("title")).append(element.getAttributeValue("show")).append(",");
			}
			if(buffer!=null&&buffer.length()>=1){
				String str=buffer.toString();
				str=str.substring(0,str.length()-1);
				req.setAttribute("titleAndShow",str);
			}*/
			formSettings = formSettings.replaceAll("\'", "%22");
			req.setAttribute("formSettings", formSettings);
			try{
			if(form.getType().equals("listPage")){
				Map map = formSettingToPageSet(doc);
				req.setAttribute("fieldshowjson",map.get("fieldshowjson"));
				req.setAttribute("fieldhiddenjson",map.get("fieldhiddenjson"));
				req.setAttribute("fieldalljson", map.get("fieldalljson"));
				req.setAttribute("primarykey",map.get("pkey"));
				Map queryMap = formSettingToQuerySet(doc);
				req.setAttribute("queryshowjson",queryMap.get("queryshowjson"));
				req.setAttribute("queryhiddenjson",queryMap.get("queryhiddenjson"));
				return "todesignlist";
			}else if(form.getType().equals("editPage")){
				Map map = formSettingToEditPageSet(doc);
				req.setAttribute("fieldlistjson",map.get("fieldlistjson"));
				return "todesignedit";
			}else if(form.getType().equals("viewPage")){
				Map map = formSettingToEditPageSet(doc);
				req.setAttribute("fieldlistjson",map.get("fieldlistjson"));
				return "todesignview";
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 刷新列表页面数据
	 * @return
	 */
	public String refreshDesignData(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String formId = req.getParameter("formId");
		if(formId!= null && !formId.equals("")){
			form = formService.findById(formId);
			
			byte[] byteArray = null;
			try {
				byteArray = form.getSettings().getBytes(1, (int) form.getSettings().length());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String	formSettings = new String(byteArray);
		  
		  //String formSettings = SQLBlobUtil.blobToString(form.getSettings());
			Document doc = XMLParse.jdomXmlToDoc(formSettings);
			Map map = formSettingToPageSet(doc);
			Map queryMap = formSettingToQuerySet(doc);
			map.put("formsettings", formSettings);
			map.putAll(queryMap);
			String formJson = JSONObject.fromObject(map).toString();
			try {
				res.getWriter().write(formJson);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 刷新编辑页面数据
	 * @return
	 */
	public String refreshEditPageDesignData(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String formId = req.getParameter("formId");
		if(formId!= null && !formId.equals("")){
			form = formService.findById(formId);
			
			//String formSettings = SQLBlobUtil.blobToString(form.getSettings());
			/**
			 * 解决xml乱码
			 */
			byte[] byteArray = null;
			try {
				byteArray = form.getSettings().getBytes(1, (int) form.getSettings().length());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String	formSettings = new String(byteArray);
			
			Document doc = XMLParse.jdomXmlToDoc(formSettings);
			Map map = formSettingToEditPageSet(doc);
			map.put("formsettings", formSettings);
			String formJson = JSONObject.fromObject(map).toString();
			try {
				res.getWriter().write(formJson);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 处理列表页面设置xml定义
	 * @param formSettings
	 * @return
	 */
	private Map formSettingToPageSet(Document doc){
		//String formSettings = SQLBlobUtil.blobToString(form.getSettings());
		//Document doc = XMLParse.jdomXmlToDoc(formSettings);\
		
		/*
		 * 构件所有的字段
		 */
		List allList = new ArrayList();
		
		List showlist = new ArrayList();
		List hiddenlist = new ArrayList();
		String pkColumnName = "";
		try{
			if(doc != null){
				Element root = doc.getRootElement();
				Element columnsEle = root.getChild("Columns");
				
				List columnsList = null;
				
				if(columnsEle != null){
					columnsList = columnsEle.getChildren();				
				}
				
				
				/*
				 * 获取QueryZone节点下的所有字段，并赋给全局变量
				 */
				Element queryZoneElement = root.getChild("QueryZone");
				List queryZoneList = null;
				if(queryZoneElement != null){
					queryZoneList = queryZoneElement.getChildren();
				}
				if(queryZoneElement !=null && queryZoneList != null && queryZoneList.size() > 0){
					for (Object o : queryZoneList) {
						Map map = new HashMap();
						Element e = (Element)o;
						String tableName = e.getAttributeValue("tableName");
						String valueString = e.getAttributeValue("name");
						String textString = e.getAttributeValue("text");
						
						map.put("tablename", tableName);
						map.put("name", valueString);
						map.put("text", textString);
						map.put("nametext", valueString + "-" + tableName);
						
						allList.add(map);
					}
				}
				
				if(columnsEle != null && columnsList != null && columnsList.size() > 0){
					for(int i=0;i<columnsList.size();i++){
						Map map = new HashMap();
						Element columnEle = (Element) columnsList.get(i);
						Element textEle = columnEle.getChild("Text");
						Element dataEle = columnEle.getChild("Data");
						String name = dataEle.getAttributeValue("name");
						if(dataEle.getAttributeValue("primaryKey").equalsIgnoreCase("true")){
							pkColumnName += pkColumnName.equals("")?name:","+name;
						}
						map.put("title", textEle.getAttributeValue("name"));
						map.put("textalign", textEle.getAttributeValue("align"));
						map.put("name", name);
						map.put("fieldDataType", dataEle.getAttributeValue("fieldDataType"));
						map.put("primaryKey", dataEle.getAttributeValue("primaryKey").toLowerCase());
						map.put("formula", "");
						/**
						 * 扩展属性

						 */
						map.put("dataFormat", textEle.getAttributeValue("dataFormat"));
						map.put("dataFormat", textEle.getAttributeValue("rows"));
						map.put("dataFormat", textEle.getAttributeValue("cols"));
						
						map.put("sortIndex", textEle.getAttributeValue("sort"));
						String dictionaryId = dataEle.getAttributeValue("dictionaryId");
						if(dictionaryId != null && !dictionaryId.equals("")){
							map.put("dicname", dictionaryId);
							map.put("fromdic", "");
						}else{
							map.put("fromdic", "");
							map.put("dicname", "");
						}
						map.put("columnrules", null==(textEle.getAttributeValue("columnrules"))?"":textEle.getAttributeValue("columnrules"));
						
						//allList.add(map);
						
						if(textEle.getAttributeValue("visible").equals("false")){
							hiddenlist.add(map);
						}else
							showlist.add(map);
					}
				}else{
					Element fieldDefEle = root.getChild("DataMapping").getChild("DataSet").getChild("Table").getChild("FieldDef");
					List list = fieldDefEle.getChildren();
					for(int i=0;i<list.size();i++){
						Map map = new HashMap();
						Element fieldEle = (Element) list.get(i);
						Element formFieldEle = fieldEle.getChild("FromField");
						Element fieldNameEle = formFieldEle.getChild("FieldName");
						String name =  fieldNameEle.getChildText("Name");
						if(formFieldEle.getChildText("IsPrimeKey").equalsIgnoreCase("true")){
							pkColumnName += pkColumnName.equals("")?name:","+name;
						}
						map.put("title", fieldEle.getChildText("DisplayName"));
						map.put("name", name);
						map.put("textalign", "");
						map.put("fromdic", "");
						map.put("dicname", "");
						map.put("fieldDataType", formFieldEle.getChildText("Type"));
						map.put("primaryKey", formFieldEle.getChildText("IsPrimeKey").toLowerCase());
						map.put("formula", "");
						map.put("dataFormat", "");
						map.put("sortIndex", "0");
						showlist.add(map);
						
						//allList.add(map);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}			
		
		Map resultMap = new HashMap();
		resultMap.put("fieldshowjson",designDataGridData(showlist));
		resultMap.put("fieldhiddenjson",designDataGridData(hiddenlist));
		
		resultMap.put("fieldalljson", designDataGridData(allList));
		resultMap.put("pkey", pkColumnName);
		return resultMap;
	}
	/**
	 * 处理列表页面设置xml定义
	 * @param formSettings
	 * @return
	 */
	private Map formSettingToQuerySet(Document doc){
		//String formSettings = SQLBlobUtil.blobToString(form.getSettings());
		//Document doc = XMLParse.jdomXmlToDoc(formSettings);
		List showlist = new ArrayList();
		List hiddenlist = new ArrayList();
		try{
			if(doc != null){
				Element root = doc.getRootElement();
				Element queryZoneEle = root.getChild("QueryZone");
				List queryZoneList = null;
				if(queryZoneEle != null){
					queryZoneList = queryZoneEle.getChildren();				
				}
				if(queryZoneEle != null && queryZoneList != null && queryZoneList.size() > 0){
					for(int i=0;i<queryZoneList.size();i++){
						Map map = new HashMap();
						
						Element queryColumnEle = (Element) queryZoneList.get(i);	
						Element editModeEle = queryColumnEle.getChild("EditMode");
						map.put("id", queryColumnEle.getAttributeValue("id"));
						map.put("type", queryColumnEle.getAttributeValue("type"));
						map.put("tableName", queryColumnEle.getAttributeValue("tableName"));
						map.put("name", queryColumnEle.getAttributeValue("name"));
						map.put("text", queryColumnEle.getAttributeValue("text"));
						map.put("fieldDataType", queryColumnEle.getAttributeValue("fieldDataType"));
						map.put("readOnly", queryColumnEle.getAttributeValue("readOnly"));
						map.put("cssClass", queryColumnEle.getAttributeValue("cssClass"));
						map.put("sortIndex", queryColumnEle.getAttributeValue("sortIndex"));
						map.put("textalign", queryColumnEle.getAttributeValue("align"));
						map.put("dateformat", queryColumnEle.getAttributeValue("dateformat"));
						map.put("validateRule", editModeEle.getAttributeValue("validateRule"));
						map.put("isExistDBSource", editModeEle.getAttributeValue("isExistDBSource"));
						map.put("isExistSQL", editModeEle.getAttributeValue("isExistSQL"));
						map.put("compDate", editModeEle.getAttributeValue("compDate"));
						map.put("compCon", editModeEle.getAttributeValue("compCon"));
						map.put("exclusiveLine", queryColumnEle.getAttributeValue("exclusiveLine"));
						//map.put("primaryKey", queryColumnEle.getAttributeValue("primaryKey"));
						map.put("operateType", queryColumnEle.getAttributeValue("operateType"));
						String dictionaryId = queryColumnEle.getAttributeValue("dictionaryId");
						if(dictionaryId != null && !dictionaryId.equals("") && !dictionaryId.equals("无")){
							map.put("dicname", dictionaryId);
							map.put("fromdic", "");
						}else{
							map.put("fromdic", "");
							map.put("dicname", "");
						}
						if(queryColumnEle.getAttributeValue("visible").equals("false")){
							hiddenlist.add(map);
						}else
							showlist.add(map);
					}
				}else{
					Element fieldDefEle = root.getChild("DataMapping").getChild("DataSet").getChild("Table").getChild("FieldDef");
					List list = fieldDefEle.getChildren();
					for(int i=0;i<list.size();i++){
						Map map = new HashMap();
						Element fieldEle = (Element) list.get(i);
						Element fromFieldEle = fieldEle.getChild("FromField");
						Element fieldNameEle = fromFieldEle.getChild("FieldName");
						map.put("id", "");
						map.put("type", "");
						map.put("tableName", fieldNameEle.getChildText("TableName"));
						
						map.put("readOnly", "false");
						map.put("cssClass", "");
						map.put("sortIndex", "0");
						map.put("validateRule", "");
						map.put("isExistDBSource","");
						map.put("isExistSQL","");
						
						map.put("text", fieldEle.getChildText("DisplayName"));
						map.put("name", fieldNameEle.getChildText("Name"));
						map.put("textalign", "");
						map.put("fieldDataType", fromFieldEle.getChildText("Type"));
						//map.put("primaryKey", fieldEle.getChild("FromField").getChildText("IsPrimeKey"));
						map.put("formula", "");
						map.put("dateformat","");
						map.put("fromdic", "");
						map.put("dicname", "");
						map.put("exclusiveLine", "false");
						map.put("operateType", "");
						showlist.add(map);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}			
		
		Map resultMap = new HashMap();
		resultMap.put("queryshowjson",designDataGridData(showlist));
		resultMap.put("queryhiddenjson",designDataGridData(hiddenlist));
		return resultMap;
	}
	/**
	 * 编辑页设置数据

	 * @param formSettings
	 * @return
	 */
	private Map formSettingToEditPageSet(Document doc){
		//Document doc = XMLParse.jdomXmlToDoc(formSettings);
		List resultlist = new ArrayList();
		try{
			if(doc != null){
				Element root = doc.getRootElement();
				Element columnsEle = root.getChild("Columns");
				List columnsList = null;
				if(columnsEle != null){
					columnsList = columnsEle.getChildren();				
				}
				if(columnsEle != null && columnsList != null && columnsList.size() > 0){
					for(int i=0;i<columnsList.size();i++){
						Map map = new HashMap();
						Element columnEle = (Element) columnsList.get(i);
						Element textEle = columnEle.getChild("Text");
						Element dataEle = columnEle.getChild("Data"); 
						Element editMode = columnEle.getChild("EditMode");
						Element RulesEngin = columnEle.getChild("RulesEngin");
						Element roles = columnEle.getChild("Roles");
						
						map.put("title", textEle.getAttributeValue("name"));
						map.put("groupid", textEle.getAttributeValue("groupId"));
						map.put("readOnly", textEle.getAttributeValue("readOnly"));
						map.put("visible", textEle.getAttributeValue("visible"));
						
						/*
						 * @GUOWEIXIN 添加树控件的值
						 */
						map.put("isCheckBox", dataEle.getAttributeValue("isCheckBox"));
						map.put("isSpread", dataEle.getAttributeValue("isSpread"));
						
						map.put("name", dataEle.getAttributeValue("name"));
						map.put("tagType", dataEle.getAttributeValue("type"));
						map.put("dataType", dataEle.getAttributeValue("fieldDataType"));						
						map.put("isprimekey", dataEle.getAttributeValue("primaryKey"));
						map.put("formula", dataEle.getAttributeValue("formula"));
						map.put("dataFunction", dataEle.getAttributeValue("dataFunction"));
						map.put("sortIndex", textEle.getAttributeValue("sortIndex"));
						map.put("exclusiveLine", textEle.getAttributeValue("exclusiveLine"));
						map.put("dateformat", textEle.getAttributeValue("dateformat"));
						map.put("isworkflow", textEle.getAttributeValue("isworkflow"));
						map.put("ismultipart", textEle.getAttributeValue("ismultipart"));
						map.put("isleafcheck", textEle.getAttributeValue("isleafcheck"));
						map.put("isselforg", textEle.getAttributeValue("isselforg"));
						map.put("orgid", textEle.getAttributeValue("orgid"));
						map.put("ismultiparthuman", textEle.getAttributeValue("ismultiparthuman"));
						map.put("orgidhuman", textEle.getAttributeValue("orgidhuman"));
						
						/**
						 * 应急控件
						 */
						map.put("issingletable", textEle.getAttributeValue("issingletable"));
						map.put("ismoretable", textEle.getAttributeValue("ismoretable"));
						map.put("isemergencytreesql", textEle.getAttributeValue("isemergencytreesql"));
						map.put("listInside", textEle.getAttributeValue("listInside"));
						map.put("listInsideParmer", textEle.getAttributeValue("listInsideParmer"));
						map.put("is_listPageForvalue", textEle.getAttributeValue("is_listPageForvalue"));
						map.put("pageurl_listPage", textEle.getAttributeValue("pageurl_listPage"));
						map.put("list_value", textEle.getAttributeValue("list_value"));
						map.put("list_text", textEle.getAttributeValue("list_text"));
						//String is_listPageForvalue = columnText.getAttributeValue("is_listPageForvalue");
						//String pageurl_listPage = columnText.getAttributeValue("pageurl_listPage");
						if( StringUtils.isNotBlank(dataEle.getAttributeValue("tablename")) ){
							map.put("columnTableName", dataEle.getAttributeValue("tablename"));
						}

						/*是否设置变量*/
						map.put("variantOrnot", textEle.getAttributeValue("variantOrnot"));
						map.put("variantType", textEle.getAttributeValue("variantType"));
						map.put("variantValue", textEle.getAttributeValue("variantValue"));
						
						
						if(editMode != null){
							map.put("validateRule", editMode.getAttributeValue("validateRule"));
							map.put("minlength", editMode.getAttributeValue("minLength")==null?"":editMode.getAttributeValue("minLength"));
							map.put("maxlength", editMode.getAttributeValue("maxLength")==null?"":editMode.getAttributeValue("maxLength"));
							map.put("maxlength2", editMode.getAttributeValue("maxLength2")==null?"":editMode.getAttributeValue("maxLength2"));
							map.put("isExistDBSource", editMode.getAttributeValue("isExistDBSource"));
							map.put("isExistSQL", editMode.getAttributeValue("isExistSQL"));
							map.put("required", editMode.getAttributeValue("required"));
						}
						String dictionaryId = dataEle.getAttributeValue("dictionaryId");
						if(dictionaryId != null && !dictionaryId.equals("") && !dictionaryId.equals("无")){
							map.put("dicname", dictionaryId);
							map.put("fromdic", "");
						}else{
							map.put("fromdic", "");
							map.put("dicname", "");
						}
						
						if(RulesEngin != null){
							String rulesservice =  RulesEngin.getAttributeValue("rulesService").replaceAll("\'", "%22");
							map.put("rulesService",rulesservice);
							map.put("eventTypes", RulesEngin.getAttributeValue("eventTypes"));
							map.put("is_jilian", RulesEngin.getAttributeValue("is_jilian"));
							map.put("jilian_column", RulesEngin.getAttributeValue("jilian_column"));
							String jilian_column_dictionaryId = RulesEngin.getAttributeValue("jilian_column_dictionaryId");
							if(jilian_column_dictionaryId != null && !jilian_column_dictionaryId.equals("") && !jilian_column_dictionaryId.equals("无")){
								map.put("jilian_column_dictionaryId",jilian_column_dictionaryId);
							}
						}
						
						if( roles != null ){
							map.put("isCustomRole", roles.getAttributeValue("isCustomRole"));
							map.put("isCustomRoleRead", roles.getAttributeValue("isCustomRoleRead"));
							map.put("isCustomRoleReadId", roles.getAttributeValue("isCustomRoleReadId"));
							map.put("isCustomRoleReadName", roles.getAttributeValue("isCustomRoleReadName"));
							map.put("isCustomRoleWrite", roles.getAttributeValue("isCustomRoleWrite"));
							map.put("isCustomRoleWriteId", roles.getAttributeValue("isCustomRoleWriteId"));
							map.put("isCustomRoleWriteName", roles.getAttributeValue("isCustomRoleWriteName"));
						}
						resultlist.add(map);					
					}
				}else{
					Element fieldDefEle = root.getChild("DataMapping").getChild("DataSet").getChild("Table").getChild("FieldDef");
					List list = fieldDefEle.getChildren();
					for(int i=0;i<list.size();i++){
						Map map = new HashMap();
						Element fieldEle = (Element) list.get(i);
						Element fromFieldEle = fieldEle.getChild("FromField");
						String name = fromFieldEle.getChild("FieldName").getChildText("Name");
						map.put("title", fieldEle.getChildText("DisplayName"));
						map.put("name", name);
						map.put("groupid", "");
						map.put("tagType", "");
						map.put("dataType", fromFieldEle.getChildText("Type"));
						map.put("length", fromFieldEle.getChildText("Length"));
						map.put("required", fromFieldEle.getChildText("IfNull"));
						map.put("isprimekey", fromFieldEle.getChildText("IsPrimeKey"));
						map.put("formula", "");
						map.put("dataFunction", "");						
						map.put("validateRule", "");
						map.put("isExistDBSource","");
						map.put("isExistSQL","");
						map.put("readOnly", "false");
						map.put("fromdic", "");
						map.put("dicname", "");
						map.put("visible", "true");
						map.put("sortIndex", "0");
						map.put("exclusiveLine", "false");

						map.put("dateformat","");
						

						/*是否设置变量*/
						map.put("variantOrnot","");
						map.put("variantType","");
						map.put("variantValue", "");
						
						resultlist.add(map);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}			
		
		Map resultMap = new HashMap();
		resultMap.put("fieldlistjson",designDataGridData(resultlist));
		return resultMap;
	}
	private String designDataGridData(List list){
		Map map = new HashMap();
		map.put("rows", list);
		map.put("total", new Integer(list.size()));
		String json = JSONObject.fromObject(map).toString();
		return json;
	}
	public String toListPageSet(){
		return "listpageset";
	}
	public String toEditPageSet(){
		return "editpageset";
	}
	public String toViewPageSet(){
		return "viewpageset";
	}
	/**
	 * 更新FormSetting
	 * @return
	 */
	public String updateFormSettings(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String formId = req.getParameter("formId");
		try {
			if(formId!= null && !formId.equals("")){
				form = formService.findById(formId);
				String temp = req.getParameter("formSettings");
				temp = temp.replaceAll("\\r", "");
				temp = temp.replaceAll("\\n", "");
				byte[] buffer = temp.getBytes();
				form.setSettings(Hibernate.createBlob(buffer));
				formService.update(form);
				try{
					pageService.update_service(form.getId());
				}catch(Exception e1){
					e1.printStackTrace();
				}
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 菜单
	 * @return
	 */
	public String menu(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String parentId = req.getParameter("parentId");
		List list = dataTableService.findChildrenByParentId(parentId);
		List resultList = new ArrayList();
		if(list != null)
			for(int i=0;i<list.size();i++){
				Map map = new HashMap();
				DataObjectMenu dom = (DataObjectMenu)list.get(i);
				map.put("id", dom.getId());
				map.put("text", dom.getName());
				if(dom.getType().equals("0"))
					map.put("state", "closed");
				else{
					map.put("attributes", "1");
				}
				resultList.add(map);
			}

		String dataJson = JSONArray.fromObject(resultList).toString();		
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String menuDOList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String groupId = req.getParameter("groupId");
		req.setAttribute("groupId", groupId);
		//codeFlag 用于标识是否是代码生成功能。如果是，就让其转到codeFlag标识页面。
		String codeFlag=req.getParameter("codeFlag");
		if("codeFlag".equals(codeFlag)){
			return "menudolistCode";
		}
		return "menudolist";
	}
	/**
	 * 跳转选择表页面
	 *@GUOWEIXIN
	 * @return
	 */
	public String toSelectTable(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dataObjectId = req.getParameter("dataObjectId");
		String xmlOperateType = req.getParameter("xmlOperateType");
		String mainObjectName=req.getParameter("mainObjectName");//主表的表名
		//得到 页面所传的所有表的名称
		String dataTablesNames = req.getParameter("dataTablesNames");
		String[] strTableNames = null;
		if(dataTablesNames!=null && !dataTablesNames.equals("")){
			strTableNames=dataTablesNames.split(",");
		}
		req.setAttribute("mainObjectName", mainObjectName);
		req.setAttribute("xmlOperateType", xmlOperateType);
		req.setAttribute("dataObjectId", dataObjectId);
		
		List columnList = new ArrayList();//2步查出来的内容存放于此
		//1 得到页面的ID。得到XML的  原始数据所保存的所有列
		List<ListColumn> xmlDataList=new ArrayList<ListColumn>();
		List<DataColumn> dataDataList=new ArrayList<DataColumn>();
		String[] xmlData;
		String[] dataData;
		String formId=req.getParameter("formId");
		ListPage listPage=null;
		try {
			String xml = queryXmlDataService.findBolbById(formId);
			Document doc= XMLParse.jdomXmlToDoc(xml);
			 listPage = (ListPage)resolveObjectDefService.resolveObject(doc);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}	
		try{
		if(listPage!=null){
			StringBuffer sbStr=new StringBuffer("");
			if("editPage".equals(listPage.getFlag())){
				List<EditColumn> listEditColumn=listPage.getEditPage().getEditColumn();
				for(int i=0;i<listEditColumn.size();i++){
					ListColumn listC=new ListColumn();
					if( listEditColumn.get(i).getTablename()==null || "".equals(listEditColumn.get(i).getTablename())){
						listC.setTablename(mainObjectName);
					}else
						listC.setTablename(listEditColumn.get(i).getTablename());
					listC.setName(listEditColumn.get(i).getName());
					xmlDataList.add(listC);
				}
			}
			if("viewPage".equals(listPage.getFlag())){
				List<ViewColumn> listViewColumn=listPage.getViewPage().getViewColumn();
				for(int i=0;i<listViewColumn.size();i++){
					ListColumn listC=new ListColumn();
					if( listViewColumn.get(i).getTablename()==null || "".equals(listViewColumn.get(i).getTablename())){
						listC.setTablename(mainObjectName);
					}else
						listC.setTablename(listViewColumn.get(i).getTablename());
					listC.setName(listViewColumn.get(i).getName());
					xmlDataList.add(listC);
				}
			}
			if("listPage".equals(listPage.getFlag())){
				for(int i=0;i<listPage.getListColumn().size();i++){
					ListColumn listC=(ListColumn)listPage.getListColumn().get(i);
					sbStr.append(listC.getTablename()+"."+listC.getName()+",");
					if( listC.getTablename()==null || "".equals(listC.getTablename())){
						listC.setTablename(mainObjectName);
					}
					xmlDataList.add(listC);
				}
				//xmlData=sbStr.toString().split(",");
		   }	
			// System.out.println("XML的：："+sbStr.toString()); 
		//2得到所有表格的列，。根据选中 表的名称			
			 List<String> listStrId=new ArrayList<String>();
				if(strTableNames!=null ){
					for(int i=0;i<strTableNames.length;i++)
					listStrId.add(strTableNames[i]);
				}
				/**
				 * @param listStrId:所传是LIST对象，里面都是表的名称（NAME）
				 * @param String类型要求只输入0
				 */
				StringBuffer sbDataStr=new StringBuffer(""); 
				columnList=dataColumnService.findAllByTableNames(listStrId,"0");
				for(int i=0;i<columnList.size();i++){
					DataColumn listC=(DataColumn)columnList.get(i); 
					sbDataStr.append(listC.getDataTable().getName()+"."+listC.getName()+",");
					dataDataList.add(listC);
				}
				// System.out.println("数据库："+sbDataStr.toString()); 
				dataData=sbDataStr.toString().split(",");				
	    //3，对1和2进行比对，反回String类型。
			List<DataColumn> resultListColumn=new ArrayList<DataColumn>();
			int isCheck=0;
		StringBuffer resultUnCheck=new StringBuffer("");
		String dataTableName;
		String dataFieldName;
		String xmlTableName;
		String xmlfieldName;
		int flagCheck=0;//用于标识
		//xml取出的数据，依次遍历 数据库取出的，看是否存在，将存在的过滤，不存在的取出 
		for(int j=0;j<dataDataList.size();j++){
		    	   flagCheck=0;
		    	   dataTableName=dataDataList.get(j).getDataTable().getName();
		    	   dataFieldName=dataDataList.get(j).getName();
		    	   for(int z=0;z<xmlDataList.size();z++){
		    		   xmlTableName=xmlDataList.get(z).getTablename();
		    		   xmlfieldName=xmlDataList.get(z).getName();
			    		 if(dataTableName.equals(xmlTableName)){  
				    			 if(dataFieldName.equals(xmlfieldName)){
					    			   break;
					    		   }
					    		   else if(!dataFieldName.equals(xmlfieldName) && z==xmlDataList.size()-1){
					    			   resultListColumn.add(dataDataList.get(j));
					    			   resultUnCheck.append(dataData[j]+",");   
					    			   flagCheck=1;
					    		   }
			    		 } 
			    		 if(z==xmlDataList.size()-1 && flagCheck==0){
			    			 resultListColumn.add(dataDataList.get(j));
			    			 resultUnCheck.append(dataData[j]+","); 
			    		 }
		    	   }
		       }
		    //  System.out.println("对比后的："+resultUnCheck.toString()); 
		    Map map=new HashMap();
		    map.put("resultListColumn",resultListColumn);
		       String formJson = JSONObject.fromObject(map).toString(); 
		     req.setAttribute("formJson",formJson);
		 req.setAttribute("resultUnCheckField",resultUnCheck.toString());
		}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "selecttable";
	}
	
	/**
	 * 清除所有的GRID信息 @GUOWEIXIN
	 * @return
	 */
	public String clearColumnInfoListGrid(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		HttpServletRequest req = ServletActionContext.getRequest();
		List columnList = new ArrayList();
		Map map = new HashMap();
		map.put("rows", columnList);
		map.put("total", new Integer(0));
		String formJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(formJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取所有数据对象信息 GUOWEIXIN
	 */
	public String getDataObjectInfoListByPage(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		Map map = new HashMap();
		int page = 2;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		page=5;
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		try {
			int totalRows = dataTableService.findTotalRows();
			List formList=dataTableService.findAllByPage(page, rows);
			//List formList = formService.findAllByPageAndDataObjectId(page,rows,dataObjectId);
			
			map.put("rows", formList);
			map.put("total", new Integer(totalRows));
			String formJson = JSONObject.fromObject(map).toString();
			res.getWriter().write(formJson);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	
	}
	/**
	 * 获得所有数据对象信息

	 * @return
	 */
	public String getDataObjectInfoList(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		List list = null;
		list = dataTableService.findAll();
		if(list == null) list = new ArrayList();
		Map map = new HashMap();
		map.put("rows", list);
		map.put("total", new Integer(list.size()));
		String formJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(formJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 对数据对象名称的模糊查询
	 * @return
	 */
	public String findAllDataObjectLikeName(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dataObjectName = req.getParameter("dataObjectName");
		String dataObjectId = req.getParameter("dataObjectId");
		List list = null;
		list = dataTableService.findAllByDataObjectIdAndLikeName(dataObjectId,dataObjectName);		
		if(list == null){
			list = new ArrayList();
		}

		Map map = new HashMap();		
		map.put("rows", list);
		map.put("total", new Integer(list.size()));


		String formJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(formJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得所有数据列信息
	 * @return
	 */
	public String getDataColumnInfoList(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		List list = dataTableService.findAll();
		String tableIds = "";
		if(list != null)
			for(int i=0;i<list.size();i++){
				DataTable dataTable = (DataTable) list.get(i);
				tableIds += tableIds == ""?"'"+dataTable.getId()+"'":",'"+dataTable.getId()+"'";
			}
		List columnList = null;
		if(!tableIds.equals("")){
			columnList = dataColumnService.findAllByTableIds(tableIds,"0");
		}
		if(columnList == null){
			columnList = new ArrayList();
		}
		Map map = new HashMap();		
		map.put("rows", columnList);
		map.put("total", new Integer(columnList.size()));


		String formJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(formJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据DataObjectId获得所有数据列信息
	 * @return
	 */
	public String getColumnInfoListByDoIds(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataTablesIds = req.getParameter("dataTablesIds");
		List columnList = null;
		try{
			if(dataTablesIds != null && !dataTablesIds.equals("")){
				columnList = dataColumnService.findAllByTableIds(dataTablesIds,"0");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(columnList == null){
			columnList = new ArrayList();
		}
		Map map = new HashMap();
		map.put("rows", columnList);
		map.put("total", new Integer(columnList.size()));
		String formJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(formJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getEditPageList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String mainObjectId = req.getParameter("mainObjectId");
		List editPageList = formService.findPageByTypeAndMainObjectId(mainObjectId,"editPage");
		List resultList = new ArrayList();
		if(editPageList != null)
			for(int i=0;i<editPageList.size();i++){
				Map map = new HashMap();
				Form form = (Form)editPageList.get(i);
				map.put("id", form.getId());
				map.put("text", form.getFormName());				
				resultList.add(map);
			}

		String dataJson = JSONArray.fromObject(resultList).toString();		
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getViewPageList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String mainObjectId = req.getParameter("mainObjectId");
		List viewPageList = formService.findPageByTypeAndMainObjectId(mainObjectId,"viewPage");
		List resultList = new ArrayList();
		if(viewPageList != null)
			for(int i=0;i<viewPageList.size();i++){
				Map map = new HashMap();
				Form form = (Form)viewPageList.get(i);
				map.put("id", form.getId());
				map.put("text", form.getFormName());				
				resultList.add(map);
			}

		String dataJson = JSONArray.fromObject(resultList).toString();		
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getTabsPageList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String mainObjectId = req.getParameter("mainObjectId");
		String pageType = req.getParameter("pageType");
		//mainObjectId 暂时不用
		List tabPageList = formService.findPageByTypeAndMainObjectId(mainObjectId,pageType);
		List resultList = new ArrayList();
		if(tabPageList != null)
			for(int i=0;i<tabPageList.size();i++){
				Map map = new HashMap();
				Form form = (Form)tabPageList.get(i);
				map.put("id", form.getId());
				map.put("text", form.getFormName());				
				resultList.add(map);
			}
		
		String dataJson = JSONArray.fromObject(resultList).toString();		
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String workFlowFormConfigSave(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String formId = req.getParameter("formId");
		String workflowId = req.getParameter("workflowId");
		if(formId != null && !formId.equals("")){
			form = formService.findById(formId);
			if(form != null){
				String formSettings = SQLBlobUtil.blobToString(form.getSettings());				
				Document doc = XMLParse.jdomXmlToDoc(formSettings);				
				if(doc != null){			
					Element e = doc.getRootElement();
					if(e != null && workflowId != null && !workflowId.equals("")){
						e.setAttribute("isUseWorkflow", "1");
						e.setAttribute("workflowId", workflowId);
						Format format = Format.getPrettyFormat();
						format.setEncoding("gb2312");
						XMLOutputter xmlout = new XMLOutputter(format);
						ByteArrayOutputStream bo = new ByteArrayOutputStream();
						try {
							xmlout.output(doc,bo);
							String xmlStr = bo.toString();
							xmlStr = xmlStr.replaceAll("gb2312", "UTF-8");
							byte[] buffer = xmlStr.getBytes();
							form.setSettings(Hibernate.createBlob(buffer));
							formService.update(form);
							res.getWriter().write("success");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	  * 根绝当前的改变的表的结构，对应的改变相应的表单。
	  * <p>
	  *   传入参数：
	  *     dataObjectId(*):数据对象的id
	  *     columnId(*):数据对象列的id
	  *   传出参数：
	  *     类型：String(flag)
	  *     值："success"[存在]、"fail"[不存在]
	  *    
	  *   action访问地址： form/form!changeFormFrame.action?dataObjectId&columnId
	  *   
	  * 修改记录：
	  *    2011-09-19 005 添加
	  * </p>
	  * @throws IOException 
	  * 
	  */
	public String changeFormFrame() throws IOException{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		final String dataObjectId = req.getParameter("dataObjectId");
		String columnId = req.getParameter("columnId");
		boolean flag = false;
		try {
			flag = formService.updateFormFrameByDoId(dataObjectId,columnId);
			new Thread(){public void run() {
				formService.updatePageService(dataObjectId);
			}}.start();
		} catch (Exception e) {
		}
		if (flag) {
			res.getWriter().write("success");
		}else {
			res.getWriter().write("fail");
		}
		return null;
	}
	
	public void setFormService(IFormService formService) {
		this.formService = formService;
	}
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	public String getDataObjectGroupId() {
		return dataObjectGroupId;
	}
	public void setDataObjectGroupId(String dataObjectGroupId) {
		this.dataObjectGroupId = dataObjectGroupId;
	}
	public void setDataTableService(IDataTableService dataTableService) {
		this.dataTableService = dataTableService;
	}
	public void setDataColumnService(IDataColumnService dataColumnService) {
		this.dataColumnService = dataColumnService;
	}
	public void setValidationRuleService(
			IValidationRuleService validationRuleService) {
		this.validationRuleService = validationRuleService;
	}
	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	public OrganizationService getOrganizationService() {
		return organizationService;
	}
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	public IQueryXmlDataService getQueryXmlDataService() {
		return queryXmlDataService;
	}

	public void setQueryXmlDataService(IQueryXmlDataService queryXmlDataService) {
		this.queryXmlDataService = queryXmlDataService;
	}
	public ResolveObjectDefService getResolveObjectDefService() {
		return resolveObjectDefService;
	}

	public void setResolveObjectDefService(
			ResolveObjectDefService resolveObjectDefService) {
		this.resolveObjectDefService = resolveObjectDefService;
	}
}
