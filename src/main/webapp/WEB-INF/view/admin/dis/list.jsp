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
    <title>站点信息</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/icon.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/windowControl.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/toolbar.js"></script>
    <script type="text/javascript">
        $(function () {
            var infolist = $('#infolist');
            infolist.datagrid({
                title: '站点列表',
                iconCls: 'icon-users',
                width: '95%',
                height: 500,
                pageSize: 20,
                pageList: [20, 30, 50, 100],
                nowrap: false,
                striped: true,
                collapsible: false,
                fitColumns: true,
                url: '${basePath}/disa/list',
                queryParams: {},
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
                        title: '编号',
                        field: 'id',
                        width: 300,
                        align: 'left'
                    },
                    {
                        title: '站点名称',
                        field: 'name',
                        width: 250,
                        align: 'center'
                    },
                    {
                        title: '位置',
                        field: 'areaname',
                        width: 400,
                        align: 'center'
                    },
                    {
                        title: '锁状态 ',
                        field: 'lock',
                        width: 200,
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
                    {
                        iconCls: "icon-add",
                        text: "添加",
                        handler: add
                    }, '-', {
                        text: '修改',
                        iconCls: 'icon-edit',
                        handler: edit
                    }, '-', {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: del
                    }]
            });
            displayMsg();

            function displayMsg() {
                infolist.datagrid('getPager').pagination({
                    beforePageText: '第',
                    afterPageText: '页，共{pages}页',
                    displayMsg: '当前显示从{from}到{to}共{total}记录'
                });
            }

            var addWin;
            var seeWin;
            var updateWin;

            function add() {
                addWin = $.createWin({
                    title: "添加",
                    url: '${basePath}/disa/prAdd',
                    height: 350,
                    width: 800,
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
                    url: '${basePath}/disa/prView',
                    data: 'id=' + id,
                    height: 550,
                    width: 800,
                    buttons: []
                });
            }

            $('#selectArea').window({
                title: '地区选择',
                width: 400,
                height: 500,
                closed: true,
                modal: true
            });


            $('#tree').tree({
                checkbox: false,
                <%--url: '${basePath}/areainfo/findAreaByCode',--%>
                url: '${basePath}/dept/getChildren',
                //simpleDataModel: true,
                onBeforeExpand: function (node, param) {
                    <%--$('#tree').tree('options').url = "${basePath}/areainfo/findAreaByParentId?parentId=" + node.id;// change the url--%>
                    <%--return true;--%>
                    $('#tree').tree('options').url = "${basePath}/dept/getChildren?parentId=" + node.id;
                },
                onClick: function (node) {
                    deptId = node.id;
                    refresh();
                }
            });


            function refresh() {
                infolist.datagrid("clearSelections");
                infolist.datagrid("reload");
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
                    url: '${basePath}/disa/prUpdate',
                    data: 'id=' + id,
                    height: 550,
                    width: 800,
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
                            $.post("${basePath}/disa/delete", {"id": selected.id}, function (json) {
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

        function setToarea() {
            var id = "";
            var show = "";
            fullname = "";
            var selections = $('#tree').tree('getSelected');
            if (selections) {
                id = selections.id;
                show = selections.attributes.areaname;
                $("#areacode").val(id);
                getAreaname(selections);
                fullname = fullname.substring(0, fullname.length - 1);
                $("#areaname").val(fullname);
            }
            $('#selectArea').window('close');
        }

        var fullname = "";

        function getAreaname(node) {
            fullname = node.text + " " + fullname;
            if (node.attributes.parentcode == 0) {
                return;
            }
            var abc = $('#tree').tree('getParent', node.target);
            getAreaname(abc);
        }
    </script>
</head>
<body>
<div>
    <table id="infolist"></table>
</div>
<div id="selectArea">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding: 10px;">
            <ul id="tree" style="margin-top: 10px;"></ul>
        </div>
        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
            <a class="easyui-linkbutton" icon="icon-ok" onclick="setToarea();">确定</a>
            <a class="easyui-linkbutton" icon="icon-cancel" onclick="$('#selectArea').window('close');">关闭</a>
        </div>
    </div>
</div>
</body>
</html>
