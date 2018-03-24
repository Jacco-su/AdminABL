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
    <title>区域授权</title>
    <link rel="stylesheet" type="text/css"
          href="${basePath}/js/jquery-easyui-1.5.3/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css"
          href="${basePath}/js/jquery-easyui-1.5.3/themes/icon.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${basePath}/js/calendar/WdatePicker.js"></script>
    <script type="text/javascript">
        var basePath="${basePath}";
        var areaId = "";
        $(function() {
            $('#tree').tree({
                checkbox: false,
                url: basePath + '/dept/getChildren',
                onBeforeExpand: function (node, param) {
                    $('#tree').tree('options').url = basePath + "/dept/getChildren?parentId=" + node.id;
                },
                onClick: function (node) {
                    areaId=node.id;
                    refresh(node.id);
                }
            });
            function refresh(obj) {
                var data={
                    "disaId":obj
                };
                //获取使用人
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
            }
        });
        function getMAC() {
            var key = ",13,,,";
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
                        alert(data.message);
                    } else {
                        alert("授权失败!");
                    }
                }

            });
        }
        function areaAuth() {
           /* var keyssMAC=$("#keyssMAC").val();
            if(keyssMAC==""){
                $.messager.alert('警告', '请选择获取钥匙', 'warning');
                return;
            }*/
            if (areaId != "") {
                var data = {
                    "areaId": areaId,
                    "startDate":$('#startDate').val(),
                    "endDate":$('#endDate').val(),
                    "userId":$('#users').combobox('getValue')
                };
                $.ajax({
                    type: "post",
                    url: basePath + "/authorization/areaAuth",
                    cache: false,
                    async: true,
                    data: data,
                    dataType: "json",
                    success: function (data) {
                        if (data.result == "1") {
                            alert(data.message);

                        } else {
                            alert("授权失败!");
                        }
                    }

                });
            }else{
                $.messager.alert('警告', '请选择一个区域', 'warning');
                return;
            }
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
            <div class="easyui-panel" title="区域授权" style="width:800px">
                <div style="padding:10px 60px 20px 60px">
                    <table cellpadding="5">
                       <%-- <tr>
                            <td>钥匙MAC:</td>
                            <td colspan="3">
                                <input id="keyssMAC" type="text" name="keyssMAC" value="" class="easyui-validatebox"
                                       required="true"/>
                                <a class="easyui-linkbutton"
                                   onclick="getMAC()">获取</a>
                            </td>
                        </tr>--%>
                        <tr>
                            <td width="100">授权时间:</td>
                            <td colspan="3">
                                <input id="startDate" name="startDate" class="easyui-validatebox" value=""/>
                                <img onclick="WdatePicker({el:'startDate',dateFmt:'yyyyMMddHHmmss'})"
                                     src="${basePath}/js/calendar/skin/datePicker.gif" width="16" height="22"
                                     align="absmiddle">
                                -
                                <input id="endDate" name="endDate" class="easyui-validatebox" value=""/>
                                <img onclick="WdatePicker({el:'endDate',dateFmt:'yyyyMMddHHmmss'})"
                                     src="${basePath}/js/calendar/skin/datePicker.gif" width="16" height="22"
                                     align="absmiddle">
                            </td>
                        </tr>
                        <tr>
                            <td>被授权人:</td>
                            <td colspan="3">
                                <select class="easyui-combobox" name="users" id="users" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select></td>
                        </tr>
                        <tr>
                            <td>
                                <button class="easyui-linkbutton" onclick="areaAuth()">区域授权</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
