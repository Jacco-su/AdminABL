<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  ~ * Copyright (c) 2016-2020  版权所有 
  ~ * 
  --%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath",path);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title> 智能门禁综合管理信息平台</title>
	<link href="${basePath}/css/mainframe.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/icon.css" />
    <script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src='${basePath}/js/easyui/frameTool.js'> </script>
	<script type="text/javascript"	src="${basePath}/js/easyui/windowControl.js"></script>
	<script type="text/javascript"	src="${basePath}/js/easyui/validate.js"></script>
	<script type="text/javascript"	src="${basePath}/js/date.js"></script>
<Style>
	#css3menu li{ float:left; list-style-type:none; width:85px;height:29px;margin-left:15px; bottom: 0;}
	#css3menu li a{	color:#fff;  font-size: 12px;background:url(${basePath}/images/login2/index2_14.gif) left no-repeat; width: 85px; height: 29px; margin:0px;padding-top:8px;display:block;text-align: center;text-decoration:none;outline: none;}

	#css3menu li a.active {
		color: #4b93ff;
		background: url(${basePath}/images/login2/index2_12.gif) left no-repeat;
		text-decoration: none;
		outline: none;
	}

	#myindex {
		padding: 0px;
		background: url(${basePath}/images/xlogin/index.PNG) center;
	}
</style>
    <script type="text/javascript">
		function appInfo(){
        var browser = {appname: 'unknown', version: 0},
            userAgent = window.navigator.userAgent.toLowerCase();
    //IE,firefox,opera,chrome,netscape
        if ( /(msie|firefox|opera|chrome|netscape)\D+(\d[\d.]*)/.test( userAgent ) ){
            browser.appname = RegExp.$1;
            browser.version = RegExp.$2;
        } else if ( /version\D+(\d[\d.]*).*safari/.test( userAgent ) ){ // safari
            browser.appname = 'safari';
            browser.version = RegExp.$2;
        }
        return browser;
    }
    var bversion=(appInfo().version);
/*	function findmsgCount(){
		$.ajax( {
			type : "get",
			url : "$ {basePath}/msg/getMsg",
			data : "state=1&limit=1",
			dataType : "text",
			cache : false,
			success : function(countText) {
				   var msgcount=parseInt(countText);

				   if(msgcount<1||isNaN(msgcount)){
					          $("#msgcount").html("<a href='javascript:void(0)'  style='color:#FFFFFF;font-size:15px;'>无新消息</a>");
				   }else{
					          $("#msgcount").html("<a href='javascript:void(0)' onclick='personIndex()' style='color:red;font-size:15px;'>"+msgcount+"条新消息</a>");
				   }
			}
		});
	}*/


        var t;
    	var _menus = eval('('+'${module}'+')');
    	var syspath="${basePath}";
    	var changeYearWin;
        $(function() {
            var alterwin;
            var contents='<form action="${basePath}/log/alterPass" method="post" id="alterPassForm" name="alterPassForm">\
		    	<table class="mytable" align="center">\
		    		<tr>\
						<td width="25%">原密码--:</td>\
						<td><input type="password" name="oldpwd" size="30" class="easyui-validatebox" required="true" /></td>\
					</tr>\
		    		<tr>\
						<td>新密码:</td>\
						<td><input type="password" size="30" class="easyui-validatebox" required="true" id="password" name="password" validType="password"/></td>\
					</tr>\
					<tr>\
						<td>重复密码:</td>\
						<td><input  class="easyui-validatebox" size="30" type="password" required="true" validType="equalTo[\'#password\']" /></td>\
					</tr>\
		    	</table>\
		    	</form>';

            var changeYearContents = "<div style='text-align:center;padding:30px;'><select name='years' id='years' style='width:100px;' onchange='changeYear()'><option value='2017'>2017年</option><option value='2018'>2018年</option><option value='2019'>2019年</option></select></div>";
		    	
            $('#alterPass').click(function() {
            	alterwin = $.createWin( {
					title : "修改密码",
					contents:contents,
					height : 200,
					width : 400,
					buttons : [ {
						text : '修改',
						iconCls : 'icon-edit',
						handler : function(){
							$('#alterPassForm').form('submit', {
								onSubmit:function(){   
									return $(this).form('validate');  
					   			},
								success : function(data) {
									var json=eval("("+data+")");
									if(json.result=='1'){
										$.messager.alert('提示','保存成功', 'warning');
											$.closeWin(alterwin);
									}else{
										$.messager.alert('提示','保存失败', 'warning');
									}
								}
							});
						}
					} ]
				});
            });
            
            $('#changeYear').click(function() {
            	changeYearWin = $.createWin( {
					title : "切换年度",
					contents:changeYearContents,
					height : 200,
					width : 400,
					buttons : []
				});
            	
            	$("#years").val($("#changeYear").html());
            });
            
            $('#loginOut').click(function() {
            	location.href= '${basePath}/log/logout';
            });

			function msg(){
				$.ajax( {
					type : "get",
					url : "${basePath}/msg/getMsg",
					data : "state=1&limit=1",
					dataType : "text",
					cache : false,
					success : function(msgs) {
						msgs = eval('(' + msgs + ')');
						$.each(msgs,function(i,msg){
							$.messager.show( {
								title : '您有新的邮件',
								msg : "<a href='javascript:' onclick=\"msgdetail('"+msg.title+"','"+msg.id+"')\">"+msg.title+"["+msg.createTime+"]</a>",
								timeout : 5000,
								showType : 'fade'
							});
						});
					}
				});
			}

			//msg();
			//findmsgCount();
            //setInterval(msg,120*1000);
           // setInterval(findmsgCount,60*1000);
            tick();
            $(".tabs-inner").click(function(){
            	initMainPanle();
    		});

        });
        
        function msgdetail(title,id){
        			var param='id=' +id;
        	         addTab(title,'${basePath}/msg/prView?'+param,'icon icon-readletter');
        }
        function initMainPanle(){
        	
        }
        function showLocale(objD)
        {
        	var str,colorhead,colorfoot;
        	var yy = objD.getYear();
        	if(yy<1900) yy = yy+1900;
        	var MM = objD.getMonth()+1;
        	if(MM<10) MM = '0' + MM;
        	var dd = objD.getDate();
        	if(dd<10) dd = '0' + dd;
        	var hh = objD.getHours();
        	if(hh<10) hh = '0' + hh;
        	var mm = objD.getMinutes();
        	if(mm<10) mm = '0' + mm;
        	var ss = objD.getSeconds();
        	if(ss<10) ss = '0' + ss;
        	var ww = objD.getDay();
        	if  ( ww==0 )  colorhead="";
        	if  ( ww > 0 && ww < 6 )  colorhead="";
        	if  ( ww==6 )  colorhead="";
        	if  (ww==0)  ww="星期日";
        	if  (ww==1)  ww="星期一";
        	if  (ww==2)  ww="星期二";
        	if  (ww==3)  ww="星期三";
        	if  (ww==4)  ww="星期四";
        	if  (ww==5)  ww="星期五";
        	if  (ww==6)  ww="星期六";
        	colorfoot=""
        	str = colorhead + yy + "-" + MM + "-" + dd + " " + hh + ":" + mm +  "  " + ww + colorfoot;
        	return(str);
        }
        function tick()
        {
        	var today;
        	today = new Date();
        	document.getElementById("localtime").innerHTML = showLocale(today);
        	window.setTimeout("tick()", 1000);
        }
        
        function personIndex(){
            $("#msgcount").html("<a href='javascript:void(0)'  style='color:#cff7ff;font-size:15px;'>消息查看中……</a>");
 	      				addTab('新消息','${basePath}/needtodo/showNewmsg','');
         }
        function userCenter(){
        				addTab('个人中心','${basePath}/log/userCenter','');
        }
        function changeYear(){
        			var year=$("#years").val();
        			$.post("${basePath}/log/changeYear",{"year":year},function(json){
        				          $.messager.alert('提示',json.message, 'warning');
        						  if(json.result==1){
        							      $("#changeYear").html(year);
        							      $.closeWin(changeYearWin);
        								   $('.tabs-inner span').each(function(i,n){
        										var t = $(n).text();
        										if(t=='个人主页') return true;
        										$('#tabs').tabs('close',t);
        									});
        								   $("#personIndex").attr("src","${basePath}/log/mainPanle");
        						  }
        			},"json");
        }
        function initblankhref(hrefvalue){
        	  $("#blankhref").attr("href",hrefvalue);
        	  $("#blankhref")[0].click();
        }
</script>
</head>
<body style="margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;" onload="initMainPanle()">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="${basePath}/images/framework/images/noscript.gif" alt='抱歉，请开启脚本支持!' />
</div></noscript>
    <div style="position:relative;roverflow: hidden; height: 34px;">
    	<%--<table border="0" cellpadding="0" cellspacing="0" width="100%" style="background: url(${basePath}/images/login2/index_09_m.jpg) repeat-x">
    		<tr>
    		    <td height="86" width="900" style="background:url(${basePath}/images/login2/index_09_l.jpg) no-repeat"></td>
    			<td height="86" style="background:url(${basePath}/images/login2/index_09_r.jpg) no-repeat"><span id="msgcount" style="float:right;margin-right:300px;">&nbsp;</span></td>
    		</tr>
    	</table>--%>
        <div style="position:absolute;bottom:0px; right: 0px; left:0px;background-color:#2668A4">
        	<div style="position:absolute;bottom: 0px;">
	        <ul id="css3menu">
	        	<c:forEach items="${root}" var="menu" varStatus="s">
				<li><a  hidefocus=true <c:if test="${s.index==0}"> class="active" </c:if> name="${menu.name}" href="javascript:;" title="${menu.menuname}">${menu.menuname}</a></li>
				</c:forEach>
			</ul>
			</div>
			
			<div style="float: right;width: 256px; height: 33px;">
				<div style=" float: right;margin-top: 6px;margin-right: 40px;background-color:#BDECFE;padding:2px 10px 2px 10px">
				<a href="javascript:void(0)" onclick="userCenter();return false;">个人中心</a>&nbsp;&nbsp;<a href="javascript:void(0)" id="alterPass">修改密码</a>&nbsp;&nbsp;<a href="${basePath}/log/logout" id="loginOut">安全退出</a>
				</div>
			</div>
	        <div style="float:right; padding-right:20px;margin-top: 8px;color:#fff; font-weight: bold;">
	        <span style="letter-spacing:2px;" id="localtime">2015-06-19 11:34</span>
	        
	        <span style="letter-spacing:2px;">&nbsp;&nbsp;${dept.name}&nbsp;&nbsp;${admin.username}</span></div>
        </div>
    </div>
    <div class="easyui-layout" style="overflow-y: hidden; height:100%;width: 100%" scroll="no">
	    <div region="west" hide="false" split="flase" title="导航菜单" style="width:180px;" id="west">
			<div id="wnav" class="easyui-accordion" fit="false" border="false"></div>
	    </div>
	    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden;" scroll="no">
	        <div id="tabs" class="easyui-tabs"  fit="true" border="false" scroll="no">
				<div title="个人主页" id="myindex">
					<%--<img src="${basePath}/images/xlogin/index.PNG">--%>
					<%--<iframe scrolling="auto" frameborder="0"  id="personIndex" src="${basePath}/log/mainPanle" style="width:100%;height:100%"></iframe>--%>
							
						</div>
			</div>
			<div style="display:none">
						 <a href="" id="blankhref" target="_blank">&nbsp;&nbsp;</a>
			</div>
	    </div>
    </div>
     <div style="padding:5px;text-align:center;">
            @版权所有 郑州海威光电科技有限公司
    </div>
    <div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>