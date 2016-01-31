<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<form id="bd_datasourceadd_form" method="post" action="datasource/dataSource!add.action">
	  <div class="pop_col_color">
		<div class="pop_col_conc">
		  <dl>
			<dd>菜单名称：</dd>
			<dt><input name="dataSource.port" class="easyui-numberbox" /><span style="color: red;">*</span> </dt>
		  </dl>
		  <dl>
			<dd>菜单图标：</dd>
			<dt>
			  	<select id="desktop-menu-ioc" ></select><span style="color: red;">*</span> 
		  	<script type="text/javascript">
		  		$(function(){
		  			var desktop_ioc = [
								{id:'assets/images/icons/icon_32_computer.png',text:'computer'},
								{id:'assets/images/icons/icon_32_drive.png',text:'drive'},
								{id:'assets/images/icons/icon_32_disc.png',text:'disc'},
								{id:'assets/images/icons/icon_32_network.png',text:'network'}	
								
							];
		  			$('#desktop-menu-ioc').combobox({
					   // url:'combobox_data.json',
					    valueField:'id',
					    textField:'text',
					    data:desktop_ioc,
					    onSelect:function (record){
					     	document.getElementById('show_desktopIoc').innerHTML='<img src="'+record.id+'" />';
					 	}
					});
		  		});
		  	</script>
		</dt>
		</dl>
	  </div>
  </div>
  <div id="show_desktopIoc" ></div>
  <div style="float:left;padding:80px 0 0 180px;">
		<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDatasourceaddFormSaveButton">保存</a>
  </div>
</form>