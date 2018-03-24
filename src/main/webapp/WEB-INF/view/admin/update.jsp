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
            width: 350,
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
        <form name="editForm" id="editForm" action="${basePath}/user/update" method="post">
            <table class="mytable" align="center">
                <tr>
                    <td>帐户:</td>
                    <td>
                        <input name="rdate" type="hidden" value="${user.rdate}"/>
                        <input name="name" readonly="readonly" class="easyui-validatebox" required="true"
                               value="${user.name}" size="40"/>
                        <input type="hidden" name="id" id="id" value="${user.id}"/>
                    </td>
                </tr>
                <tr>
                    <td>用户名:</td>
                    <td><input name="username" size="40" class="easyui-validatebox" required="true" validType="CHS"
                               value="${user.username}"/></td>
                </tr>
                <tr>
                    <td>区域:</td>
                    <td>
                        <input type="hidden" name="dept.id" id="deptId" value="${user.dept.id}"/>
                        <input size="40" readonly="readonly" class="easyui-validatebox" required="true" id="deptName"
                               value="${user.dept.name}"/>
                        <a class="easyui-linkbutton" onclick="$('#selectDept').window('open');">区域调动</a>
                    </td>
                </tr>
                <%--
                    <tr>
                        <td>绩效考核:</td>
                        <td>
                                        <select name="haskh" id="haskh">
                                                    <option value="1" <c:if test="${user.haskh==1}">selected</c:if>>参与</option>
                                                    <option value="0" <c:if test="${user.haskh==0}">selected</c:if>>不参与</option>
                                        </select>
                        </td>
                    </tr>--%>

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
                <%--<tr>
                    <td>排序序号:</td>
                    <td>
                        <select id="orderId" name="orderId" style="width:100px;">
                            <c:forEach var="x" begin="1" end="40" step="1">
                                <option value="${x}" <c:if test="${user.orderId==x}">selected</c:if>>${x}</option>
                            </c:forEach>
                        </select>

                    </td>
                </tr>--%>
                <tr>
                    <td>角色:</td>
                    <td>
                        <input name="roIdList" type="hidden"/>
                        <table align="center" width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                                <c:forEach items="${roles}" var="role" varStatus="s">
                                <td style="border: 0px;">
                                    <input name="roIdList" <c:if test="${role.roId == roIds}">checked</c:if>  type="radio" value="${role.roId}"/>${role.name }
                                </td>
                                <c:if test="${(s.index+1)%3==0}"></tr>
                            <tr></c:if>
                                </c:forEach>
                            </tr>
                        </table>
                    </td>
                </tr>

                <%--<tr>--%>
                <%--<td>区域调动:</td>--%>
                <%--<td>--%>
                <%--<div class="easyui-accordion" style="width: 500px;" border=false animate=false>--%>
                <%--<c:forEach items="${deptList}" var="dept" varStatus="s">--%>
                <%--<span><input name="deptIdList" type="checkbox" value="${dept.id}"/>${dept.name }</span>--%>
                <%--<c:if test="${(s.index+1)%5==0}"><br/></c:if>--%>
                <%--</c:forEach>--%>
                <%--</div>--%>
                <%--</td>--%>
                <%--</tr>--%>
            </table>
        </form>
    </div>
</div>