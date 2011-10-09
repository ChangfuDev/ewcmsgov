<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>调查投票</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
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
		<s:form action="saveopt" namespace="/vote/subjectitem">
			<table class="formtable" >
				<tr>
					<td width="17%" height="21px">选项方式：</td>
					<td width="83%" height="21px">
						<s:radio list="#{'SINGLETEXT':'单行文本'}" name="subjectItemVo.subjectItemStatus"></s:radio>
						<s:radio list="#{'MULTITEXT':'多行文本'}" name="subjectItemVo.subjectItemStatus"></s:radio>
					</td>
				</tr>
				<tr>
					<td >票数：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="subjectItemVo.voteNumber" maxlength="10"/>
						<s:fielderror ><s:param value="%{'subjectItemVo.voteNumber'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="subjectItemVo.id"/>
			<s:hidden name="subjectItemVo.sort"/>
			<s:hidden name="subjectId"/>
		</s:form>
	</body>
</html>