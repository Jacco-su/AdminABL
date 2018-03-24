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
    <title>授权记录</title>
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
                title: '授权记录列表',
                iconCls: 'icon-users',
                width: '95%',
                height: 500,
                pageSize: 20,
                pageList: [20, 30, 50, 100],
                nowrap: false,
                striped: true,
                collapsible: false,
                fitColumns: true,
                url: '${basePath}/authorization/list',
                queryParams: {},
                loadMsg: '数据装载中......',
                remoteSort: false,
                singleSelect: true,
                onDblClickRow: function (rowIndex, field, value) {
                    var rows = infolist.datagrid("getRows");
                    var id = rows[rowIndex].id;
                    workticket(id);
                },
                columns: [[
//                    {
//                    title: '区域名称',
//                    field: 'name',
//                    width: $(this).width() * 0.2,
//                    align: 'center'
//                },
                    {
                        title: '钥匙',
                        field: 'keyssid',
                        width: $(this).width() * 0.1,
                        rowspan: 2,
                        align: 'center'
                    }, {
                        title: '锁具编号',
                        field: 'locksid',
                        width: $(this).width() * 0.1,
                        rowspan: 2,
                        align: 'center'
                    }, {
                        title: '类型',
                        field: 'type',
                        width: $(this).width() * 0.1,
                        rowspan: 2,
                        align: 'center'
                    }, {
                        title: '使用人',
                        field: 'uid',
                        width: $(this).width() * 0.1,
                        rowspan: 2,
                        align: 'center'
                    },
//                    {
//                        title: '领用人',
//                        field: 'usernume',
//                        formatter: function (value, rowData, rowIndx) {
//                            return rowData.user.username;
//                        },
//                        width: $(this).width() * 0.1,
//                        align: 'center'
//                    },
                    {
                        title: '起始日期',
                        field: 'starttime',
                        width: $(this).width() * 0.1,
                        rowspan: 2,
                        lign: 'center'
                    }, {
                        title: '失效日期',
                        field: 'endtime',
                        width: $(this).width() * 0.1,
                        rowspan: 2,
                        align: 'center'
                    }, {
                        title: '经办人',
                        field: 'aid',
                        width: $(this).width() * 0.1,
                        rowspan: 2,
                        align: 'center'
                    },
//                    {
//                        title : '经办人',
//                        field : 'aname',
//                        formatter : function(value,rowData,rowIndx) {
//                            return rowData.user.name;
//                        },
//                        width : $(this).width() * 0.1,
//                        align : 'center'
//                    },
                    {
                        title: '授权日期',
                        field: 'adate',
                        width: $(this).width() * 0.1,
                        rowspan: 2,
                        align: 'center'
                    }, {
                        title: '工作票',
                        field: 'workticket ',
                        width: $(this).width() * 0.1,
//                        rowspan: 2,
//                        align: 'center',
//                        url:onClick() workticket(),
//                        handler: workticket(),
//                        formatter:function(value,rec){
//                            return '<a href="../authorization/add.jsp" onclick="editfunc('+value+')"> 查看</a>';
//                        },
//                        formatter: function (val, row) {
//
//                            var w = '<a style="color:red;" href="' + val + '">点击下载' + '</a>';
//                            return w;
//                        },

//                    <a href="http://localhost:8080/lanya/src/main/webapp/uploads/workticket/00129.jpg">预览图片</a>


                        formatter: function (value, row, index) { //参数row表示当前行, 参数index表示当前行的索引值
//
//                            var id = '+row.Id+'
//                            //row.Id表示这个button按钮所在的那一行的Id这个字段的值
//                            var btn = '<input type="button" id=' + index + ' value="双击查看工作票"  onclick="workticket(id)"/>';
//
//                            return btn;
                            var imgNum = $('img').length;
                            $('img').load(function () {
                                if (!--imgNum) {
                                    // 加载完成
                                }
                            });
                        }


//                        onDblClickRow: function (rowIndex, field, value) {
//                    var rows = infolist.datagrid("getRows");
//                    var id = rows[rowIndex].id;
//                    detail(id);
//                },


//                        toolbar: [
//                            {
//                                text: '详情',
//                                iconCls: 'icon-view',
//                                handler: seedetail
//                            },]
//
//                        formatter:function(value,row,index){
//                            if (row.editing){
//                                var s = '<a href="#" onclick="saverow('+index+')">Save</a> ';
//                                var c = '<a href="#" onclick="cancelrow('+index+')">Cancel</a>';
//                                return s+c;
//                            } else {
//                                var e = '<a href="#selectArea" onclick="function workticket(id)">查看3</a> ';
//                                var d = '<a href="authorization/workView" onclick="deleterow('+index+')">Delete</a>';
//                                return e+d;
//                            }
//                        }

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
//                    '-', {
//                        iconCls: "icon-add",
//                        text: "添加",
//                        handler: add
//                    }, '-', {
//                        text: '修改',
//                        iconCls: 'icon-edit',
//                        handler: edit
//                    }, '-', {
//                        text: '删除',
//                        iconCls: 'icon-remove',
//                        handler: del
//                    },
                    {
                        iconCls: "icon-add",
                        text: "票",
                        handler: workticket
                    },
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

            var addWin;
            var seeWin;
            var updateWin;

            function add() {
                addWin = $.createWin({
                    title: "添加",
                    url: '${basePath}/authorization/prAdd',
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
                    url: '${basePath}/authorization/prView',
                    data: 'id=' + id,
                    height: 550,
                    width: 800,
                    buttons: []
                });
            }

            function workticket(id) {
                seeWin = $.createWin({
                    title: "工作票",
                    url: '${basePath}/authorization/workView',
                    data: 'id=' + id,
                    height: 650,
                    width: 800,
                    buttons: []
                });
            }

            <%--$('workticket').tree({--%>
            <%--url: '${basePath}/authorization/workView',--%>
            <%--onDblClick: function (node) {--%>
            <%--var editor = $('#dg').datagrid('getEditor', {index: 1, field: field});//首先双击datagrid单元格时需要记住单元格的field (可以设置一个全局变量)--%>
            <%--$(editor.target).val(node.text);--%>

            <%--}--%>

            <%--})--%>

            $('#selectArea').window({
                title: '地区选择',
                width: 400,
                height: 500,
                closed: true,
                modal: true
            });


            $('#tree').tree({
                checkbox: false,
                url: '${basePath}/areainfo/findAreaByCode',
                simpleDataModel: true,
                onBeforeExpand: function (node, param) {
                    $('#tree').tree('options').url = "${basePath}/areainfo/findAreaByParentId?parentId=" + node.id;// change the url
                    return true;
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
                    url: '${basePath}/authorization/prUpdate',
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
                            $.post("${basePath}/authorization/delete", {"id": selected.id}, function (json) {
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
            fullname = ""
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
            if (node == null) return;					//改动
            fullname = node.text + " " + fullname;
            if (node.attributes.parentcode == 0) {
                return;
            }
            var abc = $('#tree').tree('getParent', node.target);
            getAreaname(abc);
        }


        function LoadUserInfo(row) {

            /*获取选中行*/
            var row = $('#Cse_Bespeak_Log').datagrid('getSelected'); //获取选中行

            $("#datagrid").datagrid({
                url: "authorization/workView" + row,
                iconCls: "icon-add",
                fitColumns: false,
                loadMsg: "数据加载中......",
                pagination: true,
                rownumbers: true,
                nowrap: false,
                showFooter: true,
                singleSelect: true,
                pageList: [100, 50, 20, 10],
            })


            $('#dlg').window('open');  //弹出这个dialog框
        }

    </script>
</head>
<body>
<div>
    <table id="infolist"></table>
</div>
<div id="dlg">
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
