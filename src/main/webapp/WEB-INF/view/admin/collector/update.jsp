<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%--/**   修改*/--%>
<div>
    <form name="editForm" id="editForm" action="${basePath}/collector/update" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>控制器ID:</td>
                <td>
                    <input type="hidden" name="id" value="${collectora.id}"/>
                    <input name="dept.id" type="hidden" value="${collectora.dis.id}"/>
                    <input name="ccode" value="${collectora.ccode}" class="easyui-validatebox" required="true"
                           onblur="ce()"/></td>
            </tr>

            <tr>
                <td width="100">所属站点</td>
                <td>
                    <%--<input type="text" name="disame" id="areaname" value="${collectora.dis.name}" readonly/><a--%>
                        <%--class="easyui-linkbutton" onclick="$('#selectArea').window('open');">选择</a>--%>
                    <%--&lt;%&ndash;<input type="hidden" name="discode" id="discode" value="${collector.dis}"/>&ndash;%&gt;--%>
                    <select id="dissname" name="dis.id" style="width:180px;" ></select>
                </td>
            </tr>
            <%--<tr>--%>
            <%--<td>IP地址:</td>--%>
            <%--<td>--%>
            <%--<input name="cip" value="${collectora.cip}" class="easyui-validatebox" required="true"/>--%>
            <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>日期:</td>
                <td><input name="cdate" value="${collectora.cdate}" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<script>
    $(function () {
        var data = '${qgdisList}';
        data = JSON.parse(data);
        $('#dissname').empty();
        <%--$("#usersname").prepend("<option value='0'>${collectora.dis.name}</option>");   //为Select插入一个Option(第一个位置)--%>
        for (var i = 0; i < data.length; i++) {
            if('${collectora.dis.id}'==data[i].id){
                $('#dissname').append("<option selected value='" + data[i].id + "'>" + data[i].name + "</option>");
            }else {
                $('#dissname').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
            }
        }
    });

    function ce() {
        var p = $("#ccode").val();
        if (!p.match(/\d{8}$/)) {
//            alert("请输入8位数字！");
            $.messager.alert('提示', '请输入8位数字的控制器ID！', 'warning');
        }
        if (p.length > 8) {
            $.messager.alert('提示', '控制器ID输入超过8位！', 'warning');
        }


    };

</script>