<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代码生成路径说明</title>
</head>
<body>
<table width="70%" border="1">
    <tr>
        <td class="Page_TableLabel_Left" colspan="2">生成代码摘要信息</td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left" width="30%">build文件</td>
        <td>${buildPath}</td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">工程引用包目录</td>
        <td>
            ${libPath}
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">类文件目录</td>
        <td>
           ${classPath}
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">
            配置信息目录
        </td>
        <td>
            ${confPath}
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">功能配置文件</td>
        <td>${functionPath}</td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">struts2配置文件</td>
        <td>${struts2Path}</td>
    </tr>
        <tr>
        <td class="Page_TableLabel_Left">工作流定义文件</td>
        <td>file_serial_number_workflowDefination.xml</td>
    </tr>
        <tr>
        <td class="Page_TableLabel_Left">
            java代码根目录
        </td>
        <td>
            ${basePath}
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">所有java代码</td>
        <td>
		    
            
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">
            页面代码
        </td>
        <td class="Page_TableLabel_Left">
            ${pagePath}
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">
            数据列表页面
        </td>
        <td class="Page_TableText_Left">
            listfileserialnumber.jsp
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">
            数据编辑页面
        </td>
        <td>
            editfileserialnumber.jsp
        </td>
    </tr>
    <tr>
        <td colspan="2" class="Page_TableLabel_Left">
            模块功能链接
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">
            上级模块moduleName
        </td>
        <td class="Page_TableText_Left">
            file_serial_number
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">
            编辑功能moduleName
        </td>
        <td class="Page_TableText_Left">
            file_serial_number_edit
        </td>
    </tr>
    <tr>
        <td class="Page_TableLabel_Left">
            编辑功能url
        </td>
        <td class="Page_TableText_Left">
            /fileserialnumber/viewFileSerialNumber.action?moduleName=file_serial_number_edit
        </td>
    </tr>
    </table>
</body>
</html>