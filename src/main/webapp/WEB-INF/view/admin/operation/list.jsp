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
<title>操作列表</title>
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/icon.css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/windowControl.js"></script>
<script type="text/javascript">
	$(function() {
		var infolist = $('#infolist');
		var moduleId = "";
		infolist.datagrid( {
			title : '操作列表',
			idField : "opId",
			iconCls : 'icon-users',
			width : '95%',
			height : 560,
			pageSize : 20,
			pageList : [ 20, 30, 50, 100 ],
			nowrap : false,
			striped : true,
			collapsible : true,
			fitColumns : true,
			url : '${basePath}/operation/list',
			queryParams:{
				'moduleId':moduleId
			},
			loadMsg : '数据装载中......',
			sortName : 'opId',
			sortOrder : 'desc',
			remoteSort : false,
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			} ] ],
			columns : [ [ {
				title : '操作名',
				field : 'name',
				width : $(this).width() * 0.4,
				rowspan : 2,
				align : 'center'
			}, {
				title : '操作URL',
				field : 'opt',
				width : $(this).width() * 0.6,
				rowspan : 2,
				align : 'center'
			} ] ],
			pagination : true,
			rownumbers : true,
			toolbar : [ {
				text : '添加',
				iconCls : 'icon-add',
				handler : add
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : edit
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : del
			}]
		});
		displayMsg();

		function refresh() {
			infolist.datagrid( {
				url : '${basePath}/operation/list',
				queryParams:{
					'moduleId':moduleId
				},
				loadMsg : '数据装载中......'
			});
			infolist.datagrid("clearSelections"); 
			displayMsg();
		}
		function displayMsg() {
			infolist.datagrid('getPager').pagination( {
				beforePageText : '第',
				afterPageText : '页，共{pages}页',
				displayMsg : '当前显示从{from}到{to}共{total}记录'
			});
		}
		var addWin;
		var updateWin;
		function getSelect() {
			var select = infolist.datagrid('getSelected');
			if (select) {
				$('#addFrom').window('open');
			} else {
				$.messager.alert('警告', '请选择一行数据', 'warning');
			}
		}
		function save() {
			$('#addForm').form('submit', {
				onSubmit:function(){   
	        		return $(this).form('validate');   
	   			},
				success : function(data) {
					$.messager.alert('提示', '添加成功!');
					$.closeWin(addWin);
					refresh();
				}
			});
		}

		function add() {
			addWin = $.createWin( {
				title : "操作添加",
				url : '${basePath}/operation/prAdd?cls='+moduleId,
				height : 320,
				width : 500,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-ok',
					handler : save
				} ]
			});
		}
		function edit() {
			var select = infolist.datagrid('getSelected');
			if (select) {
				updateWin = $.createWin( {
					title : "操作修改",
					url : '${basePath}/operation/prUpdate',
					data : 'id=' + select.opId,
					height : 320,
					width : 500,
					buttons : [ {
						text : '修改',
						iconCls : 'icon-ok',
						handler : update
					} ]
				});
			} else {
				$.messager.alert('警告', '请选择一行数据', 'warning');
			}
		}
		function update() {
			$('#editForm').form('submit', {
				onSubmit:function(){   
	        		return $(this).form('validate');   
	   			},
				success : function(data) {
					$.messager.alert('提示', '修改成功!');
					$.closeWin(updateWin);
					refresh();
				}
			});
		}
		function del() {
			var id = "";
			var selections = infolist.datagrid('getSelections');
			if (selections.length > 0) {
				for (i = 0; i < selections.length; i++) {
					id = id + selections[i].opId + ',';
				}
				if(id!=""){
					id=id.substring(0,id.length-1);
				}
				$.messager.confirm('警告', '确认删除么?', function(f) {
					if (f) {
						$.ajax( {
							type : "POST",
							url : "${basePath}/operation/delete",
							data : "ids=" + id,
							dataType : "text",
							cache : false,
							success : function(msg) {
								$.messager.alert('提示', '删除成功!');
								refresh();
							}
						});
					}
				});
			} else {
				$.messager.alert('警告', '请选择一行数据', 'warning');
			}
		}

		$('#tree').tree({   
			checkbox: false,   
			url: '${basePath}/module/getChildren',   
			onBeforeExpand:function(node,param){
				$('#tree').tree('options').url = "${basePath}/module/getChildren?parentId=" + node.id;                      
			},               
			onClick:function(node){             
				moduleId = node.id;
			    refresh();
			}   
		}); 
	});
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="12%" valign="top" style="border: 1px solid #99bbe8; border-right: 0;">
		<div style="width: 100%;">
			<div class="panel-header" style="border-left: 0; border-right: 0;">模块</div>
			<ul id="tree" style="margin-top: 10px;  height: 500px; overflow: scroll;">
		    </ul>
		</div>
		</td>
		<td valign="top">
		<table id="infolist"></table>
		</td>
	</tr>
</table>
</body>
</html>
