<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--<script type="text/javascript">
    $(function () {
        $('#selectArea').window({
            title: '选择采集器',
            width: 350,
            height: 500,
            closed: true,
            modal: true
        });
    });
</script>--%>
<%--&lt;%&ndash;/**   修改*/&ndash;%&gt;--%>
<div>
    <form name="editForm" id="editForm" action="${basePath}/collectore/update" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>控制器名称:</td>
                <td>
                    <input type="hidden" name="id" value="${collectore.id}"/>
                    <input name="dept.id" type="hidden" value="${collectore.collector.dis.id}"/>
                    <input name="cename" value="${collectore.cename}" class="easyui-validatebox" required="true"/></td>
            </tr>

           <tr>
                <td width="100">所属智能采集器</td>
                <td>
                    <%--<input type="hidden" name="disame" id="areaname" value="${collectore.collector.id}" readonly/>--%>
                    <%--<input size="40" readonly="readonly" class="easyui-validatebox" required="true" name="discode" id="discode"--%>
                           <%--value="${collectore.collector.ccode}"/>--%>
                    <%--<a class="easyui-linkbutton" onclick="$('#selectCt').window('open');">选择</a>--%>
                        <select id="cid" name="collector.id" style="width:180px;" ></select>
                </td>
            </tr>
            <tr>
                <td>MAC地址:</td>
                <td>
                    <input name="ceDate" value="${collectore.ceDate}" class="easyui-validatebox" type="hidden"/>
                    <input name="ceMAC" id="ceMAC" value="${collectore.ceMAC}" class="easyui-validatebox" required="true"
                           />
                </td>
            </tr>
          <%--  <tr>
                <td>日期:</td>
                <td><input name="ceDate" value="${collectore.ceDate}" class="easyui-validatebox" type="hidden"/>
                </td>
            </tr>--%>
        </table>
        <%--<div id="selectct">--%>
            <%--<div class="easyui-layout" fit="true">--%>
                <%--<div region="center" border="false" style="padding: 10px;">--%>
                    <%--<ul id="tree2" style="margin-top: 10px;"></ul>--%>
                <%--</div>--%>
                <%--<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">--%>
                    <%--<a class="easyui-linkbutton" icon="icon-ok" onclick="setToarea();">确定</a>--%>
                    <%--<a class="easyui-linkbutton" icon="icon-cancel" onclick="$('#selectCt').window('close');">关闭</a>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    </form>
</div>

<script>
    $(function () {
        var data = '${collectorList}';
        data = JSON.parse(data);
        $('#cid').empty();
        <%--$("#cid").prepend("<option value='0'>${collectore.collector.ccode}</option>");   //为Select插入一个Option(第一个位置)--%>
        for (var i = 0; i < data.length; i++) {
            if('${collectore.collector.id}' == data[i].id) {
                $('#cid').append("<option selected value='" + data[i].id + "'>" + data[i].dis.name + "-" + data[i].ccode + "</option>");
            }else{
                $('#cid').append("<option value='" + data[i].id + "'>" + data[i].dis.name + "-" + data[i].ccode + "</option>");
            }
        }
    });


</script>
