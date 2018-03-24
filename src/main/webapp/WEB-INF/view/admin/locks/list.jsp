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
    <title>智能锁信息</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/icon.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/windowControl.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/toolbar.js"></script>
    <script type="text/javascript" src="${basePath}/js/calendar/WdatePicker.js"></script>
    <script type="text/javascript">
        var basePath = "${basePath}";
        var deptId="";
        $(function () {
            var infolist = $('#infolist');
            infolist.datagrid({
                title: '智能锁列表',
                iconCls: 'icon-users',
                width: '95%',
                height: 500,
                pageSize: 20,
                pageList: [20, 30, 50, 100],
                nowrap: false,
                striped: true,
                collapsible: false,
                fitColumns: true,
                url: '${basePath}/locks/list',
                queryParams: {
                    "deptId" : deptId
                },
                loadMsg: '数据装载中......',
                remoteSort: false,
                singleSelect: true,
                onDblClickRow: function (rowIndex, field, value) {
                    var rows = infolist.datagrid("getRows");
                    var id = rows[rowIndex].id;
                    detail(id);
                },
                columns: [[
                    {
                        title: '智能锁编号',
                        field: 'lockNum',
                        width: 250,
                        align: 'center'
                    },
                    {
                        title: '识别码',
                        field: 'lockCode',
                        width: 250,
                        align: 'center'
                    },
                    {
                        title: '所属站点',
                        field: 'dissName',
                        formatter: function (value, rowData, rowIndx) {
                            return rowData.qgdis.name;
                        },
                        width: $(this).width() * 0.1,
                        align: 'center'
                    },
                    {
                        title: '地址',
                        field: 'address',
                        width: 200,
                        align: 'center'
                    },
                    {
                        title: '添加时间',
                        field: 'lockDate',
                        width: 150,
                        align: 'left'
                    },
                ]],
                pagination: true,
                rownumbers: true,
                toolbar: [
                    {
                        text: '详情',
                        iconCls: 'icon-view',
                        handler: seedetail
                    },
                    '-', {
                        text: '修改',
                        iconCls: 'icon-edit',
                        handler: edit
                    }, '-', {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: del
                    }
                ]
            });
            displayMsg();

            function displayMsg() {
                infolist.datagrid('getPager').pagination({
                    beforePageText: '第',
                    afterPageText: '页，共{pages}页',
                    displayMsg: '当前显示从{from}到{to}共{total}记录'
                });
            }
            function search(){
                addWin = $.createWin( {
                    title : "查询条件",
                    contents:"",
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
                    url : basePath+'/locks/list',
                    queryParams:{
                        'dissName':$('#dissName').val()
                    },
                    loadMsg : '数据装载中......'
                });
                infolist.datagrid("clearSelections");
                displayMsg();
                $.closeWin(addWin);
            }
            var addWin;
            var seeWin;
            var updateWin;

            function add() {
                addWin = $.createWin({
                    title: "添加",
                    url: '${basePath}/locks/prAdd',
                    height: 450,
                    width: 600,
                    buttons: [{
                        text: '保存',
                        iconCls: 'icon-ok',
                        handler: save
                    }]
                });
            }

            function save() {
                $('#addForm').form('submit', {
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (data) {
                        var json = eval("(" + data + ")");
                        $.messager.alert('提示', json.message, 'warning');
                        $.closeWin(addWin);
                        refresh();
                    }
                });
            }

            function seedetail() {
                var select = infolist.datagrid('getSelected');
                if (select) {
                    detail(select.id);
                } else {
                    $.messager.alert('警告', '请选择一行数据', 'warning');
                }
            }

            function detail(id) {
                seeWin = $.createWin({
                    title: "详情",
                    url: '${basePath}/locks/prView',
                    data: 'id=' + id,
                    height: 550,
                    width: 800,
                    buttons: []
                });
            }

            $('#selectDept').window({
                title: '安装区域选择',
                width: 400,
                height: 500,
                closed: true,
                modal: true
            });
            $('#tree').tree({
                checkbox: false,
                url: basePath+'/dept/getChildren',
                onBeforeExpand:function(node,param){
                    $('#tree').tree('options').url = basePath+"/dept/getChildren?parentId=" + node.id;
                },
                onClick:function(node){
                    deptId = node.id;
                    refresh();
                    getDiss(node.id);
                }
            });
           //获取站点
            function getDiss(obj) {
                var data={
                    "disaId":obj
                };
                $('#dissName').empty();
                $.post(basePath+"/authorization/distribution",data,function(data){
                    var d=JSON.parse(data);
                    for(var i=0;i<d.length;i++){
                        $('#dissName').append("<option value='" + d[i].id + "'>" + d[i].name + "</option>");
                    }
                });
            }

            function refresh() {
                infolist.datagrid( {
                    url : '${basePath}/locks/list',
                    queryParams:{
                        'deptId':deptId
                    },
                    loadMsg : '数据装载中......'
                });
                infolist.datagrid("clearSelections");
                displayMsg();
            }

            function edit() {
                var select = infolist.datagrid('getSelected');
                if (select) {
                    showEdit(select.id);
                } else {
                    $.messager.alert('警告', '请选择一行数据', 'warning');
                }
            }

            function update() {
                $('#editForm').form('submit', {
                    onSubmit: function () {
                        var v = $(this).form('validate');
                        if (v) {
                        }
                        return v;
                    },
                    success: function (data) {
                        var json = eval("(" + data + ")");
                        $.messager.alert('提示', json.message, 'warning');
                        $.closeWin(updateWin);
                        refresh();
                    }
                });
            }

            function showEdit(id) {
                updateWin = $.createWin({
                    title: "修改",
                    url: basePath + '/locks/prUpdate',
                    data: 'id=' + id,
                    height: 350,
                    width: 500,
                    buttons: [{
                        text: '修改',
                        iconCls: 'icon-ok',
                        handler: update
                    }]
                });

            }

            function del() {
                var selected = infolist.datagrid('getSelected');
                if (selected) {
                    $.messager.confirm('警告', '确定要删除么?', function (f) {
                        if (f) {
                            $.post("${basePath}/locks/delete", {"id": selected.id}, function (json) {
                                $.messager.alert('提示', json.message);
                                if (json.result == 1) {
                                    infolist.datagrid('reload');
                                }
                            }, "json");
                        }
                    });
                } else {
                    $.messager.alert('警告', '未选中任何数据', 'warning');
                }
            }
        });


        //        -----

        function setTodept() {
            var id = "";
            var show = "";
            fullname = "";
            var selections = $('#tree2').tree('getSelected');
            if (selections) {
                id = selections.id;
                show = selections.attributes.deptname;
                $("#deptid").val(id);
                getDisname(selections);
                fullname = fullname.substring(0, fullname.length - 1);
                $("#deptid").val(fullname);
            }
            $('#selectDept').window('close');
        }

        var fullname = "";

        function getDisname(node) {
            if (node == null) return;					//改动 控制树显示
            fullname = node.text + " " + fullname;
            if (node.attributes.parentId == 0) {
                return;
            }
            var abc = $('#tree2').tree('getParent', node.target);
            getDisname(abc);
        }
        function searLockByDiss() {
            $('#infolist').datagrid( {
                url : basePath+'/locks/list',
                queryParams:{
                    'dissName':$('#dissName').val(),
                    'deptId':deptId
                },
                loadMsg : '数据装载中......'
            });
            $('#infolist').datagrid("clearSelections");
            displayMsg();
        }
        function displayMsg() {
            $('#infolist').datagrid('getPager').pagination({
                beforePageText: '第',
                afterPageText: '页，共{pages}页',
                displayMsg: '当前显示从{from}到{to}共{total}记录'
            });
        }
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
                <table style='font-size:12px;'>
                    <tr>
                        <td>站点：</td>
                        <td><select id='dissName' style='width: 150px;'></select>
                        </td>
                        <td><button onclick="searLockByDiss()">查询</button></td>
                    </tr>
                </table>
                <table id="infolist"></table>
            </td>
        </tr>
    </table>
<div id="selectDept">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding: 10px;">
            <ul id="tree2" style="margin-top: 10px;"></ul>
        </div>
        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
            <a class="easyui-linkbutton" icon="icon-ok" onclick="setTodept();">确定</a>
            <a class="easyui-linkbutton" icon="icon-cancel" onclick="$('#selectDept').window('close');">关闭</a>
        </div>
    </div>
</div>
</body>
</html>
