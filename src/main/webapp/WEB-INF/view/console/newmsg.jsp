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
<title>新消息</title>
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
background-repeat:repeat-x;
background-position:left top;
padding:0px;
margin:0px;
}
.divcontent{
padding:10px 10px 10px 0px;
margin:0px;
background-image:url('${basePath}/images/login/2013.jpg');
background-repeat:no-repeat;
background-attachment:fixed;
background-position:left top;
height:540px;
}
-->
</style>
<script type="text/javascript">
var tabIcon="icon icon-sys";
var winParent=window.parent;
function findNeedTodo(){
	$.post("${basePath}/needtodo/findNeedTodo",{},function(data){
		  $("#fileCheck").html(data);
	},'text');
}

function findMailList(){
	$.post( 
		 "${basePath}/msg/getMsg",
		 {"state":1,"limit":5},
		function(list) {
			 var mailInfo="<ul>";
			for(var i=0;i<list.length;i++){
				var msgcont=list[i];
			    mailInfo=mailInfo+"<li><a href='javascript:' onclick=\"msgdetail('"+msgcont.title+"','"+msgcont.id+"')\">"+msgcont.title+"</a>["+msgcont.createTime+"]</li>";
			}
			mailInfo=mailInfo+"</ul>";
			
			if(list.length==0){
				mailInfo="您目前没有新邮件";
			}
			$("#mailList").html(mailInfo);
		},"json");
}
$(function() {
		//findNeedTodo();
		//findMailList();
});

function todoList(type){
	if(type==1){
		winParent.addTab('待提交绩效','${basePath}/scorecheck/needcmt',tabIcon);
	}
	if(type==2){
		winParent.addTab('待提交考核标准','${basePath}/citemcheck/needcmt',tabIcon);
	}
	if(type==3){
		winParent.addTab('待审核标准','${basePath}/citemcheck/needCheck',tabIcon);
	}
	if(type==4){
		winParent.addTab('待审核绩效','${basePath}/scorebatchcheck/needCheck',tabIcon);
	}
	if(type==5){
		winParent.addTab('待审核申诉','${basePath}/complaint/needCheck',tabIcon);
	}
	if(type==6){
		winParent.addTab('部门绩效审批','${basePath}/dsbatchcheck/dsneedCheck',tabIcon);
	}
	if(type==7){
		winParent.addTab('待提交部门绩效','${basePath}/dscheck/dsNeedcmt',tabIcon);
	}
	if(type==8){
		winParent.addTab('待审核部门申诉','${basePath}/deptcomplaint/needCheck',tabIcon);
	}
}


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
function moreMail(){
	winParent.addTab('收件箱','${basePath}/msg/receiveList','icon icon-inbox');
}
function moreNotice(){
	winParent.addTab('通知公告查看','${basePath}/article/listSee','icon icon-shtz');
}
function msgdetail(title,id){
	var param='id=' +id;
	winParent.addTab(title,'${basePath}/msg/prView?'+param,'icon icon-readletter');
}
</script>
</head>
<body>
<div class="divcontent">
<table border="0" width="99%" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
			<table cellpadding="0" cellspacing="0" border="0" width="99%" style="height: 200px;" align="center">
			<tr>
				<td valign="top" width="46.9%">
						   <table width="98%" border="0" cellspacing="0" cellpadding="0" background="images/login2/778.gif" bgcolor="#F7F7F7" style="border-left: #1e76de 1px solid; border-right: #1e76de 1px solid; ">
						      <tr>
						        <td width="7%" align="center"><img src="images/login2/7781.gif" width="36" height="30"></td>
						        <td width="93%" align="left"><span style="float: right; margin-right: 10px;"><a href="javascript:void(0)" onclick="findNeedTodo()">刷新</a></span><span class="STYLE8">待办事宜</span></td>
						      </tr>
						    </table>
						<div style="padding:10px 10px; border: #1e76de 1px solid; border-top: 0; width:98%; height: 150px; overflow: hidden;" >
							<div id="fileCheck">您目前没有要办理的事项</div>
						</div>
				</td>

					<td valign="top" width="46.9%">
						   <table width="98%" border="0" cellspacing="0" cellpadding="0" background="images/login2/778.gif" bgcolor="#F7F7F7" style="border-left: #1e76de 1px solid; border-right: #1e76de 1px solid; ">
						      <tr>
						        <td width="7%" align="center"><img src="images/login2/7781.gif" width="36" height="30"></td>
						        <td width="93%" align="left"><span style="float: right; margin-right: 10px;"><a href="javascript:void(0)" onclick="moreMail()">更多</a></span><span class="STYLE8">未读邮件</span></td>
						      </tr>
						    </table>
						<div style="padding:10px 10px; border: #1e76de 1px solid; border-top: 0; width:98%; height: 150px; overflow: hidden;" >
							<div id="mailList">您目前没有新邮件</div>
						</div>
					
					
				</td>
			</tr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" width="99%" align="center">
				<tr>
					<td width="46.9%" valign="top">
					&nbsp;
					</td>
					<td width="46.9%" valign="top">
					&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>