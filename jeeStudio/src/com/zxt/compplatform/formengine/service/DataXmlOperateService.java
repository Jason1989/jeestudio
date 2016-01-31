package com.zxt.compplatform.formengine.service;

import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.zxt.compplatform.formengine.entity.dataset.DefineFileVO;
import com.zxt.compplatform.formengine.entity.dataset.PageVO;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.dataset.WhereVO;
import com.zxt.compplatform.formengine.entity.view.Layout;
import com.zxt.compplatform.formengine.entity.view.QueryZone;

/**
 * xml业务操作接口
 * 
 * @author 007
 * 
 */
public class DataXmlOperateService implements IDataXmlOperateService {

	public DataXmlOperateService() {
	}

	/**
	 * 数据库连接
	 */
	private Connection conn;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 执行方法
	 */
	public String analysisFile(DefineFileVO df) throws Exception {
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(new File(df.getDataFileName()));
		ResolveObjectDefService re = new ResolveObjectDefService();
		StringBuffer strbufer = new StringBuffer();
		String str = null;
		String fac = null;
		String lay = null;
		String columns = null;
		String json = null;
		Map sql = null;

		/*
		 * Map map = re.resolveObject(doc, new PageVO()); PageVO listvo =
		 * (PageVO) map.get("viewList"); List listColumnsList =
		 * listvo.getListColumnsList(); List titleColumnList =
		 * listvo.getTitleColumnList(); List layoutList =
		 * listvo.getLayoutList(); List buttonList = listvo.getButtonList();
		 * List tableList = listvo.getTableLists();
		 */

		// if (layoutList != null && layoutList.size() > 0) {
		// 拼接layout
		// lay = this.spellLlayout(listvo, layoutList);
		// lay = layout.get("12");
		// System.out.println(layl);
		// }
		String firstDiv = this.frirstDiv();
		// if (facList != null && facList.size() > 0) {
		// //
		// fac = this.spellQuery(listvo, facList);
		// // System.out.println(fac);
		//
		// }
		String fieldset = this.DataQuery();
		String div = this.spellDiv();
		String divFit = this.spellDiv();
		// if (commsList != null && commsList.size() > 0) {
		// // 拼接columns
		// columns = this.spellViewList(listvo, ListColumsList, textList,
		// toolbarList);
		// // System.out.println(columns);
		// }
		/*
		 * if (tableList != null && tableList.size() > 0) { json =
		 * this.spellJosn(tableList); }
		 */
		strbufer.append(lay).append(firstDiv).append(fac).append("\n").append(
				fieldset).append("\n").append(div).append(divFit).append("\n")
				.append(json).append("\n").append(columns).append("</script>");
		str = strbufer.toString();
		return str;

	}

	/**
	 * 拼接 ViewList columns
	 * 
	 * @param listvo
	 * @param commsList
	 * @return
	 * @throws Exception
	 */
	// public String spellViewList(PageVO listvo, List commsList, List textList,
	// List toolbarList)
	// throws Exception {
	// StringBuffer buffer = new StringBuffer();
	// // buffer.append("$(function(){ ").append("\n");
	// buffer.append("$('#ss').datagrid({").append("\n");
	// buffer.append("height:
	// 300,").append("\n").append("width:800,").append("\n").append("nowrap:
	// false,")
	// .append("\n");
	// buffer.append("striped: true,").append("\n").append("autoFit:
	// true,").append("\n");
	// buffer.append(" sortName: 'code',").append("\n");
	// buffer.append("sortOrder: 'desc',").append("\n").append("idField:
	// 'code',").append("\n");
	// buffer.append(" frozenColumns: [[{").append("\n");
	// buffer.append(" field: 'ck', ").append("\n").append(" checkbox:
	// true").append("\n");
	// buffer.append(" }]],").append("\n");
	// // code', title: ' ', width: 50, align:'center', sortable: true }]],");
	// buffer.append("columns: [[").append("\n");
	// for (int i = 1; i < commsList.size(); i++) {
	// ColumnVO columnvo = (ColumnVO) commsList.get(i);
	// TextVO textvo = (TextVO) textList.get(i);
	// if (columnvo.getName() != null) {
	// buffer.append(" {field: '").append(columnvo.getName()).append("',
	// ").append("\n");
	// }
	// if (textvo.getName() != null) {
	// buffer.append(" title: '").append(textvo.getName()).append("',
	// ").append("\n").append(
	// "width: ").append(columnvo.getWidth()).append(", ").append("\n");
	// }
	// if (textvo.getTextAlign() != null) {
	// buffer.append("align: '").append(textvo.getTextAlign()).append("',
	// ").append("\n");
	// }
	// if (textvo.getSort() != null) {
	// buffer.append("sortable: ").append(textvo.getSort()).append("\n");
	// }
	// if(columnvo.getBackgroundcolor()!=null){
	// buffer.append(" field:
	// '").append(columnvo.getBackgroundcolor()).append("', " );
	// }
	// buffer.append("align:'").append("center").append("', ");
	// if(columnvo.getRowspan()!=null){
	// buffer.append("rowspan:").append("
	// ").append(columnvo.getRowspan()).append(" ");
	// }
	// if (i == commsList.size() - 1) {
	// buffer.append("}");
	// } else {
	// buffer.append("}").append(",");
	// }
	// }
	// buffer.append("]],").append("\n");
	// buffer.append("pagination: true,").append("\n");
	// buffer.append(" rownumbers: true,").append("\n");
	// buffer.append("singleSelect: true").append("\n");
	// buffer.append("});").append("\n");
	// buffer.append("$('#ss').datagrid(\"loadData\",cacheData)").append("\n");
	// buffer.append("toolbar: ['-', {").append("\n");
	// for (int j = 0; j < toolbarList.size(); j++) {
	// ToolbarVO toolbarvo = (ToolbarVO) toolbarList.get(j);
	// if (toolbarvo.getName() != null) {
	// buffer.append("text:
	// ").append(toolbarvo.getName()).append(",").append("\n");
	// buffer.append("
	// iconCls:").append(toolbarvo.getIconCls()).append(",").append("\n");
	// if(toolbarvo.getIconCls().equals("icon-save")||toolbarvo.getIconCls().equals("icon-add")||toolbarvo.getIconCls().equals("icon-search")){
	// buffer.append(" handler: function(){").append("\n");
	// buffer.append("document.getElementById(\"").append(toolbarvo.getBandId()).append("\")");
	// buffer.append(".style.display = \"\";").append("\n");
	// buffer.append("
	// $('#").append(toolbarvo.getBandId()).append(".window('open');").append("\n");
	// buffer.append("}").append("\n");
	// buffer.append(" }, '-', {").append("\n");
	// }else{
	// buffer.append(" handler: function(){").append("\n");
	// // var selected = $('#org').datagrid('getSelected');
	// buffer.append("var selected =
	// $('").append(toolbarvo.getBandId()).append("')").append("'getSelected'").append("\n");
	// buffer.append("if (selected == null) {").append("\n");
	// 'warning');").append("\n");
	// buffer.append("}").append("\n");
	// buffer.append(" else {").append("\n");
	// buffer.append("}").append("\n");
	// buffer.append("}").append("\n");
	// buffer.append(" }, '-']").append("\n");
	// buffer.append("});").append("\n");
	// buffer.append("});").append("\n");
	// }
	// }
	// }
	// System.out.println(buffer.toString());
	// String columns = buffer.toString();
	// return columns;
	// }
	/**
	 * 拼接layout
	 * 
	 * @param listvo
	 * @param layoutList
	 * @return<div id="designGridMain" class="easyui-accordion" fit="true"
	 *         style="height:200px;">
	 * @throws Exception
	 */
	public String spellLlayout(PageVO listvo, List layoutList) throws Exception {
		Map mapLayOut = new HashMap();
		StringBuffer bufferLayout = new StringBuffer();
		for (int j = 0; j < layoutList.size(); j++) {
			bufferLayout
					.append("<div id=\"designGridMain\" class=\"easyui-accordion\" ");
			Layout layoutvo = (Layout) layoutList.get(j);
			String id = layoutvo.getId();
			// bufferLayout.append("title=\"").append(layoutvo.getLayOutName()).append("\"").append("
			// ");
			// bufferLayout.append("border=\"false\"
			// collapsible=\"true\"").append(" ");
			bufferLayout.append("fit=\"true\"").append(" ");
			bufferLayout.append("style=\"").append(layoutvo.getStyle()).append(
					"\"");
			bufferLayout.append(">").append("\n");
			// String layout = bufferLayout.toString();
			// mapLayOut.put(id, bufferLayout);
		}
		// System.out.println(bufferLayout.toString());
		String layout = bufferLayout.toString();
		return layout;
	}

	/**
	 * 拼接spellQuery
	 * 
	 * @param listvo
	 * @param facList
	 * @return
	 * @throws Exception
	 */
	public String spellQuery(PageVO listvo, List facList) throws Exception {
		StringBuffer bufferFac = new StringBuffer();
		bufferFac.append(" <fieldset>").append("\n");
		bufferFac.append("<table>").append("\n").append("<tr>").append("\n");
		for (int k = 0; k < facList.size(); k++) {
			QueryZone faclistvo = (QueryZone) facList.get(k);
			bufferFac.append("<td><font size=\"2\">").append(
					faclistvo.getName()).append(":</font></td>").append("\n");
			if (faclistvo.getType().equals("easyui-combotree")) {
				bufferFac.append("<td><select id=\"");
				bufferFac.append(faclistvo.getId()).append("\"").append(" ");
				bufferFac.append("class=\"").append(faclistvo.getType())
						.append("\"");
				bufferFac
						.append(" ")
						.append("url=\"")
						.append(faclistvo)
						.append(" ")
						.append(
								"\"style=\"width:200px;\" required=\"true\"></select>");
				bufferFac.append("</td>").append("\n");
			} else if (faclistvo.getType().equals("easyui-datebox")) {
				bufferFac.append("<td><input id=\"").append(faclistvo.getId())
						.append("\"").append(" ");
				bufferFac.append("class=\"").append(faclistvo.getType())
						.append("\"").append("required=\"true\"></input></td>");
			} else if (faclistvo.getType().equals("")) {

			}
		}
		bufferFac.append("<td align='right'>");
		bufferFac
				.append("\n")
				.append(
						" <a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"icon-search\">查询</a>");
		bufferFac.append("</td>").append("\n").append("</tr>").append("\n")
				.append("</table>").append("\n").append("</fieldset>");
		String buf = bufferFac.toString();
		// System.out.println(buf);
		return buf;
	}

	/**
	 * 拼接Function 绑定js
	 * 
	 * @param listvo
	 * @param funList
	 * @return mapFunction
	 * @throws Exception
	 */
	public String spellFunction(PageVO listvo, List funList) throws Exception {
		StringBuffer buffer = new StringBuffer();

		return buffer.toString();
	}

	/**
	 * 在表中查找js
	 * 
	 * @param functionId
	 * @return
	 * @throws Exception
	 */
	// public List queryFunctionJs(FunctionVO functionId) throws Exception {
	//
	// List functionList = new ArrayList();
	//
	// return functionList;
	// }
	/**
	 * 拼接text
	 * 
	 * @param listvo
	 * @param textsList
	 * @return
	 * @throws Exception
	 */
	public String spellText(PageVO listvo, List textsList) throws Exception {
		StringBuffer bufferText = new StringBuffer();

		return null;
	}

	/**
	 * 拼接json
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String spellJosn(List tableList) throws Exception {
		Map map = this.spellSql(tableList);

		StringBuffer jsonBuffer = new StringBuffer("<script>").append("\n");
		jsonBuffer.append("var cacheData = {").append("\n").append(
				"\"total\":10,").append("\n");
		jsonBuffer.append("\"rows\":[").append("\n");
		Iterator it = map.entrySet().iterator();
		for (int i = 0; i < map.size(); i++) {
			jsonBuffer.append("{\"code\":\"").append(map.get("columnCode"))
					.append("\",");
			jsonBuffer.append("\"columnName\":\"")
					.append(map.get("columnName")).append("\",");
			jsonBuffer.append("\"columnDataType\":\"").append(
					map.get("columnDataType")).append("\"");
			if (i == map.size() - 1) {
				jsonBuffer.append("}");
			} else {
				jsonBuffer.append("}").append(",");
			}
		}
		jsonBuffer.append("]").append("\n").append("}");
		String str = jsonBuffer.toString();
		return str;

	}

	/**
	 * 拼接sql
	 * 
	 * @param tableList
	 * @return
	 * @throws Exception
	 */
	public Map spellSql(List tableList) throws Exception {
		Map map = new HashMap();
		List list = new ArrayList();
		StringBuffer strbuffer = new StringBuffer();
		for (int i = 0; i < tableList.size(); i++) {
			TableVO t = (TableVO) tableList.get(i);
			String toWhereString = null;
			List whereList = t.getWhereList();
			List fieldList = t.getFieldList();
			strbuffer.append("select").append(" ").append(t.toFieldString())
					.append(" ");
			strbuffer.append("from ").append(t.toFromString()).append(" ")
					.append("where");
			strbuffer.append(t.toWhereString());
		}
		String sql = strbuffer.toString();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			map.put("columnName", rs.getString("COL_NAME"));
			map.put("columnCode", rs.getString("COL_CODE"));
			map.put("columnDataType", rs.getString("COL_DATATYPE"));
			map.put("columnDefualtValue", rs.getString("COL_DEFAULTVALUE"));
			// listsql.add((rs.getString("COL_DATATYPE")));
		}
		rs.close();
		stmt.close();
		return map;
	}

	/**
	 * 拼接where语句
	 * 
	 * @param whereList
	 * @return
	 * @throws Exception
	 */
	public String toWhereString(List whereList) throws Exception {
		StringBuffer where = new StringBuffer();
		if (whereList != null) {
			for (int i = 0; i < whereList.size(); i++) {
				WhereVO f = (WhereVO) whereList.get(i);
				// where.append(" and " + f.toWhereString());
			}
		}
		return where.toString();
	}

	/**
	 * 拼接div
	 * 
	 * @return
	 * @throws Exception
	 */
	public String spellDiv() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("</div>").append("\n");
		String str = buffer.toString();
		// System.out.println(str);
		return str;
	}

	/**
	 * div开始
	 * 
	 * @return
	 * @throws Exception
	 */
	public String frirstDiv() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<div fit=\"true\" style=\"padding:10px;\">")
				.append("\n");
		String str = buffer.toString();
		// System.out.println(str);
		return str;

	}

	/**
	 * 拼接data
	 * 
	 * @return
	 * @throws Exception
	 */
	public String DataQuery() throws Exception {

		StringBuffer buffer = new StringBuffer();
		buffer.append(" <fieldset>").append("\n");
		buffer.append("<table id=\"ss\" ></table>").append("\n");
		buffer.append("</fieldset>").append("\n");
		String str = buffer.toString();
		// System.out.println(str);
		return str;
	}

	/**
	 * 查找表单定义
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String queryXml(String formId) throws Exception {
		String resBlob = null;
		// formId = "402887cf2b2da1ba012b2db14c1a0001";
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT").append(" ");
		buffer.append("T.STY_CONTENT").append(" ");
		buffer.append("FROM BP_T_FORM_FORMSDEFINITION T").append(" ");
		buffer.append("WHERE FORMS_ID='" + formId + "'");
		Statement stmt = conn.createStatement();
		// System.out.println(buffer.toString());
		ResultSet rs = stmt.executeQuery(buffer.toString());
		while (rs.next()) {
			Blob bcon = rs.getBlob("STY_CONTENT");
			resBlob = new String(bcon.getBytes(1L, (int) bcon.length()));

		}
		return resBlob;
	}
	// public static void main(String[] args) throws Exception {
	// DataXmlOperateService ac = new DataXmlOperateService();
	// DefineFileVO df = new DefineFileVO();
	// df.setDataUrl("");
	// df
	// .setDataFileName("D:\\workspace\\compplatform\\src\\com\\zxt\\compplatform\\formengine\\entity\\xml\\editPage.xml");
	// ac.analysisFile(df);
	// }
}
