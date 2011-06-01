var pages = 1; // 页数
var currentPage = 1;// 当前选中的页

$(function() {
	setInterval("auto_save()",600000);
	
	changeType();
	
	var st = new ArticleToolbar("articleTitle","tdTitle","FontColor,Bold,Italic,UnderLine,FontFamily,FontSize");
	st.show();
	//标题
	if ($("#articleTitleStyle").attr("value") != ""){
		var styleValue = $("#articleTitleStyle").attr("value");
		var articleTitleStyle = $("#articleTitle").attr("style") + ";" + styleValue;
		$("#articleTitle").attr("style",articleTitleStyle);
		$("#articleTitleStyle").attr("style",styleValue);
		ArticleToolbarStyle("articleTitle",styleValue);
	}
	
	st = new ArticleToolbar("articleShortTitle","tdShortTitle","FontColor,Bold,Italic,UnderLine,FontFamily,FontSize");
	st.show();
	//短标题
	if ($("#articleShortTitleStyle").attr("value") != ""){
		var styleValue = $("#articleShortTitleStyle").attr("value");
		var articleShortTitleStyle = $("#artilceShortTitle").attr("style") + ";" + styleValue;
		$("#articleShortTitle").attr("style", articleShortTitleStyle);
		$("#articleShortTitleStyle").attr("style",styleValue);
		ArticleToolbarStyle("articleShortTitle",styleValue);
	}

	st = new ArticleToolbar("articleSubTitle","tdSubTitle","FontColor,Bold,Italic,UnderLine,FontFamily,FontSize");
	st.show();
	//副标题
	if ($("#articleSubTitleStyle").attr("value") != ""){
		var styleValue = $("#articleSubTitleStyle").attr("value");
		var articleSubTitleStyle =$("#articleSubTitle").attr("style") + ";" + styleValue;
		$("#articleSubTitle").attr("style", articleSubTitleStyle);
		$("#articleSubTitleStyle").attr("style",styleValue);
		ArticleToolbarStyle("articleSubTitle",styleValue);
	}
	
	for ( var i = 1; i < pages; i++) {
		$("#pageList").append(getLi_Html(i+1));
	}
	
	if ($("#articleShortTitle").attr("value") != ""){
		$("#ShowShortTitle").attr("checked",true);
		var styleValue = $("#articleShortTitleStyle").attr("value");
		var articleTitleStyle = $("#articleShortTitle").attr("style") + ";" + styleValue;
		$("#articleShortTitle").attr("style",articleTitleStyle);
		$("#trShortTitle").show();
	}
	
	if ($("#articleSubTitle").attr("value") != ""){
		$("#ShowSubTitle").attr("checked",true);
		var styleValue = $("#articleSubTitleStyle").attr("value");
		var articleTitleStyle = $("#articleSubTitle").attr("style") + ";" + styleValue;
		$("#articleSubTitle").attr("style",articleTitleStyle);
		$("#trSubTitle").show();
	}

	$("#ShowShortTitle").click(function() {
		if ($("#ShowShortTitle").attr("checked") == true) {
			$("#trShortTitle").show();
		} else {
			$("#trShortTitle").hide();
		}
		window_resize();
	});
	
	$("#ShowSubTitle").click(function() {
		if ($("#ShowSubTitle").attr("checked") == true) {
			$("#trSubTitle").show();
		} else {
			$("#trSubTitle").hide();
		}
		window_resize();
	});
	var ewcms_short_cookies = $.cookie("ewcms_short_<sec:authentication property='name'/>");
	if (ewcms_short_cookies != null){
		$('#ewcms_short').attr('checked',true);
		$('#ShowShortTitle').attr('checked',true);
		$('#trShortTitle').show();
	}else{
		$('#ewcms_short').attr('checked',false);
		$('#ShowShortTitle').attr('checked',false);
		$('#trShortTitle').hide();
	}
	var ewcms_sub_cookies = $.cookie("ewcms_sub_<sec:authentication property='name'/>");
	if (ewcms_sub_cookies != null){
		$('#ewcms_sub').attr('checked',true);
		$('#ShowSubTitle').attr('checked',true);
		$('#trSubTitle').show();
	}else{
		$('#ewcms_sub').attr('checked',false);
		$('#ShowSubTitle').attr('checked',false);
		$('#trSubTitle').hide();
	}
	for (var i=1;i<=5;i++){
		var ewcms_cookies = $.cookie("ewcms_" + i + "_<sec:authentication property='name'/>");
		if (ewcms_cookies != null){
			$('#ewcms_' + i).attr('checked',true);
			$('#trShowHide_' + i).show();
		}else{
			$('#ewcms_' + i).attr('checked',false);
			$('#trShowHide_' + i).hide();
		}
	}
	
	document.title="文档编辑：" + $('#articleTitle').attr('value');
	window_resize();
});
$(window).bind("resize", function () {
	window_resize();
});
function window_resize(){
	var height = $(window).height() - $("#buttonBarTable").height() - $("#inputBarTable").height() - $("#pageBarDiv").height() - 10;
	var width = $(window).width() - 30*2;
	$("div #_DivContainer").css("height",height + "px");
	try{
		if (tinyMCE.getInstanceById('_Content_' + currentPage) != null){
			tinyMCE.getInstanceById('_Content_' + currentPage).theme.resizeTo(width,(height - 107));
		}else{
			$("#_Content_" + currentPage).css("width", (width + 2) + "px");
			$("#_Content_" + currentPage).css("height", (height - 40) + "px");
		}
	}catch(errRes){
	}
}
function addPage() {
	pages = pages + 1;
	$("#pageList").append(getLi_Html(pages));
	var tr_Content_Temple = getTrContent_Html(pages).replace(/\&quot;/g, "\"").replace(/\&lt;/g, "<").replace(/\&gt;/g, ">").replace(/\&nbsp;/g, " ").replace(/\&amp;/g, "&");
	$("#tableContent").append(tr_Content_Temple);
	try{
		tinyMCE.execCommand('mceAddControl', false, '_Content_' + pages);
	}catch(err_add){
	}
	setActivePage(pages);
}
function delPage() {
	if (pages == 1) {return;}
	
	try{
		if (tinyMCE.getInstanceById('_Content_' + pages) != null){
			tinyMCE.execCommand('mceRemoveControl', false, '_Content_' + pages);
		}
	}catch(err_del){
		//alert(err_del);
	}
	
	$("#trContent_" + pages).remove();
	$("#p" + pages).remove();
	
	pages = pages - 1;
	if (currentPage > pages) {
		currentPage = pages;
	}
	setActivePage(currentPage);

}
function getTrContent_Html(page) {
	var tr_content_html = "<tr id='trContent_" + page + "' >" 
			+ "	 <td>"
			+ "		<textarea id='_Content_" + page + "' class='mceEditor'></textarea>"
			+ "     <input type='hidden' id='textAreaContent_" + page + "' name='textAreaContent'/>"
			+ "   </td>"
			+ "</tr>";
	return tr_content_html;
}
function getLi_Html(page){
	var li_html = "<li onclick=\"changePage('p" + page + "')\" " 
				+ "onmouseover=\"onOverPage('p" + page + "')\" " 
				+ "onmouseout=\"onOutPage('p" + page + "')\" " 
				+ "id=\"p" + page + "\" "
				+ "name=\"tabs\"><b>页 " + page + "</b>" 
				+ "</li>"
	return li_html;
}
function setActivePage(page) {
	var currentTab = $("#p" + page);
	if (currentPage == page && currentTab.attr("class") == "current") {
		return;
	}
	for ( var i = 0; i < pages; i++) {
		var tab = $("#p" + (i + 1));
		var content = $("#trContent_" + (i + 1));
		if (tab.attr("class") == "current") {
			tab.attr("class", "");
			content.hide();
			break;
		}
	}

	currentTab.attr("class", "current");
	$("#trContent_" + page).show();
	$("#_Content_" + page).focus();
	
	currentPage = page;
	
	window_resize();
}
function changePage(id){
	var pageNum = id.substr(1);
	pageNum = parseInt(pageNum);
	setActivePage(pageNum);
}
function onOverPage(id){
	var li_object = $("#" + id);
	if (li_object.attr("class")==""){
		li_object.attr("class","pagetabOver");
	}
}
function onOutPage(id){
	var li_object = $("#" + id);
	if (li_object.attr("class")=="pagetabOver"){
		li_object.attr("class","");
	}
}
function insertFileToCkeditorOperator(){
	editifr_pop.insert(function(data){
		$.each(data, function(index,value){
			var html_obj="";
			var type = value.type;
			if (type=="ANNEX"){
				html_obj="<a href='../../" + value.releasePath + "'>" + value.title + "</a>";
			}else if (type=="IMAGE"){
				html_obj="<p style='text-align: center;'><img border='0' src='../../" + value.releasePath + "'/></p><p style='text-align: center;'>" + value.title + "</p>";
			}else if (type=="FLASH"){
				html_obj="";
			}else if (type=="VIDEO"){
				html_obj="";
			}
			if (tinyMCE.getInstanceById('_Content_' + pages) != null){
				tinyMCE.execInstanceCommand('_Content_' + pages,'mceInsertContent',false,html_obj);
				//tinyMCE.execCommand('mceInsertContent',false,html_obj);
			}
	   });
    });
	$("#pop-window").window("close");
}
function selectHistoryOperator(url){
	var operator_type = editifr_pop.selectOperator();
	var maxPage = operator_type[0];
	var version = operator_type[1];
	var parameter = {};
	parameter["historyId"] = operator_type[2];
	
	var details;
	$.post(url, parameter ,function(data) {
		details = data;
	});
		
	$.messager.confirm("提示","确定要把第 【" + version + "】号 版本替换当前的内容吗?",function(r){
		if (r){
			currentPage = 1;
			if (maxPage!=pages){
				if(maxPage>pages){
					for(var i=pages+1;i<=maxPage;i++){
						addPage();
					}
				}else{
					for (var i=pages;i>maxPage;i--){
						delPage();
					}
				}
			}
			for (var i=1;i<=pages;i++){
				if (tinyMCE.getInstanceById('_Content_' + i) != null){
					tinyMCE.get('_Content_' + i).setContent(details[i-1]);
				}else{
					$("#_Content_" + i).val(details[i-1]);
				}
			}
			setActivePage(1);
		}
	});
	$("#pop-window").window("close");
}
//新建文章
function createArticle(url){
	if ($("#articleVo_id").val()==""){
		$.messager.confirm("提示","文章尚未保存，确认新建文章?",function(r){
			if (r){
				window.location = url;
			}
		});
	}else{
		window.location = url;
	}
}
//提取关键字和摘要
function getKeywordOrSummary(type,url){
	if ($('#articleVo_type').val() == "TITLE"){
		$.messager.alert("提示","标题新闻不用提取【关键字】或【摘要】","info");
		return;
	}
	var title = $("#articleTitle").attr("value");
	if (title == ""){ 
		$.messager.alert("提示","提取【关键字】或【摘要】时，标题不能为空","info");
		$("#title").focus()
		return;
	}
	
	var contents = "";
	for (var i = 1;i <= pages;i++){
		var contentDetail = tinyMCE.get('_Content_' + i).getContent();
		contents += contentDetail;
    }
	if (contents=="") {
		$.messager.alert("提示","提取【关键字】或【摘要】时，内容不能为空","info");
		$("#_Content_" + currentPage).focus();
		return;
	}
	
    var parameter = {};
    parameter["title"] = title;
    parameter["content"] = contents;

	if (type=="keyWord"){
		$.post(url, parameter ,function(data) {
			if (data != ""){
				$.messager.alert("提示","提取【关键字】成功","info");
				$("#keyword").attr("value",data);
			}else{
				$.messager.alert("提示","提取【关键字】失败,可自行添加","info");
			}
		});
	}else{
		$.post(url, parameter ,function(data) {
			if (data != ""){
				$.messager.alert("提示","提取【摘要】成功","info");
				$("#summary").attr("value",data);
			}else{
				$.messager.alert("提示","提取【摘要】失败,可自行添加","info");
			}
		});
	}
}
//保存文章
function saveArticle(){
	if ($.trim($("#articleTitle").attr("value"))==""){
		$.messager.alert("提示","文章标题不能为空","info");
		return;
	}
	var articleType = $('#articleVo_type').val();
	if ( articleType == 'GENERAL'){
		for (var i = 1;i <= pages;i++){
			var contentDetail = tinyMCE.get('_Content_' + i).getContent();
			contentDetail = contentDetail.replace(/<br \/>/g,"");
			contentDetail = contentDetail.replace(/<p>\&nbsp;<\/p>/g,"");
			if ($.trim(contentDetail) == ""){
				setActivePage(i);
				$.messager.alert("提示","文章内容不能为空!","info");
				return;
			}
			$("#textAreaContent_" + i).attr("value", contentDetail);
		}
	}else if (articleType == 'TITLE'){
		var articleLinkAddr = $('#linkAddr').val();
		if ($.trim(articleLinkAddr) == ''){
			$.messager.alert('提示','链接地址不能为空!','info')
			return;
		}
	}
	
	if ($("#ShowShortTitle").attr("checked") == false || $.trim($("#articleShortTitle").attr("value")) == ""){
		$("#articleShortTitle").attr("value","");
		$("#articleShortTitleStyle").attr("value","");
	}
	if ($("#ShowSubTitle").attr("checked") == false || $.trim($("#articleSubTitle").attr("value")) == "") {
		$("#articleSubTitle").attr("value","");
		$("#articleSubTitleStyle").attr("value","");
	}
//	$("#articleSave").submit();
//	window.opener.window.articleReload();
	var params=$('#articleSave').serialize();
	$.post("save.do" ,params ,function(data){
		if (data == "false"){
			$.messager.alert("提示","文章保存失败","info");
		}else if (data == "system-false"){
			$.messager.alert("提示","系统错误","info");
		}else if (data != ""){
			$("#state").attr("value", data.state);
			$("#saveTime_general").html("<font color='#FF0000'>" + data.modified + "</font>");
			$("#saveTime_title").html("<font color='#FF0000'>" + data.modified + "</font>");
			window.opener.window.articleReload();
			$.messager.alert('提示','文章保存成功','info');
		}
	});
}
//提交审核文章
function submitReview(url){
	$.post(url, {} ,function(data) {
		if (data == "true"){
			$.messager.alert("提示","提交审核成功","info");
			window.opener.window.articleReload();
			return;
		}else if (data == "false"){
			$.messager.alert("提示","提交审核失败，只有在【初稿】或【重新编辑】的文章才能提交审核","info");
			return;
		}else if (data == "system-false"){
			$.messager.alert("提示","系统错误","info");
			return;
		}
	});
}
var noImage = "../../source/image/article/nopicture.jpg";
//清除引用图片
function clearImage(){
	$("#referenceImage").attr("src",noImage);
	$("#article_image").attr("value","");
}
//关闭文档编辑器
function closeArticle(){
	$.messager.confirm("提示","确定要关闭文档编辑器吗?",function(r){
		if (r){
			window.opener.window.articleReload();
			window.close();
		}
	});
}
//选择历史内容
function selectHistory(url){
	if ($('#articleVo_type').val() == "TITLE"){
		$.messager.alert("提示","标题新闻没有历史记录","info");
		return;
	}
	var articleId = $("#articleVo_id").attr("value");
	if (articleId == ""){
		$.messager.alert("提示","新增记录没有历史记录","info");
		return;
	}
	$("#selectHistory_span").attr("style","");
	$("#save_span").attr("style","display:none");
	
	$("#editifr_pop").attr("src",url);
	openWindow("#pop-window",{width:800,height:600,title:"历史内容选择"});
}
//选择相关文章
function selectRelated(url){
	if ($('#articleVo_type').val() == "TITLE"){
		$.messager.alert("提示","标题新闻没有相关文章","info");
		return;
	}
	var articleId = $("#articleVo_id").attr("value");
	if (articleId == ""){
		$.messager.alert("提示","在新增状态下不能查看相关文章！","info");
		return;
	}
	$("#selectHistory_span").attr("style","display:none");
	$("#save_span").attr("style","");
	$("#editifr_pop").attr("src",url);
	openWindow("#pop-window",{width:800,height:600,title:"相关文章选择"});
}
//选择推荐文章
function selectRecommend(url){
	if ($('#articleVo_type').val() == "TITLE"){
		$.messager.alert("提示","标题新闻没有推荐文章","info");
		return;
	}
	var articleId = $("#articleVo_id").attr("value");
	if (articleId == ""){
		$.messager.alert("提示","在新增状态下不能查看推荐文章！","info");
		return;
	}
	$("#selectHistory_span").attr("style","display:none");
	$("#save_span").attr("style","");
	$("#editifr_pop").attr("src",url);
	openWindow("#pop-window",{width:800,height:600,title:"推荐文章选择"});
}
function showHide(username){
    var showHideLabel_value = $('#showHideLabel').text();
    if ($.trim(showHideLabel_value) == '展开'){
        $('#trShowHide_1').show();
        $('#trShowHide_2').show();
        $('#trShowHide_3').show();
        $('#trShowHide_4').show();
        $('#trShowHide_5').show();
        $('#imgShowHide').attr('src', '../../source/image/article/hide.gif');
    	$('#showHideLabel').text('收缩');
    }else{
    	for (var i=1;i<=5;i++){
    		var ewcms_cookies = $.cookie('ewcms_' + i + '_' + username);
    		if (ewcms_cookies != null){
    			$('#ewcms_' + i).attr('checked',true);
    			$('#trShowHide_' + i).show();
    		}else{
    			$('#ewcms_' + i).attr('checked',false);
    			$('#trShowHide_' + i).hide();
    		}
    	}
        $('#imgShowHide').attr('src', '../../source/image/article/show.gif');
    	$('#showHideLabel').text('展开');
    }
    window_resize();
}
function changeType(){
    var articleType = $('#articleVo_type').val();
    if (articleType == "TITLE"){
        $('#table_content').hide();
        $('#pageBarTable_general').hide();
        $('#pageBarTable_title').show();
        $('#tr_linkaddr').show();
    }else if (articleType == "GENERAL"){
        $('#table_content').show();
        $('#pageBarTable_general').show();
        $('#pageBarTable_title').hide();
        $('#tr_linkaddr').hide();
    }
    window_resize();
}
function openAnnexWindow(url){
	$('#systemtab_annex').tabs('select','本地附件');
    $('#uploadifr_annex_id').attr('src',url);
    openWindow("#annex-window",{width:600,height:500,title:"本地附件"});
}
function insertAnnexOperator(){
    var tab = $('#systemtab_annex').tabs('getSelected');
    var title = tab.panel('options').title;
    if(title == '本地附件'){
        uploadifr_annex.insert(function(data){
            $.each(data,function(index,value){
                var html_obj="<a href='../.." + value.releasePath + "'>" + value.title + "</a>";
                if (tinyMCE.getInstanceById('_Content_' + currentPage) != null){
    				tinyMCE.execInstanceCommand('_Content_' + currentPage,'mceInsertContent',false,html_obj);
    				//tinyMCE.execCommand('mceInsertContent',false,html_obj);
    			}
            });
        });
    }else{
        queryifr_annex.insert(function(data){
            $.each(data,function(index,value){
                var html_obj="<a href='../.." + value.releasePath + "'>" + value.title + "</a>";
                if (tinyMCE.getInstanceById('_Content_' + currentPage) != null){
    				tinyMCE.execInstanceCommand('_Content_' + currentPage,'mceInsertContent',false,html_obj);
    				//tinyMCE.execCommand('mceInsertContent',false,html_obj);
    			}
            });
        });
    }
	$("#annex-window").window("close");
}
//选择引用图片
function selectImage(url){
	openImageWindow(false, false, url);
}
function openImageWindow(multi,content_image,url){
	$('#systemtab_image').tabs('select','本地图片');
    $('#image_multi_id').val(multi);
    $('#content_image_id').val(content_image);
    if(multi){
        $('#uploadifr_image_id').attr('src',url + '?multi=true');
    }else{
        $('#uploadifr_image_id').attr('src',url + '?multi=false');
    }
    openWindow("#image-window",{width:600,height:500,title:"图片选择"});
}
function insertImageOperator(){
    var tab = $('#systemtab_image').tabs('getSelected');
    var title = tab.panel('options').title;
    var content_image = $('#content_image_id').val();
    if(title == '本地图片'){
        uploadifr_image.insert(function(data){
            $.each(data,function(index,value){
                if (content_image=="true"){
                	var html_obj="<p style='text-align: center;'><img border='0' src='../.." + value.releasePath + "'/></p><p style='text-align: center;'>" + value.title + "</p>";
                	if (tinyMCE.getInstanceById('_Content_' + currentPage) != null){
        				tinyMCE.execInstanceCommand('_Content_' + currentPage,'mceInsertContent',false,html_obj);
        				//tinyMCE.execCommand('mceInsertContent',false,html_obj);
        			}
                }else{
    				$("#referenceImage").attr("src", "../.." + value.releasePath);
    				$("#article_image").attr("value", value.releasePath);
                }
            });
        });
    }else{
        queryifr_image.insert(function(data){
            $.each(data,function(index,value){
                if (content_image=="true"){
                	var html_obj="<p style='text-align: center;'><img border='0' src='../.." + value.releasePath + "'/></p><p style='text-align: center;'>" + value.title + "</p>";
                	if (tinyMCE.getInstanceById('_Content_' + currentPage) != null){
        				tinyMCE.execInstanceCommand('_Content_' + currentPage,'mceInsertContent',false,html_obj);
        				//tinyMCE.execCommand('mceInsertContent',false,html_obj);
        			}
                }else{
    				$("#referenceImage").attr("src", "../.." + value.releasePath);
    				$("#article_image").attr("value", value.releasePath);
                }
            });
        });
    }
	$("#image-window").window("close");
}

function auto_save() {
	if ($.trim($("#articleTitle").attr("value"))==""){
		return;
	}
	if ($('#articleVo_type').val() == "GENERAL"){
		var autoSave = false;
		for (var i = 1;i <= pages;i++){
			var editor_id = "_Content_" + i;
			var content = tinyMCE.get(editor_id).getContent();
			var notDirty = tinyMCE.get(editor_id);
			content = content.replace(/<br \/>/g,"");
			content = content.replace(/<p>\&nbsp;<\/p>/g,"");
			$("#textAreaContent_" + i).attr("value", content);
			if ($.trim(content) == ""){
				continue;
			}
			if(tinyMCE.getInstanceById(editor_id).isDirty()) {
				autoSave = true;
				notDirty.isNotDirty = true;
			}
		}
		if ($("#ShowShortTitle").attr("checked") == false || $.trim($("#articleShortTitle").attr("value")) == ""){
			$("#articleShortTitle").attr("value","");
			$("#articleShortTitleStyle").attr("value","");
		}
		if ($("#ShowSubTitle").attr("checked") == false || $.trim($("#articleSubTitle").attr("value")) == "") {
			$("#articleSubTitle").attr("value","");
			$("#articleSubTitleStyle").attr("value","");
		}
		if (autoSave){
			var params=$('#articleSave').serialize();
			$.post("save.do" ,params ,function(data){
				if (data != "false" && data != "system-false" && data != ""){
					$("#state").attr("value", data.state);
					$("#saveTime_general").html("<font color='#0000FF'>" + data.modified + "</font>");
					$("#saveTime_title").html("<font color='#0000FF'>" + data.modified + "</font>");
					window.opener.window.articleReload();
				}
			});
		}
	}
}
function ewcmsCookies(){
	openWindow("#ewcms-cookies",{width:300,height:215,top:236,left:458,title:"设置常用项"});
}
function ewcmsCookiesOk(){
	$("#ewcms-cookies").window("close");
}
var isok=function(){};
function ewcmsCookiesSet(obj,trId,username){
	var id = obj.id;
	if ($('#' + id).attr('checked') == true){
		$.cookie(id + '_' + username,'true',{expires:14});
		if (id == 'ewcms_short'){
			$('#' + trId).show();
			$('#ShowShortTitle').attr('checked',true);
		}else if (id == 'ewcms_sub'){
			$('#' + trId).show();
			$('#ShowSubTitle').attr('checked', true);
		}else if (id == 'ewcms_toolbar'){
			$("div[id='DivToolbar']").each(function(){
				$(this).show();
			});
			$('#ewcms_toolbar').attr('checked', true);
			//_GetJsData("../../source/js/article-toolbar.js",isok);  
		}else{
			$('#' + trId).show();
		}
	}else{
		$.cookie(id + '_' + username, null);
		if (id == 'ewcms_short'){
			$('#' + trId).hide();
			$('#ShowShortTitle').attr('checked',false);
		}else if (id == 'ewcms_sub'){
			$('#' + trId).hide();
			$('#ShowSubTitle').attr('checked',false);
		}else if (id == 'ewcms_toolbar'){
			$("div[id='DivToolbar']").each(function(){
				$(this).hide();
			});
			$('#ewcms_toolbar').attr('checked', false);
		}else{
			$('#' + trId).hide();
		}
	}
	
	window_resize();
}
function ewcmsCookiesInit(username){
	var ewcms_short_cookies = $.cookie("ewcms_short_" + username);
	if (ewcms_short_cookies != null){
		$('#ewcms_short').attr('checked',true);
		$('#ShowShortTitle').attr('checked',true);
		$('#trShortTitle').show();
	}else{
		$('#ewcms_short').attr('checked',false);
		$('#ShowShortTitle').attr('checked',false);
		$('#trShortTitle').hide();
	}
	var ewcms_sub_cookies = $.cookie("ewcms_sub_" + username);
	if (ewcms_sub_cookies != null){
		$('#ewcms_sub').attr('checked',true);
		$('#ShowSubTitle').attr('checked',true);
		$('#trSubTitle').show();
	}else{
		$('#ewcms_sub').attr('checked',false);
		$('#ShowSubTitle').attr('checked',false);
		$('#trSubTitle').hide();
	}
	var ewcms_toolbar_cookies = $.cookie("ewcms_toolbar_" + username);
	if (ewcms_toolbar_cookies != null){
		$('#ewcms_toolbar').attr('checked', true);
		$("div[id='DivToolbar']").each(function(){
			$(this).show();
		});
		//_GetJsData("../../source/js/article-toolbar.js",isok);  
	}else{
		$('#ewcms_toolbar').attr('checked', false);
		$("div[id='DivToolbar']").each(function(){
			$(this).hide();
		});

	}
	for (var i=1;i<=5;i++){
		var ewcms_cookies = $.cookie("ewcms_" + i + "_" + username);
		if (ewcms_cookies != null){
			$('#ewcms_' + i).attr('checked',true);
			$('#trShowHide_' + i).show();
		}else{
			$('#ewcms_' + i).attr('checked',false);
			$('#trShowHide_' + i).hide();
		}
	}
}
//样式初始化
function ArticleToolbarStyle(styleID, styleValue){
	var styleArr = styleValue.split(";");
	for (var i = 0 ; i < styleArr.length ; i++){
		var styleKey = styleArr[i].split(":")[0];
		var styleValue = styleArr[i].split(":")[1];
		if ($.trim(styleKey) == "font-weight"){//粗体
			if ($.trim(styleValue) == "bold"){
				$("#" + styleID + "_Bold").attr("value","bold");
				$("#" + styleID + "_Bold_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
				$("#" + styleID + "_Bold_Div").get()[0].isClicked = true;
			}else{
				$("#" + styleID + "_Bold").attr("value","normal");
				$("#" + styleID + "_Bold_Div").attr("style","border:none;background:none");
				$("#" + styleID + "_Bold_Div").get()[0].isClicked = false;
			}
		}else if ($.trim(styleKey) == "font-style"){//斜体
			if ($.trim(styleValue) == "italic"){
				$("#" + styleID + "_Italic").attr("value","italic");
				$("#" + styleID + "_Italic_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
				$("#" + styleID + "_Italic_Div").get()[0].isClicked = true;
			}else{
				$("#" + styleID + "_Italic").attr("value","normal");
				$("#" + styleID + "_Italic_Div").attr("style","border:none;background:none");
				$("#" + styleID + "_Italic_Div").get()[0].isClicked = false;
			}
		}else if ($.trim(styleKey) == "text-decoration"){//下划线
			if ($.trim(styleValue) == "underline"){
				$("#" + styleID + "_UnderLine").attr("value","underline");
				$("#" + styleID + "_UnderLine_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
				$("#" + styleID + "_UnderLine_Div").get()[0].isClicked = true;
			}else{
				$("#" + styleID + "_UnderLine").attr("value","none");
				$("#" + styleID + "_UnderLine_Div").attr("style","border:none;background:none");
				$("#" + styleID + "_UnderLine_Div").get()[0].isClicked = false;
			}
		}else if ($.trim(styleKey) == "font-family"){//字体
			$("#" + styleID + "_FontFamily").attr("value",$.trim(styleValue));
			$("#" + styleID + "_FontFamily_Select").val($.trim(styleValue));
		}else if ($.trim(styleKey) == "font-size"){//字号
			$("#" + styleID + "_FontSize").attr("value",$.trim(styleValue));
			$("#" + styleID + "_FontSize_Select").val($.trim(styleValue));
		}
	}
}