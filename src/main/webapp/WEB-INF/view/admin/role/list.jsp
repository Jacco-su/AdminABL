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
<title>角色列表</title>
<link rel="stylesheet" type="text/css"	href="${basePath}/css/mainframe.css" />
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="${basePath}/js/easyui/themes/icon.css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/windowControl.js"></script>
<script type="text/javascript">
	$(function() {
		var infolist = $('#infolist');
		infolist.datagrid( {
			title : '角色列表',
			iconCls : 'icon-users',
			width : '95%',
			height : 560,
			pageSize : 20,
			pageList : [ 20, 30, 50, 100 ],
			nowrap : false,
			striped : true,
			collapsible : true,
			fitColumns : true,
			url : '${basePath}/role/list',
			loadMsg : '数据装载中......',
			sortName : 'orderId',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect:true,
			onDblClickRow:function(rowIndex, field, value){
				var rows=infolist.datagrid("getRows");
				var id=rows[rowIndex].roId;
				showEdit(id);
			},
			columns : [ [
		{
				title : '角色名',
				field : 'name',
				width : $(this).width() * 0.7,
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
				url : '${basePath}/role/list',
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
		var id;
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
					var v =  $(this).form('validate');   
	        		if(v){
		        		var ms = "";
		        		var ops = "";
	        			var selections = $('#ops').tree('getChecked');
	        			for (i = 0; i < selections.length; i++) {
	        				if(!selections[i].iconCls){
	        					ops +=  selections[i].id + ',';
	        				}else{
	        					ms +=  selections[i].id + ',';
	        				}
	        			}
	        			if(ms.length>0){
	        				ms=ms.substring(0,ms.length-1);
	        			}
	        			if(ops.length>0){
	        				ops=ops.substring(0,ops.length-1);
	        			}
	        			$("#opIdList").val(ops);
	        			$("#modules").val(ms);
	        		}
	        		return v; 
	   			},
				success : function(data) {
					$.messager.show( {
						title : '温馨提示:',
						msg : '添加成功!',
						timeout : 5000,
						showType : 'slide'
					});
					$.closeWin(addWin);
					refresh();
				}
			});
		}

		function add() {
			addWin = $.createWin( {
				title : "角色添加",
				url : '${basePath}/role/prAdd',
				height : 550,
				width : 800,
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
				showEdit(select.roId);
			} else {
				$.messager.alert('警告', '请选择一行数据', 'warning');
			}
		}
		function showEdit(id){
			updateWin = $.createWin( {
				title : "角色修改",
				url : '${basePath}/role/prUpdate',
				data : 'id=' + id,
				height : 550,
				width : 800,
				buttons : [ {
					text : '修改',
					iconCls : 'icon-ok',
					handler : update
				} ]
			});
		}
		function update() {
			$('#editForm').form('submit', {
				onSubmit:function(){   
	        		var v =  $(this).form('validate');   
	        		if(v){
	        			var ms = "";
		        		var ops = "";
		        		var fs = "";
	        			var selections = $('#ops').tree('getChecked');
	        			for (i = 0; i < selections.length; i++) {
	        				if(!selections[i].iconCls){
	        					ops +=  selections[i].id + ',';
	        				}else{
	        					ms +=  selections[i].id + ',';
	        				}
	        			}
	        			if(ms.length>0){
	        				ms=ms.substring(0,ms.length-1);
	        			}
	        			if(ops.length>0){
	        				ops=ops.substring(0,ops.length-1);
	        			}
	        			$("#opIdList").val(ops);
	        			$("#modules").val(ms);
	        		}
	        		return v;
	   			},
				success : function(data) {
					$.messager.show( {
						title : '温馨提示:',
						msg : '修改成功!',
						timeout : 5000,
						showType : 'slide'
					});
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
					id = id + selections[i].roId + ',';
				}
				$.messager.confirm('警告', '确认删除么?', function(f) {
					if (f) {
						$.ajax( {
							type : "POST",
							url : "${basePath}/role/delete",
							data : "ids=" + id,
							dataType : "text",
							cache : false,
							success : function(msg) {
								$.messager.show( {
									title : '温馨提示:',
									msg : '删除成功!',
									timeout : 5000,
									showType : 'slide'
								});
								refresh();
							}
						});
					}
				});
			} else {
				$.messager.alert('警告', '请选择一行数据', 'warning');
			}
		}
		function query() {
			var queryParams = infolist.datagrid('options').queryParams;
			queryParams.queryWord = $('#qq').val();
			infolist.datagrid( {
				url : 'easyQuery.action'
			});
			displayMsg();
			$('#query').window('close');
		}
	});
</script>
</head>
<body>

<table id="infolist"></table>
</body>
</html>
