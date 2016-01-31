<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;padding:20px 0 0 20px;">
      <table>
         <tr>
            <td align="right">版本号：</td><td>${log.codeVersionId}</td>
         </tr>
         <tr>
            <td align="right">表单编码：</td><td>${log.codeFormsId }</td>
         </tr>
         <tr>
            <td align="right">生成代码的用户：</td><td>${log.codeUserId}</td>
         </tr>
         <tr>
            <td align="right">生成代码的时间：</td><td>${log.codeCreateTime }</td>
         </tr>
          <tr>
            <td align="right">代码指定路径：</td><td>${log.codePath}</td>
         </tr>
         <tr>
            <td align="right">生成文件名：</td><td></td>
         </tr>
         <c:forEach var="file" items="${files}">
         <tr>
            <td align="right"></td>
            <td>${file}</td>
         </tr>
         </c:forEach>
         
         <tr>
            <td align="right">版本备注：</td><td>${log.codeRemark }</td>
         </tr>
      </table>
</div>
