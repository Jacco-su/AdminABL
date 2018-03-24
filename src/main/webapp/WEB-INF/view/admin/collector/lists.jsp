<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>采集器列表</title>
    <link rel="stylesheet" type="text/css"
          href="${basePath}/js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css"
          href="${basePath}/js/easyui/themes/icon.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/windowControl.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/toolbar.js"></script>
    <script type="text/javascript">
        var basePath="${basePath}";
        $(function() {
            var deptId = "";
            var infolist = $('#infolist');
            infolist.datagrid( {
                title: '采集器列表',
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
                url : '${basePath}/collector/list',
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
//                    {
//                        title: '区域',
//                        field : 'deptname',
//                        formatter : function(value,rowData,rowIndx) {
//                            return rowData.dept.name;
//                        },
//                        width : $(this).width() * 0.1,
//                        align : 'center'
//                    },
                    {
                        title: '采集器编号',
                        field: 'id',
                        width: $(this).width() * 0.1,
                        align: 'center'
                    }, {
                        title: '采集器ID',
                        field: 'ccode',
                        width: $(this).width() * 0.2,
                        align: 'center'
                    },
                    {
                        title: '所属站点',
                        field: 'dissName',
                        formatter: function (value, rowData, rowIndx) {
                            return rowData.dis.name;
                        },
                        width: $(this).width() * 0.2,
                        align: 'center'
                    },
                    {
                        title: '添加日期 ',
                        field: 'cdate',
                        width: $(this).width() * 0.2,
                        align: 'left'
//                        formatter:function(date)
//                        { /* 调用函数显示时间 */
////                            SimpleDateFormat c = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//                            return format(date);
//
//                        }
                    }
                ]],
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
                    url : '${basePath}/collector/list',
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
            function add() {
                if (deptId != "") {
                    addWin = $.createWin( {
                        title: "采集器添加",
                        url : basePath+'/collector/prAdd?deptId='+deptId,
                        height: 350,
                        width: 500,
                        buttons : [ {
                            text : '保存',
                            iconCls : 'icon-ok',
                            handler: save
                        } ]
                    });
                }else{
                    $.messager.alert('警告', '请选择一个区域', 'warning');
                }
            }

            function save() {
                var a = $('#dissname').val();
                if (a =="") {
                    $.messager.alert('提示', '请先选择站点!', 'warning');
                    alert('请先选择站点!');
                    return;
                }
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
                    title: "采集器修改",
                    url : basePath+'/collector/prUpdate?deptId='+deptId,
                    data : 'id=' +id,
                    height: 350,
                    width: 500,
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
                            $.messager.alert('提示', '修改成功', 'warning');
                        }else{
                            $.messager.alert('提示', '修改失败', 'warning');
                        }
                        $.closeWin(updateWin);
                        infolist.datagrid('reload');
                    }
                });
            }
            function del() {
                var select = infolist.datagrid('getSelected');
                if (select) {
                    $.messager.confirm('警告', '确认删除么?', function(f) {
                        if (f) {
                            $.ajax( {
                                type : "POST",
                                url : basePath+"/collector/delete",
                                data : "id=" + select.id,
                                dataType : "text",
                                cache : false,
                                success : function(msg) {
                                    var json = eval("(" + msg + ")");
                                    $.messager.alert('提示', json.message, 'warning');
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
                    contents: "<table style='font-size:12px;'><tr><td>采集器：</td><td><input id='dissName' /></td></tr></table>",
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
                    url : basePath+'/collector/list',
                    queryParams:{
                        'dissName':$('#dissName').val()
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
                    //getDiss(node.id);
                }
            });
//获取站点
            function getDiss(obj) {
                var data={
                    "disaId":obj
                };
                console.log($('#dissname'));
                $('#dissname').empty();
                $.post(basePath+"/authorization/distribution",data,function(data){
                    var d=JSON.parse(data);
                    for(var i=0;i<d.length;i++){
                        $('#dissname').append("<option value='" + d[i].id + "'>" + d[i].name + "</option>");
                    }
                });
            }
        });
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="530">
    <tr>
        <td width="12%" valign="top"
            style="border: 1px solid #99bbe8; border-right: 0;">
            <div class="panel-header" style="border-left: 0; border-right: 0;">区域</div>
            <ul id="tree" style="margin-top: 10px;"></ul>
        </td>
        <td valign="top" style="border: 1px solid #99bbe8;">
            <table id="infolist"></table>
        </td>
    </tr>
</table>
</body>
</html>
