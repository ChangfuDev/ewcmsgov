<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
	<head>
		<title>历史内容</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>" />
		<script>
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:' ',
					queryURL:'<s:url namespace="/document/history" action="query"><s:param name="articleId" value="articleId"></s:param></s:url>',
					deleteURL:' ',
					editwidth:1000,
					editheight:700
				});
				//数据表格定义 						
				openDataGrid({
					singleSelect:true,
					columns:[[
					          {field:'historyId',title:'编号',width:60,hidden:true},
					          {field:'version',title:'版本号',width:100,sortable:true},
			                  {field:'maxPage',title:'页数',width:200},
			                  {field:'historyTime',title:'时间',width:200}
							]],
			         toolbar:[
							{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
							{text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack}
						    ],
			         pagination:true
				});
			});
			function selectOperator(){
				var rows = $('#tt').datagrid('getSelections');
		        if(rows.length == 0){
		        	$.messager.alert('提示','请选择记录','info');
		            return;
		        }
		        if(rows.length > 1){
					$.messager.alert('提示','只能选择一条记录','info');
					return;
			    }
			    var operator_type=[];
			    operator_type.push(rows[0].maxPage);
			    operator_type.push(rows[0].version);
			    //operator_type.push(getQueryStringRegExp("articleId"));
			    operator_type.push(rows[0].historyId);
			    return operator_type;
			}
			function queryDateSearch(){
				url = globaoptions.queryURL;
				url = url + '&startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val();
			    $('#tt').datagrid({
			        pageNumber:1,
			        url:url
			    });
			    $('#query-window').window('close');			
			}
		</script>
		<ewcms:datepickerhead/>	
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding: 2px;" border="false">
			<table id="tt" fit="true"></table>
		</div>
		
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">开始时间：</td>
                                <td class="tdinput">
                                	<ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">结束时间：</td>
                                <td class="tdinput">
                                	<ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/>
                                </td>
                            </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="queryDateSearch();">查询</a>
                </div>
            </div>
        </div>	
	</body>
</html>