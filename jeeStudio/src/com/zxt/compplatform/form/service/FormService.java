package com.zxt.compplatform.form.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.zxt.compplatform.datacolumn.entity.DataColumn;
import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.form.dao.IFormDao;
import com.zxt.compplatform.form.entity.Form;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.util.StringFormat;
import com.zxt.framework.common.util.XMLParse;

/**
 * 表单业务逻辑操作接口实现
 * 
 * @author 007
 */
public class FormService implements IFormService {
	private static final Logger log = Logger.getLogger(FormService.class);
	/**
	 * 表单持久化接口
	 */
	private IFormDao formDao;
	/**
	 * 数据对象列操作接口
	 */
	private IDataColumnService dataColumnService;
	/**
	 * 页面业务操作接口
	 */
	private PageService pageService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#delete(com.zxt.compplatform.form.entity.Form)
	 */
	public void delete(Form form) {
		formDao.delete(form);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#deleteAll(java.util.List)
	 */
	public void deleteAll(List paramCollection) {
		formDao.deleteAll(paramCollection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#deleteAllByObjectId(java.lang.String)
	 */
	public void deleteAllByObjectId(String objectId) {
		List list = findAllByObjectId(objectId);
		if (list != null && list.size() > 0) {
			formDao.deleteAll(list);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#deleteById(java.lang.String)
	 */
	public void deleteById(String id) {
		formDao.delete(findById(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findAll()
	 */
	public List findAll() {
		String paramString = " from Form t order by t.sortIndex ";
		return formDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findAllByIds(java.lang.String)
	 */
	public List findAllByIds(String ids) {
		String paramString = " from Form t where t.id in (" + ids
				+ ") order by t.sortIndex ";
		return formDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findAllByObjectId(java.lang.String)
	 */
	public List findAllByObjectId(String objectId) {
		String paramString = " from Form t where t.dataTable.id = '" + objectId
				+ "'";
		return formDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findTotalRows()
	 */
	public int findTotalRows() {
		String queryString = " select count(id) from Form t ";
		return formDao.findTotalRows(queryString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findAllByPage(int,
	 *      int)
	 */
	public List findAllByPage(int page, int rows) {
		String paramString = " from Form t  order by t.sortIndex ";
		return formDao.findAllByPage(paramString, page, rows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findTotalRowsByDataObjectId(java.lang.String)
	 */
	public int findTotalRowsByDataObjectId(String dataObjectId) {
		String queryString = " select count(id) from Form t where t.dataTable.id = '"
				+ dataObjectId + "' ";
		return formDao.findTotalRows(queryString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findAllByPageAndDataObjectId(int,
	 *      int, java.lang.String)
	 */
	public List findAllByPageAndDataObjectId(int page, int rows,
			String dataObjectId) {
		String paramString = " from Form t where t.dataTable.id = '"
				+ dataObjectId + "'  order by id asc ";
		return formDao.findAllByPage(paramString, page, rows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findPageByTypeAndMainObjectId(java.lang.String,
	 *      java.lang.String)
	 */
	public List findPageByTypeAndMainObjectId(String mainObjectId,
			String pageType) {
		String paramString = " from Form t "; //where t.dataTable.id = '" +
		 //mainObjectId + "' ";
		if (!pageType.equals("tabs")) {
			paramString = paramString + " where t.type = '" + pageType + "' ";
		}
		paramString += " order by t.sortIndex ";
		return formDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#findById(java.lang.String)
	 */
	public Form findById(String id) {
		String paramString = " from Form t where t.id = '" + id + "' ";
		List list = formDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (Form) list.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#insert(com.zxt.compplatform.form.entity.Form)
	 */
	public void insert(Form form) {
		formDao.create(form);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#update(com.zxt.compplatform.form.entity.Form)
	 */
	public void update(Form form) {
		formDao.update(form);
	}

	/**
	 * @param formDao
	 */
	public void setFormDao(IFormDao formDao) {
		this.formDao = formDao;
	}

	/*
	 * (non-Javadoc) 此处为单个节点的修改，需要添加多个节点同时的添加和删除
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#changeFormFrameByDoId(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#updateFormFrameByDoId(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean updateFormFrameByDoId(String dataObjectId, String columnId) {
		String queryString = " from Form t where t.dataTable.id = '"
				+ dataObjectId + "'  order by t.sortIndex ";
		List formList = formDao.find(queryString);// 判断是不是有doc文档

		// 查询数据对象列
		DataColumn column = dataColumnService.findById(columnId);
		Document doc = null;

		for (int i = 0; i < formList.size(); i++) {
			Form form = (Form) formList.get(i);
			// XMLParse.jdomXmlToDoc(form.getSettings());
			try {
				Blob b = form.getSettings();
				// byte[] byteArray = b.getBytes(1, (int) b.length());
				String tmpStr = "";
				StringBuffer sb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader(b
						.getBinaryStream()));
				while ((tmpStr = br.readLine()) != null) {
					sb.append(tmpStr);
				}
				// 将配置转换成doc文档
				doc = XMLParse.jdomXmlToDoc(sb.toString());

				String type = form.getType();
				if (StringUtils.isNotBlank(type)) {
					if (Constant.TAB_VIEWPAGE.equals(type)) {
						updateViewPageColumnChange(doc, column, form.getId());
					} else if (Constant.TAB_LISTPAGE.equals(type)) {
						updateListPageColumnChange(doc, column, form.getId());
					} else if (Constant.TAB_EDITPAGE.equals(type)) {
						updateEditPageColumnChange(doc, column, form.getId());
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return true;
	}

	/*
	 * 遍历doc对象，对相应的列进行操作 --编辑页
	 */
	private void updateEditPageColumnChange(Document doc, DataColumn column,
			String formId) {
		// 解析doc
		Element root = doc.getRootElement();

		// 将xml内容输出到控制台
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(Format.getPrettyFormat());
		try {
			// outputter.output(root, new PrintWriter(System.out));// 输出到控制台
		} catch (Exception e) {
			e.printStackTrace();
		}

		XPath xpath = null;
		XPath xpath1 = null;
		Element tableFieldMap = null;
		Element tableFieldDef = null;
		try {
			xpath = XPath.newInstance("DataMapping//ToTable");
			xpath1 = XPath.newInstance("DataMapping/DataSet/Table/FieldDef");
			tableFieldMap = (Element) xpath.selectSingleNode(root);
			tableFieldDef = (Element) xpath1.selectSingleNode(root);
		} catch (JDOMException e1) {
			e1.printStackTrace();
		}

		Element columns = root.getChild("Columns");
		// 将column配置插入
		StringBuffer fieldMapString = new StringBuffer();
		fieldMapString
				.append("<FieldMap type=\"IN\"> <Operand type=\"")
				.append(column.getDataType())
				.append("\"> <OprName type=\"NAME\">")
				.append("<Name>")
				.append(column.getName())
				.append(
						"</Name> </OprName> <OprValue type=\"FIELD\"> <FieldName ")
				.append("type=\"NAME\"> <TableName type=\"NAME\">").append(
						column.getDataTable().getName()).append("<DSName>")
				.append(column.getDataTable().getDataSource().getName())
				.append("</DSName>").append(
						"<STType>TABLE</STType></TableName><Name>").append(
						column.getName()).append("</Name>")// 当前数据对象的类型默认设置为TABLE
				.append("</FieldName> </OprValue> </Operand> </FieldMap>");
		Document fieldMap1 = XMLParse.jdomXmlToDoc(fieldMapString.toString());

		Element sssss = fieldMap1.getRootElement();
		tableFieldMap.addContent((Element) sssss.clone());

		/* <Field> */
		Element field = new Element("Field");
		field.setAttribute("type", "FIX");

		/* <DisplayName> */
		Element displayName = new Element("DisplayName");
		displayName.setText(column.getTitle());

		/* <Description> */
		Element description = new Element("Description");
		description.setText(column.getDescription());

		/* <FromField> */
		Element fromField = new Element("FromField");
		fromField.setAttribute("type", "FIELD");
		fromField.addContent(new Element("Type").setText(column.getDataType()));
		fromField.addContent(new Element("Length").setText(column
				.getDataLength()
				+ ""));
		if ("1".equals(column.getIsPrimaryKey())) {
			fromField.addContent(new Element("IsPrimeKey").setText("TRUE"));
		} else {
			fromField.addContent(new Element("IsPrimeKey").setText("FALSE"));
		}
		if ("1".equals(column.getNullable())) {
			fromField.addContent(new Element("IfNull").setText("Y"));
		} else {
			fromField.addContent(new Element("IfNull").setText("N"));
		}

		/* <FieldName> */
		Element fromFileName = new Element("FieldName");
		fromFileName.setAttribute("type", "NAME");

		Element fieldTableName = new Element("TableName").setAttribute("type",
				"NAME").setText(column.getDataTable().getName()).addContent(
				new Element("DSName").setText(column.getDataTable()
						.getDataSource().getName())).addContent(
				new Element("STType").setText("TABLE"));// TODO 现默认设置为TABLE

		fromFileName.addContent(fieldTableName);
		fromFileName.addContent(new Element("Index").setText("0"));
		fromFileName.addContent(new Element("Name").setText(column.getName()));

		fromField.addContent(fromFileName);
		/* <ToField> */
		Element tofield = new Element("ToField");

		tofield.addContent(new Element("Index").setText("0"));
		tofield.addContent(new Element("Name").setText(column.getName()));
		tofield.addContent(new Element("Type").setText(column.getDataType()));
		tofield.addContent(new Element("Length").setText(column.getDataLength()
				+ ""));

		field.addContent(displayName);
		field.addContent(description);
		field.addContent(fromField);
		field.addContent(tofield);

		tableFieldDef.addContent(field);

		/* <Column> */
		Element column1 = new Element("Column");

		/* <Text> */
		Element text = new Element("Text");
		text.setAttribute("id", "");
		text.setAttribute("name", column.getTitle());
		text.setAttribute("align", "");
		text.setAttribute("visible", "true");
		text.setAttribute("style", "");
		text.setAttribute("sortIndex", "0");
		text.setAttribute("event", "");
		text.setAttribute("readOnly", "false");
		text.setAttribute("groupId", "");
		text.setAttribute("exclusiveLine", "false");
		/* 是否设置变量 */
		text.setAttribute("variantOrnot", "");
		text.setAttribute("variantType", "0");
		text.setAttribute("variantValue", "");
		/*
		 * text.setAttribute("dateformat", ""); text.setAttribute("rows", "");
		 * text.setAttribute("cols", "");
		 */

		/*
		 * <Column> <Text id="" name="test1" align="" visible="true" style=""
		 * sortIndex="0" event="" readOnly="false" groupId=""
		 * exclusiveLine="false" /> <Data id="" name="test1" type=""
		 * dictionaryId="" style="" event="" fieldDataType="varchar"
		 * primaryKey="false" formula="" dictionary="" needs="false" />
		 * <EditMode id="" data="" reminder="" required="" validateRule=""
		 * maxLength="" /> </Column>
		 */

		/* <Data> */
		Element data = new Element("Data");
		data.setAttribute("id", "");
		data.setAttribute("name", column.getName());
		data.setAttribute("type", "");
		data.setAttribute("dictionaryId", "");
		data.setAttribute("style", "");
		data.setAttribute("event", "");
		data.setAttribute("fieldDataType", column.getDataType());
		data.setAttribute("primaryKey", "false");
		data.setAttribute("formula", "");
		data.setAttribute("dictionary", "");
		/* data.setAttribute("dataFunction", ""); */
		data.setAttribute("needs", "false");

		/* <EditMode> */
		Element editMode = new Element("EditMode");
		editMode.setAttribute("id", "");
		editMode.setAttribute("data", "");
		editMode.setAttribute("reminder", "");
		editMode.setAttribute("required", "");
		editMode.setAttribute("validateRule", "");
		editMode.setAttribute("maxLength", "");

		/* <RulesEngin> */
		/*
		 * Element rulesEngin = new Element("RulesEngin");
		 * rulesEngin.setAttribute("rulesService", "");
		 * rulesEngin.setAttribute("rulesRowsParmer", "");
		 * 
		 * column1.addContent(rulesEngin);
		 */
		column1.addContent(text);
		column1.addContent(data);
		column1.addContent(editMode);

		columns.addContent(column1);

		// 将xml内容输出到控制台
		/*
		 * XMLOutputter outputter1 = new XMLOutputter();
		 * outputter1.setFormat(Format.getPrettyFormat()); try {
		 * outputter1.output(root, new PrintWriter(System.out));// 输出到控制台 }
		 * catch (IOException e) { e.printStackTrace(); }
		 */
		// 将数据存入库
		updateFormSettingById(doc, formId);
	}

	/*
	 * 遍历doc对象，对相应的列进行操作 --查看页
	 */
	private void updateViewPageColumnChange(Document doc, DataColumn column,
			String formId) {

		Element root = doc.getRootElement();

		XPath fieldPath = null;
		Element fieldElement = null;
		Element columnElement = root.getChild("Columns");
		try {
			fieldPath = XPath.newInstance("DataMapping/DataSet/Table/FieldDef");
			fieldElement = (Element) fieldPath.selectSingleNode(root);
		} catch (JDOMException e1) {
			e1.printStackTrace();
		}

		/*
		 * 添加field <Field type="FIX"> <DisplayName>订单ID</DisplayName>
		 * <Description>订单ID</Description> <FromField type="FIELD">
		 * <Type>varchar</Type> <Length>100</Length> <IsPrimeKey>TRUE</IsPrimeKey>
		 * <IfNull>N</IfNull> <FieldName type="NAME"> <TableName type="NAME">
		 * CRM_ORDERS <DSName>CRM</DSName> <STType>TABLE</STType> </TableName>
		 * <Index>0</Index> <Name>order_id</Name> </FieldName> </FromField>
		 * <ToField> <Index>0</Index> <Name>order_id</Name> <Type>varchar</Type>
		 * <Length>100</Length> </ToField> </Field>
		 */

		StringBuffer fieldStr = new StringBuffer();
		fieldStr.append("<Field type=\"FIX\">").append("<DisplayName>").append(
				column.getTitle()).append("</DisplayName>").append(
				"<Description>").append(column.getDescription()).append(
				"</Description>").append("<FromField type=\"FIELD\">").append(
				"<Type>").append(column.getDataType()).append("</Type>")
				.append("<Length>").append(column.getDataLength()).append(
						"</Length>").append("<IsPrimeKey>FASLE</IsPrimeKey>");// 默认设置为false
		if ("".equals(fieldStr)) {
			fieldStr.append("<IfNull>Y</IfNull>");// 默认设置为false？？？？？？
		} else {
			fieldStr.append("<IfNull>N</IfNull>");
		}
		fieldStr.append("<FieldName type=\"NAME\">").append(
				"<TableName type=\"NAME\">").append(
				column.getDataTable().getName()).append("<DSName>").append(
				column.getDataTable().getDataSource().getName()).append(
				"</DSName>").append("<STType>TABLE</STType>").append(
				"</TableName>").append("<Index>0</Index>").append("<Name>")
				.append(column.getName()).append("</Name>").append(
						"</FieldName>").append("</FromField>").append(
						"<ToField>").append("<Index>0</Index>")
				.append("<Name>").append(column.getName()).append("</Name>")
				.append("<Type>").append(column.getDataType())
				.append("</Type>").append("<Length>").append(
						column.getDataLength()).append("</Length>").append(
						"</ToField>").append("</Field>");

		Document fieldDoc = XMLParse.jdomXmlToDoc(fieldStr.toString());
		fieldElement.addContent((Element) fieldDoc.getRootElement().clone());

		/*
		 * 添加column <Column> <Text id="" name="订单号" align="" visible="true"
		 * style="" sortIndex="2" event="" readOnly="true" groupId=""
		 * exclusiveLine="false"> </Text> <Data id="" name="order_code" type=""
		 * dictionaryId="" style="" event="" fieldDataType="varchar"
		 * primaryKey="false" dictionary="" formula="" dataFunction=""
		 * needs="false"> </Data> </Column>
		 */
		StringBuffer columnBuffer = new StringBuffer();

		columnBuffer
				.append("<Column>")
				.append("<Text id=\"\" name=\"")
				.append(column.getTitle())
				.append("\" align=\"\" visible=\"true\" style=\"\" ")
				.append(
						"sortIndex=\"0\" event=\"\" readOnly=\"false\" groupId=\"\" ")
				.append("exclusiveLine=\"false\">")
				.append("</Text>")
				.append("<Data id=\"\" name=\"")
				.append(column.getName())
				.append("\" type=\"\" dictionaryId=\"\" ")
				.append("style=\"\" event=\"\" fieldDataType=\"")
				.append(column.getDataType())
				.append("\" primaryKey=\"false\" ")
				.append(
						"dictionary=\"\" formula=\"\" dataFunction=\"\" needs=\"false\">")
				.append("</Data>").append("</Column> ");

		Document columnDoc = XMLParse.jdomXmlToDoc(columnBuffer.toString());
		columnElement.addContent((Element) columnDoc.getRootElement().clone());

		// 将数据存入库
		updateFormSettingById(doc, formId);
	}

	/*
	 * 遍历doc对象，对相应的列进行操作 --列表页 在添加节点时，首先要判断是不是已经有相应的节点存在。
	 */
	private void updateListPageColumnChange(Document doc, DataColumn column,
			String formId) {

		Element root = doc.getRootElement();

		XPath fieldPath = null;
		Element fieldElement = null;
		XPath queryPath = null;
		Element queryElement = null;
		Element columnElement = root.getChild("Columns");
		try {
			fieldPath = XPath.newInstance("DataMapping/DataSet/Table/FieldDef");
			fieldElement = (Element) fieldPath.selectSingleNode(root);

			queryPath = XPath.newInstance("/Form/QueryZone");
			queryElement = (Element) queryPath.selectSingleNode(root);
		} catch (JDOMException e1) {
			e1.printStackTrace();
		}

		/*
		 * 添加field <Field type="FIX"> <DisplayName>销售代表</DisplayName>
		 * <Description>销售代表</Description> <FromField type="FIELD">
		 * <Type>varchar</Type> <Length>50</Length> <IsPrimeKey>FALSE</IsPrimeKey>
		 * <IfNull>Y</IfNull> <FieldName type="NAME"> <TableName type="NAME">
		 * CRM_ORDERS <DSName>CRM</DSName> <STType>TABLE</STType> </TableName>
		 * <Index>0</Index> <Name>emlpoyee_id</Name> </FieldName> </FromField>
		 * <ToField> <Index>0</Index> <Name>emlpoyee_id</Name> <Type>varchar</Type>
		 * <Length>50</Length> </ToField> </Field>
		 */
		StringBuffer fieldStr = new StringBuffer();
		fieldStr.append("<Field type=\"FIX\">").append("<DisplayName>").append(
				column.getTitle()).append("</DisplayName>")
				.append("<Description>")
				.append(column.getDescription())
				.append("</Description>")
				.append("<FromField type=\"FIELD\">")
				.append("<Type>")
				.append(column.getDataType())
				.append("</Type>")
				.append("<Length>")
				.append(column.getDataLength())
				.append("</Length>")
				.append("<IsPrimeKey>FASLE</IsPrimeKey>")
				// 默认设置为false
				.append("<IfNull>N</IfNull>")
				// 默认设置为false？？？？？？
				.append("<FieldName type=\"NAME\">").append(
						"<TableName type=\"NAME\">").append(
						column.getDataTable().getName()).append("<DSName>")
				.append(column.getDataTable().getDataSource().getName())
				.append("</DSName>").append("<STType>TABLE</STType>").append(
						"</TableName>").append("<Index>0</Index>").append(
						"<Name>").append(column.getName()).append("</Name>")
				.append("</FieldName>").append("</FromField>").append(
						"<ToField>").append("<Index>0</Index>")
				.append("<Name>").append(column.getName()).append("</Name>")
				.append("<Type>").append(column.getDataType())
				.append("</Type>").append("<Length>").append(
						column.getDataLength()).append("</Length>").append(
						"</ToField>").append("</Field>");

		Document fieldDoc = XMLParse.jdomXmlToDoc(fieldStr.toString());
		fieldElement.addContent((Element) fieldDoc.getRootElement().clone());

		/*
		 * 添加querycolumn <QueryColumn id="" type="4" tableName="CRM_ORDERS"
		 * name="send_time" text="配送日期" fieldDataType="varchar" visible="true"
		 * readOnly="false" cssClass="" sortIndex="5" dictionaryId="" formula=""
		 * align="" exclusiveLine="false" operateType="6" style=""> <EditMode
		 * id="" data="" reminder="" validateRule=""></EditMode> </QueryColumn>
		 */
		StringBuffer queryBuffer = new StringBuffer();

		queryBuffer
				.append("<QueryColumn id=\"\" type=\"4\" tableName=\"")
				.append(column.getDataTable().getName())
				.append("\" ")
				.append("name=\"")
				.append(column.getName())
				.append("\" text=\"")
				.append(column.getTitle())
				.append("\" fieldDataType=\"")
				.append(column.getDataType())
				.append("\" visible=\"true\" ")
				.append(
						"readOnly=\"false\" cssClass=\"\" sortIndex=\"5\" dictionaryId=\"\" formula=\"\" ")
				.append(
						"align=\"\" exclusiveLine=\"false\" operateType=\"6\" style=\"\"> ")
				.append(
						"<EditMode id=\"\" data=\"\" reminder=\"\" validateRule=\"\"></EditMode>")
				.append("</QueryColumn>");

		Document queryDoc = XMLParse.jdomXmlToDoc(queryBuffer.toString());
		queryElement.addContent((Element) queryDoc.getRootElement().clone());
		/*
		 * 添加column
		 * 
		 * <Column> <Text id="" name="工作流字段" align="" dataFormat=""
		 * visible="false" style="" sort="16"> </Text> <Data id=""
		 * name="ENV_DATAMETER" dictionaryId="" fieldDataType="nvarchar"
		 * primaryKey="false" formula=""> </Data> </Column>
		 */

		StringBuffer columBuffer = new StringBuffer();

		columBuffer
				.append("<Column>")
				.append("<Text id=\"\" name=\"")
				.append(column.getTitle())
				.append("\" align=\"\" dataFormat=\"\" style=\"\" ")
				.append("sort=\"0\" visible=\"true\"></Text> ")
				.append("<Data id=\"\" name=\"")
				.append(column.getName())
				.append("\" type=\"\" dictionaryId=\"\" ")
				.append("fieldDataType=\"")
				.append(column.getDataType())
				.append("\" primaryKey=\"false\" formula=\"\"></Data></Column>");

		Document columnDoc = XMLParse.jdomXmlToDoc(columBuffer.toString());
		columnElement.addContent((Element) columnDoc.getRootElement().clone());
		// 将数据存入库
		updateFormSettingById(doc, formId);
	}

	/*
	 * 查询数据对象下需要修改的表单
	 */
	public boolean updateAllFormBydeleteField(String dataObjectId,
			String fieldName) {
		/**
		 * 查询数据对象下的所有表单
		 */
		String queryString = " from Form t where t.dataTable.id = '"
				+ dataObjectId + "'  order by t.sortIndex ";
		List formList = formDao.find(queryString);// 判断是不是有doc文档

		Document document = null;
		for (int i = 0; i < formList.size(); i++) {
			Form form = (Form) formList.get(i);
			try {
				Blob b = form.getSettings();
				// byte[] byteArray = b.getBytes(1, (int) b.length());
				String tmpStr = "";
				StringBuffer sb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader(b
						.getBinaryStream()));
				while ((tmpStr = br.readLine()) != null) {
					sb.append(tmpStr);
				}
				// 将配置转换成doc文档
				document = XMLParse.jdomXmlToDoc(sb.toString());

				if (StringUtils.isNotBlank(form.getType())) {
					if (Constant.TAB_VIEWPAGE.equals(form.getType())) {
						updateViewFormBydeleteField(document, fieldName, form
								.getId());
					} else if (Constant.TAB_LISTPAGE.equals(form.getType())) {
						updateListFormBydeleteField(document, fieldName, form
								.getId());
					} else if (Constant.TAB_EDITPAGE.equals(form.getType())) {
						updateEditFormBydeleteField(document, fieldName, form
								.getId());
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	/*
	 * 方法描述 通过字段名 更新编辑表单
	 */
	private boolean updateEditFormBydeleteField(Document doc, String fieldName,
			String formId) {
		Element root = doc.getRootElement();

		XPath xpath = null;
		XPath xpath1 = null;
		XPath columnsPath = null;
		List fieldMap = null;
		List fieldDef = null;
		Element columns = null;
		try {
			// 删除FieldMap
			xpath = XPath.newInstance("DataMapping//ToTable/FieldMap");
			fieldMap = xpath.selectNodes(root);
			if (fieldMap != null && fieldMap.size() > 0) {
				for (int i = 0; i < fieldMap.size(); i++) {
					Element item = (Element) fieldMap.get(i);
					Element textElement = (Element) XPath.selectSingleNode(
							item, "Operand/OprName/Name");
					if (StringUtils.isNotBlank(textElement.getText())
							&& textElement.getText().equals(fieldName)) {
						item.getParentElement().removeContent(item);
					}
				}
			}
			// 将xml内容输出到控制台
			// XMLOutputter outputter = new XMLOutputter();
			// outputter.setFormat(Format.getPrettyFormat());
			// try {
			// outputter.output(root, new PrintWriter(System.out));// 输出到控制台
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// 删除Field
			xpath1 = XPath
					.newInstance("DataMapping/DataSet/Table/FieldDef/Field");
			fieldDef = xpath1.selectNodes(root);
			if (fieldDef != null && fieldDef.size() > 0) {
				for (int i = 0; i < fieldDef.size(); i++) {
					Element item = (Element) fieldDef.get(i);
					Element textElement = (Element) XPath.selectSingleNode(
							item, "FromField/FieldName/Name");
					if (StringUtils.isNotBlank(textElement.getText())
							&& textElement.getText().equals(fieldName)) {
						item.getParentElement().removeContent(item);
					}
				}
			}

			// 删除column
			columnsPath = XPath.newInstance("/Form/Columns/Column/Data[@name='"
					+ fieldName + "']");
			columns = (Element) columnsPath.selectSingleNode(root);
			if (columns != null) {
				columns.getParentElement().getParentElement().removeContent(
						columns.getParentElement());
			}

		} catch (JDOMException e1) {
			e1.printStackTrace();
		}

		updateFormSettingById(doc, formId);// 更新数据库
		return false;
	}

	/*
	 * 方法描述 通过字段名 更新列表表单
	 */
	private boolean updateListFormBydeleteField(Document doc, String fieldName,
			String formId) {

		Element root = doc.getRootElement();

		XPath queryXpath = null;
		XPath xpath1 = null;
		XPath columnsPath = null;
		Element query = null;
		List fieldDef = null;
		Element columns = null;
		try {
			// 删除QueryColumn
			queryXpath = XPath
					.newInstance("/Form/QueryZone/QueryColumn[@name='"
							+ fieldName + "']");
			query = (Element) queryXpath.selectSingleNode(root);
			query.getParentElement().removeContent(query);

			// 删除Field
			xpath1 = XPath
					.newInstance("DataMapping/DataSet/Table/FieldDef/Field");
			fieldDef = xpath1.selectNodes(root);
			if (fieldDef != null && fieldDef.size() > 0) {
				for (int i = 0; i < fieldDef.size(); i++) {
					Element item = (Element) fieldDef.get(i);
					Element textElement = (Element) XPath.selectSingleNode(
							item, "FromField/FieldName/Name");
					if (StringUtils.isNotBlank(textElement.getText())
							&& textElement.getText().equals(fieldName)) {
						item.getParentElement().removeContent(item);
					}
				}
			}

			// 删除column
			columnsPath = XPath.newInstance("/Form/Columns/Column/Data[@name='"
					+ fieldName + "']");
			columns = (Element) columnsPath.selectSingleNode(root);
			if (columns != null) {
				columns.getParentElement().getParentElement().removeContent(
						columns.getParentElement());
			}
		} catch (JDOMException e1) {
			e1.printStackTrace();
		}

		updateFormSettingById(doc, formId);// 更新数据库
		return false;
	}

	/*
	 * 方法描述 通过字段名 更新详情页表单
	 */

	private boolean updateViewFormBydeleteField(Document doc, String fieldName,
			String formId) {

		Element root = doc.getRootElement();

		XPath xpath = null;
		XPath xpath1 = null;
		XPath columnsPath = null;
		List fieldMap = null;
		List fieldDef = null;
		Element columns = null;
		try {
			// 删除Field
			xpath1 = XPath
					.newInstance("DataMapping/DataSet/Table/FieldDef/Field");
			fieldDef = xpath1.selectNodes(root);
			if (fieldDef != null && fieldDef.size() > 0) {
				for (int i = 0; i < fieldDef.size(); i++) {
					Element item = (Element) fieldDef.get(i);
					Element textElement = (Element) XPath.selectSingleNode(
							item, "FromField/FieldName/Name");
					if (StringUtils.isNotBlank(textElement.getText())
							&& textElement.getText().equals(fieldName)) {
						item.getParentElement().removeContent(item);
					}
				}
			}

			// 删除column
			columnsPath = XPath.newInstance("/Form/Columns/Column/Data[@name='"
					+ fieldName + "']");
			columns = (Element) columnsPath.selectSingleNode(root);
			if (columns != null) {
				columns.getParentElement().getParentElement().removeContent(
						columns.getParentElement());
			}
		} catch (JDOMException e1) {
			e1.printStackTrace();
		}

		updateFormSettingById(doc, formId);// 更新数据库
		return false;
	}

	/*
	 * public String doc2String(Document doc) { String str = null;
	 * 
	 * try{ TransformerFactory factory = TransformerFactory.newInstance();
	 * Transformer trans = factory.newTransformer();
	 * trans.setOutputProperty(OutputKeys.ENCODING, "gb2312"); // 处理汉字编码
	 * 
	 * ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); StreamResult
	 * strOut = new StreamResult(byteOut);
	 * 
	 * Source xmlSource = new DOMSource(doc);
	 * 
	 * trans.transform(xmlSource, strOut);
	 * 
	 * str = strOut.toString(); }catch(Exception e){ e.printStackTrace(); }
	 * 
	 * return str; }
	 */

	/*
	 * 更新数据库表单xml
	 */
	public boolean updateFormSettingById(Document document, String formId) {

		String formSetXML = "";
		byte[] buffer = null;
		Form form = null;
		/**
		 * 转换字符串
		 */
		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.setFormat(Format.getCompactFormat());

		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		/**
		 * 转换xml字符串
		 */
		try {
			xmlOut.output(document, out);
			buffer = out.toByteArray();

			formSetXML = new String(buffer, "utf-8");
			formSetXML = StringFormat.replaceBlank(formSetXML);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 保存字节数组
		 */
		form = findById(formId);
		buffer = formSetXML.getBytes();
		form.setSettings(Hibernate.createBlob(buffer));
		formDao.update(form);
		/*
		 * byte[] byteArray = null; try { byteArray =
		 * form.getSettings().getBytes(1, (int) form.getSettings().length()); }
		 * catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } String formSettings = new String(byteArray);
		 * log.info("formSettings: "+formSettings);
		 */
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.form.service.IFormService#updatePageService(java.lang.String)
	 */
	public void updatePageService(String dataObjectId) {
		String queryString = " from Form t where t.dataTable.id = '"
				+ dataObjectId + "'  order by t.sortIndex ";
		List formList = formDao.find(queryString);// 判断是不是有doc文档

		for (int i = 0; i < formList.size(); i++) {
			Form form = (Form) formList.get(i);
			pageService.update_service(form.getId());
		}
	}

	public IDataColumnService getDataColumnService() {
		return dataColumnService;
	}

	public void setDataColumnService(IDataColumnService dataColumnService) {
		this.dataColumnService = dataColumnService;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

}
