<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath %>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心</title>
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/icon.css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}/js/sha1.js"></script>
<script type="text/javascript">
function updateInfo(){
        var phoneReg=/^\d{11}$/;
		var phone=$("#phone").val();
		phone=$.trim(phone);
		if(phone!=""){
				if(phoneReg.test(phone)==false){
					alert("手机号码格式不正确");
					$("#phone").focus();
					return;
				}
		}
		var emailReg=/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var email=$("#email").val();
		email=$.trim(email);
		if(email!=""){
			  if(emailReg.test(email)==false){
					alert("邮箱格式不正确");
					$("#email").focus();
					return;
			  }
		}
	    var haskh=$('#haskh').val();
		var data={"phone":phone,"email":email,"haskh":hex_sha1(haskh)};
		var url="${basePath}/user/updateInfo";
		$.post(url,data,function(json){
			     alert(json.message);
		},"json");
}
</script>
<style type="text/css">
.span6{
       width:300px;
}
.span1{
       width:80px;
}
a.download{ color:#436493; text-decoration:none;}
a.download:hover{ color:Red; text-decoration:underline;}

</style>
</head>
<body>
<div>
<table class="mytable" width="800">
<tr><td  width="100">账号：</td><td>${user.name}</td></tr>
<tr><td  width="100">姓名：</td><td>${user.username}</td></tr>
    <tr>
        <td width="100">区域：</td>
        <td>${user.dept.name}</td>
    </tr>
<tr><td  width="100">手机：</td><td><input type="text" id="phone" style="width: 200px;" value="${user.phone}" /></td></tr>
<tr><td  width="100">Email：</td><td><input type="text" id="email" style="width: 200px;" value="${user.email}" /></td></tr>
	<c:if test="${operateMap['check_detail_show']==1}">
	<tr><td  width="100">查看密码：
	</td><td><input type="text" id="haskh" value="" style="width: 200px;"/></td></tr>
	</c:if>
<tr><td colspan="2" align="center"><input type="button"   value="保 存" onclick="updateInfo();"/></td></tr>
<tr>
<td  width="100">备注：</td>
<td>
<span style="color:green">系统部分功能在IE6浏览器下显示效果可能不太好，建议更换成360浏览器</span>
</td>
</tr>
</table>
</div>
</body>