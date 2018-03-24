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
    <title>区域列表</title>
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
        var parentId="";
        $(function () {
            var addWin;
            $('#tree').tree({
                checkbox: false,
                url: '${basePath}/dept/getChildren',
                onBeforeExpand: function (node, param) {
                    $('#tree').tree('options').url = "${basePath}/dept/getChildren?parentId="
                        + node.id;
                },
                onClick: function (node) {
                    $.ajax({
                        type: "get",
                        url: "${basePath}/dept/prUpdate",
                        data: "id=" + node.id,
                        dataType: "text",
                        cache: false,
                        success: function (data) {
                            $("#dept").html(data);
                        }
                    });
                },onLoadSuccess: function (node, data) {
                    $('#tree').tree('expandAll');
                }
            });
            function refresh() {
                $('#tree').tree({url: '${basePath}/dept/getChildren'});
               // var node = $('#tree').tree('find',parentId);
                //$('#tree').tree('expandTo', node.target).tree('select', node.target);
            }

//            function save() {
//                $('#addForm').form('submit', {
//                    onSubmit: function () {
//                        return $(this).form('validate');
//                    },
//                    success: function (data) {
//                        $.messager.show({
//                            title: '温馨提示:',
//                            msg: '添加成功!',
//                            timeout: 5000,
//                            showType: 'slide'
//                        });
//                        refresh();
//                        $.closeWin(addWin);
//                    }
//                });
//            }
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

            function add(parentId) {
                $('#tempDeptId').val('');
                addWin = $.createWin({
                    title: "区域添加",
                    url: '${basePath}/dept/prAdd?parentId=' + parentId,
                    height: 320,
                    width: 500,
                    buttons: [{
                        text: '保存',
                        iconCls: 'icon-ok',
                        handler: save
                    }]
                });
            }

            $('#toolbar').toolbar({
                items: [{
                    iconCls: "icon-add",
                    text: "添加根节点",
                    handler: function () {
                        add('null');
                    }
                }, "-", {
                    iconCls: "icon-add",
                    text: "添加子节点",
                    handler: function () {
                        var selected = $('#tree').tree('getSelected');
                        if (selected) {
                            parentId=selected.id;
                            add(selected.id);
                        } else {
                            $.messager.alert('警告', '请选择父节点', 'warning');
                        }
                    }
                }, "-", {
                    iconCls: "icon-save",
                    text: "保存修改",
                    handler: function () {
                        $('#editForm').form('submit', {
                            onSubmit: function () {
                                return $(this).form('validate');
                            },
                            success: function (data) {
                                refresh();
                                $.messager.alert('提示', '修改成功!', 'warning');

                                /*         var json = eval("(" + data + ")");
                                         $.messager.alert('提示', json.message, 'warning');
                                         $.messager.alert('提示', '修改成功!', 'warning');*/

                            }

                        });
                    }
                }, "-", {
                    iconCls: "icon-remove",
                    text: "删除",
                    handler: function () {
                        var selected = $('#tree').tree('getSelected');
                        if (selected) {
                            $.messager.confirm('警告', '确认删除么?', function (f) {
                                if (f) {
                                    $.ajax({
                                        type: "get",
                                        url: "${basePath}/dept/delete",
                                        data: "ids=" + selected.id,
                                        dataType: "json",
                                        cache: false,
                                        success: function (msg) {
                                            $.messager.alert('提示', msg.message, 'warning');
                                            if (msg.result == 1) {
                                                $("#dept").html("");
                                            }
                                            refresh();
                                        }
                                    });
                                }
                            });
                        } else {
                            $.messager.alert('警告', '请选择一行数据', 'warning');
                        }
                    }
                }]
            });


            $('#selectArea').window({
                title: '地区选择',
                width: 400,
                height: 500,
                closed: true,
                modal: true
            });

            $('#diqutree').tree({
                checkbox: false,
                url: '${basePath}/areainfo/findAreaByCode',
                simpleDataModel: true,
                onBeforeExpand: function (node, param) {
                    $('#diqutree').tree('options').url = "${basePath}/areainfo/findAreaByParentId?parentId=" + node.id;// change the url
                    return true;
                },
            });
        });

        function setArea() {
            var selections = $('#diqutree').tree('getSelected');
            if (selections) {
                if ($('#tempDeptId').val() == "") {
                    $('#addareacode').val(selections.id);
                    $('#addareaname').val(selections.text);
                } else {
                    $("#areacode").val(selections.id);
                    $('#areaname').val(selections.text);
                }
            }
            $('#selectArea').window('close');
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
            <div class="panel-header" style="border-left: 0; border-right: 0;">修改区域</div>
            <div id="toolbar"></div>
            <div id="dept"></div>
        </td>
    </tr>
</table>

<div id="selectArea">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding: 10px;">
            <ul id="diqutree" style="margin-top: 10px;"></ul>
        </div>
        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
            <a class="easyui-linkbutton" icon="icon-ok" onclick="setArea();">确定</a>
            <a class="easyui-linkbutton" icon="icon-cancel" onclick="$('#selectArea').window('close');">关闭</a>
        </div>
    </div>
</div>
</body>
</html>
