<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>


<style>

    input:focus {
        color: rgba(90, 90, 90, 0.99);
    }

    input:link {
        color: rgba(233, 228, 230, 0.8);
    }
</style>
<div>
    <form name="addForm" id="addForm" action="${basePath}/collectore/add" method="post">
        <table class="mytable" align="center">

            <tr>
                <td>采集器ID:</td>
                <td>
                    <%--<input name="ceCode"  class="easyui-validatebox" required="true"/>--%>
                    <%--<a class="easyui-linkbutton" onclick="getID()">选择</a>--%>
                    <select id="ccode" name="collector.id" class="easyui-validatebox"
                            style="width: 150px">
                        <%--<option value=""> ${collectorList.ccode}</option>--%>
                    </select>
                    <%--<select id="dissName" name="qgdis.id" style="width: 220px;"></select>--%>

                </td>
            </tr>
            <tr>
                <td>控制器名称:</td>
                <td><input name="cename" value="" class=""/></td>
            </tr>
            <tr>
                <td>MAC地址:</td>
                <td>
                    <input name="ceMAC" id="ceMAC" class="easyui-validatebox" required="true"/>
                </td>
            </tr>

            <%--<tr>--%>
            <%--<td >所属站点:</td>--%>
            <%--<td>--%>
            <%--<input type="text" name="name" id="areaname" value="" readonly/><a class="easyui-linkbutton"--%>
            <%--onclick="$('#selectArea').window('open');">选择</a>--%>
            <%--<input type="hidden" name="areacode" id="areacode" value=""/>--%>
            <%--</td>--%>
            <%--</tr>--%>

            <%--<tr>--%>
            <%--<td>日期:</td>--%>
            <%--<td>--%>
            <%--<%--%>

            <%--SimpleDateFormat ft =--%>
            <%--new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");--%>
            <%--Date dNow = new Date();--%>
            <%--//                        out.print( "<h5 align=\"center\">" + ft.format(dNow)+"</h5>" );--%>
            <%--//Date date = ft.format(new Date());--%>
            <%--String str = ft.format(dNow);--%>
            <%--System.out.println(str);--%>
            <%--%>--%>
            <%--<input name="cdate" type="text" value=<%=ft.format(dNow)%> required="true"/>--%>
            <%--</td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>

<script>
    $(function () {
        var data = '${collectorList}';
        data = JSON.parse(data);
        $('#ccode').empty();
        for (var i = 0; i < data.length; i++) {
            $('#ccode').append("<option value='" + data[i].id + "'>" +data[i].dis.name+"-"+data[i].ccode + "</option>");
        }
    });

</script>
