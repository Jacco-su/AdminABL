<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div>
    <table class="mytable" align="center">
        <%--<tr>--%>
        <%--<td>排序:</td>--%>
        <%--<td>${authorizationa.workticker}--%>


        <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td>ddd:</td>
            <td>
                <%--${basePath}+'/${authorizationa.workticket}'--%>
                <%
                    String imgUrl;
                    //这个地址是你实际从数据库里取出来的,，如果怕照片显示不出来，加上绝对路径request.getContextPath()+"/"+imgUrl
                    imgUrl =“select
                    workticket
                    from
                    authorization”;
                    request
                    .
                    getContextPath
                    (
                    )
                    +
                    "/"
                    +
                    imgUrl
                    ;
                %>
                <img src="<%=request.getContextPath()+"/"+imgUrl %>" alt="images"/>


            </td>
        </tr>

    </table>
</div>