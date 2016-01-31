
var fmSelecttableJoinTypeData = [{id:"-1", text:"\u8bf7\u9009\u62e9"}, {id:"INNER", text:"\u5185\u8fde\u63a5"}, {id:"LEFTOUT", text:"\u5de6\u8fde\u63a5"}, {id:"RIGHTOUT", text:"\u53f3\u8fde\u63a5"}];
var fmSelecttableParaObjTypeData = [{id:"0", text:"\u53d8\u91cf"}, {id:"1", text:"\u5e38\u91cf"}, {id:"2", text:"\u5bf9\u8c61"}, {id:"3", text:"\u7cfb\u7edf\u53d8\u91cf"}];
var fmSelecttableParaJoinStyleData = [{id:"AND", text:"AND"}, {id:"OR", text:"OR"}];
var fmSelecttableJoinObjData = new Array();
var fmSelecttableMainObjColumnData = new Array();
var fmSelecttableParaObjData = new Array();
var mainObjectName;
function fmSelecttableFilterJoinAddItem() {
	var f = $("#fm_selecttable_filterjoin_tbody");
	var e = $(f.find("tr").last()).clone();
	var b = false;
	if (e.css("display") == "none") {
		b = true;
		e.css("display", "");
	}
	e.attr("id", "");
	var c = $(e).find("td");
	for (var d = 0; d < c.length; d++) {
		if (d < 6) {
			pageSetElementSerialNumber(c.eq(d).find("select"));
			c.eq(d).find("span").remove();
		}
		if (d == 6) {
			var a = c.eq(d).find("a");
			pageSetElementSerialNumber(a);
			if (b) {
				a.linkbutton();
			}
		}
	}
	e.appendTo(f);
	fmSelecttableFilterJoinInitData(e);
}
function fmSelecttableFilterJoinInitData(d) {
	var f = $(d).find("td select[id^='fm_selecttable_jointype_']");
	f.combobox({valueField:"id", textField:"text", width:60, editable:false, data:fmSelecttableJoinTypeData});
	f.combobox("setValue", "-1");
	var e = $(d).find("td select[id^='fm_selecttable_joinoper_']");
	e.combobox({valueField:"id", textField:"text", width:55, editable:false, data:fmSelecttableJoinOperData});
	e.combobox("setValue", "-1");
	var c = $(d).find("td select[id^='fm_selecttable_joinobj_']");
	var a = $(d).find("td select[id^='fm_selecttable_joinobjcol_']");
	var g = $(d).find("td select[id^='fm_selecttable_mainobj_']");
	var b = $(d).find("td select[id^='fm_selecttable_mainobjcol_']");
	initJoinObjSelectData("", g, b);
	initJoinObjSelectData(b, c, a);
}
function fmSelecttableFilterParamAddItem() {
	var c = $("#fm_selecttable_filterparam_tbody");
	var n = $(c.find("tr").last()).clone();
	var h = false;
	if (n.css("display") == "none") {
		h = true;
		n.css("display", "");
	}
	n.attr("id", "");
	var f = $(n).find("td");
	for (var b = 0; b < f.length; b++) {
		if (b == 0) {
			var m = f.eq(b).find("span");
			m.css("display", "");
			pageSetElementSerialNumber(m);
			pageSetElementSerialNumber(m.find("select"));
			m.find("span").remove();
		}
		if (b == 5) {
			pageSetElementSerialNumber(f.eq(b));
			pageSetElementSerialNumber(f.eq(b).find("input"));
		}
		if (b > 0 && b < 5) {
			pageSetElementSerialNumber(f.eq(b).find("select"));
			f.eq(b).find("span").remove();
		}
		if (b == 6) {
			pageSetElementSerialNumber(f.eq(b));
			pageSetElementSerialNumber(f.eq(b).find("select[id^='fm_selecttable_parajoinobj_']"));
			pageSetElementSerialNumber(f.eq(b).find("select[id^='fm_selecttable_parajoinobjcol_']"));
			f.eq(b).find("span").remove();
		}
		if (b == 7) {
			var i = f.eq(b).find("a");
			pageSetElementSerialNumber(i);
			if (h) {
				i.linkbutton();
			}
		}
	}
	n.appendTo(c);
	var o = $(n).find("td span[id^='fm_selecttable_parajoinstyletd_']");
	o.find("select").combobox({valueField:"id", textField:"text", width:55, editable:false, data:fmSelecttableParaJoinStyleData});
	var k = $(n).find("td select[id^='fm_selecttable_paraoper_']");
	k.combobox({valueField:"id", textField:"text", width:55, editable:false, data:fmSelecttableJoinOperData});
	k.combobox("setValue", "-1");
	var d = $(n).find("td select[id^='fm_selecttable_paraobj_']");
	var l = $(n).find("td select[id^='fm_selecttable_paraobjcol_']");
	var g = $(n).find("td select[id^='fm_selecttable_parajoinobj_']");
	var e = $(n).find("td select[id^='fm_selecttable_parajoinobjcol_']");
	initParaObjSelectData(d, l);
	initParaJoinObjSelectData(g, e);
	var a = $(n).find("td select[id^='fm_selecttable_paraobjtype_']");
	initParaObjTypeSelectData(a);
}
function initSelectTableFilterData() {
	fmSelecttableJoinObjData.length = 0;
	fmSelecttableMainObjColumnData.length = 0;
	var g = $("#fm_formselecttable_tablesYes").datagrid("getRows");
	if (g) {
		for (var c = 0; c < g.length; c++) {
			if (g[c]["id"] == mainObjectId) {
				mainObjectName = g[c]["name"];
			}
			fmSelecttableJoinObjData.push({id:g[c]["name"], text:g[c]["name"]});
		}
	}
	var a = $("#fm_formselecttable_columns").datagrid("getSelections");
	if (a) {
		for (var c = 0; c < a.length; c++) {
			if (a[c]["dataTable"]["id"] == mainObjectId) {
				fmSelecttableMainObjColumnData.push({id:a[c]["name"], text:a[c]["name"]});
			}
		}
	}
	fmSelecttableParaObjData.length = 0;
	var g = $("#fm_formselecttable_tablesYes").datagrid("getRows");
	if (g) {
		for (var c = 0; c < g.length; c++) {
			fmSelecttableParaObjData.push({id:g[c]["name"], text:g[c]["name"]});
		}
	}
	var l = $("#fm_selecttable_mainobj_0");
	var k = $("#fm_selecttable_mainobjcol_0");
	var m = $("#fm_selecttable_joinobj_0");
	var h = $("#fm_selecttable_joinobjcol_0");
	initJoinObjSelectData("", l, k);
	initJoinObjSelectData(k, m, h);
	fmInitParaComboboxData(fmSelecttableJoinObjData, mainObjectName, "fm_selecttable_mainobj_0");
	var d = $("#fm_selecttable_paraobj_0");
	var j = $("#fm_selecttable_paraobjcol_0");
	initParaObjSelectData(d, j);
	var f = $("#fm_selecttable_parajoinobj_0");
	var e = $("#fm_selecttable_parajoinobjcol_0");
	initParaJoinObjSelectData(f, e);
	var b = $("#fm_selecttable_paraobjtype_0");
	initParaObjTypeSelectData(b);
}
function initJoinObjSelectData(c, b, a) {
	a.combobox();
	b.combobox({valueField:"id", textField:"text", data:fmSelecttableJoinObjData, editable:false, onChange:function (f, e) {
		var d = fmSelecttableGridToSelect(f);
		a.combobox({valueField:"id", textField:"text", editable:false, data:d});
		a.combobox("setValue", "");
	}});
}
function initParaObjSelectData(b, a) {
	a.combobox();
	b.combobox({valueField:"id", textField:"text", data:fmSelecttableParaObjData, editable:false, onChange:function (e, d) {
		var c = fmSelecttableGridToSelect(e);
		a.combobox({valueField:"id", textField:"text", data:c, editable:false, onChange:function (i, h) {
			var f = a.attr("id");
			var g = f.substring(f.lastIndexOf("_") + 1);
			if ($("#fm_selecttable_paraobjtype_" + parseInt(g)).combobox("getValue") == "0") {
				$("#fm_selecttable_paravalue_" + parseInt(g)).val(i);
			}
		}});
		a.combobox("setValue", "");
	}});
}
function initParaJoinObjSelectData(b, a) {
	a.combobox();
	b.combobox({valueField:"id", textField:"text", data:fmSelecttableParaObjData, editable:false, onChange:function (e, d) {
		var c = fmSelecttableGridToSelect(e);
		a.combobox({valueField:"id", textField:"text", editable:false, data:c});
		a.combobox("setValue", "");
	}});
}
function initParaObjTypeSelectData(a) {
	a.combobox({valueField:"id", textField:"text", width:55, data:fmSelecttableParaObjTypeData, editable:false, onChange:function (h, d) {
		var i = a.attr("id");
		var c = i.substring(i.lastIndexOf("_") + 1);
		var b = $("#fm_selecttable_parajoinobjtd_" + parseInt(c));
		var e = $("#fm_selecttable_paravaluetd_" + parseInt(c));
		var g = $("#fm_selecttable_paravalue_" + parseInt(c));
		if (h == "2") {
			b.css("display", "");
			e.css("display", "none");
		} else {
			if (h == "0") {
				b.css("display", "none");
				e.css("display", "");
				var f = $("#fm_selecttable_paraobjcol_" + parseInt(c)).combobox("getValue");
				if (f) {
					g.val(f);
					g.attr("readonly", true);
				}
			} else {
				b.css("display", "none");
				e.css("display", "");
				g.val("");
				g.attr("readonly", false);
			}
		}
	}});
}
function fmSelecttableGridToSelect(d) {
	var a = new Array();
	var c = $("#fm_formselecttable_columns").datagrid("getSelections");
	if (c) {
		for (var b = 0; b < c.length; b++) {
			if (c[b]["dataTable"]["name"] == d) {
				a.push({id:c[b]["name"], text:c[b]["name"]});
			}
		}
	}
	return a;
}
function formSelectTablePageInit() {
	$("#fm_formselecttable_tablesYes").datagrid({width:215, height:350, nowrap:false, striped:true, autoFit:true, idField:"id", onLoadSuccess:function (g) {
		$(this).datagrid("selectRecord", mainObjectId);
		if (xmlOperateType == "update" && updateXmlUtils && updateFromTablesNode && updateFromTablesNode != null) {
			var c = updateXmlUtils.getChildNodes(updateFromTablesNode);
			if (c) {
				var b = c.length;
				var f = $(this).datagrid("getRows");
				for (var e = 0; e < b; e++) {
					var h = c[e];
					var a = updateXmlUtils.getChildNodeByTagName(h, "TableName");
					if (f) {
						for (var d = 0; d < f.length; d++) {
							if (updateXmlUtils.getInnerText(a) == f[d]["name"]) {
								$(this).datagrid("selectRecord", f[d]["id"]);
								break;
							}
						}
					}
				}
			}
		}
	}, onSelect:flushFormSelectTableColumnsData, onUnselect:unselectFlushFormSelectTableColumnsData, frozenColumns:[[{field:"ck", checkbox:true}]], columns:[[{field:"name", title:"\u8868\u540d", width:145, sortable:true}]], rownumbers:true});
	setTimeout("fmFormselecttableTablesLoadData()", 1);
	$("#fm_formselecttable_columns").datagrid({width:450, height:377, nowrap:false, striped:true, autoFit:true, idField:"id", frozenColumns:[[{field:"ck", checkbox:true}]], columns:[[{field:"name", title:"\u5217\u540d", width:130, sortable:true}, {field:"title", title:"\u4e2d\u6587\u540d", width:100}, {field:"datatablenname", title:"\u8868\u540d", width:135, sortable:true, formatter:function (b, c) {
		var a = "";
		if (c.dataTable && c.dataTable != null) {
			a = c.dataTable.name;
		}
		return a;
	}}]], rownumbers:true});
	$("#fm_selecttable_filter").appendTo("body").window({title:"\u8fc7\u6ee4\u6761\u4ef6", width:800, modal:true, shadow:false, closed:true, closable:true, collapsible:false, height:200});
	$("#fm_selecttable_filter_joinitemadd").linkbutton();
	$("#fm_selecttable_filter_paraitemdelete_0").linkbutton();
	$("#fm_selecttable_filter_joinitemdelete_0").linkbutton();
	$("#fm_selecttable_filter_paramitemadd").linkbutton();
	$("#fm_selecttable_filter_close").linkbutton();
	$("#fm_selecttable_filter_submit").linkbutton();
	$("#fm_selecttable_jointype_0").combobox({valueField:"id", textField:"text", listWidth:60, editable:false, data:fmSelecttableJoinTypeData});
	$("#fm_selecttable_joinoper_0").combobox({valueField:"id", textField:"text", listWidth:54, editable:false, data:fmSelecttableJoinOperData});
	$("#fm_selecttable_paraoper_0").combobox({valueField:"id", textField:"text", listWidth:54, editable:false, data:fmSelecttableJoinOperData});
	$("#fm_selecttable_parajoinstyle_0").combobox({valueField:"id", textField:"text", editable:false, data:fmSelecttableParaJoinStyleData});
	$("#fm_selecttable_paraobjtype_0").combobox();
	$("#fm_selecttable_joinobj_0").combobox();
	$("#fm_selecttable_mainobjcol_0").combobox();
	$("#fm_selecttable_joinobjcol_0").combobox();
	$("#fm_selecttable_paraobj_0").combobox();
	$("#fm_selecttable_paraobjcol_0").combobox();
	$("#fm_selecttable_parajoinobj_0").combobox();
	$("#fm_selecttable_parajoinobjcol_0").combobox();
	setTimeout("initTableColumnSelectedDataFromXML()", 400);
}
function fmFormselecttableTablesLoadData() {
	$("#fm_formselecttable_tablesYes").datagrid("loadData", fmFormSelectTableTablesData);
}
function initTableColumnSelectedDataFromXML() {
	if (xmlOperateType == "update" && updateXmlUtils && updateTableNode && updateTableNode != null) {
		$("#fm_formselecttable_columns").datagrid("clearSelections");
		var m = false;
		if (updateTableNode.length) {
			if (updateTableNode.length > 0) {
				m = true;
			}
		} else {
			m = true;
		}
		if (m) {
			var c = updateXmlUtils.getChildNodeByTagName(updateTableNode, "FieldDef");
			var g = updateXmlUtils.getChildNodes(c);
			if (g) {
				var k = g.length;
				var n = $("#fm_formselecttable_columns").datagrid("getRows");
				for (var h = 0; h < k; h++) {
					var a = g[h];
					var b = updateXmlUtils.getChildNodeByTagName(a, "FromField");
					var f = updateXmlUtils.getChildNodeByTagName(b, "FieldName");
					var e = updateXmlUtils.getChildNodeByTagName(f, "Name");
					var l = updateXmlUtils.getChildNodeByTagName(f, "TableName");
					if (n) {
						for (var d = 0; d < n.length; d++) {
							if (updateXmlUtils.getInnerText(l) == n[d]["dataTable"]["name"] && updateXmlUtils.getInnerText(e) == n[d]["name"]) {
								$("#fm_formselecttable_columns").datagrid("selectRecord", n[d]["id"]);
								break;
							}
						}
					}
				}
			}
		}
	}
}
function flushFormSelectTableColumnsData(e, d) {
	var b = $("#fm_formselecttable_tablesYes").datagrid("getRows");
	var c = "";
	for (var a = 0; a < b.length; a++) {
		c += c == "" ? "'" + b[a]["id"] + "'" : ",'" + b[a]["id"] + "'";
	}
	$.post("form/form!getColumnInfoListByDoIds.action", {dataTablesIds:c}, function (f) {
		$("#fm_formselecttable_columns").datagrid("clearSelections");
		$("#fm_formselecttable_columns").datagrid("loadData", f);
		$("#fm_formselecttable_columns").datagrid("selectAll");
	});
}
function unselectFlushFormSelectTableColumnsData(b, a) {
	$("#fm_formselecttable_tables").datagrid("selectRecord", mainObjectId);
}
var xmlUtils;
function fmFormSelectTableSubmit() {
	var d = $(":radio[name='form.type'][checked=true]").val();
	var f = 0;
	var a = 0;
	var g = $(":input[name='form.description']").val();
	var c = "<Form version=\"1.0\" title=\"\"  cssClass=\"\" css=\"\" type=\"" + d + "\" rows=\"" + f + "\" cols=\"" + a + "\" description=\"" + g + "\" isUseWorkflow=\"0\" workflowId=\"\"></Form>";
	xmlUtils = new XmlUtils({dataType:"json"});
	xmlUtils.loadXmlString(c);
	var b = xmlUtils.getRoot();
	var e = xmlUtils.createNode("DataMapping", "", {}, b);
	dataObjectSelectedInfo(e, d);
	fmFormToXmlDefaultSelected(b, d);
	xmlUtils.createNode("Tabs", "", {isConfig:"0"}, b);
	xmlUtils.createNode("Params", "", {isConfig:"0"}, b);
	xmlUtils.createNode("Pages", "", {}, b);
	xmlUtils.createNode("Pagination", "", {visible:"false"}, b);
	xmlUtils.createNode("Groups", "", {visible:"false"}, b);
	$(":input[name='formSettings']").val("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlUtils.toString());
	$("#fm_formadd_selecttable").window("close");
	clearFilterJoinTbodyTr();
}
function dataObjectSelectedInfo(n, u) {
	var b = $("#fm_formselecttable_tablesYes").datagrid("getRows");
	if (b && b.length > 0) {
		var m = xmlUtils.createNode("DataSet", "", {name:"DATASET1"}, n);
		xmlUtils.createNode("DisplayName", "DataSet1", {}, m);
		xmlUtils.createNode("Description", "\u6570\u636e\u96c61", {}, m);
		var e = xmlUtils.createNode("DataSources", "", {}, m);
		var c = "";
		if (b[0]["dataSource"]["dbType"] == "1") {
			c = "ORACLE";
		} else {
			if (b[0]["dataSource"]["dbType"] == "2") {
				c = "SQLSERVER";
			}
		}
		var p = xmlUtils.createNode("DataSource", "", {type:"DB", dataType:c}, e);
		xmlUtils.createNode("DSName", b[0]["dataSource"]["name"], {}, p);
		xmlUtils.createNode("DisplayName", b[0]["dataSource"]["name"], {}, p);
		var f = "";
		if (b[0]["type"] == "1") {
			f = "TABLE";
		} else {
			if (b[0]["type"] == "2") {
				f = "VIEW";
			}
		}
		var t = xmlUtils.createNode("Table", "", {type:f, name:"table1"}, m);
		xmlUtils.createNode("DisplayName", "table1", {}, t);
		xmlUtils.createNode("Description", "\u88681", {}, t);
		var a = xmlUtils.createNode("GetTable", "", {type:"COMPONENT"}, t);
		var o;
		if ("editPage" == u) {
			var r = xmlUtils.createNode("SetTable", "", {type:"CUSTOM"}, t);
			var k = xmlUtils.createNode("SetOption", "", {oprRange:"I,U,D"}, r);
			var j = xmlUtils.createNode("SetOptionItem", "", {name:"t", type:"TABLE"}, k);
			xmlUtils.createNode("Index", "0", {}, j);
			o = xmlUtils.createNode("ToTable", "", {type:"CUSTOM"}, j);
		}
		var l = xmlUtils.createNode("FromTables", "", {}, a);
		var h = 1;
		$("#fm_form" + xmlOperateType + "_selectedtables").datagrid("loadData", {total:0, rows:[]});
		for (var q = 0; q < b.length; q++) {
			var g = xmlUtils.createNode("FromTable", "", {}, l);
			var d = xmlUtils.createNode("TableName", b[q]["name"], {type:"NAME"}, g);
			xmlUtils.createNode("DSName", b[q]["dataSource"]["name"], {}, d);
			xmlUtils.createNode("STType", "TABLE", {}, d);
			if (b[q]["id"] == mainObjectId) {
				xmlUtils.createNode("Index", 0, {}, g);
				if ("editPage" == u && o != null) {
					var s = xmlUtils.createNode("TableName", b[q]["name"], {type:"NAME"}, o);
					xmlUtils.createNode("DSName", b[q]["dataSource"]["name"], {}, s);
					xmlUtils.createNode("STType", "TABLE", {}, s);
				}
			} else {
				xmlUtils.createNode("Index", h, {}, g);
				h = h + 1;
			}
			$("#fm_form" + xmlOperateType + "_selectedtables").datagrid("appendRow", b[q]);
		}
		$("#fm_form" + xmlOperateType + "_selectedtables").datagrid("acceptChanges");
		dataColumnSelectedInfo(t, u, o);
		fmJoinConditionAppendToXML(l);
		fmParaConditionAppendToXML(a);
	}
}
function dataColumnSelectedInfo(j, r, m) {
	var f = $("#fm_formselecttable_columns").datagrid("getSelections");
	var d = xmlUtils.createNode("FieldDef", "", {type:"FIX"}, j);
	if (f) {
		$("#fm_form" + xmlOperateType + "_selectedcolumns").datagrid("loadData", {total:0, rows:[]});
		for (var n = 0; n < f.length; n++) {
			var q = xmlUtils.createNode("Field", "", {type:"FIX"}, d);
			xmlUtils.createNode("DisplayName", f[n]["title"], {}, q);
			xmlUtils.createNode("Description", f[n]["description"], {}, q);
			var g = xmlUtils.createNode("FromField", "", {type:"FIELD"}, q);
			xmlUtils.createNode("Type", f[n]["dataType"], {}, g);
			xmlUtils.createNode("Length", f[n]["dataLength"], {}, g);
			if (f[n]["isPrimaryKey"] == "1") {
				xmlUtils.createNode("IsPrimeKey", "TRUE", {}, g);
			} else {
				xmlUtils.createNode("IsPrimeKey", "FALSE", {}, g);
			}
			xmlUtils.createNode("IfNull", f[n]["nullable"], {}, g);
			var k = xmlUtils.createNode("FieldName", "", {type:"NAME"}, g);
			var e = xmlUtils.createNode("TableName", f[n]["dataTable"]["name"], {type:"NAME"}, k);
			xmlUtils.createNode("DSName", f[n]["dataTable"]["dataSource"]["name"], {}, e);
			xmlUtils.createNode("STType", "TABLE", {}, e);
			xmlUtils.createNode("Index", f[n]["sortNo"], {}, k);
			xmlUtils.createNode("Name", f[n]["name"], {}, k);
			var a = xmlUtils.createNode("ToField", "", {}, q);
			xmlUtils.createNode("Index", f[n]["sortNo"], {}, a);
			xmlUtils.createNode("Name", f[n]["name"], {}, a);
			xmlUtils.createNode("Type", f[n]["dataType"], {}, a);
			xmlUtils.createNode("Length", f[n]["dataLength"], {}, a);
			if ("editPage" == r && m != null && f[n]["dataTable"]["id"] == mainObjectId) {
				var c = {type:"IN"};
				if (f[n]["isPrimaryKey"] == "1") {
					c = {type:"KEY"};
				}
				var l = xmlUtils.createNode("FieldMap", "", c, m);
				var h = xmlUtils.createNode("Operand", "", {type:f[n]["dataType"]}, l);
				var p = xmlUtils.createNode("OprName", "", {type:"NAME"}, h);
				xmlUtils.createNode("Name", f[n]["name"], {}, p);
				var s = xmlUtils.createNode("OprValue", "", {type:"FIELD"}, h);
				var b = xmlUtils.createNode("FieldName", "", {type:"NAME"}, s);
				var o = xmlUtils.createNode("TableName", f[n]["dataTable"]["name"], {type:"NAME"}, b);
				xmlUtils.createNode("DSName", f[n]["dataTable"]["dataSource"]["name"], {}, o);
				xmlUtils.createNode("STType", "TABLE", {}, o);
				xmlUtils.createNode("Name", f[n]["name"], {}, b);
			}
			$("#fm_form" + xmlOperateType + "_selectedcolumns").datagrid("appendRow", f[n]);
		}
		$("#fm_form" + xmlOperateType + "_selectedcolumns").datagrid("acceptChanges");
	}
}
function fmFormToXmlDefaultSelected(v, y) {
	var q = xmlUtils.createNode("QueryZone", "", {visible:"false", showType:"0", isConfig:"0"}, v);
	var t = xmlUtils.createNode("Columns", "", {isConfig:"0"}, v);
	var u = xmlUtils.createNode("Buttons", "", {isConfig:"0"}, v);
	var f = "";
	var k = "";
	var j = $("#fm_formselecttable_columns").datagrid("getSelections");
	if (j) {
		var x = 0;
		for (var r = 0; r < j.length; r++) {
			var s = xmlUtils.createNode("Column", "", {}, t);
			var w = "true";
			var a = "true";
			var z = "false";
			if (j[r]["isPrimaryKey"] == "1") {
				f += f == "" ? j[r]["name"] : "," + j[r]["name"];
				k = j[r]["dataType"];
				z = "true";
				w = "false";
				a = "false";
			} else {
				x++;
			}
			var p = "";
			var c = "";
			if ("listPage" == y) {
				if (x > 5) {
					w = "false";
				}
				p = {id:"", name:j[r]["title"], align:"", dataFormat:"", visible:w, style:"", sort:"0"};
				c = {id:"", name:j[r]["name"], dictionaryId:"", fieldDataType:j[r]["dataType"], primaryKey:z, formula:"", tablename:j[r]["dataTable"]["name"]};
			} else {
				if ("editPage" == y) {
					p = {id:"", name:j[r]["title"], align:"", visible:w, style:"", sortIndex:"0", event:"", readOnly:"false", groupId:"", exclusiveLine:"false"};
					c = {id:"", name:j[r]["name"], type:"", dictionaryId:"", style:"", event:"", fieldDataType:j[r]["dataType"], primaryKey:z, formula:"", dictionary:"", needs:"false", tablename:j[r]["dataTable"]["name"]};
				} else {
					if ("viewPage" == y) {
						p = {id:"", name:j[r]["title"], align:"", visible:w, style:"", sortIndex:"0", event:"", readOnly:"true", groupId:"", exclusiveLine:"false"};
						c = {id:"", name:j[r]["name"], type:"", dictionaryId:"", style:"", event:"", fieldDataType:j[r]["dataType"], primaryKey:z, formula:"", dictionary:"", needs:"false", tablename:j[r]["dataTable"]["name"]};
					}
				}
			}
			xmlUtils.createNode("Text", "", p, s);
			xmlUtils.createNode("Data", "", c, s);
			//xmlUtils.createNode("columnTable", "", {tablename:j[r]["dataTable"]["name"]}, s);
			if ("listPage" == y) {
				if (x > 3) {
					a = "false";
				}
				var h = {id:"", type:"", tableName:j[r]["dataTable"]["name"], name:j[r]["name"], text:j[r]["title"], fieldDataType:j[r]["dataType"], visible:a, readOnly:"false", cssClass:"", sortIndex:"0", dictionaryId:"", formula:"", align:"", exclusiveLine:"false", operateType:"", style:""};
				var e = {id:"", data:"", reminder:"", validateRule:""};
				xmlUtils.setAttribute(q, "visible", "true");
				var g = xmlUtils.createNode("QueryColumn", "", h, q);
				xmlUtils.createNode("EditMode", "", e, g);
			}
		}
	}
	if ("listPage" == y) {
		var n = xmlUtils.createNode("Button", "", {id:"", type:LIST_BUTTON_SINGLE_VIEW, name:"view", visible:"true"}, u);
		var b = xmlUtils.createNode("Event", "", {Event:"1", JSId:""}, n);
		xmlUtils.createNode("Param", "", {key:f, fieldDataType:k, value:f}, b);
		var d = xmlUtils.createNode("Button", "", {id:"", type:LIST_BUTTON_SINGLE_UPDATE, name:"update", visible:"true"}, u);
		var m = xmlUtils.createNode("Event", "", {Event:"1", JSId:""}, d);
		xmlUtils.createNode("Param", "", {key:f, fieldDataType:k, value:f}, m);
		var l = xmlUtils.createNode("Button", "", {id:"", type:LIST_BUTTON_SINGLE_DELETE, name:"delete", visible:"true"}, u);
		var o = xmlUtils.createNode("Event", "", {Event:"1", JSId:""}, l);
		xmlUtils.createNode("Param", "", {key:f, fieldDataType:k, value:f}, o);
		var ll = xmlUtils.createNode("Button", "", {id:"", type:LIST_BUTTON_SINGLE_COPY, name:"copy", visible:"true"}, u);
		var oo = xmlUtils.createNode("Event", "", {Event:"1", JSId:""}, ll);
		xmlUtils.createNode("Param", "", {key:f, fieldDataType:k, value:f}, oo);
		xmlUtils.createNode("Button", "", {id:"", type:LIST_BUTTON_MUTI_ADD, name:"add", visible:"true"}, u);
	} else {
		if ("eidtPage" == y) {
			xmlUtils.createNode("Button", "", {id:"", type:EDIT_BUTTON_SAVE, name:"\u4fdd\u5b58", visible:"true"}, u);
			xmlUtils.createNode("Button", "", {id:"", type:EDIT_BUTTON_CANCEL, name:"\u5173\u95ed", visible:"true"}, u);
		} else {
			if ("viewPage" == y) {
				xmlUtils.createNode("Button", "", {id:"", type:VIEW_BUTTON_CANCEL, name:"\u5173\u95ed", visible:"true"}, u);
			}
		}
	}
}
function fmSelecttableFilterOpen() {
	$("#fm_selecttable_filter").window("open");
	initSelectTableFilterData();
	initFilterDataFromXML();
}
function initFilterDataFromXML() {
	if (xmlOperateType == "update" && updateXmlUtils && updateGetTableNode && updateGetTableNode != null) {
		var e = updateXmlUtils.getChildNodeByTagName(updateGetTableNode, "FromTables");
		var p = updateXmlUtils.getChildNodeByTagName(updateGetTableNode, "Where");
		var c = updateXmlUtils.getChildNodeByTagName(p, "Condition");
		var f = updateXmlUtils.getChildNodes(e);
		if (f) {
			var z = f.length;
			var g = 0;
			for (var y = 0; y < z; y++) {
				var h = f[y];
				var D = updateXmlUtils.getChildNodeByTagName(h, "Index");
				if (D && updateXmlUtils.getInnerText(D) != "0") {
					var u = updateXmlUtils.getChildNodeByTagName(h, "JoinCondition");
					if (u != null) {
						$("#fm_selecttable_joincheck").attr("checked", true);
						var J = updateXmlUtils.getChildNodeByTagName(u, "Condition");
						var I = updateXmlUtils.getChildNodeByTagName(J, "Operate");
						var m = updateXmlUtils.getChildNodeByTagName(I, "Operands");
						var q = updateXmlUtils.getChildNodes(m);
						var B;
						var l;
						var k;
						var s;
						if (q) {
							for (var x = 0; x < q.length; x++) {
								var t = q[x];
								var C = updateXmlUtils.getChildNodeByTagName(t, "OprName");
								var G = updateXmlUtils.getChildNodeByTagName(t, "OprValue");
								var o = updateXmlUtils.getChildNodeByTagName(G, "FieldName");
								var v = updateXmlUtils.getChildNodeByTagName(o, "TableName");
								var n = updateXmlUtils.getChildNodeByTagName(o, "Name");
								var b = updateXmlUtils.getChildNodeByTagName(C, "Index");
								if (updateXmlUtils.getInnerText(b) == "0") {
									B = updateXmlUtils.getInnerText(v);
									l = updateXmlUtils.getInnerText(n);
								} else {
									k = updateXmlUtils.getInnerText(v);
									s = updateXmlUtils.getInnerText(n);
								}
							}
						}
						var E = u.getAttribute("type");
						var A = I.getAttribute("type");
						if (g > 0) {
							fmSelecttableFilterJoinAddItem();
						} else {
							clearFilterJoinTbodyTr();
						}
						$("#fm_selecttable_jointype_" + g).combobox("setValue", E);
						for (var H in joinCoditionObject) {
							if (joinCoditionObject[H] == A) {
								$("#fm_selecttable_joinoper_" + g).combobox("setValue", H);
								break;
							}
						}
						fmInitParaComboboxData(fmSelecttableJoinObjData, B, "fm_selecttable_mainobj_" + g);
						var a = fmSelecttableGridToSelect(B);
						fmInitParaComboboxData(a, l, "fm_selecttable_mainobjcol_" + g);
						fmInitParaComboboxData(fmSelecttableJoinObjData, k, "fm_selecttable_joinobj_" + g);
						var r = fmSelecttableGridToSelect(k);
						fmInitParaComboboxData(r, s, "fm_selecttable_joinobjcol_" + g);
						g++;
					}
				}
			}
		}
		if (c) {
			var w = c.getAttribute("type");
			var g = 0;
			$("#fm_selecttable_paracheck").attr("checked", true);
			if (w == "NORMAL") {
				fmXmlDataParseToParaCondition(c, g, "");
			} else {
				if (w == "LIST") {
					var F = updateXmlUtils.getChildNodes(c);
					for (var y = 0; y < F.length; y++) {
						var u = F[y];
						var d = u.getAttribute("type");
						var J = updateXmlUtils.getChildNodeByTagName(u, "Condition");
						fmXmlDataParseToParaCondition(J, g, d);
						g++;
					}
				}
			}
		}
	}
}
function fmXmlDataParseToParaCondition(f, e, d) {
	var l = updateXmlUtils.getChildNodeByTagName(f, "Operate");
	var w = l.getAttribute("type");
	var g;
	var r;
	var q;
	var u;
	var h;
	var k;
	var o = updateXmlUtils.getChildNodeByTagName(l, "Operands");
	var p = updateXmlUtils.getChildNodes(o);
	for (var v = 0; v < p.length; v++) {
		var c = p[v];
		var x = updateXmlUtils.getChildNodeByTagName(c, "OprName");
		var b = updateXmlUtils.getChildNodeByTagName(x, "Index");
		var y = updateXmlUtils.getChildNodeByTagName(c, "OprValue");
		if (updateXmlUtils.getInnerText(b) == "0") {
			if (y.getAttribute("type") == "FIELD") {
				var n = updateXmlUtils.getChildNodeByTagName(y, "FieldName");
				var t = updateXmlUtils.getChildNodeByTagName(n, "TableName");
				var m = updateXmlUtils.getChildNodeByTagName(n, "Name");
			}
			g = updateXmlUtils.getInnerText(t);
			r = updateXmlUtils.getInnerText(m);
		} else {
			if (y.getAttribute("type") == "PARAM") {
				var i = updateXmlUtils.getChildNodeByTagName(y, "ParamName");
				h = updateXmlUtils.getInnerText(i);
				k = 0;
			} else {
				if (y.getAttribute("type") == "VALUE") {
					var i = updateXmlUtils.getChildNodeByTagName(y, "Value");
					h = updateXmlUtils.getInnerText(i);
					k = 1;
				} else {
					if (y.getAttribute("type") == "FIELD") {
						var n = updateXmlUtils.getChildNodeByTagName(y, "FieldName");
						var t = updateXmlUtils.getChildNodeByTagName(n, "TableName");
						var m = updateXmlUtils.getChildNodeByTagName(n, "Name");
						q = updateXmlUtils.getInnerText(t);
						u = updateXmlUtils.getInnerText(m);
						k = 2;
					}else {
						if (y.getAttribute("type") == "SYSPARAM") {
							var i = updateXmlUtils.getChildNodeByTagName(y, "SysValue");
							h = updateXmlUtils.getInnerText(i);
							k = 3;
						}
					}
				} 
			}
		}
	}
	if (e > 0) {
		fmSelecttableFilterParamAddItem();
	} else {
		clearFilterParamTbodyTr();
	}
	if (d != "") {
		$("#fm_selecttable_parajoinstyle_" + e).combobox("setValue", d);
	}
	for (var z in joinCoditionObject) {
		if (joinCoditionObject[z] == w) {
			$("#fm_selecttable_paraoper_" + e).combobox("setValue", z);
			break;
		}
	}
	fmInitParaComboboxData(fmSelecttableParaObjData, g, "fm_selecttable_paraobj_" + e);
	var s = fmSelecttableGridToSelect(g);
	fmInitParaComboboxData(s, r, "fm_selecttable_paraobjcol_" + e);
	$("#fm_selecttable_paraobjtype_" + e).combobox("setValue", k);
	if (k == 2) {
		fmInitParaComboboxData(fmSelecttableParaObjData, q, "fm_selecttable_parajoinobj_" + e);
		var a = fmSelecttableGridToSelect(q);
		fmInitParaComboboxData(a, u, "fm_selecttable_parajoinobjcol_" + e);
	} else {
		$("#fm_selecttable_paravalue_" + e).val(h);
	}
}
function fmInitParaComboboxData(a, c, b) {
	for (var d = 0; d < a.length; d++) {
		if (a[d]["text"] == c) {
			$("#" + b).combobox("setValue", a[d]["id"]);
			break;
		}
	}
}
function fmJoinConditionAppendToXML(e) {
	if ($("#fm_selecttable_joincheck").attr("checked")) {
		var m = document.getElementById("fm_selecttable_filterjoin_tbody");
		var v = m.rows;
		var n = v.length;
		for (var z = 0; z < n; z++) {
			var x = v[z];
			if (x.id != "fm_selecttable_joindefault_tr") {
				var t = $(x).find("td select[id^='fm_selecttable_jointype_']");
				var F = $(x).find("td select[id^='fm_selecttable_joinoper_']");
				var w = $(x).find("td select[id^='fm_selecttable_mainobj_']");
				var B = $(x).find("td select[id^='fm_selecttable_mainobjcol_']");
				var g = $(x).find("td select[id^='fm_selecttable_joinobj_']");
				var q = $(x).find("td select[id^='fm_selecttable_joinobjcol_']");
				var E = t.combobox("getValue") == "-1" ? "" : t.combobox("getValue");
				var f = F.combobox("getValue") == "-1" ? "" : F.combobox("getValue");
				var a = w.combobox("getValue");
				var u = B.combobox("getValue");
				var y = g.combobox("getValue");
				var b = q.combobox("getValue");
				var d = xmlUtils.getChildNodes(e);
				var A;
				var D;
				for (var C = 0; C < d.length; C++) {
					var r = d[C];
					var j = xmlUtils.getChildNodeByTagName(r, "TableName");
					if (xmlUtils.getInnerText(j) == y) {
						var s = xmlUtils.createNode("JoinCondition", "", {type:E}, r);
						var G = xmlUtils.createNode("Condition", "", {type:"NORMAL"}, s);
						var p = xmlUtils.createNode("Operate", "", {type:joinCoditionObject[f]}, G);
						A = xmlUtils.createNode("Operands", "", {}, p);
						var c = xmlUtils.createNode("Operand", "", {type:"Varchar"}, A);
						var l = xmlUtils.createNode("OprName", "", {type:"INDEX"}, c);
						xmlUtils.createNode("Index", "1", {type:"INDEX"}, l);
						var h = xmlUtils.createNode("OprValue", "", {type:"FIELD"}, c);
						var o = xmlUtils.createNode("FieldName", "", {type:"NAME"}, h);
						xmlUtils.createNode("Name", b, {}, o);
						xmlUtils.addNode(o, xmlUtils.copyNode(j));
					} else {
						if (xmlUtils.getInnerText(j) == a) {
							D = xmlUtils.createNode("Operand", "", {type:"Varchar"});
							var l = xmlUtils.createNode("OprName", "", {type:"INDEX"}, D);
							xmlUtils.createNode("Index", "0", {type:"INDEX"}, l);
							var h = xmlUtils.createNode("OprValue", "", {type:"FIELD"}, D);
							var o = xmlUtils.createNode("FieldName", "", {type:"NAME"}, h);
							xmlUtils.createNode("Name", u, {}, o);
							xmlUtils.addNode(o, xmlUtils.copyNode(j));
						}
					}
				}
				xmlUtils.addNode(A, D);
			}
		}
	}
}
function fmParaConditionAppendToXML(d) {
	if ($("#fm_selecttable_paracheck").attr("checked")) {
		var m = xmlUtils.createNode("Where", "", {}, d);
		var z;
		var r = document.getElementById("fm_selecttable_filterparam_tbody");
		var j = r.rows;
		var a = j.length;
		if (a > 2) {
			z = xmlUtils.createNode("Condition", "", {type:"LIST"}, m);
		}
		for (var s = 0; s < a; s++) {
			var q = j[s];
			if (q.id != "fm_selecttable_paradefault_tr") {
				var e = $(q).find("td select[id^='fm_selecttable_paraoper_']");
				var h = $(q).find("td select[id^='fm_selecttable_paraobj_']");
				var b = $(q).find("td select[id^='fm_selecttable_paraobjcol_']");
				var g = $(q).find("td input[id^='fm_selecttable_paravalue_']");
				var v = $(q).find("td select[id^='fm_selecttable_paraobjtype_']");
				var w = $(q).find("td select[id^='fm_selecttable_parajoinstyle_']");
				var i = $(q).find("td select[id^='fm_selecttable_parajoinobj_']");
				var x = $(q).find("td select[id^='fm_selecttable_parajoinobjcol_']");
				var t = e.combobox("getValue") == "-1" ? "" : e.combobox("getValue");
				var f = h.combobox("getValue");
				var c = b.combobox("getValue");
				var p = v.combobox("getValue");
				var u = w.combobox("getValue");
				var n = i.combobox("getValue");
				var y = x.combobox("getValue");
				var l = g.val();
				if (a > 2) {
					var o;
					if (s == 0) {
						o = xmlUtils.createNode("JoinCondition", "", {type:""}, z);
					} else {
						o = xmlUtils.createNode("JoinCondition", "", {type:u}, z);
					}
					fmParaConditionParseToXML(o, d, t, f, c, p, l, n, y);
				} else {
					fmParaConditionParseToXML(m, d, t, f, c, p, l, n, y);
				}
			}
		}
	}
}
function fmParaConditionParseToXML(a, e, q, f, c, o, i, k, h) {
	var s = xmlUtils.createNode("Condition", "", {type:"NORMAL"}, a);
	var m = xmlUtils.createNode("Operate", "", {type:joinCoditionObject[q]}, s);
	var p = xmlUtils.createNode("Operands", "", {}, m);
	var j = xmlUtils.createNode("Operand", "", {type:"Varchar"}, p);
	var n = xmlUtils.createNode("OprName", "", {type:"INDEX"}, j);
	xmlUtils.createNode("Index", "0", {}, n);
	var g = xmlUtils.createNode("OprValue", "", {type:"FIELD"}, j);
	var l = xmlUtils.createNode("FieldName", "", {type:"NAME"}, g);
	xmlUtils.createNode("Name", c, {}, l);
	copyTableNodeToFieldNode(e, l, f);
	var r = xmlUtils.createNode("Operand", "", {type:"Varchar"}, p);
	var d = xmlUtils.createNode("OprName", "", {type:"INDEX"}, r);
	xmlUtils.createNode("Index", "1", {}, d);
	if (o == "0") {
		var b = xmlUtils.createNode("OprValue", "", {type:"PARAM"}, r);
		xmlUtils.createNode("ParamName", i, {}, b);
	} else {
		if (o == "1") {
			var b = xmlUtils.createNode("OprValue", "", {type:"VALUE"}, r);
			xmlUtils.createNode("Value", i, {}, b);
		} else {
			if (o == "2") {
				var b = xmlUtils.createNode("OprValue", "", {type:"FIELD"}, r);
				var l = xmlUtils.createNode("FieldName", "", {type:"NAME"}, b);
				xmlUtils.createNode("Name", h, {}, l);
				copyTableNodeToFieldNode(e, l, k);
			}else{
				var b = xmlUtils.createNode("OprValue", "", {type:"SYSPARAM"}, r);
				xmlUtils.createNode("SysValue", i, {}, b);
			}
		}
	}
}
function copyTableNodeToFieldNode(c, a, f) {
	var g = xmlUtils.getChildNodeByTagName(c, "FromTables");
	var e = xmlUtils.getChildNodes(g);
	for (var b = 0; b < e.length; b++) {
		var h = e[b];
		var d = xmlUtils.getChildNodeByTagName(h, "TableName");
		if (xmlUtils.getInnerText(d) == f) {
			xmlUtils.addNode(a, xmlUtils.copyNode(d));
		}
	}
}
function fmSelecttableFilterSubmit() {
	var B = true;
	if ($("#fm_selecttable_joincheck").attr("checked")) {
		var o = document.getElementById("fm_selecttable_filterjoin_tbody");
		var u = o.rows;
		var p = u.length;
		for (var x = 0; x < p; x++) {
			var v = u[x];
			if (v.id != "fm_selecttable_joindefault_tr") {
				var z = $(v).find("td select[id^='fm_selecttable_mainobjcol_']");
				var f = $(v).find("td select[id^='fm_selecttable_joinobj_']");
				var r = $(v).find("td select[id^='fm_selecttable_joinobjcol_']");
				var s = $(v).find("td select[id^='fm_selecttable_jointype_']");
				var E = $(v).find("td select[id^='fm_selecttable_joinoper_']");
				var n = s.combobox("getValue");
				var D = E.combobox("getValue");
				var m = z.combobox("getValue");
				var A = f.combobox("getValue");
				var F = r.combobox("getValue");
				if (n == "-1" || D == "-1") {
					$.messager.alert("\u63d0\u793a", "\u8bf7\u9009\u62e9\u5173\u8054\u7c7b\u578b\u6216\u8005\u64cd\u4f5c\u7b26\uff01", "warning");
					B = false;
				} else {
					if (m == "" || A == "" || F == "") {
						$.messager.alert("\u63d0\u793a", "\u5173\u8054\u5bf9\u8c61\u6570\u636e\u9009\u62e9\u4e0d\u5168\uff01", "warning");
						B = false;
					}
				}
			}
		}
	}
	if ($("#fm_selecttable_paracheck").attr("checked")) {
		var w = document.getElementById("fm_selecttable_filterparam_tbody");
		var i = w.rows;
		var c = i.length;
		for (var y = 0; y < c; y++) {
			var v = i[y];
			if (v.id != "fm_selecttable_paradefault_tr") {
				var d = $(v).find("td select[id^='fm_selecttable_paraoper_']");
				var h = $(v).find("td select[id^='fm_selecttable_paraobj_']");
				var b = $(v).find("td select[id^='fm_selecttable_paraobjcol_']");
				var e = $(v).find("td input[id^='fm_selecttable_paravalue_']");
				var C = $(v).find("td input[name^='fm_selecttable_paratype_'][checked=true]");
				var g = h.combobox("getValue");
				var a = b.combobox("getValue");
				var q = d.combobox("getValue");
				var l = e.val();
				var t = C.val();
				if (q == "-1") {
					$.messager.alert("\u63d0\u793a", "\u8bf7\u9009\u62e9\u64cd\u4f5c\u7b26\uff01", "warning");
					B = false;
				} else {
					if (t == "0" && l == "") {
						$.messager.alert("\u63d0\u793a", "\u8bf7\u8f93\u5165\u6587\u672c\u503c\uff01", "warning");
						B = false;
					} else {
						if (g == "" || a == "") {
							$.messager.alert("\u63d0\u793a", "\u53c2\u6570\u8fc7\u6ee4\u6570\u636e\u9009\u62e9\u4e0d\u5168\uff01", "warning");
							B = false;
						}
					}
				}
			}
		}
	}
	if (B) {
		$("#fm_selecttable_filter").window("close");
	}
}
function fmSelecttableFilterClose() {
	clearFilterJoinTbodyTr();
	clearFilterParamTbodyTr();
	$("#fm_selecttable_joincheck").attr("checked", false);
	$("#fm_selecttable_paracheck").attr("checked", false);
	$("#fm_selecttable_filter").window("close");
}
function clearFilterJoinTbodyTr() {
	$("#fm_selecttable_filterjoin_tbody tr").each(function (a, b) {
		if (a > 1) {
			$(b).remove();
		}
	});
}
function clearFilterParamTbodyTr() {
	$("#fm_selecttable_filterparam_tbody tr").each(function (a, b) {
		if (a > 1) {
			$(b).remove();
		}
	});
}

