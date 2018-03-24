<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>

<script type="text/javascript">
    <%--$(function () {--%>
    <%--var data = '${collectorList}';--%>
    <%--data = JSON.parse(data);--%>
    <%--$('#cid').empty();--%>
    <%--for (var i = 0; i < data.length; i++) {--%>
    <%--$('#cid').append("<option value='" + data[i].ccode + "'>" + data[i].ccode + "</option>");--%>
    <%--}--%>
    <%--});--%>

    $(function () {
        var data = '${usersList}';
        data = JSON.parse(data);
        $('#username').empty();
        for (var i = 0; i < data.length; i++) {
            $('#username').append("<option value='" + data[i].id + "'>" + data[i].username + "</option>");
        }
    });


    function getMVC() {
        alert("暂不支持自动获取!");
        return;
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

</script>


<style>
    input:focus {
        color: rgba(7, 7, 7, 0.99);
    }

    input:link {
        color: rgba(80, 76, 78, 0.8);
    }
</style>

<div>
    <form name="addForm" id="addForm" action="${basePath}/keyss/add" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>钥匙编号:</td>
                <td><input name="keyssCode" id="keyssCode" value="" class="easyui-validatebox" required="true"/></td>
            </tr>

            <tr>
                <td>自定义名称:</td>
                <td>
                    <input name="keyssName" class="easyui-validatebox" value="" required="true"/>
                </td>
            </tr>
            <%--<tr>--%>
            <%--<td>采集器ID:</td>--%>
            <%--<td>--%>

            <%--<select id="cid" name="collector.id" style="width: 200px;"></select>--%>
            <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>钥匙MAC:</td>
                <td>
                    <input id="keyssMAC" type="text" name="keyssMAC" value="" class="easyui-validatebox"
                           required="true"/>
                    <a class="easyui-linkbutton"
                       onclick="getMVC()">获取</a>
                </td>
            </tr>

            <tr>
                <td width="100">领用人:</td>
                <td>
                    <%--<input type="text" name="userName" id="userName" value="" />--%>
                    <%--<a class="easyui-linkbutton" onclick="$('#selectUser').window('open');">选择</a>--%>
                    <%--<input type="hidden" name="userid" id="usercode" value=""/>--%>

                    <select id="username" name="userName" style="width:150px;">

                        <%--<option value="">---请选择---</option>--%>
                    <%--<c:forEach items="${userList}" var="user" varStatus="s">--%>
                    <%--<option value="${user.id}"--%>
                    <%--<c:if test="${user.id eq seuser.id}">selected</c:if>>${user.name}</option>--%>
                    <%--</c:forEach>--%>
                    <%--</select>--%>
                    <%--<a class="easyui-linkbutton" icon="icon-ok" onclick="search();">查询</a>--%>


                </td>

            </tr>


        </table>
    </form>
</div>

<div>
    <table>
        <tr>
            <td></td>
        </tr>

    </table>
</div>
