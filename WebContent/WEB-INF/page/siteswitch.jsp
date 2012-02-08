<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>站点切换</title>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
       <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'>
       <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
       <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
       <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
       <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
       <script type="text/javascript" src='<s:url value="/ewcmssource/page/siteswitch.js"/>'></script>
		<script type="text/javascript">
    		var _siteSwitch = new SiteSwitch({
    		    queryUrl:'<s:url action="siteQuery"/>',
    		    switchUrl:'<s:url action="index"/>'
    		});
    		
    		$(function(){
    		    _siteSwitch.init();
    		});
    		
    		function pageSubmit(){
    		    _siteSwitch.switchSite();
    		}
		</script>
	</head>
	<body>
        <table id="tt"></table>
        <s:hidden name="siteId"/>
	</body>
</html>