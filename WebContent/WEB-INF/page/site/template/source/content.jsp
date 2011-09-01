<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板资源编辑</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>				
		<script type="text/javascript" src='<s:url value="/source/editarea/edit_area_full.js"/>'></script>				
		<script type="text/javascript">
			function tipMessage(){							
			    <s:if test="hasActionMessages()">  
			        <s:iterator value="actionMessages">  
						parent.alertInfo('<s:property escape="false"/>');
			        </s:iterator>  
		     	</s:if> 		     	
			}	
		</script>
	</head>
	<body onload="tipMessage();">	
		<s:form action="saveContent" namespace="/site/template/source">
			<table class="formtable" align="center">
				<tr>
					<td ><s:textarea name="sourceContent" style="height:380px; width:100%;" ></s:textarea></td>
				</tr>	
				<tr>
					<td style="padding:0;">
						<div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
							<a class="easyui-linkbutton" icon="icon-save" href="javascript:document.forms[0].submit();">保存</a>
						 	<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:document.forms[0].reset();">重置</a>
						</div>								
					</td>
				</tr>																							
			</table>		
			<s:hidden name="sourceVo.id"/>	
		</s:form>
	</body>
</html>