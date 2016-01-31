<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<div  class="easyui-accordion" fit="true" border="false" style="background: url(jquery-easyui-1.1.2/images/left_bg.jpg);overflow: auto;" id="frameAccordionMenu"  >
	<div title="在线帮助"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
			<ul id="frame_menu_tree_${stauts.index}" style="padding:10px;" class="easyui-tree" animate="true">
				<li>
			        <span>构件平台</span>
			        <ul>
			        	<li>
				        	<span>数据源操作</span>
					    </li>
					    <li>
					        <span>数据对象操作</span>
					    </li>
					    <li>
					        <span>数据</span>
					    </li>
					    <li>
					        <span>表单设计</span>
					    </li>
			        </ul>
			    </li>
			</ul>
			<ul id="frame_menu_tree_${stauts.index}" style="padding:10px;" class="easyui-tree" animate="true">
				<li>
			        <span>系统管理</span>
			        <ul>
			        	<li>
				        	<span>数据源操作</span>
					    </li>
					    <li>
					        <span>数据对象操作</span>
					    </li>
					    <li>
					        <span>数据</span>
					    </li>
					    <li>
					        <span>表单设计</span>
					    </li>
			        </ul>
			    </li>
			</ul>
		</div>
</div>