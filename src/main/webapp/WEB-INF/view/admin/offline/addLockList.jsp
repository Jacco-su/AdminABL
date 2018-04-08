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
    <title>门锁添加</title>
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
        var deptId="";
        var deptAreaCode="";
        $(function () {
            $('#tree').tree({
                checkbox: false,
                url: basePath + '/dept/getChildren',
                onBeforeExpand: function (node, param) {
                    $('#tree').tree('options').url = basePath + "/dept/getChildren?parentId=" + node.id;
                },
                onClick: function (node) {
                    refresh(node.id);
                    deptId=node.id;
                    deptAreaCode=node.areaCode;
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
                    }else{
                        $("#disa").combobox("clear")//下拉框加载数据,设置默认值为
                            .combobox("loadData", disaData);
                        $("#collector").combobox("clear")//下拉框加载数据,设置默认值为
                            .combobox("loadData", []);
                        $("#collectore").combobox("clear")//下拉框加载数据,设置默认值为
                            .combobox("loadData", []);
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
                                }else{
                                    $("#collector").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", []);
                                    $("#collectore").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", []);
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
                                }else{
                                    $("#collectore").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", []);
                                }
                            });
                        }
                    }
                });
            }
        });
        function initLock() {
            var lockCode=$("#lockCode").val();
            if(lockCode==""){
                alert("请填写锁识别码!");
                return
            }
            if(lockCode.length!=16){
                alert("请输入正确的识别码");
                return;
            }
            getLock(2,lockCode);
        }

        function getLock(t,lockCode) {
            if(deptAreaCode==""){
                alert("请先选择区域!");
                return;
            }
            $("#lockCode").val("");
            var serial=$('#serials').combobox('getValue');
            if(serial==""||serial=="0"){
                alert("请选择串口!");
                return;
            }
            var data={
                "serial":serial,
                "T":t,
                "lockNum":lockCode,
                "deptId":deptAreaCode
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
                        if (t == 2) {
                            alert(data.message);
                        } else {
                            var lockNum = data.message.split(";")[1];
                            $("#lockCode").val(lockNum);
                        }
                    } else {
                        if (t == 2) {
                            alert("初始化门锁信息未获取到!");
                        } else {
                            alert("未获取到门锁信息!");
                        }
                    }
                }

            });
        }
    </script>
</head>
<body>
<div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" height="530">
        <tr>
            <td width="12%" valign="top"
                style="border: 1px solid #99bbe8; border-right: 0;">
                <div class="panel-header" style="border-left: 0; border-right: 0;">区域</div>
                <ul id="tree" style="margin-top: 10px;"></ul>
            </td>
            <td valign="top" style="border: 1px solid #99bbe8;">
                <div class="easyui-panel" title="添加门锁" style="width:800px">
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
                                <td>站点:</td>
                                <td colspan="2">
                                    <select class="easyui-combobox" name="qgdis.id" id="disa" style="width: 180px;"
                                            data-options="editable:false,valueField:'id', textField:'text'"
                                            required="true">
                                        <option>---请选择---</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td width="100">门锁编号:</td>
                                <td colspan="2">
                                    <input id="lockNum" name="lockNum" style="width: 200px;" required="true"/>
                            </tr>
                            <tr>
                                <td width="100">门锁识别码:</td>
                                <td colspan="2">
                                    <input id="lockCode" name="lockCode" style="width: 200px;" required="true"/>
                                    <a class="easyui-linkbutton"
                                       onclick="getLock(1,'')">获取门锁识别码</a>
                                    <a class="easyui-linkbutton"
                                       onclick="getLock(2,'')">初始化门锁</a>
                                    <a class="easyui-linkbutton"
                                       onclick="initLock(3)">添加门锁识别码(初始化)</a>
                            </tr>
                            <tr>
                                <td width="100">详细地址:</td>
                                <td colspan="2">
                    <textarea name="address" class="easyui-validatebox" id="address" cols="30" rows="3"
                    ></textarea>
                            </tr>
                            <tr>
                                <td>
                                    <button class="easyui-linkbutton" onclick="save()">确认添加</button>
                                </td>
                            </tr>
                        </table>
                        <%--</form>--%>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    //        保存
    function save() {
        var dis = $('#disa').combobox('getValue');
        var lockCode = $("#lockCode").val();
        if (dis == null || dis == "" || dis == 0 || dis == "---请选择---") {
            $.messager.alert('提示', "请选择正确站点", 'warning');
            dis.focus();
            return false;
        }
        if ($("#lockNum").val() == null || $("#lockNum").val() == "") {
            $.messager.alert('提示', "请选择门锁编号", 'warning');
            dis.focus();
            return ;
        }
        if (lockCode == null || lockCode == "") {
            $.messager.alert('提示', "请获取门锁识别码", 'warning');
            lockCode.focus();
            return ;
        }
        var serial=$('#serials').combobox('getValue');
        if(serial==""||serial=="0"){
            alert("请选择串口!");
            return;
        }

        var data = {
            "qgdis.id": $('#disa').combobox('getValue'),
            "collector": '',
            "collectore": '',
            "lockNum": $("#lockNum").val(),
            "lockCode": $("#lockCode").val(),
            "lockDate": $("#lockDate").val()
        };
        $.ajax({
            type: "post",
//                url: basePath+"/redis/get",
            url: basePath + "/locks/add",
            cache: false,
            async: false,
            data: data,
            dataType: "json",
            success: function (data) {
                if (data.result == "1") {
                    $("#lockNum").val("");
                    $("#lockCode").val("");
                    $("#lockDate").val("");
                    $("#address").val("");
                    $.messager.alert('提示', data.message, 'success');
                } else {
                    $.messager.alert('提示', data.message, 'warning');
                }
            }
        });
    }
</script>
</html>
