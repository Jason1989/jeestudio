<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/include.jsp"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>添加</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script language="javascript" type="text/javascript">
		$(function(){
			var dataSourceTypeData = [{"id":1,"text":"ORACLE"},
				{"id":2,"text":"SQLSERVER"},
				{"id":3,"text":"MYSQL"}];
			$('#dataSourceType').combobox({
				data:dataSourceTypeData,
				valueField:'id',
				textField:'text'
			});			
		});
    </script>
  </head>  
  <body>

		<div class="pop_col_col">
			<div class="pop_col_conc">
				<dl>
					<dd>
						数据源编码：
					</dd>
					<dt>
						${dataSource.id }
					</dt>
				</dl>
				<dl>
					<dd>
						数据源名称：
					</dd>
					<dt>
						${dataSource.name }
					</dt>
				</dl>
			</div>
			<div class="pop_col_conc">
				<dl>
					<dd>
						数据源类型：
					</dd>
					<dt>
					  <c:if test="${ dataSource.dbType==1}">
					  	ORACLE
					  </c:if>
					  <c:if test="${ dataSource.dbType==2}">
					  	SQLSERVER
					  </c:if>
						
					</dt>
				</dl>
				<dl>
					<dd>
						服务器地址：
					</dd>
					<dt>
						${ dataSource.ipAddress}
					</dt>
				</dl>
			</div>
		</div>
		<div class="pop_col_color">
			<div class="pop_col_conc">
				<dl>
					<dd>
						端口号：
					</dd>
					<dt>
						${ dataSource.port}
					</dt>
				</dl>
				<dl>
					<dd>
						实例名：
					</dd>
					<dt>
						${ dataSource.sid}
					</dt>
				</dl>
			</div>
			<div class="pop_col_conc">
				<dl>
					<dd>
						用户名：
					</dd>
					<dt>
						${ dataSource.username}
					</dt>
				</dl>
				<dl>
					<dd>
						口令：
					</dd>
					<dt>
						${ dataSource.password}
					</dt>
				</dl>
			</div>
		</div>
		<div class="pop_col_col">
					<div class="pop_col_conc">
					  <dl>
						<dd>状态：</dd>
						<dt>${ dataSource.state}</dt>
					  </dl>
					  <dl>
						<dd> </dd>
						<dt>
						   
						</dt>
						</dl>
					  </div>
					<div class="pop_col_conc">
					  <dl>
						<dd>描述：</dd>
						<dt>
						  ${dataSource.description}
						</dt>
					  </dl>
					  <dl>
						<dd> </dd>
						<dt>
						   
						</dt>
						</dl>
					  </div>
				  </div>  
				  <div style="float:left;padding:80px 0 0 260px;">
					<a href="javascript:;" id="view_close" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_datasource_view').window('close');">关闭</a>
				  </div>

  </body>
</html>
