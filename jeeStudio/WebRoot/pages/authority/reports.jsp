<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object obj = request.getAttribute("oid");
String oid = "";
if(obj!=null){
	oid = obj.toString();
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>报表统计列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
		    $('#report').tree({
				checkbox: false,	
				fit:true,							
				url: '<%=basePath%>pages/authority/reports_data.json',
				onClick:function(node){
					var id = node.id;
					if(id!=1){
						var r_url = '<%=basePath%>ReportServer?__showtoolbar__=false&__pi__=true&reportlet='+id+'.cpt&op=write';
             			 //$('#reportFrame').attr("src", r_url)
             			   window.showModalDialog(r_url, null, "dialogWidth:1024px;dialogHeight:768px;status:no;help:no;resizable:yes;");
             			 //window.open(r_url);
					}
				}
			});
			//展开组织机构
			setTimeout(function(){
				$("#report").tree('expandAll');
			},500);
		});
		
	</script>     
	<style>
	#report_table a {color:#545454;text-decoration:none;}
	#report_table{color:#545454;font-size:16px;font-weight:bold;}
	#report_table .tr1{height:40px;background:#f5f5f5;}
	#report_table .tr2{height:40px;background:#fff;}
	</style>
  </head>

  <body>
    <div id="organizationlist" class="easyui-panel" border="true" fit="true" style="" >
    		 <div class="easyui-layout" collapsible="true" fit="true" border="false">
				<div region="center" title="报表统计"  border="false" style="padding-top:10px;padding-left:10px;padding-right:10px;background:#fff;border:1px solid #ccc;height: 365px;" >
				     <table border="0" id="report_table" width="100%">
				     	<tr class="tr1">
				     		<td align="left" width="17px"><img src="<%=path %>/images/baobiao.gif"/></td>
				     		<td align="left" style="padding-left:10px;"><a href="javascript:void(0);" onclick="openReport('ygdht')" >已归档合同统计</a></td>
				     	</tr>
				     	<tr class="tr2">
				     		<td align="left" width="17px"><img src="<%=path %>/images/baobiao.gif"/></td>
				     		<td align="left" style="padding-left:10px;"><a  href="javascript:void(0);" onclick="openReport('yspf')" >月度预算-预算批复见表(YY-1)公司汇总用表</a></td>
				     	</tr>
				     	<tr class="tr1">
				     		<td align="left" width="17px"><img src="<%=path %>/images/baobiao.gif"/></td>
				     		<td align="left" style="padding-left:10px;"><a href="javascript:void(0);" onclick="openReport('dwhtsl')" >单位合同数量统计</a></td>
				     	</tr>
				     	<tr class="tr2">
				     		<td align="left" width="17px"><img src="<%=path %>/images/baobiao.gif"/></td>
				     		<td align="left" style="padding-left:10px;"><a  href="javascript:void(0);" onclick="openReport('ydystj')" >月度预算统计</a></td>
				     	</tr>
				     	<tr class="tr1">
				     		<td align="left" width="17px"><img src="<%=path %>/images/baobiao.gif"/></td>
				     		<td align="left" style="padding-left:10px;"><a  href="javascript:void(0);" onclick="openReport('ndystj')" >年度预算统计</a></td>
				     	</tr>
				     </table>
				</div>
				<div region="south" border="false" style="text-align:center;height:1px;padding: 13px;">
				</div>
      		 </div>
   </div>
  </body>
</html>
