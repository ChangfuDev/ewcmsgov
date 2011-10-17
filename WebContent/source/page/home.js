/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var updateUsername = function(name){
    $('#user-name').html(name);
}

var home = function(refurbish,windowId){
    
    this._refurbish = refurbish || true;
    this._windowId = windowId || '#edit-window';
}

home.prototype.addTab=function(title,src){
    var tabob = $('#systemtab');
    var content = '<iframe src='  + src+ ' width=100% frameborder=0 height=100%/>';
    
    if (tabob.tabs('exists', title) && this._refurbish) {
        tabob.tabs('select', title);
        var tab = tabob.tabs("getTab", title);
        tabob.tabs('update', {
            tab : tab,
            options : {
                content :content
            }
        });
    } else {
        tabob.tabs('add', {
            title : title,
            content : content,
            closable : true
        });
    }    
}

home.prototype.init = function(urls){
    var windowId = this._windowId;
 
    $('#button-main').bind('click',function(){
        $('#mm').menu('show',{
            left:$(this).offset().left - 80,
            top:$(this).offset().top + 35
        });
       
    }).hover(function(){
        $(this).addClass('l-btn l-btn-plain m-btn-plain-active');
    },function(){
        $(this).removeClass('l-btn l-btn-plain m-btn-plain-active');
    });
    
    $('#user-menu').bind('click',function(){
        openWindow(windowId,{width:550,height:300,title:'修改用户信息',url:urls.user}); 
    });
    $('#password-menu').bind('click',function(){
        openWindow(windowId,{width:550,height:230,title:'修改密码',url:urls.password}); 
    });
    $('#switch-menu').bind('click',function(){
        openWindow(windowId,{width:550,height:230,title:'站点切换',url:urls.siteswitch,iframeID:'#editifr'}); 
    });      
    $('#exit-menu').bind('click',function(){
        window.location = urls.exit;
    });
}

home.prototype.getPopMessage=function(url){
    var popInterval = this._popInterval;
    var currentAjax = $.ajax({
        type:'post',
        datatype:'json',
        cache:false,
        url:url + '?clientTime=' + new Date(),
        data: '',
        success:function(message, textStatus){
            if (message != 'false'){
                for (var i=0;i<message.length;i++){
                    $.messager.show({title:message[i].title,msg:message[i].content,width:350,height:200,timeout:0,showType:'fade'});
                }
            }
        },
        beforeSend:function(XMLHttpRequest){
        },
        complete:function(XMLHttpRequest, textStatus){
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            clearInterval(popInterval);
            if(currentAjax) {currentAjax.abort();}
        }
    });
}

home.prototype.setPopInterval=function(popInterval){
    this._popInterval = popInterval;
}

home.prototype.getNotice = function(url,detailUrl){
	var noticeInterval = this._noticeInterval;
	var currentAjax = $.ajax({
		type:'post',
		datatype:'json',
		cache:false,
		url: url,
		data: '',
		success:function(message, textStatus){
    		if (message != 'false'){
    			$('#notice .t-list').empty();
    			var noticesHtml = '<div class="t-list"><table width="100%">';
    			var pro = [];
        		for (var i=0;i<message.length;i++){
        			pro.push('<tr><td width="70%"><a href="javascript:void(0);" onclick="showRecord(\'' + detailUrl + '\',' + message[i].id + ');" style="text-decoration:none;" alt="' + message[i].title + '"><span class="ellipsis">' + message[i].title + '</span></a></td><td width="10%">[' + message[i].userName + ']' + '</td><td width="20%" align="right">' + message[i].sendTime + '</td></tr>');
        		}
        		var html = pro.join("");
        		noticesHtml += html + '</table></div>'
        		$(noticesHtml).appendTo('#notice');
    		}
		},
		beforeSend:function(XMLHttpRequest){
		},
		complete:function(XMLHttpRequest, textStatus){
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			clearInterval(noticeInterval);
			if(currentAjax) {currentAjax.abort();}
		}
	});
}

home.prototype.setNoticeInterval = function(noticeInterval){
	this._noticeInterval = noticeInterval;
}

home.prototype.getSubscription = function(url, detailUrl){
	var subscriptionInterval = this._subscriptionInterval;
	var currentAjax = $.ajax({
		type:'post',
		datatype:'json',
		cache:false,
		url: url,
		data: '',
		success:function(message, textStatus){
    		if (message != 'false'){
    			$('#subscription .t-list').empty();
    			var subscriptionHtml = '<div class="t-list"><table width="100%">';
    			var pro = [];
        		for (var i=0;i<message.length;i++){
        			pro.push('<tr><td width="70%"><a href="javascript:void(0);" onclick="showRecord(\'' + detailUrl + '\',' + message[i].id + ');" style="text-decoration:none;"><span class="ellipsis">' + message[i].title + '</span></a></td><td width="10%">[' + message[i].userName + ']' + '</td><td width="20%" align="right">' + message[i].sendTime + '</td></tr>');
        		}
        		var html = pro.join("");
        		subscriptionHtml += html + '</table></div>'
        		$(subscriptionHtml).appendTo('#subscription');
    		}
		},
		beforeSend:function(XMLHttpRequest){
		},
		complete:function(XMLHttpRequest, textStatus){
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			clearInterval(subscriptionInterval);
			if(currentAjax) {currentAjax.abort();}
		}
	});
}

home.prototype.setSubscriptionInterval = function(subscriptionInterval){
	this._subscriptionInterval = subscriptionInterval;
}

home.prototype.getTipMessage=function(url){
    var tipInterval = this._tipInterval;
    var currentAjax = $.ajax({
        type:'post',
        datatype:'json',
        cache:false,
        url:url,
        data: '',
        success:function(message, textStatus){
        	$('#tipMessage').empty();
        	var html = '<span id="messageFlash">';
            if (message != 'false'){
            	var tiplength = message.length;
            	html += '<a href="javascript:void(0);" onclick="javascript:_home.addTab(\'个人消息\',\'message/index.do\');return false;" onfocus="this.blur();" style="color:red;font-size:13px;text-decoration:none;">【<img src="./source/image/msg/msg_new.gif"/>新消息(' + tiplength + ')】</a>';
            }
            html += '</span>';
            $(html).appendTo('#tipMessage');
        },
        beforeSend:function(XMLHttpRequest){
        },
        complete:function(XMLHttpRequest, textStatus){
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            clearInterval(tipInterval);
            if(currentAjax) {currentAjax.abort();}
        }
    });
}

home.prototype.setTipInterval=function(tipInterval){
    this._tipInterval = tipInterval;
}

function showRecord(url, id){
	url = url + '&id=' + id;
	$('#editifr_detail').attr('src',url);
	ewcmsBOBJ.openWindow('#detail-window',{width:700,height:400,title:'内容'});
}