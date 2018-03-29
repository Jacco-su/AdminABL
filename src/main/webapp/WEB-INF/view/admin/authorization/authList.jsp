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
    <title>在线授权</title>
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
        $(function() {
            $('#tr_collectore').hide();
           // getkeys();
            $('#tree').tree({
                checkbox: false,
                url: basePath+'/dept/getChildren',
                onBeforeExpand:function(node,param){
                    $('#tree').tree('options').url = basePath+"/dept/getChildren?parentId=" + node.id;
                },
                onClick:function(node){
                    refresh(node.id);
                },onLoadSuccess: function (node, data) {
                    $('#tree').tree('expandAll');
                }
            });
            //获取钥匙
            function getkeys() {
                $.post(basePath+"/authorization/keys",null,function(data){
                    var d=JSON.parse(data);
                    $('#keys').empty();
                    var keyData = []; //创建数组
                    for(var i=0;i<d.length;i++){
                        keyData.push({
                            "id": d[i].keyssMAC,
                            "text": d[i].keyssCode
                        });
                    }
                    $("#keys").combobox("clear")//下拉框加载数据,设置默认值为
                        .combobox("loadData", keyData).combobox("setValue", d[0].keyssMAC);
                });
            }

            //获取站点
            function refresh(obj) {
                var data={
                    "disaId":obj
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
                $('#disa').combobox({
                    onSelect: function (row) {
                        if (row != null) {
                            var data={
                                "disaId":row.id
                            };
                            $('#collectore').empty();
                            $.post(basePath+"/authorization/disa/collector",data,function(data){
                                var d=JSON.parse(data);
                                var collectorData = []; //创建数组
                                for(var i=0;i<d.length;i++){
                                    collectorData.push({
                                        "id": d[i].id,
                                        "text": d[i].ccode
                                    });
                                }
                                if( d[0]!=null) {
                                    $("#collector").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", collectorData).combobox("setValue", d[0].id);
                                }else{
                                    $("#collector").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", collectorData);
                                    $("#collectore").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", []);
                                }
                            });
                            //获取锁具
                           /* $.post(basePath+"/authorization/disa/locks",data,function(data){
                                var d=JSON.parse(data);
                                $('#locks').empty();
                                var collectorData = []; //创建数组
                                for(var i=0;i<d.length;i++){
                                    collectorData.push({
                                        "id": d[i].id,
                                        "text": d[i].lockCode
                                    });
                                }
                                if( d[0]!=null) {
                                    $("#locks").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", collectorData).combobox("setValue", d[0].id);
                                }
                            });*/
                        }
                    }
                });
                $('#collector').combobox({
                    onSelect: function (row) {
                        if (row != null) {
                            var data={
                                "collectorId":row.id
                            };
                            $.post(basePath+"/authorization/collector/collectore",data,function(data){
                                var d=JSON.parse(data);
                                $('#collectore').empty();
                                var collectorData = []; //创建数组
                                for(var i=0;i<d.length;i++){
                                    collectorData.push({
                                        "id": d[i].id,
                                        "text": d[i].ceMAC
                                    });
                                }
                                //console.log(collectorData);
                                if( d[0]!=null){
                                    $("#collectore").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", collectorData).combobox("setValue", d[0].id);
                                }else{
                                    $("#collectore").combobox("clear")//下拉框加载数据,设置默认值为
                                        .combobox("loadData", collectorData);
                                }
                            });
                        }
                    }
                });
            }
        });
        function keyBinding() {
            var key=$('#collector').combobox('getText')
                +",7,"
                +""+","
                +""
                +","
                +$('#users').combobox('getValue');
            var data={
                "key":key
            };
            $.ajax({
                type: "post",
                url: basePath+"/redis/get",
                cache:false,
                async:false,
                data:data,
                dataType: "json",
                success: function(data){
                    if (data.result == "1") {
                        alert(data.message);
                    } else {
                        alert("绑定蓝牙钥匙失败");
                    }
                }

            });
        }
        function keyTiming() {
//            var key=$('#collector').combobox('getText')+",12,"+$('#collectore').combobox('getText')+","+""+",";  去掉控制器
            var key=$('#collector').combobox('getText')+",12,"+""+","+""+",";
            var data={
                "key":key
            };
            $.ajax({
                type: "post",
                url: basePath+"/redis/get",
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
            if($('#startDate').val()==""){
               alert("请选择授权开始时间!");
               return;
            }
            if($('#endDate').val()==""){
                alert("请选择授权结束时间!");
                return;
            }
            if($('#locks').val()==""){
                alert("请填写锁序列号");
                return;
            }
            var key=$('#collector').combobox('getText')
                + ",5,"
//                +$('#collectore').combobox('getText')+","
                +""+","
                +""+","
                +$('#locks').val()+","
                +$('#startDate').val()+","
                +$('#endDate').val()+","
                +$('#users').combobox('getValue')+","
                +$('#disa').combobox('getValue');
            var data={
                "key":key
            };
            $.ajax({
                type: "post",
                url: basePath+"/redis/get",
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
            var key=$('#collector').combobox('getText') +","
                +t+","
//                +$('#collectore').combobox('getText')+","
                +""+","
                +""+","
                +$('#locks').val()+","
                +$('#startDate').val()+","
                +$('#endDate').val()+","
                +$('#users').combobox('getValue');
            var data={
                "key":key
            };
            $.ajax({
                type: "post",
                url: basePath+"/redis/get",
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
                            <td>站点:</td>
                            <td colspan="3">
                                <select class="easyui-combobox" name="disa" id="disa" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>控制器:</td>
                            <td colspan="3">
                                <select class="easyui-combobox" id="collector" name="collector" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select>
                            </td>
                        </tr>
                        <tr id="tr_collectore">
                            <td>控制器:</td>
                            <td colspan="3">
                                <select class="easyui-combobox" name="collectore" id="collectore" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select>
                            </td>
                        </tr>
                <%--        <tr>
                            <td>选择钥匙:</td>
                            <td colspan="3">
                                <select class="easyui-combobox" id="keys" name="keys" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>

                                </select></td>
                        </tr>--%>
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
                            <td>选择锁具:</td>
                            <td>
                                <input width="180px" name="locks" id="locks">
                               <%-- <select class="easyui-combobox" name="locks" id="locks" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select></td>--%>
                            <td>
                                <button class="easyui-linkbutton" onclick="getLock(2)">初始门锁</button>
                            </td>
                            <td>
                                <button class="easyui-linkbutton" onclick="getLock(1)">在线门锁信息</button>
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
                            <td>被授权人:</td>
                            <td colspan="3">
                                <select class="easyui-combobox" name="users" id="users" style="width: 180px;"
                                        data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select></td>
                        </tr>
                        <tr>
                            <td>
                                <button class="easyui-linkbutton" onclick="onlineAuth()">在线授权</button>
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
