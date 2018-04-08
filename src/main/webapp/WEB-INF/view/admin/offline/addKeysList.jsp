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
    <title>钥匙添加</title>
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

//            getkeys();
            $('#tree').tree({
                checkbox: false,
                url: basePath + '/dept/getChildren',
                onBeforeExpand: function (node, param) {
                    $('#tree').tree('options').url = basePath + "/dept/getChildren?parentId=" + node.id;
                },
                onClick: function (node) {
                    refresh(node.id);
                }
            });
           //获取可用串口
            $.post(basePath+"/offline/serial",null,function(data){
                var d=data.split(",");
                $('#serials').empty();
                var serialData = []; //创建数组
                for(var i=0;i<d.length;i++){
                    serialData.push({
                        "id": d[i],
                        "text": d[i]
                    });
                }
                if( d[0]!=null) {
                    $("#serials").combobox("clear")//下拉框加载数据,设置默认值为
                        .combobox("loadData", serialData).combobox("setValue", d[0]);
                }
            });
            //获取站点
            function refresh(obj) {
                var data = {
                    "disaId": obj
                };
                $.post(basePath + "/authorization/distribution", data, function (data) {
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
                //获取人
                $.post(basePath + "/authorization/user", data, function (data) {
                    var d = JSON.parse(data);
                    $('#users').empty();
                    var userData = []; //创建数组
                    for (var i = 0; i < d.length; i++) {
                        userData.push({
                            "id": d[i].id,
                            "text": d[i].username
                        });
                    }
                    if (d[0] != null) {
                        $("#users").combobox("clear")//下拉框加载数据,设置默认值为
                            .combobox("loadData", userData).combobox("setValue", d[0].id);
                    }
                });
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
                            //获取锁具
                            $.post(basePath + "/authorization/disa/locks", data, function (data) {
                                var d = JSON.parse(data);
                                $('#locks').empty();
                                var collectorData = []; //创建数组
                                for (var i = 0; i < d.length; i++) {
                                    collectorData.push({
                                        "id": d[i].id,
                                        "text": d[i].lockCode
                                    });
                                }
                                if (d[0] != null) {
                                    $("#locks").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", collectorData).combobox("setValue", d[0].id);
                                }
                            });
                        }
                    }
                });
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

        //保存
        function save() {
            sb1();
            function sb1() {
                if ($('#users') == null || $('#users') == "") {
                    $.messager.alert('提示', "请选择使用人", 'warning');
                    return false;
                } else {
                    var data = {
                        "user.id": $('#users').combobox('getValue'),
                        "keyssCode": $("#keyssCode").val(),
                        "keyssMAC": $("#keyssMAC").val()
                    };
                    $.ajax({
                        type: "post",
                        url: basePath + "/keyss/add",
                        cache: false,
                        async: true,
                        data: data,
                        dataType: "json",
                        success: function (data) {
                            if (data.result == "1") {
                                $.messager.alert('提示', data.message, 'success');
                            } else {
                                $.messager.alert('提示', data.message, 'warning');
                            }
                        }
                    });
                }
            }
        }
        
        function getMAC() {
            var serial=$('#serials').combobox('getValue');
            if(serial==""||serial=="0"){
                alert("请选择串口!");
                return;
            }
            var data={
                "serial":serial,
                "T":13
            };
            $.ajax({
                type: "post",
                url: basePath+"/offline/read",
                cache: false,
                async: false,
                data: data,
                dataType: "json",
                success: function (data) {
                    if (data.result == "1") {
                        $('#keyssMAC').val(data.message.split("->")[1]);
                    } else {
                        alert("读取钥匙信息失败!");
                    }
                }

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
            <div class="easyui-panel" title="添加钥匙" style="width:800px">
                <div style="padding:10px 60px 20px 60px">
                    <table cellpadding="5">
                        <tr>
                            <td>串口:</td>
                            <td colspan="3">
                                <select class="easyui-combobox" name="serials" id="serials" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>钥匙编号:</td>
                            <td colspan="2"><input name="keyssCode" id="keyssCode" value="" style="width: 180px;"
                                                   class="easyui-validatebox" required="true"/></td>
                        </tr>
                        <tr>
                            <td>钥匙MAC:</td>
                            <td colspan="2">
                                <input id="keyssMAC" type="text" readonly name="keyssMAC" style="width: 180px;" value=""
                                       class="easyui-validatebox"
                                       required="true"/>
                                <button class="easyui-linkbutton"
                                        onclick="getMAC()">获取
                                </button>
                            </td>
                        </tr>

                        <tr>
                            <td width="100">领用人:</td>
                            <td colspan="2">
                                <select class="easyui-combobox" name="userName" id="users" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select>

                            </td>

                        </tr>
                        <tr>
                            <td>
                                <button class="easyui-linkbutton" onclick="save()">保存</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </td>
    </tr>
</table>
</div>
</body>
</html>
