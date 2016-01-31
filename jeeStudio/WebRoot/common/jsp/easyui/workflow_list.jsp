<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<div id="activity-tab-window" ></div>
<script>
	function activity_operator(APP_ID,mid,precursorId,gridId,title,formId,height,width){
		initEasyuiWindow('activity-tab-window','&nbsp;'+title,height,width);
		var url='acttab_.action?atw=1&gridId='+gridId+'&APP_ID='+APP_ID+'&mid='+mid+'&precursorId='+precursorId+'&formId='+formId+'&date='+new Date().getTime();
		$('#activity-tab-window').window({href:url});
		$('#activity-tab-window').window('open');
	}

	function activity_compar_getparam(name){
		var param_value = $("input[name="+name+"]").val();
		if(param_value){
			return param_value;
		}else{
			return '_';
		}
	}
</script>