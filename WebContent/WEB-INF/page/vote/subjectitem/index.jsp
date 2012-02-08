<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>问卷调查主题列表</title>	
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/vote/subjectitem" action="query"/>?subjectId=' + $('#subjectId').val() + '');

				ewcmsBOBJ.addToolItem('上移','icon-up',upOperate);
				ewcmsBOBJ.addToolItem('下移','icon-down',downOperate);

				ewcmsBOBJ.setWinWidth(500);
				ewcmsBOBJ.setWinHeight(200);
				
				ewcmsBOBJ.openDataGrid('#tt_item',{
                    columns:[[
                              {field:'id',title:'编号',width:60},
                              {field:'title',title:'选项名称',width:500},
                              {field:'statusDescription',title:'选择方式',width:100},
                              {field:'voteNumber',title:'票数',width:60}
                      ]]
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setDatagridID('#tt_item');
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/vote/subjectitem" action="input"/>?subjectId=' + $('#subjectId').val() + '');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/vote/subjectitem" action="delete"/>?subjectId=' + $('#subjectId').val() + '');
			});
			function upOperate(){
				var rows = $('#tt_item').datagrid('getSelections');
				if(rows.length == 0){
	            	$.messager.alert('提示','请选择问卷调查主题列表记录','info');
	                return;
	            }
	            if (rows.length > 1){
					$.messager.alert('提示','只能选择一个问卷调查主题列表记录','info');
					return;
		        }
		        var url = '<s:url namespace="/vote/subjectitem" action="up"/>';
		        var parameter = 'subjectId=' + $('#subjectId').val() + '&selections=' + rows[0].id + '';
	            $.post(url,parameter,function(data){
					if (data == "false"){
						$.messager.alert("提示","上移失败","info");
						return;
					}else if (data == "false-system"){
						$.messager.alert("提示","系统错误","info");
						return;
					}
					$("#tt_item").datagrid('reload');
		            
	            });
	            return false;
			}
			function downOperate(){
				var rows = $('#tt_item').datagrid('getSelections');
				if(rows.length == 0){
	            	$.messager.alert('提示','请选择问卷调查主题列表记录','info');
	                return;
	            }
	            if (rows.length > 1){
					$.messager.alert('提示','只能选择一个问卷调查主题列表记录','info');
					return;
		        }
		        var url = '<s:url namespace="/vote/subjectitem" action="down"/>';
		        var parameter = 'subjectId=' + $('#subjectId').val() + '&selections=' + rows[0].id + '';
	            $.post(url,parameter,function(data){
					if (data == "false"){
						$.messager.alert("提示","下移失败","info");
						return;
					}else if (data == "false-system"){
						$.messager.alert("提示","系统错误","info");
						return;
					}
					$("#tt_item").datagrid('reload');
	            });
	            return false;
			}
			function queryOperate(){
				$('#tt_item').datagrid('clearSelections');
				queryCallBack();
			}
		</script>
	</head>
	<body class="easyui-layout">
	    <div region="center" style="padding:2px;" split="true">  
			<table id="tt_item" fit="true" split="true"></table>
	    </div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;问卷调查主题列表" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                	<a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#edit-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                        <tr>
                            <td class="tdtitle">编号：</td>
                            <td class="tdinput">
                                <input type="text" id="id" name="id" class="inputtext"/>
                            </td>
                            <td class="tdtitle">选项名称：</td>
                            <td class="tdinput">
                                <input type="text" id="title" name="title" class="inputtext"/>
                            </td>
                        </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <s:hidden id="subjectId" name="subjectId"/>
	</body>
</html>