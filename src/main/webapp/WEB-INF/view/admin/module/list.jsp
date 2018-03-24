<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模块列表</title>
<link rel="stylesheet" type="text/css"
	href="${basePath}/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/icon.css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/windowControl.js"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/toolbar.js"></script>
<script type="text/javascript">
	$(function() {
		var addWin;
		$('#tree').tree({
			checkbox : false,
			url : 'getChildren',
			onBeforeExpand : function(node, param) {
				$('#tree').tree('options').url = "${basePath}/module/getChildren?parentId="
					+ node.id;
				},
				onClick : function(node) {
					$.ajax( {
						type : "get",
						url : "${basePath}/module/prUpdate",
						data : "id=" + node.id,
						dataType : "text",
						cache : false,
						success : function(data) {
							$("#module").html(data);
						}
					});
				}
			});
		function refresh(){
			$('#tree').tree({url : '${basePath}/module/getChildren'});
		}
		function save() {
			$('#addForm').form('submit', {
				onSubmit:function(){   
	        		return $(this).form('validate');   
	   			},
				success : function(data) {
					$.messager.alert('提示','添加成功!');
					refresh();
					$.closeWin(addWin);
				}
			});
		}
		function add(parentId){
			addWin = $.createWin( {
				title : "模块添加",
				url : '${basePath}/module/prAdd?parentId='+parentId,
				height : 320,
				width : 500,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-ok',
					handler : save
				} ]
			});
		}
		$('#toolbar').toolbar({
			items:[{
				iconCls:"icon-add",
				text:"添加根节点",
				handler:function(){
					add('null');
				}
			},"-",{
				iconCls:"icon-add",
				text:"添加子节点",
				handler:function(){
					var selected=$('#tree').tree('getSelected');
					if(selected){
						add(selected.id);
					}else{
						$.messager.alert('警告', '请选择父节点', 'warning');
					}
				}
			},"-",{
				iconCls:"icon-save",
				text:"保存修改",
				handler:function(){
					$('#editForm').form('submit', {
						onSubmit:function(){   
			        		return $(this).form('validate');   
			   			},
						success : function(data) {
							$.messager.alert('提示','修改成功!');
						}
					});
				}
			},"-",{
				iconCls:"icon-remove",
				text:"删除",
				handler:function(){
					var selected=$('#tree').tree('getSelected');
					if(selected){
						$.messager.confirm('警告', '确认删除么?', function(f) {
							if (f) {
								$.ajax( {
									type : "get",
									url : "${basePath}/module/delete",
									data : "ids=" + selected.id,
									dataType : "json",
									cache : false,
									success : function(msg) {
										$.messager.alert('提示',msg.message);
										if(msg.result==1){
													$("#module").html("");
													refresh();
										}
									}
								});
							}
						});
					}else{
						$.messager.alert('警告', '请选择一行数据', 'warning');
					}
				}
			}]
		});
	});
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="530">
	<tr>
		<td width="12%" valign="top"
			style="border: 1px solid #99bbe8; border-right: 0;">
			<div class="panel-header" style="border-left: 0; border-right: 0;">模块 </div>
			<ul id="tree" style="margin-top: 10px;"></ul>
		</td>
		<td valign="top" style="border: 1px solid #99bbe8;">
			<div class="panel-header" style="border-left: 0; border-right: 0;">修改模块</div>
			<div id="toolbar"></div>
			<div id="module"></div>
		</td>
	</tr>
</table>
</body>
</html>
