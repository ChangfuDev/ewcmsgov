<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>过滤块属性</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>	
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
        <script type="text/javascript">
			function tipMessage(){
			    <s:if test="hasActionMessages()">  
			        <s:iterator value="actionMessages">  
						$.messager.alert('提示','<s:property escape="false"/>','info');
			        </s:iterator>  
		     	</s:if>  
			}
            <s:property value="javaScript"/>
        </script>		
	</head>
	<body onload="tipMessage();">
		<s:form action="save" namespace="/crawler/filter">
			<table class="formtable" >
				<tr>
					<td>表达式：</td>
					<td class="formFieldError">
						<s:textarea id="regex" name="filterBlockVo.regex" cols="50"/><a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-help" onclick="parent.parent.helpOperate();"></a><font color="red">*</font>
						<s:fielderror><s:param value="%{'filterBlockVo.regex'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="filterBlockVo.id"/>
			<s:hidden name="parentId" id="parentId"/>
			<s:hidden name="gatherId" id="gatherId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>