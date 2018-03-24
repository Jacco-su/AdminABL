<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<script>
//    //获取人
//    $(function (data) {
//        var d = JSON.parse(data);
//        $('#users').empty();
//        var userData = []; //创建数组
//        for (var i = 0; i < d.length; i++) {
//            userData.push({
//                "id": d[i].id,
//                "text": d[i].username
//            });
//        }
//        if (d[0] != null) {
//            $("#users").combobox("clear")//下拉框加载数据,设置默认值为
//                .combobox("loadData", userData).combobox("setValue", d[0].id);
//        }
//    });

    $(function () {
        $('#userTree').tree({
            checkbox: false,
            url: basePath+'/dept/getChildren',
            onBeforeExpand:function(node,param){
                $('#userTree').tree('options').url = basePath+"/dept/getChildren?parentId=" + node.id;
            },
            onClick:function(node){
                getUsers(node.id);
            }
        });
        function getUsers(obj) {
            var data={
                "disaId":obj
            };
            //获取使用人
            $.post(basePath+"/authorization/user",data,function(data){
                var d=JSON.parse(data);
                $('#usersname').empty();
                for(var i=0;i<d.length;i++){
                    if(${keyss.user.id == d[i].id}){
                        $('#usersname').append("<option selected value='" + d[i].id + "'>" + d[i].username + "</option>");
                    }else{
                        $('#usersname').append("<option value='" + d[i].id + "'>" + d[i].username + "</option>");
                    }

                }
            });
        }
    });
</script>
<%--/**   修改*/--%>
<div>
    <form name="editForm" id="editForm" action="${basePath}/keyss/update" method="post">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" height="530">
            <tr>
                <td width="24%" valign="top"
                    style="border: 1px solid #99bbe8; border-right: 0;">
                    <div class="panel-header" style="border-left: 0; border-right: 0;">区域</div>
                    <ul id="userTree" style="margin-top: 10px;"></ul>
                </td>
                <td valign="top" style="border: 1px solid #99bbe8;">
                    <table class="mytable" align="center">
                        <tr>
                            <td>钥匙编号:</td>
                            <td><input type="hidden" name="id" value="${keyss.id}"/>
                                <input name="dept.id" type="hidden" value="${keyss.user.id}"/>
                                <input name="keyssCode" value="${keyss.keyssCode}" class="easyui-validatebox" required="true"/></td>
                        </tr>
<%--                        <tr>
                            <td>钥匙名称:</td>
                            <td><input name="keyssName" value="${keyss.keyssName}" class="easyui-validatebox" required="true"/>
                            </td>
                        </tr>--%>
                        <tr>
                            <td>钥匙MAC:</td>
                            <td><input name="keyssMAC"  readonly value="${keyss.keyssMAC}" class="easyui-validatebox" required="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="100">领用人：</td>
                            <td>
                                <select id="usersname" name="user.id" style="width: 200px;">
                                    <option value="${keyss.user.id}">${keyss.user.username}</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

    </form>
</div>
