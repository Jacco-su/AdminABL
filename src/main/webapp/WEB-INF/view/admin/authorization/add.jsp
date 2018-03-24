<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%--<link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">--%>
<%--<link rel="stylesheet" type="text/css" href="../../themes/icon.css">--%>
<%--<link rel="stylesheet" type="text/css" href="../demo.css">--%>
<%--<script type="text/javascript" src="../../jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="../../jquery.easyui.min.js"></script>--%>

<meta charset="UTF-8">
<title>Basic DateTimeBox - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/default/easyui.css"/>
<%--<link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/icon.css"/>--%>
<%--<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.4.4/demo/demo.css">--%>

<%--<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.4.4/themes/default/easyui.css"/>--%>
<%--&lt;%&ndash;<script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>&ndash;%&gt;--%>
<%--&lt;%&ndash;<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>&ndash;%&gt;--%>
<%--&lt;%&ndash;<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/jquery.min.js"></script>&ndash;%&gt;--%>

<%--<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>--%>
<%--<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.4.4/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>--%>

<link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/icon.css"/>
<script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>


</head>

<script>
    //    $('#dt').datetimebox({
    //        value: '',
    //        required: true,
    //        showSeconds: true,
    //        timespinner: true,
    //        width: '80px',
    //
    //    });
    //
    //    //$('#ss').timespinner({
    //    //    min: '',
    //    //    required: true,
    //    //    showSeconds: true,
    //    //    width:80px;
    //    //
    //    //});
    //    //$('#ss').timespinner({
    //    //    required:true,
    //    //    increment:10
    //    //    width:80px;
    //    //});
    //    $(function () {
    //        $('#ss').timespinner({
    //            value: '00:00',
    //            min: '00:00',
    //            max: '23:59',
    //            editable: false,
    //            width: '80px',
    //            height: '100px',
    //        });
    //    });


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
    <form name="addForm" id="addForm" action="${basePath}/authorization/onLine" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>
                    获取钥匙MAC:
                </td>
                <td>
                    <input type="" onclick="keyssMAC"> <br/>
                </td>
            </tr>
            <tr>
                <td>
                    授权期限:
                </td>
                <td>
                    <div style="margin:20px 0;"></div>
                    <div class="" style="width:100%;max-width:400px;padding:30px 60px;">
                        <div style="margin-bottom:20px">
                            <input class="easyui-datetimebox" label="Select DateTime:" labelPosition="top"
                                   style="width:100%;">
                            <input class="easyui-datetimebox" style="width:60px">
                            <input class="easyui-datetimebox" editable="false" type="text" name="startDate"
                                   id="startDate" value="${startDate}"/>
                        </div>
                        <div style="margin-bottom:20px">
                            <%--<input class="easyui-datetimebox" label="Description:" labelPosition="top" style="width:100%;">--%>

                            <input id="lockDate" name="lockDate" class="easyui-validatebox" required="true" value=""/>
                            <img onclick="WdatePicker({el:'lockDate'})"
                                 src="${basePath}/js/calendar/skin/datePicker.gif" width="16" height="22"
                                 align="absmiddle">
                        </div>

                    </div>
                </td>
                <td>
                    <input class="easyui-datebox" data-options="sharedCalendar:'#cc'">

                    <input class="easyui-datebox" data-options="sharedCalendar:'#cc'">
                </td>

            </tr>
            <tr>
                <td>
                    授权使用人:
                </td>
                <td>
                    <input type="text" name="name" id="username" value="" readonly/> <a class="easyui-linkbutton"
                                                                                        onclick="$('#selectUser').window('open');">选择</a>
                    <input type="hidden" name="userid" id="usercode" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    锁具范围:
                </td>
                <td>
                    <input type="text"> <br/>
                </td>
            </tr>
            <tr>
                <td>
                    操作人:
                </td>
                <td>
                    <input type="text"> <br/>
                </td>
            </tr>
            <tr>
                <td>
                    工作票:
                </td>
                <td>
                    <input type="file" value="ss"> <br/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" value="确认授权">
                </td>
                <td>
                    <input type="reset" value="重置"><br/>
                </td>
            </tr>


        </table>
    </form>
</div>

<script>
    $(function () {
        var data = '${dissList}';
        data = JSON.parse(data);
        $('#dissName').empty();
        for (var i = 0; i < data.length; i++) {
            $('#dissName').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
        }
    })
</script>

