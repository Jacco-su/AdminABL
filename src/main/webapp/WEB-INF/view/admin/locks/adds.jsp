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
    <title>添加门锁</title>
    <link rel="stylesheet" type="text/css"
          href="${basePath}/js/jquery-easyui-1.5.3/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css"
          href="${basePath}/js/jquery-easyui-1.5.3/themes/icon.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"
            charset="UTF-8"></script>
    <script type="text/javascript" src="${basePath}/js/calendar/WdatePicker.js"></script>
    <script type="text/javascript">


        var basePath = "${basePath}";
        $(function () {
            var infolist = $('#infolist');
            infolist.datagrid({
                title: '智能锁列表',
                iconCls: 'icon-us-=-ers',
                width: '95%',
                height: 500,
                pageSize: 20,
                pageList: [20, 30, 50, 100],
                nowrap: false,
                striped: true,
                collapsible: false,
                fitColumns: true,
                url: '${basePath}/locks/list',
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
//                    {
//                        iconCls: "icon-add",
//                        text: "添加",
//                        handler: add
//                    },
                    '-', {
                        text: '修改',
                        iconCls: 'icon-edit',
                        handler: edit
                    }, '-', {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: del
                    }]
            });


//        $(function () {
            refresh();

            //获取站点
            function refresh() {
                var data = {
                    "disaId": ""
                };
                <%--$(function () {--%>
                <%--var data='${dissList}';--%>
                <%--data=JSON.parse(data);--%>
                <%--$('#disa').empty();--%>
                <%--for (var i=0;i<data.length;i++){--%>
                <%--$('#disa').append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");--%>
                <%--}--%>
                <%--})--%>
                $.post(basePath + "/authorization/distribution", data, function (data) {
                    <%--var d='${dissList}';--%>
                    var d = JSON.parse(data);
                    $('#disa').empty();
                    var disaData = []; //创建数组
                    for (var i = 0; i < d.length; i++) {
                        disaData.push({
                            "id": d[i].id,
                            "text": d[i].name
                        });
                    }
                    if (d[0] != null) {
                        $("#disa").combobox("clear")//下拉框加载数据,设置默认值为
                            .combobox("loadData", disaData).combobox("setValue", d[0].id);
                    }
                });
                //获取采集器
                $('#disa').combobox({
                    onSelect: function (row) {
                        if (row != null) {
                            var data = {
                                "disaId": row.id
                            };
                            $('#collectore').empty();
                            $.post(basePath + "/authorization/disa/collector", data, function (data) {
                                var d = JSON.parse(data);
                                $('#collector').empty();
                                var collectorData = []; //创建数组
                                for (var i = 0; i < d.length; i++) {
                                    collectorData.push({
                                        "id": d[i].id,
                                        "text": d[i].ccode
                                    });
                                }
                                if (d[0] != null) {
                                    $("#collector").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", collectorData).combobox("setValue", d[0].id);
                                }
                            });
                        }
                    }
                });
                //获取控制器
                $('#collector').combobox({
                    onSelect: function (row) {
                        if (row != null) {
                            var data = {
                                "collectorId": row.id
                            };
                            $.post(basePath + "/authorization/collector/collectore", data, function (data) {
                                var d = JSON.parse(data);
                                $('#collectore').empty();
                                var collectorData = []; //创建数组
                                for (var i = 0; i < d.length; i++) {
                                    collectorData.push({
                                        "id": d[i].id,
                                        "text": d[i].ceMAC
                                    });
                                }
                                //console.log(collectorData);
                                if (d[0] != null) {
                                    $("#collectore").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", collectorData).combobox("setValue", d[0].id);
                                }
                            });
                        }
                    }
                });
            }
        });

        //初始化 获取门锁信息
        function getLock(t) {
            var key = $('#collector').combobox('getText') + ","
                + t + ","
                + $('#collectore').combobox('getText') + ","
                + $('#keys').combobox('getText') + ","
                + $('#locks').combobox('getText') + ","
                + $('#startDate').val() + ","
                + $('#endDate').val() + ","
                + $('#users').combobox('getValue');
            var data = {
                "key": key
            };
            $.ajax({
                type: "post",
                url: basePath + "/redis/get",
                cache: false,
                async: false,
                data: data,
                dataType: "json",
                success: function (data) {
                    if (data.result == "1") {
                        if (t == 2) {
                            alert(data.message);
                        } else {
                            $("#locks").empty();
                            var collectorData = []; //创建数组
                            var lockNum = data.message.split(":")[1];
                            collectorData.push({
                                "id": lockNum,
                                "text": lockNum
                            });
                            console.log(data.message.split(":")[1]);
                            console.log(collectorData);
                            $("#locks").combobox("clear")//下拉框加载数据,设置默认值为
                                .combobox("loadData", collectorData).combobox("setValue", lockNum);
                        }
                    } else {
                        if (t == 2) {
                            alert("初始化门锁失败!");
                        } else {
                            alert("读取门锁信息失败!");
                        }
                    }
                }

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
                }
            });
        }


    </script>
</head>
<body>
<div>
    <table id="infolist"></table>
</div>
<div>
    <form name="addForm" id="addForm" action="${basePath}/locks/add" method="post">
        <table class="mytable" align="center">


            <tr>
                <td>站点:</td>
                <td colspan="2">
                    <select class="easyui-combobox" name="qgdis.id" id="disa" style="width: 180px;"
                            data-options="editable:false,valueField:'id', textField:'text'">
                        <option value="0">---请选择---</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>选择采集器:</td>
                <td colspan="2">
                    <select class="easyui-combobox" id="collector" name="collector" style="width: 180px;"
                            data-options="editable:false,valueField:'id', textField:'text'">
                        <option value="0">---请选择---</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>控制器:</td>
                <td colspan="2">
                    <select class="easyui-combobox" name="collectore" id="collectore" style="width: 200px;"
                            data-options="editable:false,valueField:'id', textField:'text'">
                        <option value="0">---请选择---</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="100">门锁编号:</td>
                <td colspan="2">
                    <input id="lockNum" name="lockNum" style="width: 200px;"/>
            </tr>
            <tr>
                <td width="100">门锁识别码:</td>
                <td colspan="2">
                    <input id="lockCode" name="lockCode" style="width: 200px;"/>
                    <a class="easyui-linkbutton"
                       onclick="getLock(2)">初始化</a>
                    <a class="easyui-linkbutton"
                       onclick="getLock(1)">获取</a>
            </tr>

            <tr>
                <td width="100">添加时间:</td>
                <td colspan="2">
                    <input id="lockDate" name="lockDate" class="easyui-validatebox" required="true" value=""/>
                    <img onclick="WdatePicker({el:'lockDate'})" src="${basePath}/js/calendar/skin/datePicker.gif"
                         width="16" height="22" align="absmiddle">
            </tr>
            <tr>
                <td width="100">详细地址:</td>
                <td colspan="2">
                    <textarea name="address" class="easyui-validatebox" id="address" cols="30" rows="3"
                              required="true"></textarea>
            </tr>

            <tr>
                <td>
                    <button class="easyui-linkbutton" onclick="save()">确认添加</button>
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>