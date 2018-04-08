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
    <title>离线授权</title>
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
            //getkeys();
            $('#userList').datagrid({
                onCheck:function(index, row){
                    getkeys(row.id);
                }
            });
            //获取钥匙
            function getkeys(userId) {
                var data={
                    "userId":userId
                };
                var keyData = []; //创建数组
                $('#keysList').datagrid('loadData', keyData);
                $.post(basePath+"/authorization/keys/user",data,function(data){
                    var d=JSON.parse(data);
                    for(var i=0;i<d.length;i++){
                        keyData.push({
                            "id": d[i].keyssMAC,
                            "keyssCode": d[i].keyssCode
                        });
                    }
                    $('#keysList').datagrid('loadData', keyData);
                });
                console.log(keyData);
            }
            function getUsers(obj) {
                var data={
                    "disaId":obj
                };
                //获取使用人
                $.post(basePath+"/authorization/user",data,function(data){
                    var d=JSON.parse(data);
                    var userData = []; //创建数组
                    for(var i=0;i<d.length;i++){
                        userData.push({
                            "id": d[i].id,
                            "name": d[i].username
                        });
                    }
                    $('#userList').datagrid('loadData', userData);
                });
            }
            //获取门锁
            function getLocks(obj) {
                var data={
                    "deptId":obj
                };
                //获取锁具
                $.post(basePath+"/authorization/dept/locks",data,function(data){
                    var d=JSON.parse(data);
                    $('#locksList').empty();
                    var locksData = []; //创建数组
                    for(var i=0;i<d.length;i++){
                        locksData.push({
                            "id": d[i].lockCode,
                            "name": d[i].lockNum
                        });
                    }
                    $('#locksList').datagrid('loadData', locksData);
                });
            }
            $("#stepTwo").panel('close');
            $("#stepThere").panel('close');
            $('#tree').tree({
                checkbox: false,
                url: basePath+'/dept/getChildren',
                onBeforeExpand:function(node,param){
                    $('#tree').tree('options').url = basePath+"/dept/getChildren?parentId=" + node.id;
                },
                onClick:function(node){
                    //refresh(node.id);
                    deptId=node.id;
                    getUsers(deptId);
                    getLocks(deptId);
                },onLoadSuccess: function (node, data) {
                    $('#tree').tree('expandAll');
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
        });
        function stepAuth(num) {
            if(num==1){
                $("#stepOne").panel('open');
                $("#stepTwo").panel('close');
                $("#stepThere").panel('close');
            }
            if(num==2){
                if (deptId != "") {
                    //getUsers(deptId);
                    $("#stepOne").panel('close');
                    $("#stepTwo").panel('open');
                    $("#stepThere").panel('close');
                }else{
                    $.messager.alert('警告', '请选择一个区域', 'warning');
                }
            }
            if(num==3){
                var userRow = $("#userList").datagrid("getChecked");
                var keysRow = $("#keysList").datagrid("getChecked");
                if(userRow==""){
                    $.messager.alert('警告', '请选择一个用户', 'warning');
                    return;
                }
                if(keysRow==""){
                    $.messager.alert('警告', '请选择一个钥匙', 'warning');
                    return;
                }
                $("#stepOne").panel('close');
                $("#stepTwo").panel('close');
                $("#stepThere").panel('open');
            }
        }
        //离线授权
        function offlineAuth() {
            var userRow = $("#userList").datagrid("getChecked");
            var keysRow = $("#keysList").datagrid("getChecked");
            if(userRow==""){
                $.messager.alert('警告', '请选择一个用户', 'warning');
                return;
            }
            if(keysRow==""){
                $.messager.alert('警告', '请选择一个钥匙', 'warning');
                return;
            }
            var userId=userRow[0].id;
            var keysId=keysRow[0].id;
            var locksRows=$("#locksList").datagrid("getChecked");
            if(locksRows==""){
                $.messager.alert('警告', '请至少选择一个锁具', 'warning');
                return;
            }
            var authName=$('#authName').val();
            if(authName==""){
                $.messager.alert('警告', '请填写授权名称', 'warning');
                return;
            }
            var authStartTime=$('#authStartTime').val();
            if(authStartTime==""){
                $.messager.alert('警告', '请填写授权开始时间', 'warning');
                return;
            }
            var authEndTime=$('#authEndTime').val();
            if(authEndTime==""){
                $.messager.alert('警告', '请填写授权结束时间', 'warning');
                return;
            }
            var serial=$('#serials').combobox('getValue');
            if(serial==""){
                alert("请选择串口!");
                return;
            }
            var authLocks="";
            var authLocksId="";
            for(var i=0;i<locksRows.length;i++){
                authLocks+=locksRows[i].name+",";
                authLocksId+=locksRows[i].id+",";
            }
            var data={
                "user.id":userId,
                "keysId":keysId,
                "authName":authName,
                "authType":$("#authType").val(),
                "authStartTime":authStartTime,
                "authEndTime":authEndTime,
                "authKeys":$("#keysList").datagrid("getChecked")[0].keyssCode,
                "authKeysId":keysId,
                "authLocks":authLocks,
                "authLocksId":authLocksId,
                "serial":serial
            };
            $.ajax({
                type: "post",
                url: basePath + "/offline/save",
                cache: false,
                async: true,
                data: data,
                dataType: "json",
                success: function (data) {
                    if(data.result=="1"){
                        alert(data.message);

                    }else{
                        alert("保存失败!");
                    }
                }
            })
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
            <div class="easyui-panel" title="开始授权" style="width:900px" id="stepOne">
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
                            <td>授权类型:</td>
                            <td colspan="5">
                                <select id="authType" class="easyui-combobox"  name="authType" style="width: 180px;" data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">开关锁</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>授权名称:</td>
                            <td colspan="5">
                                <input width="180px" style="width: 200px" name="authName" id="authName">
                            </td>
                        </tr>
                        <tr>
                            <td width="100">授权时间:</td>
                            <td colspan="5">
                                <input id="authStartTime" name="authStartTime" class="easyui-validatebox"  value=""/><img onclick="WdatePicker({el:'authStartTime',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${basePath}/js/calendar/skin/datePicker.gif" width="16" height="22" align="absmiddle">
                                -<input id="authEndTime" name="authEndTime" class="easyui-validatebox"    value=""/><img onclick="WdatePicker({el:'authEndTime',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${basePath}/js/calendar/skin/datePicker.gif" width="16" height="22" align="absmiddle">
                            </td>
                        </tr>
                        <tr>
                            <td><button class="easyui-linkbutton" onclick="stepAuth(2)">下一步</button></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="easyui-panel" title="选择人员->选择钥匙" style="width:800px" id="stepTwo">
                <div style="padding:10px 60px 20px 60px">
                    <table cellpadding="5">
                        <tr>
                            <td colspan="3">
                                <%--<select class="easyui-combobox"  name="users" id="users" style="width: 180px;" data-options="editable:false,valueField:'id', textField:'text'">
                                    <option value="0">---请选择---</option>
                                </select>--%>
                                <table class="easyui-datagrid" id="userList"  data-options="singleSelect:true" title = "用户列表" style="width:350px;height:250px">
                                    <thead>
                                    <tr>
                                        <th data-options="field:'id',checkbox:true"></th>
                                        <th data-options="field:'name'" width="520px">姓名</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </td>
                            <td colspan="3">
                                <table class="easyui-datagrid" id="keysList" data-options="singleSelect:true" title = "钥匙列表" style="width:350px;height:250px">
                                    <thead>
                                    <tr>
                                        <th data-options="field:'id',checkbox:true"></th>
                                        <th data-options="field:'keyssCode'" width="520px">钥匙</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td><button class="easyui-linkbutton" onclick="stepAuth(1)">上一步</button></td>
                            <td><button class="easyui-linkbutton" onclick="stepAuth(3)">下一步</button></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="easyui-panel" title="门锁->离线授权" style="width:800px" id="stepThere">
                <div style="padding:10px 60px 20px 60px">
                    <table cellpadding="5">
                        <tr>
                            <td colspan="6">
                                <table class="easyui-datagrid" title = "门锁列表" id="locksList"  style="width:350px;height:250px">
                                    <thead>
                                    <tr>
                                        <th data-options="field:'id',checkbox:true"></th>
                                        <th data-options="field:'name'" width="520px">门锁</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td><button class="easyui-linkbutton" onclick="stepAuth(2)">上一步</button></td>
                            <td><button class="easyui-linkbutton" onclick="offlineAuth(3)">完成</button></td>
                        </tr>
                    </table>
                </div>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
