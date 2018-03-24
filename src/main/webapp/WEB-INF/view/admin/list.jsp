<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户列表</title>
<link rel="stylesheet" type="text/css"
	href="${basePath}/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/js/easyui/themes/icon.css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/windowControl.js"></script>
<script type="text/javascript"	src="${basePath}/js/easyui/validate.js"></script>
<script type="text/javascript">
    var basePath="${basePath}";
	$(function() {
		var deptId = "";
		var infolist = $('#infolist');
		infolist.datagrid( {
			title : '用户列表',
			iconCls : 'icon-users',
			width : '95%',
			height : 560,
			pageSize : 20,
			pageList : [ 20, 30, 50, 100 ],
			nowrap : false,
			striped : true,
			collapsible : false,
			fitColumns : true,
			singleSelect : true,
			url : '${basePath}/user/list',
			queryParams:{
				'deptId':deptId
			},
			loadMsg : '数据装载中......',
			remoteSort : false,
			onDblClickRow:function(rowIndex, field, value){
				var rows=infolist.datagrid("getRows");
				var id=rows[rowIndex].id;
				showEdit(id);
			},
			columns : [ [ 
			{
                title: '区域',
				field : 'deptname',
				formatter : function(value,rowData,rowIndx) {
					   return rowData.dept.name;
				},
				width : $(this).width() * 0.1,
				align : 'center'
			},
                {
                    title : '工号',
                    field : 'id',
                    width : $(this).width() * 0.1,
                    rowspan : 2,
                    align : 'center'
                },

                {
				title : '帐户',
				field : 'name',
				width : $(this).width() * 0.1,
				rowspan : 2,
				align : 'center'
			},{
				title : '用户名',
				field : 'username',
				width : $(this).width() * 0.1,
				rowspan : 2,
				align : 'center'
			},{
				title : '手机',
				field : 'phone',
				width : $(this).width() * 0.1,
				rowspan : 2,
				align : 'center'
			}, {
				title : '邮箱',
				field : 'email',
				width : $(this).width() * 0.1,
				rowspan : 2,
				align : 'center'
			}, {
				title : '注册时间',
				field : 'rdate',
				width : $(this).width() * 0.1,
				rowspan : 2,
				align : 'center'
			}, {
				title : '角色',
				field : 'roles',
				formatter : function(value, rec) {
					var t = "";
					$.each(value, function(i, v) {
						t += v.name + " ";
					});
					return t;
				},
                    width: $(this).width() * 0.1,
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
			},'-', {
				text : '重置密码',
				iconCls : 'icon-revise',
				handler : initPassword
			},'-', {
				text : '查询',
				iconCls : 'icon-search',
				handler : search
			} ]
		});
		displayMsg();

		function refresh() {
			infolist.datagrid( {
				url : '${basePath}/user/list',
				queryParams:{
					'deptId':deptId
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
		
		function initPassword(){
			var select = infolist.datagrid('getSelected');
			if (select) {
					$.messager.confirm('警告', '确定要重置密码?', function(f) {
						if (f) {
								  $.post("${basePath}/user/initPassword",{"id":select.id},function(json){
									  	$.messager.alert('提示', json.message, 'warning');
								  },"json");
						}
					});
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
					var json=eval("("+data+")");
					if(json.result=='1'){
						$.messager.alert('提示', '保存成功', 'warning');
						$.closeWin(addWin);
						refresh();
					}else{
						$.messager.alert('提示', '保存失败', 'warning');
						$.closeWin(addWin);
						refresh();
					}

				}
			});
		}

		function add() {
			if (deptId != "") {
				addWin = $.createWin( {
					title : "用户添加",
					url : basePath+'/user/prAdd?deptId='+deptId,
					height : 350,
					width : 550,
					buttons : [ {
						text : '保存',
						iconCls : 'icon-ok',
						handler : save
					} ]
				});
			}else{
                $.messager.alert('警告', '请选择一个区域', 'warning');
			}
		}
		function edit() {
			var select = infolist.datagrid('getSelected');
			if (select) {
				showEdit(select.id);
			} else {
				$.messager.alert('警告', '请选择一行数据', 'warning');
			}
		}
		function showEdit(id){
					updateWin = $.createWin( {
						title : "用户修改",
						url : basePath+'/user/prUpdate',
						data : 'id=' +id,
						height : 350,
						width : 550,
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
	        		return $(this).form('validate');   
	   			},
				success : function(data) {
					var json=eval("("+data+")");
					if(json.result=='1') {
						$.messager.alert('提示', '保存成功', 'warning');
					}else{
						$.messager.alert('提示', '保存失败', 'warning');
					}
					$.closeWin(updateWin);
					infolist.datagrid('reload');
				}
			});
		}
		function del() {
			var id = "";
			var selections = infolist.datagrid('getSelections');
			if (selections.length > 0) {
				for (i = 0; i < selections.length; i++) {
					id = id + selections[i].id + ',';
				}
				$.messager.confirm('警告', '确认删除么?', function(f) {
					if (f) {
						$.ajax( {
							type : "POST",
							url : basePath+"/user/delete",
							data : "ids=" + id,
							dataType : "text",
							cache : false,
							success : function(msg) {
								$.messager.alert('提示', '删除成功!', 'warning');
								infolist.datagrid('reload');
							}
						});
					}
				});
			} else {
				$.messager.alert('警告', '请选择一行数据', 'warning');
			}
		}
		
		function search(){
			addWin = $.createWin( {
				title : "查询条件",
				contents:"<table style='font-size:12px;'><tr><td>用户姓名：</td><td><input id=username /></td></tr></table>",
				width : 300,
				buttons : [ {
					text : '查询',
					iconCls : 'icon-search',
					handler : query
				} ]
			});
		}
		function query() {
			infolist.datagrid( {
				url : basePath+'/user/list',
				queryParams:{
					'username':$('#username').val()
				},
				loadMsg : '数据装载中......'
			});
			infolist.datagrid("clearSelections");
			displayMsg();
			$.closeWin(addWin);
		}

		$('#tree').tree({   
			checkbox: false,   
			url: basePath+'/dept/getChildren', 
			onBeforeExpand:function(node,param){
				$('#tree').tree('options').url = basePath+"/dept/getChildren?parentId=" + node.id;                      
			},               
			onClick:function(node){
			     deptId = node.id;
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
			<div class="panel-header" style="border-left: 0; border-right: 0;">区域</div>
			<ul id="tree" style="margin-top: 10px;  height: 500px; overflow: scroll;">
		    </ul>
		</div>
		</td>
		<td valign="top">
		<table id="infolist"></table>
		</td>
	</tr>
</table>
<div id="selectDept">
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding: 10px;">
			<ul id="deptTree" style="margin-top: 10px;"></ul>
		</div>
		<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
			<a class="easyui-linkbutton"  icon="icon-ok" onclick="selectDept();">确定</a>
			<a class="easyui-linkbutton" icon="icon-cancel" onclick="$('#selectDept').window('close');">关闭</a>
		</div>
	</div>
</div>
</body>
</html>
