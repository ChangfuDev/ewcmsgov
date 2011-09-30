<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>发件箱</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/datagrid-detailview.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
		<script>
		$(function(){
			ewcmsBOBJ = new EwcmsBase();
			ewcmsBOBJ.setQueryURL('<s:url namespace="/message/send" action="query"/>');
			
			ewcmsBOBJ.delToolItem('新增');
			ewcmsBOBJ.delToolItem('修改');
			ewcmsBOBJ.delToolItem('删除');
			ewcmsBOBJ.delToolItem('查询');
			ewcmsBOBJ.delToolItem('缺省查询');
			
			
			ewcmsBOBJ.addToolItem('新增','icon-add', null, 'btnAdd');
			ewcmsBOBJ.addToolItem('删除', 'icon-remove', delCallBack, 'btnRemove');
			ewcmsBOBJ.addToolItem('查询', 'icon-search', queryCallBack, 'btnSearch');
			ewcmsBOBJ.addToolItem('缺省查询', 'icon-back', initOperateQuery, 'btnBack');
			
			ewcmsBOBJ.openDataGrid('#tt',{
				singleSelect : true,
                columns:[[
						 {field:'id',title:'编号',width:60},
						 {field:'userName',title:'用户',hidden:true},
						 {field:'title',title:'标题',width:800},
		                 {field:'sendTime',title:'发送时间',width:125},
		                 {field:'statusDescription',title:'状态',width:100},
		                 {field:'typeDescription',title:'类型',width:100}
                  ]]
			});

			ewcmsOOBJ = new EwcmsOperate();
			ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			ewcmsOOBJ.setInputURL('<s:url namespace="/message/send" action="input"/>');
			ewcmsOOBJ.setDeleteURL('<s:url namespace="/message/send" action="delete"/>');
			
			$("#tt").datagrid({
				view : detailview,
				detailFormatter : function(rowIndex, rowData) {
					return detailGridData(rowData.msgContents);
				}
			});
			initSubMenu();
		});
		function initSubMenu() {
			$('#btnAdd .l-btn-left').attr('class', 'easyui-linkbutton').menubutton({menu : '#btnSubAdd'});
		}
		//内容数据
		function detailGridData(msgContents){
			var htmls = [];
			if (msgContents.length == 0) {
				htmls.push('<div style="padding:5px 0">没有内容记录!</div>');
			} else {
				htmls.push('<div style="padding:5px 0;"><div class="datagrid-header" style="height:22px;">');
				htmls.push('<div class="datagrid-header-inner" style="display: block;">');
				htmls.push('<table cellspacing="0" cellpadding="0" border="0" style="height: 23px;">');
				htmls.push('<tr style="height: 21px">');
				htmls.push('<td><div class="datagrid-cell" style="width: 20px; text-align: center;"><span></span></div></td>');
				htmls.push('<td><div class="datagrid-cell" style="width: 600px; text-align: left;"><span>内容</span></div></td>');
				htmls.push('</tr>');
				htmls.push('</table>');
				htmls.push('</div>');
				htmls.push('</div>');
				htmls.push('<div class="datagrid-body">');
				for ( var i = 0; i < msgContents.length; i++) {
					htmls.push('<table cellspacing="0" cellpadding="0" border="0"><tr style="height: 21px">'
									+ '<td><div class="datagrid-cell" style="width: 20px; text-align: center;"><span>'
									+ (msgContents.length - i)
									+ '</span></div></td>'
									+ '<td><div class="datagrid-cell" style="width: 600px; text-align: left;"><span>'
									+ msgContents[i].detail
									+ '</span></div></td>'
									+ '</tr></table>');
				}
				htmls.push('</div></div>');
			}
			return htmls.join("");
		}
		function initOperateQuery(){
			defQueryCallBack();
			initSubMenu();
		}
		function addSubscription(){
			var rows = $("#tt").datagrid("getSelections");
			if (rows.length == 0){
				$.messager.alert("提示","请选择新增订阅的记录","info");
				return;
			}
			if (rows.length > 1){
				$.messager.alert("提示","只能选择一个记录进行新增订阅","info");
				return;
			}
			if (rows[0].type == 'SUBSCRIPTION'){
				var url = '<s:url namespace="/message/content" action="input"/>?msgSendId=' + rows[0].id;
				$("#editifr").attr('src', url);
				ewcmsBOBJ.openWindow('#edit-window',{width : 550,height : 200,title : '订阅'});
			}else{
				$.messager.alert('提示','只能是订阅的记录才能再新增订阅内容','info');
				return;
			}
		}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;消息" style="display:none;">
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
                            </tr>
                            <tr>
                                <td class="tdtitle">名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
                                </td>
                            </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');initSubMenu();">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="btnSubAdd" style="width:80px;display:none;">
        	<div id="btnMsg" iconCls="" onclick="addCallBack();">消息</div>
	        <div id="btnRec" iconCls="" onclick="addSubscription();">订阅</div>
	    </div>
	</body>
</html>