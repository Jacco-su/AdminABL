<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<script type="text/javascript">
    var roIds = "${roIds}";
    $.each(roIds.split(','), function (i, v) {
        $('input[type=checkbox][name=roIdList][value=' + v + ']').attr("checked", true);
    });
    var deptIds = "${deptIds}";
    $.each(deptIds.split(','), function (i, v) {
        $('input[type=checkbox][name=deptIdList][value=' + v + ']').attr("checked", true);
    });
    $(function () {

        $('#deptList').accordion({animate: false});
        $('#deptTree').tree({
            checkbox: false,
            url: '${basePath}/dept/getChildren',
            checkbox: false,
            onBeforeExpand: function (node, param) {
                $('#deptTree').tree('options').url = "${basePath}/dept/getChildren?parentId=" + node.id;
            }
        });

        $('#selectDept').window({
            title: '选择区域',
            width: 300,
            height: 500,
            closed: true,
            modal: true
        });
    });

    function selectDept() {
        var id = "";
        var show = "";
        var selections = $('#deptTree').tree('getSelected');
        if (selections) {
            id = selections.id;
            show = selections.text;
        }
        $('#deptId').val(id);
        $('#deptName').val(show);
        $('#selectDept').window('close');
    }
</script>
<div>
    <div>
        <form name="editForm" id="editForm" action="${basePath}/user/uupdate" method="post">
            <table class="mytable" align="center">

                <tr>
                    <td>姓名:</td>
                    <td>
                        <input name="rdate" type="hidden" value="${user.rdate}"/>
                        <input type="hidden"  name="dept.id" id="deptId" value="${user.dept.id}"/>
                        <input type="hidden" name="id"  id="id"  value="${user.id}"/>
                        <input name="username" size="40" class="easyui-validatebox" required="true" validType="CHS"
                               value="${user.username}"/></td>
                </tr>
                <%--<tr>--%>
                <%--<td>区域:</td>--%>
                <%--<td>--%>
                <%--<input type="hidden" name="dept.id" id="deptId" value="${user.dept.id}"/>--%>
                <%--<input size="40" readonly="readonly" class="easyui-validatebox" required="true" id="deptName"--%>
                <%--value="${user.dept.name}"/>--%>
                <%--<a class="easyui-linkbutton" onclick="$('#selectDept').window('open');">区域调动</a>--%>
                <%--</td>--%>
                <%--</tr>--%>

                <tr>
                    <td>手机:</td>
                    <td><input name="phone" size="40" class="easyui-validatebox" validType="mobile"
                               value="${user.phone}"/></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input name="email" size="40" class="easyui-validatebox" validType="email"
                               value="${user.email}"/></td>
                </tr>
               <%-- <tr>
                    <td>排序序号:</td>
                    <td>
                        <select id="orderId" name="orderId" style="width:100px;">
                            <c:forEach var="x" begin="1" end="40" step="1">
                                <option value="${x}" <c:if test="${user.orderId==x}">selected</c:if>>${x}</option>
                            </c:forEach>
                        </select>

                    </td>
                </tr>--%>

            </table>
        </form>
    </div>
    <div id="selectDept">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px;">
                <ul id="deptTree" style="margin-top: 10px;"></ul>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a class="easyui-linkbutton" onclick="selectDept();">确定</a>
                <a class="easyui-linkbutton" onclick="$('#selectDept').window('close');">关闭</a>
            </div>
        </div>
    </div>
</div>