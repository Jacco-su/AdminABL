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
    <title>离线即时授权</title>
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
        var deptId="";
        $(function() {
           // getkeys();
            $('#tree').tree({
                checkbox: false,
                url: basePath+'/dept/getChildren',
                onBeforeExpand:function(node,param){
                    $('#tree').tree('options').url = basePath+"/dept/getChildren?parentId=" + node.id;
                },
                onClick:function(node){
                    refresh(node.id);
                    deptId=node.areaCode;
                },onLoadSuccess: function (node, data) {
                    $('#tree').tree('expandAll');
                }
            });
            //获取站点
            function refresh(obj) {
                var data={
                    "disaId":obj
                };
                //获取使用人
                $.post(basePath+"/authorization/user",data,function(data){
                    var d=JSON.parse(data);
                    $('#users').empty();
                    var userData = []; //创建数组
                    for(var i=0;i<d.length;i++){
                        userData.push({
                            "id": d[i].id,
                            "text": d[i].username
                        });
                    }
                    if( d[0]!=null) {
                        $("#users").combobox("clear")//下拉框加载数据,设置默认值为
                            .combobox("loadData", userData).combobox("setValue", d[0].id);
                    }
                });
            }
            //获取可用串口
            $.post(basePath+"/offline/serial",null,function(data){
                //var d=JSON.parse(data);
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
        });

        function keyBinding() {
            var userName=$('#users').combobox('getText');
            $.messager.confirm('警告', '确定要绑定钥匙给'+userName+"?", function (f) {
                if (f) {
                    var serial=$('#serials').combobox('getValue');
                    if(serial==""){
                        alert("请选择串口!");
                        return;
                    }
                    var userId=$('#users').combobox('getValue');
                    if(userId=="0"||userId==""){
                        alert("请选择用户!");
                        return;
                    }
                    var data={
                        "serial":serial,
                        "T":7,
                        "userId":userId
                    };
                    $.ajax({
                        type: "post",
                        url: basePath+"/offline/read",
                        cache:false,
                        async:false,
                        data:data,
                        dataType: "json",
                        success: function(data){
                            if (data.result == "1") {
                                alert(data.message);
                            } else {
                                alert("绑定蓝牙钥匙失败!");
                            }
                        }

                    });
                }
                });
        }
        function keyTiming() {
            var serial=$('#serials').combobox('getValue');
            if(serial==""){
                alert("请选择串口!");
                return;
            }
            var data={
                "serial":serial,
                "T":12
            };
            $.ajax({
                type: "post",
                url: basePath+"/offline/read",
                cache:false,
                async:false,
                data:data,
                dataType: "json",
                success: function(data){
                    if(data.result=="1"){
                        alert(data.message);
                    }else{
                        alert("蓝牙钥匙校时失败！");
                    }
                }

            });
        }
        function onlineAuth() {
            var startDate=$('#startDate').val();
            if(startDate==""){
               alert("请选择授权开始时间!");
               return;
            }
            var endDate=$('#endDate').val();
            if(endDate==""){
                alert("请选择授权结束时间!");
                return;
            }
            var lockNum=$('#locks').val();
            if(lockNum==""){
                alert("请填写锁识别码!");
                return;
            }
            var serial=$('#serials').combobox('getValue');
           if(serial==""){
               alert("请选择串口!");
               return;
           }
            var userId=$('#users').combobox('getValue');
            if(userId=="0"||userId==""){
                alert("请选择用户!");
                return;
            }
            var keys=$('#keys').val();
            if(keys==""){
                alert("请先获取钥匙地址！!");
                return;
            }
            var data={
                "serial":serial,
                "T":5,
                "startDate":startDate,
                "endDate":endDate,
                "lockNum":lockNum,
                "userId":userId,
                "keysId":keys
            };
            $.ajax({
                type: "post",
                url: basePath+"/offline/read",
                cache:false,
                async:false,
                data:data,
                dataType: "json",
                success: function(data){
                    if(data.result=="1"){
                        alert(data.message);
                    }else{
                        alert("授权失败!");
                    }
                }

            });
        }
        function getLock(t) {
            var serial=$('#serials').combobox('getValue');
            if(serial==""){
                alert("请选择串口!");
                return;
            }
            if(deptId==""){
                alert("请先选择区域!");
                return;
            }
            var data={
                "serial":serial,
                "T":t,
                "lockNum":"",
                "deptId":deptId
            };
            $.ajax({
                type: "post",
                url: basePath+"/offline/read",
                cache:false,
                async:false,
                data:data,
                dataType: "json",
                success: function(data){
                    if(data.result=="1"){
                        if(t==2){
                            alert(data.message);
                        }else{
                            $("#locks").empty();
                            var collectorData = []; //创建数组
                            var lockNum=data.message.split(";")[1];
                            $("#locks").val(lockNum);
                           /* collectorData.push({
                                "id": lockNum,
                                "text":lockNum
                            });
                            console.log(data.message.split(";")[1]);
                            console.log(collectorData);
                            $("#locks").combobox("clear")//下拉框加载数据,设置默认值为
                                .combobox("loadData", collectorData).combobox("setValue",lockNum);*/
                        }
                    }else{
                        if(t==2){
                            alert("初始化门锁失败!");
                        }else{
                            alert("读取门锁信息失败!");
                        }
                    }
                }

            });
        }
        function findKeys() {
            var serial=$('#serials').combobox('getValue');
            if(serial==""){
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
                cache:false,
                async:false,
                data:data,
                dataType: "json",
                success: function(data){
                    if(data.result=="1"){
                        //alert(data.message);
                        $('#keys').val(data.message.split("->")[1]);
                    }else{
                        alert("蓝牙钥匙获取失败，请重试！");
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
            <div class="easyui-panel" title="开始授权" style="width:800px">
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
                            <td>
                                操作钥匙:
                            </td>
                            <td colspan="3">
                                <button class="easyui-linkbutton" onclick="keyBinding()">绑定钥匙</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button class="easyui-linkbutton" onclick="keyTiming()">钥匙校时</button>
                            </td>
                        </tr>
                        <tr>
                            <td>钥匙Mac地址:</td>
                            <td>
                                <input width="180px" name="keys" id="keys" readonly>
                                <%-- <select class="easyui-combobox" name="locks" id="locks" style="width: 180px;"
                                         data-options="editable:false,valueField:'id', textField:'text'">
                                     <option value="0">---请选择---</option>
                                 </select></td>--%>
                            <td>
                                <button class="easyui-linkbutton" onclick="findKeys()">获取钥匙信息</button>
                            </td>
                        </tr>
                        <tr>
                            <td>门锁识别码:</td>
                            <td>
                                <input width="180px" name="locks" id="locks" readonly>
                               <%-- <select class="easyui-combobox" name="locks" id="locks" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select></td>--%>
                            <td>
                                <button class="easyui-linkbutton" onclick="getLock(2)">初始化门锁</button>
                            </td>
                            <td>
                                <button class="easyui-linkbutton" onclick="getLock(1)">获取门锁识别码</button>
                            </td>
                        </tr>

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
                            <td>用户:</td>
                            <td colspan="3">
                                <select class="easyui-combobox" name="users" id="users" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select></td>
                        </tr>
                        <tr>
                            <td>
                                <button class="easyui-linkbutton" onclick="onlineAuth()">离线授权</button>
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
