<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户列表</title>
<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
	function dbbark(){
			$("#barkresult").html("正在备份……");
			var url="${basePath}/user/dbbark";
			var data={};
			$.post(url,data,function(text){
					$("#barkresult").html(text);
			},"text");
	}
</script>
</head>
<body>
<div>
                <p style="color:green">备注：系统每天晚上11:30会自动备份。</p>
				<p><input type="button" style="width:200px;height:50px;padding:12px;text-align:center;"  value="点击备份" onclick="dbbark();"/></p>
				<p id="barkresult" style="color:green"></p>
</div>
</body>
</html>
