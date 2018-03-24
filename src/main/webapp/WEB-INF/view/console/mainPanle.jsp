<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath %>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>个人主页</title>
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/icon.css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/windowControl.js"></script>
<style>
<!--
*{font-size:16px; font-family:Tahoma,Verdana,微软雅黑,新宋体}
a{ color:Black; text-decoration:none;}
a:hover{ color:Red; text-decoration:underline;}
body
{ 
background-image:url('${basePath}/images/login/2013_right.jpg');
background-repeat:repeat;
background-position:left top;
padding:0px;
margin:0px;
}
.divcontent{
padding:0px;
margin:0px;
background-image:url('${basePath}/images/login/jianwei.jpg');
background-repeat:no-repeat;
background-attachment:fixed;
background-position:left top;
height:98%;
}
-->
</style>
<script type="text/javascript">
var tabIcon="icon icon-sys";
var winParent=window.parent;
function findNeedTodo(){
	$.post("${basePath}/needtodo/findNeedTodo",{},function(data){
		  $("#fileCheck").html(data);
		  winParent.findmsgCount();
	},'text');
}

function findMailList(){
	$.post( 
		 "${basePath}/msg/getMsg",
		 {"state":1,"limit":5},
		function(list) {
			 winParent.findmsgCount();
			 var mailInfo="<ul>";
			for(var i=0;i<list.length;i++){
				var msgcont=list[i];
			    mailInfo=mailInfo+"<li><a href='javascript:' onclick=\"msgdetail('"+msgcont.title+"','"+msgcont.id+"')\">"+msgcont.title+"["+msgcont.createTime+"]</a></li>";
			}
			mailInfo=mailInfo+"</ul>";
			
			if(list.length==0){
				mailInfo="您目前没有新邮件";
			}
			$("#mailList").html(mailInfo);
		},"json");
}
function suggestCheck(){
	<%--$.post( --%>
			 <%--"${basePath}/needtodo/canCheckCount",{},function(info){--%>
							<%--$("#tixingList").html(info);--%>
			  <%--},"text");--%>
}
$(function() {
		//findNeedTodo();
		//setInterval(findNeedTodo,600*1000);
		//findMailList();
		//findNoticeList();
		//suggestCheck();
		
});

function seeAtricle(id) {
	updateWin = $.createWin( {
		title : "公告详情",
		url : '${basePath}/article/prView',
		data : 'id=' + id,
		height : 650,
		width : 800,
		buttons : []
	});
}

function findNoticeList(){
	var fileresolt = false;
	//通知公告
		$.ajax( {
			type : "get",
			url : "${basePath}/article/getTzgg",
			dataType : "json",
			cache : false,
			success : function(data) {
					 var content="<ul>";
					 for(var i=0;i<data.length;i++){
						      var title=data[i].title;
						      if(title.length>20){
						    	   title=title.substring(0,20)+"……"
						      }
						      content=content+"<li><a href='javascript:void(0)' onclick=\"seeAtricle('"+data[i].id+"')\">"+title+"["+data[i].createTime+"]</a></li>";
					 }
					 content=content+"</ul>";
					 $("#formView").html(content);
			}
		});

	
}
function moreMail(){
	winParent.addTab('收件箱','${basePath}/msg/receiveList','icon icon-inbox');
}
function moreNotice(){
	winParent.addTab('通知公告查看','${basePath}/article/listSee','icon icon-shtz');
}
function msgdetail(title,id){
	var param='id=' +id;
	winParent.addTab(title,'${basePath}/msg/prView?'+param,'icon icon-readletter');
	findMailList();
}
</script>
</head>
<body>
<div class="divcontent">
<table border="0" width="99%" cellpadding="0" cellspacing="0" style="margin-top:10px;">
	<tr>
		<td valign="top">
			<table cellpadding="0" cellspacing="0" border="0" width="99%" style="height: 500px;" align="center">
			<tr>
				<td valign="top" width="46.9%">
						   <table width="98%" border="0" cellspacing="0" cellpadding="0" background="images/login2/778.gif" bgcolor="#F7F7F7" style="border-left: #1e76de 1px solid; border-right: #1e76de 1px solid; ">
						      <tr>
						        <td width="7%" align="center"><img src="images/login2/7781.gif" width="36" height="30"></td>
						        <td width="93%" align="left"><span style="float: right; margin-right: 10px;"><a href="javascript:void(0)" onclick="findNeedTodo()">刷新</a></span><span class="STYLE8">待办事宜</span></td>
						      </tr>
						    </table>
						<div style="padding:10px 10px; border: #1e76de 1px solid; border-top: 0; width:96%; height: 490px; overflow: hidden;" >
							<div id="fileCheck">
								<img src="images/qgqqqun.png">
							</div>
						</div>
				</td>

				<td valign="top" width="46.9%">
						   <table width="98%" border="0" cellspacing="0" cellpadding="0" background="images/login2/778.gif" bgcolor="#F7F7F7" style="border-left: #1e76de 1px solid; border-right: #1e76de 1px solid; ">
						      <tr>
						        <td width="7%" align="center"><img src="images/login2/7781.gif" width="36" height="30"></td>
						        <td width="93%" align="left"><span style="float: right; margin-right: 10px;"><a href="javascript:void(0)" onclick="moreNotice()">更多</a></span><span class="STYLE8">通知公告</span></td>
						      </tr>
						    </table>
						<div style="padding:10px 10px; border: #1e76de 1px solid; border-top: 0; width:98%; height: 490px; overflow: hidden;" >
							<div id="formView"><span style="font:16px; vertical-align: bottom;"></span></div>
						</div>
				</td>

			</tr>
			</table>
			<%--<table cellpadding="0" cellspacing="0" border="0" width="99%" align="center">
				<tr>

					<td valign="top" width="46.9%">
						   <table width="98%" border="0" cellspacing="0" cellpadding="0" background="images/login2/778.gif" bgcolor="#F7F7F7" style="border-left: #1e76de 1px solid; border-right: #1e76de 1px solid; ">
						      <tr>
						        <td width="7%" align="center"><img src="images/login2/7781.gif" width="36" height="30"></td>
						        <td width="93%" align="left"><span style="float: right; margin-right: 10px;"><a href="javascript:void(0)" onclick="moreMail()">更多</a></span><span class="STYLE8">未读邮件</span></td>
						      </tr>
						    </table>
						<div style="padding:10px 10px; border: #1e76de 1px solid; border-top: 0; width:98%; height: 250px; overflow: hidden;" >
							<div id="mailList">您目前没有新邮件</div>
						</div>


				</td>

					<td width="46.9%" valign="top">
								   <table width="98%" border="0" cellspacing="0" cellpadding="0" background="images/login2/778.gif" bgcolor="#F7F7F7" style="border-left: #1e76de 1px solid; border-right: #1e76de 1px solid; ">
								      <tr>
								        <td width="7%" align="center"><img src="images/login2/7781.gif" width="36" height="30"></td>
										  <td width="93%" align="left"><span style="float: right; margin-right: 10px;"><a href="javascript:void(0)" onclick="moreMail()">更多</a></span><span class="STYLE8">报表统计</span></td>
								      </tr>
								    </table>
								<div style="padding:10px 10px; border: #1e76de 1px solid; border-top: 0; width:98%; height: 150px; overflow: hidden;" >
									<div id="tixingList"></div>
								</div>
					</td>
				</tr>
			</table>--%>
		</td>
	</tr>
</table>
</div>
</body>
</html>