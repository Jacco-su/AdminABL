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
    <title>站点列表</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/icon.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/windowControl.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/toolbar.js"></script>
    <script type="text/javascript">
        var basePath = "${basePath}";
        $(function () {
            var areacode = "";
            var infolist = $('#infolist');
            infolist.datagrid({
                title: '站点列表',
                iconCls: 'icon-users',
                width: '95%',
                height: 560,
                pageSize: 20,
                pageList: [20, 30, 50, 100],
                nowrap: false,
                striped: true,
                collapsible: false,
                fitColumns: true,
                singleSelect: true,
                url: '${basePath}/qgorga/list',
                queryParams: {
                    'areacode': areacode
                },
                loadMsg: '数据装载中......',
                remoteSort: false,
                onDblClickRow: function (rowIndex, field, value) {
                    var rows = infolist.datagrid("getRows");
                    var id = rows[rowIndex].id;
                    showEdit(id);
                },
                columns: [[{
                    title: '区域名称',
                    field: 'name',
                    width: 200,
                    align: 'center'
                },
                    {
                        title: '区域位置',
                        field: 'address',
                        width: 300,
                        align: 'left'
                    },
                    {
                        title: '上级区域',
                        field: 'areaname',
                        width: 200,
                        align: 'center'
                    },

                    {
                        title: '区域编号',
                        field: 'id',
                        width: $(this).width() * 0.1,
                        align: 'left'
                    },
                ]],
                pagination: true,
                rownumbers: true,
                toolbar: [{
                    text: '添加',
                    iconCls: 'icon-add',
                    handler: add
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit',
                    handler: edit
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: del
                },]
            });
            displayMsg();

            function refresh() {
                infolist.datagrid({
                    url: '${basePath}/qgorga/list',
                    queryParams: {
                        'deptId': deptId
                    },
                    loadMsg: '数据装载中......'
                });
                infolist.datagrid("clearSelections");
                displayMsg();
            }

            function displayMsg() {
                infolist.datagrid('getPager').pagination({
                    beforePageText: '第',
                    afterPageText: '页，共{pages}页',
                    displayMsg: '当前显示从{from}到{to}共{total}记录'
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
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (data) {
                        var json = eval("(" + data + ")");
                        if (json.result == '1') {
                            $.messager.alert('提示', '保存成功', 'warning');
                            $.closeWin(addWin);
                            refresh();
                        } else {
                            $.messager.alert('提示', '保存失败', 'warning');
                            $.closeWin(addWin);
                            refresh();
                        }

                    }
                });
            }

            function add() {
                if (deptId != "") {
                    addWin = $.createWin({
                        title: "站点添加",
                        url: basePath + '/qgorga/prAdd?deptId=' + deptId,
                        height: 350,
                        width: 500,
                        buttons: [{
                            text: '保存',
                            iconCls: 'icon-ok',
                            handler: save
                        }]
                    });
                } else {
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

            function showEdit(id) {
                updateWin = $.createWin({
                    title: "站点修改",
                    url: basePath + '/qgorga/prUpdate',
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

            function update() {
                $('#editForm').form('submit', {
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (data) {
                        var json = eval("(" + data + ")");
                        if (json.result == '1') {
                            $.messager.alert('提示', '保存成功', 'warning');
                        } else {
                            $.messager.alert('提示', '保存失败', 'warning');
                        }
                        $.closeWin(updateWin);
                        infolist.datagrid('reload');
                    }
                });
            }

            function del() {
                var select = infolist.datagrid('getSelected');
                if (select) {
                    $.messager.confirm('警告', '确认删除么?', function (f) {
                        if (f) {
                            $.ajax({
                                type: "POST",
                                url: basePath + "/qgorga/delete",
                                data: "id=" + select.id,
                                dataType: "text",
                                cache: false,
                                success: function (msg) {
                                    var json = eval("(" + msg + ")");
                                    if (json.result == '0') {
                                        $.messager.alert('提示', '有智能锁不能删除', 'warning');
                                    } else if (json.result == '1') {
                                        $.messager.alert('提示', '有采集器不能删除', 'warning');
                                    } else {
                                        $.messager.alert('提示', '删除成功', 'warning');
                                    }
//                                    $.messager.alert('提示', '删除成功!', 'warning');
                                    infolist.datagrid('reload');
                                }
                            });
                        }
                    });
                } else {
                    $.messager.alert('警告', '请选择一行数据', 'warning');
                }
            }

            function search() {
                addWin = $.createWin({
                    title: "查询条件",
                    contents: "<table style='font-size:12px;'><tr><td>地区名称：</td><td><input id='name' /></td></tr></table>",
                    width: 300,
                    buttons: [{
                        text: '查询',
                        iconCls: 'icon-search',
                        handler: query
                    }]
                });
            }

            function query() {
                infolist.datagrid({
                    url: basePath + '/qgorga/list',
                    queryParams: {
                        'name': $('#name').val()
                    },
                    loadMsg: '数据装载中......'
                });
                infolist.datagrid("clearSelections");
                displayMsg();
                $.closeWin(addWin);
            }

            $('#tree').tree({
                checkbox: false,
                url: '${basePath}/areainfo/findAreaByCode',
                onBeforeExpand: function (node, param) {
                    $('#tree').tree('options').url = basePath + "/areainfo/findAreaByParentId?parentId=" + node.id;
                },
                onClick: function (node) {
                    areacode = node.id;
                    refresh();
                }
            });

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
